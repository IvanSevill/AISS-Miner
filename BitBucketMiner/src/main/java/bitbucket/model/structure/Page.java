package bitbucket.model.structure;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Page<T> {
    @JsonProperty("values")
    private List<T> values;

    public List<T> getValues() { return values; }
    public void setValues(List<T> values) { this.values = values; }
}
