package group17.ci;
 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
 
@RestController
public class Router {
 
    @GetMapping("/") public String healthCheck()
    {
        return "healthy";
    }

    @GetMapping("/logs") public String displayAllLogs()
    {
        return "All logs";
    }

    // Adding a variable endpoint to capture a log ID
    @GetMapping("/logs/{commitId}") 
    public String displayLog(@PathVariable String commitId) {
        // You can use the logId variable in your method to fetch and return specific log details

        System.out.println("Hello someone just visited this link: probably github sending something");

        return "Commit ID: " + commitId;
    }
}