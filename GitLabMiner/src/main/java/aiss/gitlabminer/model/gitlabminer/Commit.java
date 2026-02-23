package aiss.gitlabminer.model.gitlabminer;

import com.fasterxml.jackson.annotation.JsonProperty;

// https://gitlab.com/api/v4/projects/gitlab-org%2Fgitlab/repository/commits
public class Commit {

    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("message")
    private String message;

    @JsonProperty("author_name")
    private String author_name;

    @JsonProperty("author_email")
    private String author_email;

    @JsonProperty("authored_date")
    private String authored_date;

    @JsonProperty("web_url")
    private String web_url;

    public Commit() {}

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getWeb_url() {
        return web_url;
    }
    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public String getAuthored_date() {
        return authored_date;
    }
    public void setAuthored_date(String authored_date) {
        this.authored_date = authored_date;
    }

    public String getAuthor_email() {
        return author_email;
    }
    public void setAuthor_email(String author_email) {
        this.author_email = author_email;
    }

    public String getAuthor_name() {
        return author_name;
    }
    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return "Commit{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message.length() + '\'' +
                ", author_name='" + author_name + '\'' +
                ", author_email='" + author_email + '\'' +
                ", authored_date='" + authored_date + '\'' +
                ", web_url='" + web_url + '\'' +
                '}';
    }
}
