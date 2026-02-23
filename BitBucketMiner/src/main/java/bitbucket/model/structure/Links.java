package bitbucket.model.structure;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Links {
    @JsonProperty("self")    private Link self;
    @JsonProperty("html")    private Link html;
    @JsonProperty("avatar")  private Link avatar;
    @JsonProperty("comments")private Link comments;

    public Link getSelf() { return self; }
    public void setSelf(Link self) { this.self = self; }

    public Link getHtml() { return html; }
    public void setHtml(Link html) { this.html = html; }

    public Link getAvatar() { return avatar; }
    public void setAvatar(Link avatar) { this.avatar = avatar; }

    public Link getComments() { return comments; }
    public void setComments(Link comments) { this.comments = comments; }

    @Override
    public String toString() {
        return "Links{" +
                "self=" + self +
                ", html=" + html +
                ", avatar=" + avatar +
                ", comments=" + comments +
                '}';
    }
}
