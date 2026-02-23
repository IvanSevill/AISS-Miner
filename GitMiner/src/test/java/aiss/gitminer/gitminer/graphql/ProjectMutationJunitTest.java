package aiss.gitminer.gitminer.graphql;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import aiss.gitminer.graphql.Mutation;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.ProjectRepository;
import aiss.gitminer.graphql.ProjectInput;

import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProjectMutationJunitTest {
// I decided to use Mockito, so I don't need to raise the whole database
// and I can try this simple Junit test with the mock data

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private Mutation mutation;

    @Test
    public void testAddProject() {

        //Creating a test-project
        ProjectInput input = new ProjectInput();
        input.setId("proj-1");
//        To  test with incorrect data
//        input.setId("pro-1");
        input.setName("Test Project");
        input.setWebUrl("http://example.com");

        // Imitating the output from database
        Project saved = new Project();
        saved.setId(input.getId());
        saved.setName(input.getName());
        saved.setWebUrl(input.getWebUrl());
        when(projectRepository.save(any(Project.class))).thenReturn(saved);

        // Imitating post
        Project result = mutation.addProject(input);

        // Confirming correctness of data
        assertNotNull(result);
        assertEquals("proj-1", result.getId());
        assertEquals("Test Project", result.getName());
        assertEquals("http://example.com", result.getWebUrl());

        System.out.println("=== ProjectMutationJunitTest ===");
        System.out.println("Returned Project ID:   " + result.getId());
        System.out.println("Returned Project Name: " + result.getName());
        System.out.println("Returned Project URL:  " + result.getWebUrl());

        verify(projectRepository, times(1)).save(any(Project.class));
    }
}
