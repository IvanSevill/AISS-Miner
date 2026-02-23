package githubminer.model.githubminer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import githubminer.model.githubminer.commitPackage.Commit;
import githubminer.model.githubminer.issuePackage.Issue;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Project {
    private String id;
    private String name;
    private List<Commit> commits;
    private List<Issue> issues;

    public Project(String id, String name, String web_url,String url) {
        this.id = id;
        this.name = name;
        this.commits = new ArrayList<>();
        this.issues = new ArrayList<>();
        this.web_url = web_url;
        this.url = url;
    }

    @JsonProperty("html_url")
    private String web_url;

    @JsonProperty("url")
    private String url;


    // getters & setters

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
    public void setWeb_url(String url) {
        this.web_url = url;
    }

    public List<Commit> getCommits() {
        return commits;
    }
    public void setCommits(List<Commit> commits) {
        this.commits = commits;
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

    public String getUrl() {
        return url;
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
