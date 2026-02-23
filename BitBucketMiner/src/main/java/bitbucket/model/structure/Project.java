package bitbucket.model.structure;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Project {
    @JsonProperty("uuid")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("links")
    private Links links;

    private List<Issue> issues;
    private List<Commit> commits;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Links getLinks() { return links; }
    public void setLinks(Links links) { this.links = links; }

    public String getWebUrl() {
        if (links != null && links.getHtml() != null) {
            return links.getHtml().getHref();
        }
        return null;
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

    public List<Commit> getCommits() {
        return commits;
    }

    public void setCommits(List<Commit> commits) {
        this.commits = commits;
    }

    @Override
    public String toString() {
        return "Project{" +
                "\nid='" + id + '\'' +
                ", \nname='" + name + '\'' +
                ", \nlinks=" + links +
                ", \nissues=" + issues +
                ",\n commits=" + commits +
                '}';
    }
}
