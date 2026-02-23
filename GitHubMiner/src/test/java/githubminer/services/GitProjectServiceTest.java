package githubminer.services;

import githubminer.model.githubminer.Project;
import githubminer.model.gitminer.GitProject;
import githubminer.parsers.GeneralParser;
import githubminer.service.ProjectService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GitProjectServiceTest {

    @Autowired
    ProjectService projectService;

    @Test
    @DisplayName("Get project by owner and name")
    void getProjectByOwnerAndName(){
        Project p = projectService.getProject("spring-projects", "spring-framework",10,10, 1);

        GeneralParser parser = new GeneralParser();
        GitProject gitProject = parser.parseProject(p);

        System.out.println(gitProject);
    }
}
