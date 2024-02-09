package group17.ci;

import java.util.ArrayList;

/**
 * This class contains the Payload structure for GitHub webhook pull request events. It is used to serialize the json fields that are needed. 
 * 
 * @author Group 17
 * @version 1.0.0
 */
public class PushPayload {
    public String ref;
    public String before;
    public String after;
    public HeadCommit head_commit;
    public Repo repository;
    
}

/**
 * This class contains repository info and is nested inside PullRequest
 */
class Repo {
    public String full_name;
    public String ssh_url; // ssh url e.g. git@github.com:Lussebullen/DD2480_DECIDE.git
    public String clone_url; // clone url e.g. https://github.com/asirago/GitHubActionsTest.git

}

/**
 * This class contains info about HEAD and is nested inside PullRequest
 */
class HeadCommit {
    public String id; 
    public String message;
    public String timestamp;
    public ArrayList<String> added;
    public ArrayList<String> modified;
    public ArrayList<String> removed;
}