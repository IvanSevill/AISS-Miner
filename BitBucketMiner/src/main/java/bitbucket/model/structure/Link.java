package bitbucket.model.structure;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Link {
    @JsonProperty("href")
    private String href;

    public String getHref() { return href; }
    public void setHref(String href) { this.href = href; }

    @Override
    public String toString() {
        return "Link{" +
                "href='" + href + '\'' +
                '}';
    }
}
