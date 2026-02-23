package githubminer.model.githubminer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @JsonProperty("id")
    private String id;
    @JsonProperty("login")
    private String username;

    @JsonProperty("name")
    private String name;

    @JsonProperty("avatar_url")
    private String avatarUrl;
    @JsonProperty("html_url")
    private String web_url;

    public User(String id, String username, String name, String avatarUrl, String html_url) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.web_url = html_url;
    }

    public User() {

    }


    // getters & setters

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getWebUrl() {
        return web_url;
    }
    public void setWebUrl(String web_url) {
        this.web_url = web_url;
    }

    @Override
    public String toString() {
        return "\n\tUser{" +
                "\n\t\t\tid='" + getId() + '\'' +
                ",\n\t\t\tusername='" + getUsername() + '\'' +
                ",\n\t\t\tname='" + getName() + '\'' +
                ",\n\t\t\tavatarUrl='" + getAvatarUrl() + '\'' +
                ",\n\t\t\tweb_url='" + getWebUrl() + '\'' +
                '}';
    }
}
