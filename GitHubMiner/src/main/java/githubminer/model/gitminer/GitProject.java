
package githubminer.model.gitminer;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;


public class GitProject {

    // Properties
    public String id;
    public String name;
    public String web_url;

    // Relations
    private List<GitCommit> gitCommits;
    private List<GitIssue> gitIssues;

    public GitProject() {
        gitCommits = new ArrayList<>();
        gitIssues = new ArrayList<>();
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getWebUrl() {
        return web_url;
    }
    public void setWebUrl(String webUrl) {
        this.web_url = webUrl;
    }

    public List<GitCommit> getCommits() {
        return gitCommits;
    }
    public void setCommits(List<GitCommit> gitCommits) {
        this.gitCommits = gitCommits;
    }

    public List<GitIssue> getIssues() {
        return gitIssues;
    }
    public void setIssues(List<GitIssue> gitIssues) {
        this.gitIssues = gitIssues;
    }

    @Override
    public String toString() {
        return "Project{" +
                "\nid='" + getId() + '\'' +
                ",\nname='" + getName() + '\'' +
                ",\nweb_url='" + getWebUrl() + '\'' +
                ",\ncommits=" + getCommits() +
                ",\nissues=" + getIssues() +
                '}';
    }
}
