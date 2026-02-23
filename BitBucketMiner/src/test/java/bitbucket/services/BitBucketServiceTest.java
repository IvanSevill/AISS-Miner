package bitbucket.services;
import bitbucket.model.structure.Project;
import bitbucket.service.ProjectService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BitBucketServiceTest {

    @Autowired
    private ProjectService projectService;

    @Test
    @DisplayName("Fetch and parse a Bitbucket project")
    void fetchBitbucketProject() {
        String workspace = "gentlero";
        String repoSlug  = "bitbucket-api";

        Project project = projectService.getProject(workspace, repoSlug,2,20,2);
        System.out.println(project);
    }
}
