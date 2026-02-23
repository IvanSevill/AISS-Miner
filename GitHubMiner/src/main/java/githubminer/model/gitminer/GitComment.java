
package githubminer.model.gitminer;

public class GitComment {

    // Properties
    private String id;
    private String body;
    private String created_at;
    private String updated_at;

    // Relations
    private GitUser author;

    public GitComment() {
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }

    public GitUser getAuthor() {
        return author;
    }
    public void setAuthor(GitUser author) {
        this.author = author;
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

    @Override
    public String toString() {
        return "\n\t\t\tComment{" +
                "\n\t\t\t\tid='" + getId() + '\'' +
                ",\n\t\t\t\tbody='" + (getBody().length()>30 ? getBody().substring(0,30)+"…":getBody()) + '\''+
                ",\n\t\t\t\tcreatedAt='" + getCreatedAt() + '\'' +
                ",\n\t\t\t\tupdatedAt='" + getUpdatedAt() + '\'' +
                ",\n\t\t\t\tauthor=" + getAuthor() +
                '}';
    }

}
