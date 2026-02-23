package githubminer.model.githubminer.commitPackage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommitDetail {

    @JsonProperty("author")
    private CommitAuthor commitAuthor;

    @JsonProperty("message")
    private String message;


    public CommitDetail( CommitAuthor commitAuthor, String message) {
        this.commitAuthor = commitAuthor;
        this.message = message;
    }

    public CommitAuthor getCommitAuthor() {
        return commitAuthor;
    }
    public void setCommitAuthor(CommitAuthor commitAuthor) {
        this.commitAuthor = commitAuthor;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    // Se define como titulo de un commit a la primera linea del mensaje de commit (https://midu.dev/buenas-practicas-escribir-commits-git/)
    public String getTitle() {
        if (message == null) return null;
        int newlineIndex = message.indexOf('\n');
        return newlineIndex == -1 ? message : message.substring(0, newlineIndex);
    }
}
