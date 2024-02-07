package group17.ci;
 
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

    // Adding a variable endpoint to capture a log ID
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
