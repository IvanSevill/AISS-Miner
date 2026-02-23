package bitbucket.model.structure;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Comment {
    @JsonProperty("id")
    private String id;
    @JsonProperty("created_on")
    private String createdAt;
    @JsonProperty("updated_on")
    private String updatedAt;
    @JsonProperty("content")
    private Content content;
    @JsonProperty("user")
    private User user;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCreatedAt() { return createdAt; }

    public String getUpdatedAt() { return updatedAt; }

    public String getBody() { return content.getRaw(); }

    public User getAuthor() { return user; }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", content=" + content +
                ", user=" + user +
                '}';
    }
}
