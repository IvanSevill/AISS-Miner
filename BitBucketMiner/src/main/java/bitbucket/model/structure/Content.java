package bitbucket.model.structure;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Content {
    @JsonProperty("raw")
    private String raw;

    public String getRaw() { return raw; }
    public void setRaw(String raw) { this.raw = raw; }
}
