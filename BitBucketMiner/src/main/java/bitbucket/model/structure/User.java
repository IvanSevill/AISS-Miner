package bitbucket.model.structure;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    @JsonProperty("uuid")
    private String id;
    @JsonProperty("nickname")
    private String username;
    @JsonProperty("display_name")
    private String name;
    @JsonProperty("links")
    private Links links;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getName() { return name; }

    public String getAvatarUrl(){return links.getAvatar().getHref();}

    public String getWebUrl(){return  links.getSelf().getHref();}

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", links=" + links +
                '}';
    }
}
