package bitbucket.model.gitMiner;

import java.util.List;

public class GitIssue {
    private String id;
    private String title;
    private String description;
    private String state;
    private String createdAt;
    private String updatedAt;
    private String closedAt;
    private List<String> labels;
    private Integer votes;
    private GitUser author;
    private GitUser assignee;
    private List<GitComment> comments;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    public String getClosedAt() { return closedAt; }
    public void setClosedAt(String closedAt) { this.closedAt = closedAt; }

    public List<String> getLabels() { return labels; }
    public void setLabels(List<String> labels) { this.labels = labels; }

    public Integer getVotes() { return votes; }
    public void setVotes(Integer votes) { this.votes = votes; }

    public GitUser getAuthor() { return author; }
    public void setAuthor(GitUser author) { this.author = author; }

    public GitUser getAssignee() { return assignee; }
    public void setAssignee(GitUser assignee) { this.assignee = assignee; }

    public List<GitComment> getComments() { return comments; }
    public void setComments(List<GitComment> comments) { this.comments = comments; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("BBIssue{")
                .append("id='").append(id).append('\'')
                .append(", title='").append(title).append('\'')
                .append(", state='").append(state).append('\'');

        sb.append(", commentsCount=").append(comments.size());
        List<GitComment> fewComments = comments.stream().limit(3).toList();
        sb.append(", commentsPreview=").append(fewComments);
        sb.append('}');
        return sb.toString();
    }

}
