package githubminer.service;

import githubminer.exception.TooManyRequestsException;
import githubminer.model.githubminer.Comment;
import githubminer.model.githubminer.Project;
import githubminer.model.githubminer.commitPackage.Commit;
import githubminer.model.githubminer.issuePackage.Issue;
import githubminer.model.gitminer.GitProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.Map.Entry;

@Service
public class ProjectService {

    @Value("${github.baseUri}")
    private String baseUri;

    @Value("${gitminer.uri}")
    private String gitminerBaseUri;

    @Value("${github.token}")
    private String token;

    @Value("${github.pageSize}")
    private String pageSize;

    @Autowired
    RestTemplate restTemplate;

    private HttpEntity<?> createHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        if (token != null && !token.isEmpty()) {
            headers.set("Authorization", "token " + token);
        }
        return new HttpEntity<>(headers);
    }

    private String buildUrl(String base, Map<String, String> queryParams) {
        if (queryParams.isEmpty()) {
            return base;
        }

        String url = base + "?";
        for (Entry<String, String> entry : queryParams.entrySet()) {
            url += entry.getKey() + "=" + entry.getValue() + "&";
        }

        return url.substring(0, url.length() - 1);
    }


    public Project getProject(String owner, String repo, int sinceCommits, int sinceIssues, int maxPages) {
        try {
            String repoUri = baseUri + owner + "/" + repo;

            // Obtener info del proyecto
            ResponseEntity<Project> projectResponse = restTemplate.exchange(
                    repoUri, HttpMethod.GET, createHttpEntity(), Project.class);
            Project project = projectResponse.getBody();

            System.out.println("Fetched project: " + project.getName());

            // Fechas ISO para parámetros since
            String sinceCommitsStr = Instant.now().minus(sinceCommits, ChronoUnit.DAYS).toString();
            String sinceIssuesStr = Instant.now().minus(sinceIssues, ChronoUnit.DAYS).toString();

            // Issues
            Map<String, String> issueParams = new HashMap<>();
            issueParams.put("since", sinceIssuesStr);
            issueParams.put("per_page", "10");

            List<Issue> issues = fetchPaginatedData(
                    buildUrl(repoUri + "/issues", issueParams), Issue[].class, maxPages);
            System.out.println("Fetched " + issues.size() + " issues.");

            // Commits
            Map<String, String> commitParams = new HashMap<>();
            commitParams.put("since", sinceCommitsStr);
            commitParams.put("per_page", pageSize);

            List<Commit> commits = fetchPaginatedData(
                    buildUrl(repoUri + "/commits", commitParams), Commit[].class, maxPages);
            System.out.println("Fetched " + commits.size() + " commits.");

            // Comments de los issues
            System.out.println("Fetching comments for each issue...");
            issues.forEach(this::añadeComments);
            System.out.println("Finished fetching all comments.\n");

            project.setCommits(commits);
            project.setIssues(issues);
            return project;

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

    private <T> List<T> fetchPaginatedData(String baseUrl, Class<T[]> clazz, int maxPages) {
        List<T> result = new ArrayList<>();
        for (int page = 1; page <= maxPages; page++) {
            String paginatedUrl = baseUrl + "&page=" + page;
            ResponseEntity<T[]> response = restTemplate.exchange(paginatedUrl, HttpMethod.GET, createHttpEntity(), clazz);
            T[] body = response.getBody();
            if (body == null || body.length == 0) break;
            result.addAll(Arrays.asList(body));
        }
        return result;
    }

    private void añadeComments(Issue issue) {
        ResponseEntity<Comment[]> commentResponse =
                restTemplate.exchange(issue.getCommentsUrl(), HttpMethod.GET, createHttpEntity(), Comment[].class);
        List<Comment> comments = Arrays.asList(Objects.requireNonNull(commentResponse.getBody()));
        issue.setComments(comments);
    }

    public void sendProjectToGitMiner(GitProject project) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<GitProject> request = new HttpEntity<>(project, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    gitminerBaseUri + "/projects", request, String.class);

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
