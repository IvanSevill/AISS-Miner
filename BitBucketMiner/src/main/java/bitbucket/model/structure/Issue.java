package bitbucket.model.structure;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Issue {
    @JsonProperty("id")          private String id;
    @JsonProperty("title")       private String title;
    @JsonProperty("content")     private Content content;
    @JsonProperty("state")       private String state;
    @JsonProperty("created_on")  private String createdAt;
    @JsonProperty("updated_on")  private String updatedAt;
    @JsonProperty("votes")       private Integer totalVotes;
    @JsonProperty("reporter")    private User author;
    @JsonProperty("assignee")    private User assignee;
    @JsonProperty("links")       private Links links;

    private List<Comment> comments;
    private String closedAt;
    private List<String> labelNames;

    public List<String> getLabelNames() {return null;}

    public String getClosedAt() {return null;}
    public void setClosedAt(String closedAt) {this.closedAt = closedAt;}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return content.getRaw(); }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    public Integer getTotalVotes() { return totalVotes; }
    public void setTotalVotes(Integer votes) { this.totalVotes = votes; }

    public User getAuthor() { return author; }
    public void setAuthor(User reporter) { this.author = reporter; }

    public User getAssignee() { return assignee; }
    public void setAssignee(User assignee) { this.assignee = assignee; }

    public Links getLinks() { return links; }
    public void setLinks(Links links) { this.links = links; }

    public List<Comment> getComments() { return comments; }
    public void setComments(List<Comment> comments) { this.comments = comments; }

    @Override
    public String toString() {
        return "Issue{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content=" + content +
                ", state='" + state + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", totalVotes=" + totalVotes +
                ", author=" + author +
                ", assignee=" + assignee +
                ", links=" + links +
                ", comments=" + comments +
                ", closedAt='" + closedAt + '\'' +
                ", labelNames=" + labelNames +
                '}';
    }
}
