
package aiss.gitlabminer.model.gitminer;

public class GitUser {

    // Properties
    private String id;
    private String username;
    private String name;
    private String avatar_url;
    private String web_url;

    public GitUser(){

    }

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
        return avatar_url;
    }
    public void setAvatarUrl(String avatarUrl) {
        this.avatar_url = avatarUrl;
    }

    public String getWebUrl() {
        return web_url;
    }
    public void setWebUrl(String webUrl) {
        this.web_url = webUrl;
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
