package githubminer.model.githubminer.issuePackage;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vote {

    @JsonProperty("total_count")
    private Integer totalCount;

    public Vote() {}

    public Vote(Integer tCount) {
        this.totalCount = tCount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "totalCount=" + totalCount +
                '}';
    }
}