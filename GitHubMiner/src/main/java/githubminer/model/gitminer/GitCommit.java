package githubminer.model.gitminer;

public class GitCommit {

    // Properties
    private String id;
    private String title;
    private String message;
    private String author_name;
    private String author_email;
    private String authored_date;
    private String web_url;

    public GitCommit() {}

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

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthorName() {
        return author_name;
    }
    public void setAuthorName(String authorName) {
        this.author_name = authorName;
    }

    public String getAuthorEmail() {
        return author_email;
    }
    public void setAuthorEmail(String authorEmail) {
        this.author_email = authorEmail;
    }

    public String getAuthoredDate() {
        return authored_date;
    }
    public void setAuthoredDate(String authoredDate) {
        this.authored_date = authoredDate;
    }

    public String getWebUrl() {
        return web_url;
    }
    public void setWebUrl(String webUrl) {
        this.web_url = webUrl;
    }

    @Override
    public String toString() {
        return "\n\tCommit{" +
                "\n\t\tid='" + getId() + '\'' +
                ", \n\t\ttitle='" + getTitle() + '\'' +
                ", \n\t\tmessage (lenght)='" + (getMessage() != null ? String.valueOf(getMessage().length()) : null) + '\'' +
                ", \n\t\tauthor_date='" + getAuthoredDate() + '\'' +
                ", \n\t\tauthor_email='" + getAuthorEmail() + '\'' +
                ", \n\t\tcommitter_name='" + getAuthorName() + '\'' +
                ", \n\t\tweb_url='" + getWebUrl() + '\'' +
                '}';
    }
}
