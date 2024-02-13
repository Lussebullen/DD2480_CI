package group17.ci;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
 
@RestController
public class Router {

    enum CIBuildStatus {
        GOOD,
        BAD
    }
    
    /**
     * Handles GET request to / and displays "healthy" if endpoint is accessible
     *
     * @param none
     * @return String "healthy"
     */
    @GetMapping("/") public String healthCheck()
    {
        return "healthy";
    }

    /**
     * Handles GET request to /logs and displays the commit SHA of all saved logs
     *
     * @param none
     * @return String containing git SHA of commit with a link to the specific log of that commit.
     */
    @GetMapping("/logs") public String displayAllLogs()
    {
        File folder = new File("./src/main/resources/logs");
        File[] files = folder.listFiles();
        StringBuilder sb = new StringBuilder();
        //Get sorted log files and display commit SHA and status
        try{
        Arrays.sort(files, Comparator.comparingLong(File::lastModified));
            for (File file : files) {
                String commitSha = file.getName().substring(0, file.getName().length() - 4);
                String htmllink = String.join("","<a href=\"","/logs/",commitSha,"\">",commitSha,"</a>");
                sb.append(htmllink);
                sb.append(getStatus(file));
                sb.append("<br>");
            }
            return sb.toString();
        }
        catch (Exception e){
            return "No logs found";
        }
    }

    /**
     * Handles GET request to /logs/<commitId> and displays the contents of that commit log
     *
     * @param String.commitId
     * @return String containing contents of the requested log file
     */
    @GetMapping("/logs/{commitId}") 
    public String displayLog(@PathVariable String commitId) throws IOException{
        // You can use the logId variable in your method to fetch and return specific log details
        File folder = new File("./src/main/resources/logs/");
        File[] files = folder.listFiles();
        for (File file : files) {
            String fileName = file.getName();
            String fileNameTrimmed = fileName.substring(0, fileName.length() - 4); //remove .log file extension
            if (fileNameTrimmed.equals(commitId)) { 
                String content = Files.readString(Path.of(file.getPath()));
                return content;
            }
        }
        return "CommitId not found";
    }

    /**
     * Compile, build/test and log assessment branch on CI server using a shell script executed using a process.
     *
     * @param   commitHash  Commit-SHA as a string format. Should not start with #.
     * 
     * return   CIBuildStatus.GOOD if no failures occured, BAD variant otherwise.
     */
    private CIBuildStatus compileAndTestBranch(String commitHash, String cloneURL, String branchName) 
    {
        int exitStatus = 1;
        String[] command = { "./compileTestLog.sh", commitHash , cloneURL, branchName};
        try {
            Process process = Runtime.getRuntime().exec(command);
            exitStatus = process.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return exitStatus == 1 ? CIBuildStatus.BAD : CIBuildStatus.GOOD;
    }


    /**
     * Handles POST request to /github-webhook and works as a receiver of GitHub pull request webhooks events
     *
     * @param payload The data representing the pull request payloada GitHub sent us via webhooks.
     */
    @PostMapping("/github-webhook")
    public void githubReceiver(@RequestBody PushPayload payload) {
        // Start time
        long startTime = System.nanoTime();

        String branchName = payload.ref.substring("refs/heads/".length());
        if (!branchName.equals("assessment")) {
            return;
        }

        // Send pending commit status
        sendCommitStatus(payload, new CommitStatus("pending", "building and testing...", "ciserver/build-test", "https://dd2480-ciserver.asirago.xyz/logs/" + payload.head_commit.id));

        // Compile and test the project
        CIBuildStatus buildStatus = compileAndTestBranch(payload.head_commit.id, payload.repository.clone_url, branchName);

        // Elapsed time
        int elapsedTime = (int) ((System.nanoTime() - startTime) / 1_000_000_000.0);

        // Send SUCCESS commit status
        if (buildStatus == CIBuildStatus.GOOD) {
            sendCommitStatus(payload, new CommitStatus("success", "Successful in " + elapsedTime + "s", "ciserver/build-test", "https://dd2480-ciserver.asirago.xyz/logs/" + payload.head_commit.id));
        }

        // Send FAILURE commit status
        if (buildStatus == CIBuildStatus.BAD) {
            sendCommitStatus(payload, new CommitStatus("failure", "Failing after " + (int) elapsedTime / 1_000_000_000.0 + "s", "ciserver/build-test", "https://dd2480-ciserver.asirago.xyz/logs/" + payload.head_commit.id));
        }
    }

    /**
     * Sends a POST request to set commit status on GitHub for certain commit
     * using the provided PushPayload and CommitStatus objects
     *
     * @param payload contains information about the repository and commit
     * @param status the status object to e sent to GitHub
     */
    public void sendCommitStatus(PushPayload payload, CommitStatus status) {

        String apiUrl = "https://api.github.com/repos/" + payload.repository.full_name + "/statuses/" + payload.head_commit.id;

        // Add header to POST request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "application/vnd.github+json");
        headers.set("Authorization","Bearer " + System.getenv("ACCESS_TOKEN"));
        headers.set("X-GitHub-Api-Version", "2022-11-28");

        // Convert CommitStatus object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String statusJson = "";
        try {
            statusJson = objectMapper.writeValueAsString(status);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add  JSON body and headers to request
        HttpEntity<String> request= new HttpEntity<>(statusJson, headers);

        // Send POST request
        ResponseEntity<Void> response = new RestTemplate().postForEntity(apiUrl, request, Void.class);

        System.out.println("Sent commit status payload: " + statusJson);
        System.out.println(response);
    }

    /**
     * Checks a log file for the status of the build and test and returns a string with the status
     *
     * @param File the log file containing build and test information
     * @returns a string specifying log status result.
     */
    private static String getStatus(File file) {
        StringBuilder sb = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            int lineCount = 0;
            //If file contains "BUILD SUCCESS" x2 -> success, "BUILD SUCCESS" x1 -> failed test, else -> build failed
            int buildSuccesses = 0;
            String line;
            while((line = br.readLine()) != null){
                //Append the date to the status
                if (lineCount == 0){
                    sb.append(" [");
                    sb.append(line.substring(6));
                    sb.append("] ");
                }
                else if(line.contains("BUILD FAILURE"))
                {
                    sb.append(" [BUILD FAILURE]");
                    br.close();
                    return sb.toString();
                }
                else if(line.contains("BUILD SUCCESS"))
                {
                    buildSuccesses++;
                    if (buildSuccesses == 2){
                        sb.append(" [BUILD SUCCESS]");
                        br.close();
                        return sb.toString();
                    }
                }
                lineCount++;
            }
            sb.append("[TESTS FAILED]");
            br.close();
            return sb.toString();
        }
        catch (Exception e){
            return " [Could not read file]";
        }
    }

    /**
     * CommitStatus contains all the necessary information to be sent to GitHub
     *
     */
    class CommitStatus {
        public String state;
        public String target_url;
        public String description;
        public String context;

        public CommitStatus(String state, String description, String context, String target_url) {
            this.state = state;
            this.description = description;
            this.context = context;
            this.target_url = target_url;
        }

    }
}
