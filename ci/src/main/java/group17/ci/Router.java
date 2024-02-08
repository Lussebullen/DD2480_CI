package group17.ci;

import java.io.File;
import java.util.Arrays;
import org.eclipse.jgit.api.Git;

import java.io.BufferedReader;
import java.io.InputStreamReader;
 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
 
@RestController
public class Router {
 
    @GetMapping("/") public String healthCheck()
    {
        return "healthy";
    }

    /**
     * Handles POST request to /logs and displays the full file names of all saved logs
     *
     * @param none
     * @return String containing full file names including extensions of all logs
     */
    @GetMapping("/logs") public String displayAllLogs()
    {
        // location for this method: \DD2480\DD2480_CIServer\DD2480_CI-main\ci

        File folder = new File("./src/main/resources/logs");
        File[] files = folder.listFiles();
        StringBuilder sb = new StringBuilder();
        for (File file : files) {
            sb.append(file.getName());
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Handles POST request to /logs/<commitId> and displays the contents of that commit log
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
            String fileNameTrimmed = fileName.substring(0, fileName.length() - 5); //remove .json file extension
            if (fileNameTrimmed.equals(commitId)) { 
                String content = Files.readString(Path.of(file.getPath()));
                return content;
            }
        }
        return "CommitId not found";
    }

    /*
     * Compile and test cloned branch attempt 1.0
     * Rough outline with suggestion on how to deal with branch compilation/testing with
     * additional logging by using processess together with shell script
     */
    private void compileAndTestBranch(String commitHash) 
    {
        String[] command = { "./compileTestLog.sh", commitHash };
        try {
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            BufferedReader bufferReader = new BufferedReader(new InputStreamReader( process.getInputStream()));

            // No null check as script always echoes but might be worth adding nonetheless
            String processOutputMessage = bufferReader.readLine(); 

            if (processOutputMessage.equals("<FORMAT IF ALL GOOD>")) {
                //do stuff
            } else if (processOutputMessage.equals("<FORMAT IF COMPILE FAILURE>")) {
                //do other stuff
            } else if (processOutputMessage.equals("<FORMAT IF TEST FAILS>")) {
                //do other stuff
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     * Clone repo attempt 1.0.
     * Current issues: If destination directory exists from prior and is not empty 
     *                 then clone fails. Cleaner would be to pull, worth checking out 
     *                 but documentation is broken?
     *
     * Note:           Currently returns local repo destination but this is just a preliminary
     *                 setup that might be changed. Idea is to give method handling compilation this location
     *                  so it can find the pom.xml file to call maven.
     */
    private String cloneRepo()
    {
        String destination = "latestRepoClone"; //Relative to working directory
        try {
            Git.cloneRepository()
               .setURI("https://github.com/Lussebullen/DD2480_CI.git")
               .setDirectory(new File(destination)) 
               .setBranchesToClone(Arrays.asList("refs/heads/assessment"))
               .setBranch("refs/heads/assessment")
               .call();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return destination;
    }

    /**
     * Handles POST request to /github-webhook and works as a receiver of GitHub pull request webhooks events
     *
     * @param payload The data representing the pull request payloada GitHub sent us via webhooks.
     */
    @PostMapping("/github-webhook")
    public void githubReceiver(@RequestBody PullRequestPayload payload) {

        try {
            String prettyJson = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(payload);
            System.out.println(prettyJson); // Print the pretty printed JSON
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
