package group17.ci;

/**
 * This class contains the Payload structure for GitHub webhook pull request events. It is used to serialize the json fields that are needed. 
 * 
 * @author Group 17
 * @version 1.0.0
 */
public class PullRequestPayload {
    public String action;
    public String number;
    public PullRequest pull_request;
    
}

/**
 * This class contains all information regarding the pull request and is neted inside PullRequestPayload
 *
 * */
class PullRequest {
    public String html_url;
    public String title;
    public String body;
    public User user;
    public String created_at;
    public String updated_at;
    public Head head;
    public Repo repo;
}

/**
 * This class contains info about HEAD and is nested inside PullRequest
 */
class Head {
    public String label; // login:branch e.g. asirago:issue/58
    public String ref; // branch e.g. issue/58
    public String sha; // commit hash e.g. 5dc964a2730e153208e09ac242bcd40c919374d2
}


/**
 * This class contains User information and is nested inside PullRequest
 */
class User {
    public String login;
    public String html_url;
}
