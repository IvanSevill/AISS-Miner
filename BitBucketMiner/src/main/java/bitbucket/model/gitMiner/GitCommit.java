package bitbucket.model.gitMiner;

public class GitCommit {
    private String id;
    private String title;
    private String message;
    private String authorName;
    private String authorEmail;
    private String authoredDate;
    private String webUrl;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public String getAuthorEmail() { return authorEmail; }
    public void setAuthorEmail(String authorEmail) { this.authorEmail = authorEmail; }

    public String getAuthoredDate() { return authoredDate; }
    public void setAuthoredDate(String authoredDate) { this.authoredDate = authoredDate; }

    public String getWebUrl() { return webUrl; }
    public void setWebUrl(String webUrl) { this.webUrl = webUrl; }

    @Override
    public String toString() {
        return "BBCommit{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", message='" + (message != null ? message.substring(0, Math.min(30, message.length())) + "…" : null) + '\'' +
                ", authorName='" + authorName + '\'' +
                ", authorEmail='" + authorEmail + '\'' +
                ", authoredDate='" + authoredDate + '\'' +
                ", webUrl='" + webUrl + '\'' +
                '}';
    }
}
