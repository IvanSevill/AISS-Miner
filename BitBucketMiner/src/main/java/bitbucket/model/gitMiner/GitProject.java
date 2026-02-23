package bitbucket.model.gitMiner;

import java.util.List;

public class GitProject {
    private String id;
    private String name;
    private String webUrl;
    private List<GitCommit> commits;
    private List<GitIssue> issues;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getWebUrl() { return webUrl; }
    public void setWebUrl(String webUrl) { this.webUrl = webUrl; }

    public List<GitCommit> getCommits() { return commits; }
    public void setCommits(List<GitCommit> commits) { this.commits = commits; }

    public List<GitIssue> getIssues() { return issues; }
    public void setIssues(List<GitIssue> issues) { this.issues = issues; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("BBProject{")
                .append("\nid='").append(id).append('\'')
                .append(",\n name='").append(name).append('\'')
                .append(",\n webUrl='").append(webUrl).append('\'');
        sb.append(",\n commitsCount=").append(commits.size());
        List<GitCommit> fewCommits = commits.stream().limit(2).toList();
        sb.append(",\n commitsPreview=").append(fewCommits);
        sb.append(",\n issuesCount=").append(issues.size());
        List<GitIssue> fewIssues = issues.stream().limit(2).toList();
        sb.append(",\n issuesPreview=").append(fewIssues);
        sb.append("\n}");
        return sb.toString();
    }

}
