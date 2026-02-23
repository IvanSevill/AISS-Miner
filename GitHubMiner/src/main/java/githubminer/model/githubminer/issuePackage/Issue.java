package githubminer.model.githubminer.issuePackage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import githubminer.model.githubminer.Comment;
import githubminer.model.githubminer.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Issue {
    @JsonProperty("number")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("body")
    private String description;

    @JsonProperty("state")
    private String state;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("closed_at")
    private String closedAt;

    @JsonProperty("labels")
    private List<Label> labels;

    @JsonProperty("reactions")
    private Vote votes;

    @JsonProperty("user")
    private User author;

    @JsonProperty("assignee")
    private User assignee;

    @JsonProperty("comments_url")
    private String commentsUrl;

    @JsonIgnore
    private List<Comment> comments;

    public Issue(String id, String title, String description, String state, String createdAt, String updatedAt, String closedAt, List<Label> labels, Vote votes, User author, User assignee) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.state = state;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.closedAt = closedAt;
        this.labels = labels;
        this.votes = votes;
        this.author = author;
        this.assignee = assignee;
        this.comments = new ArrayList<>();
    }

    public Issue() {

    }

    // getters & setters

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
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getClosedAt() {
        return closedAt;
    }
    public void setClosedAt(String closedAt) {
        this.closedAt = closedAt;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    // Extra: si quieres solo los nombres de los labels
    public List<String> getLabelNames() {
        return labels != null ? labels.stream().map(Label::getName).collect(Collectors.toList()) : List.of();
    }

    public Vote getVotes() {
        return votes;
    }
    public void setVotes(Vote votes) {
        this.votes = votes;
    }

    public Integer getTotalVotes() {
        return votes != null ? votes.getTotalCount() : null;
    }

    public String getCommentsUrl() {
        return commentsUrl;
    }
    public void setCommentsUrl(String commentsUrl) {
        this.commentsUrl = commentsUrl;
    }

    public List<Comment> getComments() {
        return comments;
    }
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User getAssignee() {
        return assignee;
    }
    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public User getAuthor() {
        return author;
    }
    public void setAuthor(User author) {
        this.author = author;
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
                ", \n\t\tvotes=" + getTotalVotes() +
                ", \n\t\tauthor=" + getAuthor() +
                ", \n\t\tassignee=" + getAssignee() +
                ", \n\t\tcomments=" + getComments() +
                "\n\t}";
    }


}