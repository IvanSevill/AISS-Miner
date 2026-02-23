package githubminer.model.githubminer.commitPackage;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Commit {

    @JsonProperty("sha")
    private String id;

    @JsonProperty("commit")
    private CommitDetail commitDetail;

    @JsonProperty("html_url")
    private String web_url;

    // getters & setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public Commit() {
    }

    public Commit(String id, CommitDetail commitDetail, String web_url) {
        this.id = id;
        this.commitDetail = commitDetail;
        this.web_url = web_url;
    }

    public String getUrl(){
        return web_url;
    }

    public String getMessage(){
        return commitDetail.getMessage();
    }

    public String getTitle(){
        return commitDetail.getTitle();
    }

    public String getAuthoredDate(){
        return commitDetail.getCommitAuthor().getDate();
    }

    public String getAuthorEmail(){
        return commitDetail.getCommitAuthor().getEmail();
    }

    public String getAuthorName(){
        return commitDetail.getCommitAuthor().getName();
    }

    @Override
    public String toString() {
        return "\n\tCommit{" +
                "\n\t\tid='" + getId() + '\'' +
                ", \n\t\ttitle='" + getTitle() + '\'' +
                ", \n\t\tmessage (lenght)='" + (getMessage() != null ? String.valueOf(getMessage().length()) : null) + '\'' +
                ", \n\t\tauthorDate='" + getAuthoredDate() + '\'' +
                ", \n\t\tauthorEmail='" + getAuthorEmail() + '\'' +
                ", \n\t\tcommitterName='" + getAuthorName() + '\'' +
                ", \n\t\twebUrl='" + getUrl() + '\'' +
                '}';
    }
}