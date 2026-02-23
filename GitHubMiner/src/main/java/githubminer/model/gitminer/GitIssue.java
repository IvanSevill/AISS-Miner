
package githubminer.model.gitminer;

import java.util.List;

public class GitIssue {

    // Properties
    private String id;
    private String title;
    private String description;
    private String state;
    private String created_at;
    private String updated_at;
    private String closed_at;
    private List<String> labels;
    private Integer votes;

    // Relations
    private GitUser author;
    private GitUser assignee;
    private List<GitComment> gitComments;

    public GitIssue() {
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    public String getCreatedAt() {
        return created_at;
    }
    public void setCreatedAt(String createdAt) {
        this.created_at = createdAt;
    }

    public String getUpdatedAt() {
        return updated_at;
    }
    public void setUpdatedAt(String updatedAt) {
        this.updated_at = updatedAt;
    }

    public String getClosedAt() {
        return closed_at;
    }
    public void setClosedAt(String closedAt) {
        this.closed_at = closedAt;
    }

    public List<String> getLabels() {
        return labels;
    }
    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public GitUser getAuthor() {
        return author;
    }
    public void setAuthor(GitUser author) {
        this.author = author;
    }

    public GitUser getAssignee() {
        return assignee;
    }
    public void setAssignee(GitUser assignee) {
        this.assignee = assignee;
    }

    public Integer getVotes() {
        return votes;
    }
    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public List<GitComment> getComments() {
        return gitComments;
    }
    public void setComments(List<GitComment> gitComments) {
        this.gitComments = gitComments;
    }

    @Override
    public String toString() {
        return "\n\tIssue{" +
                " \n\t\tid='" + getId() + '\'' +
                ", \n\t\ttitle='" + getTitle() + '\'' +
                ", \n\t\tdescription (lenght)='" + (getDescription() != null ? String.valueOf(getDescription().length()) : null) + '\'' +
                ", \n\t\tstate='" + getState() + '\'' +
                ", \n\t\tcreatedAt='" + getCreatedAt() + '\'' +
                ", \n\t\tupdatedAt='" + getUpdatedAt() + '\'' +
                ", \n\t\tclosedAt='" + getCreatedAt() + '\'' +
                ", \n\t\tlabels=" + getLabels() +
                ", \n\t\tvotes=" + getVotes() +
                ", \n\t\tauthor=" + getAuthor() +
                ", \n\t\tassignee=" + getAssignee() +
                ", \n\t\tcomments=" + getComments() +
                "\n\t}";
    }



}
