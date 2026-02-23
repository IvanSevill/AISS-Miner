package aiss.gitlabminer.service;

import aiss.gitlabminer.model.gitlabminer.Comment;
import aiss.gitlabminer.model.gitlabminer.Commit;
import aiss.gitlabminer.model.gitlabminer.Issue;
import aiss.gitlabminer.model.gitlabminer.Project;
import aiss.gitlabminer.model.gitminer.GitProject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import java.net.URI;

import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${gitlab.baseUri}")
    private String gitlabBaseUri;

    @Value("${gitminer.baseUri}")
    private String gitminerBaseUri;

    @Value("${gitlab.token}")
    private String token;

    private HttpEntity<?> createHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        if (token != null && !token.isEmpty()) {
            headers.set("Authorization", "Bearer " + token);
        }
        return new HttpEntity<>(headers);
    }

    public Project getProject(String owner, String repoName) {
        try {

            String namespaceWithProject = URLEncoder.encode(owner + "/" + repoName, StandardCharsets.UTF_8);
            String urlStr = gitlabBaseUri + namespaceWithProject;

            URI repoUri = new URI(urlStr);

            System.out.println("Endpoint: " + repoUri);


            ResponseEntity<Project> projectResponse = restTemplate.exchange(
                    repoUri,
                    HttpMethod.GET,
                    createHttpEntity(),
                    Project.class
            );

            Project project = projectResponse.getBody();
            if (project != null) {
                System.out.println("Fetched project: " + project.getName());
            } else {
                System.out.println("No project found.");
            }

            //Commits
            List<Commit> commits = new ArrayList<>();

            String commitStr = gitlabBaseUri + namespaceWithProject + "/repository/commits";
            URI commitUri = new URI(commitStr);
            ResponseEntity<Commit[]> commitResponse = restTemplate.exchange(
                    commitUri,
                    HttpMethod.GET,
                    createHttpEntity(),
                    Commit[].class
            );

            project.setCommits(Arrays.asList(commitResponse.getBody()));

            //Issues
            List<Issue> issues = new ArrayList<>();

            String issueStr = gitlabBaseUri + namespaceWithProject + "/issues";
            URI issueUri = new URI(issueStr);
            ResponseEntity<Issue[]> issueResponse = restTemplate.exchange(
                    issueUri,
                    HttpMethod.GET,
                    createHttpEntity(),
                    Issue[].class
            );


            List<Issue> commentsIssues = Arrays.asList(issueResponse.getBody());

            commentsIssues.forEach(issue -> setCommentsForIssue(issue, namespaceWithProject));

            project.setIssues(commentsIssues);



            return project;

        } catch (RestClientResponseException e) {
            System.out.println("Error HTTP: " + e.getRawStatusCode() + " - " + e.getStatusText());
            System.out.println("Response body: " + e.getResponseBodyAsString());
            System.out.println("No se pudo obtener el proyecto desde GitLab");
            return null;

        } catch (Exception e) {
            System.out.println("General error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private void setCommentsForIssue(Issue issue, String namespaceWithProject) {
        List<Comment> comments;

        String commentStr = gitlabBaseUri + namespaceWithProject + "/issues/" + issue.getIid() + "/notes";
        URI commentUri;
        try {
            commentUri = new URI(commentStr);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        try {
            ResponseEntity<Comment[]> commentResponse = restTemplate.exchange(
                    commentUri,
                    HttpMethod.GET,
                    createHttpEntity(),
                    Comment[].class
            );
            comments = Arrays.asList(commentResponse.getBody());
        } catch (RestClientResponseException e) {
            if (e.getRawStatusCode() == 401) {
                System.out.println("401 Unauthorized for comments of issue " + issue.getIid() + ", setting empty list.");
                comments = new ArrayList<>();
            } else {
                throw e;
            }
        }

        issue.setComments(comments);
    }


    public void sendProjectToGitMiner(GitProject gitProject) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<GitProject> request = new HttpEntity<>(gitProject, headers);

            System.out.println("Enviando a GitMiner:");
            System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(gitProject));

            System.out.println(gitminerBaseUri + "projects");
            ResponseEntity<String> response = restTemplate.postForEntity(
                    gitminerBaseUri + "projects", request, String.class);

            System.out.println("Project sent to GitMiner. Response: " + response.getStatusCode());

        } catch (RestClientResponseException e) {
            System.out.println("Error al enviar el proyecto a GitMiner: " + e.getRawStatusCode() + " - " + e.getStatusText());
            System.out.println("Cuerpo de la respuesta: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            System.out.println("Error general al enviar el proyecto: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
