package bitbucket.model.structure;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Commit {
    @JsonProperty("hash")
    private String id;
    @JsonProperty("date")
    private String authoredDate;
    @JsonProperty("message")
    private String message;
    @JsonProperty("author")
    private Author author;
    @JsonProperty("links")
    private Links links;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() {return message; }
    public String getAuthoredDate() { return authoredDate; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getAuthorName() { return author.getUser().getUsername(); }

    public String getAuthorEmail() {
        String raw = author.getRaw();
        if (raw == null) {
            return null;
        }
        int start = raw.indexOf('<');
        int end = raw.indexOf('>');
        if (start >= 0 && end > start) {
            return raw.substring(start + 1, end).trim();
        }
        return null;
    }
    public String getUrl() { return links.getSelf().getHref(); }

    @Override
    public String toString() {
        return "Commit{" +
                "id='" + id + '\'' +
                ", authoredDate='" + authoredDate + '\'' +
                ", message='" + message + '\'' +
                ", author=" + author +
                ", links=" + links +
                '}';
    }

    public static class Author {
        @JsonProperty("raw")
        private String raw;
        @JsonProperty("user")
        private User user;

        public String getRaw() { return raw; }
        public void setRaw(String raw) { this.raw = raw; }

        public User getUser() { return user; }
        public void setUser(User user) { this.user = user; }

        @Override
        public String toString() {
            return "Author{" +
                    "raw='" + raw + '\'' +
                    ", user=" + user +
                    '}';
        }
    }
}
