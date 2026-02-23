package bitbucket.service;

import bitbucket.model.structure.*;
import bitbucket.model.gitMiner.GitProject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import bitbucket.exception.TooManyRequestsException;

import java.util.*;


@Service
public class ProjectService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${bitbucket.baseUri}")
    private String bitbucketBaseUri;

    @Value("${gitminer.baseUri}")
    private String gitminerBaseUri;

    @Value("${bitbucket.token}")
    private String token;

    @Value("${bitbucket.pageSize}")
    private int pageSize;

    private HttpEntity<?> createHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        if (token != null && !token.isEmpty()) {
            headers.set("Authorization", "Bearer " + token);
        }
        return new HttpEntity<>(headers);
    }

    // with values from Controller
    public Project getProject(String workspace, String repoSlug, int nCommits, int nIssues, int maxPages) {

        try{
            String base = bitbucketBaseUri + workspace + "/" + repoSlug;

            Project repo = restTemplate.exchange(base, HttpMethod.GET, createHttpEntity(), Project.class).getBody();

            // commits

            List<Commit> allCommits = new ArrayList<>();
            String commitsUrl = base + "/commits";
            for (int page = 1; page <= maxPages && allCommits.size() < nCommits; page++) {
                String url = commitsUrl + "?pagelen=" + nCommits + "&page=" + page;
                ResponseEntity<Page<Commit>> resp = restTemplate.exchange(
                        url, HttpMethod.GET, createHttpEntity(),
                        new ParameterizedTypeReference<Page<Commit>>() {}
                );
                List<Commit> values = resp.getBody().getValues();
                if (values.isEmpty()) break;
                int needed = nCommits - allCommits.size();
                allCommits.addAll(values.subList(0, Math.min(needed, values.size())));
            }

            repo.setCommits(allCommits);

            // issues


            List<Issue> allIssues = new ArrayList<>();
            String issuesUrl = base + "/issues";
            for (int page = 1; page <= maxPages && allIssues.size() < nIssues; page++) {
                String url = issuesUrl + "?pagelen=" + nIssues + "&page=" + page;
                ResponseEntity<Page<Issue>> resp = restTemplate.exchange(
                        url, HttpMethod.GET, createHttpEntity(),
                        new ParameterizedTypeReference<Page<Issue>>() {}
                );
                List<Issue> values = resp.getBody().getValues();
                if (values.isEmpty()) break;
                int needed = nIssues - allIssues.size();
                allIssues.addAll(values.subList(0, Math.min(needed, values.size())));
            }

            repo.setIssues(allIssues);

            // limit de los comentarios para cada issue
            for (Issue issue : allIssues) {
                String commentsHref = issue.getLinks().getComments().getHref();
                List<Comment> comments = new ArrayList<>();
                for (int page = 1; page <= maxPages; page++) {
                    String url = commentsHref + "?pagelen="+pageSize+"&page=" + page;
                    ResponseEntity<Page<Comment>> resp = restTemplate.exchange(
                            url, HttpMethod.GET, createHttpEntity(),
                            new ParameterizedTypeReference<>() {}
                    );
                    List<Comment> values = resp.getBody().getValues();
                    if (values.isEmpty()) break;
                    comments.addAll(values);
                }
                issue.setComments(comments);
            }

            return repo;
        } catch (RestClientResponseException e) {
            System.out.println("Error HTTP: " + e.getRawStatusCode() + " - " + e.getStatusText());
            System.out.println("Response body: " + e.getResponseBodyAsString());

            if (e.getRawStatusCode() == 403 && e.getResponseBodyAsString().toLowerCase().contains("rate limit")) {
                throw new TooManyRequestsException("Demasiadas peticiones a la API de GitHub. Añade un token si no estás usando uno.");
            }
            return null;
        } catch (Exception e) {
            System.out.println("General error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

    }

    // hecho for Controller
    public void sendProjectToGitMiner(GitProject project) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<GitProject> request = new HttpEntity<>(project, headers);

            System.out.println("Enviando a GitMiner:");
            System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(project));

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