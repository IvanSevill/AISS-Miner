package aiss.gitminer.gitminer.graphql;

import aiss.gitminer.graphql.Query;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectQueryJunitTest {


    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private Query query;

    @Test
    void allProjects_ShouldReturnProjectList() {
        //Creating fake project
        Project proj1 = new Project();
        proj1.setId("proj-1");
//        To test with incorrect data
//        proj1.setId("pro-1");
        proj1.setName("Test Project");
        proj1.setWebUrl("http://example.com");

        // If method findAll is called we return our fake project
        when(projectRepository.findAll()).thenReturn(List.of(proj1));
        List<Project> result = query.getProjects();

        // Confirming correctness of the returned data
        assertThat(result)
                .hasSize(1)
                .first()
                .satisfies(p -> {
                    assertThat(p.getId()).isEqualTo("proj-1");
                    assertThat(p.getName()).isEqualTo("Test Project");
                    assertThat(p.getWebUrl()).isEqualTo("http://example.com");
                });

        System.out.println("=== ProjectQueryJunitTest ===");
        System.out.println("Returned projects count: " + result.size());
        result.forEach(p -> {
            System.out.println("Project ID:   " + p.getId());
            System.out.println("Project Name: " + p.getName());
            System.out.println("Project URL:  " + p.getWebUrl());
        });

    }
}
