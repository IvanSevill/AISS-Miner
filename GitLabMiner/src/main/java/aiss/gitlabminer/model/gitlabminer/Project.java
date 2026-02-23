package aiss.gitlabminer.model.gitlabminer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Project {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("http_url_to_repo")
    private String web_url;

    private List<Commit> commits;
    private List<Issue> issues;

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
    public void setWebUrl(String web_url) {
        this.web_url = web_url;
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

    @Override
    public String toString() {
        return "\nProject{" +
                "\nid='" + id + '\'' +
                ",\nname='" + name + '\'' +
                ",\nweb_url='" + web_url + '\'' +
                ",\ncommits=" + commits +
                ",\nissues=" + issues +
                '}';
    }
}
