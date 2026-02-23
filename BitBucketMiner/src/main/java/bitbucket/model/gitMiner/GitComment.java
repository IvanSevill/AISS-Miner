package bitbucket.model.gitMiner;

public class GitComment {
    private String id;
    private String body;
    private String createdAt;
    private String updatedAt;
    private GitUser author;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    public GitUser getAuthor() { return author; }
    public void setAuthor(GitUser author) { this.author = author; }

    @Override
    public String toString() {
        return "BBComment{" +
                "id='" + id + '\'' +
                ", body='" + (body != null ? body.substring(0, Math.min(30, body.length())) + "…" : null) + '\'' +
                ", author=" + author +
                '}';
    }
}
