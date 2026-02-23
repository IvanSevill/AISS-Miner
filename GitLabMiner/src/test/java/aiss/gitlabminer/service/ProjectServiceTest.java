package aiss.gitlabminer.service;

import aiss.gitlabminer.model.gitlabminer.Project;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

class ProjectServiceTest {

    @Autowired
    private ProjectService projectService;

    @Test
    @DisplayName("Fetch and parse a GitLab project")
    void getProject() {
        String owner = "gitlab-org";
        String repoName = "gitlab";

        Project project = projectService.getProject(owner,repoName);
        System.out.println(project);

    }
}