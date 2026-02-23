package aiss.gitlabminer.model.gitlabminer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Issue {

    @JsonProperty("id")
    private String id;

    @JsonProperty("iid")
    private String iid;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("state")
    private String state;

    @JsonProperty("created_at")
    private String created_at;

    @JsonProperty("updated_at")
    private String updated_at;

    @JsonProperty("closed_at")
    private String closed_at;

    @JsonProperty("labels")
    private List<String> labels;

    @JsonProperty("upvotes")
    private Integer upvotes;

    @JsonProperty("downvotes")
    private Integer downvotes;

    @JsonIgnore
    private List<Comment> comments;

    @JsonProperty("assignee")
    private User assignee;

    @JsonProperty("author")
    private User author;

    public Issue() {}
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getIid() {
        return iid;
    }
    public void setIid(String iid) {
        this.iid = iid;
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
    public void setCreatedAt(String created_at) {
        this.created_at = created_at;
    }
    
    public String getUpdatedAt() {
        return updated_at;
    }
    public void setUpdatedAt(String updated_at) {
        this.updated_at = updated_at;
    }
    
    public String getClosedAt() {
        return closed_at;
    }
    public void setClosedAt(String closed_at) {
        this.closed_at = closed_at;
    }
    
    public List<String> getLabelNames() {
        return labels;
    }
    public void setLabelNames(List<String> labels) {
        this.labels = labels;
    }
    
    public Integer getTotalVotes() {
        return upvotes + downvotes;
    }
    public void setTotalVotes(Integer upvotes, Integer downvotes) {
        this.upvotes = upvotes;
        this.downvotes = downvotes;
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
        return "Issue{" +
                "id='" + id + '\'' +
                ", iid='" + iid + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description.length() + '\'' +
                ", state='" + state + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", closed_at='" + closed_at + '\'' +
                ", labels=" + labels +
                ", votes=" + getTotalVotes() +
                ", comments=" + comments +
                ", assignee=" + assignee +
                ", author=" + author +
                '}';
    }
}
