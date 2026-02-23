package githubminer.controller;

import githubminer.model.githubminer.Project;
import githubminer.model.gitminer.GitProject;
import githubminer.parsers.GeneralParser;
import githubminer.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/github")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{owner}/{repoName}")
    public ResponseEntity<String> postProjectToGitminer(
            @PathVariable String owner,
            @PathVariable String repoName,
            @RequestParam(defaultValue = "2") int sinceCommits,
            @RequestParam(defaultValue = "20") int sinceIssues,
            @RequestParam(defaultValue = "2") int maxPages) {

        System.out.println("Parametros: " +
                "\nsinceCommits: " + sinceCommits +
                "\nsinceIssues: " + sinceIssues +
                "\nmaxPages: " + maxPages);

        // Obtener el proyecto desde GitHub
        Project project = projectService.getProject(owner, repoName, sinceCommits, sinceIssues, maxPages);
        GitProject gitProject = GeneralParser.parseProject(project);

        if (project == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No se pudo obtener el proyecto desde GitHub");
        }

        try {
            // Enviar directamente el proyecto original a GitMiner
            projectService.sendProjectToGitMiner(gitProject);
            return ResponseEntity.status(201).body("Proyecto enviado a GitMiner correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al enviar a GitMiner: " + e.getMessage());
        }
    }


    @GetMapping("/{owner}/{repoName}")
    public GitProject getProjectToGitminer(
            @PathVariable String owner,
            @PathVariable String repoName,
            @RequestParam(defaultValue = "2") int sinceCommits,
            @RequestParam(defaultValue = "20") int sinceIssues,
            @RequestParam(defaultValue = "2") int maxPages) {

        System.out.println("Parametros (" +
                "\tsinceCommits: " + sinceCommits +
                "\t, sinceIssues: " + sinceIssues +
                "\t, maxPages: " + maxPages + "\t)");


        // Obtener el proyecto desde GitHub
        Project project = projectService.getProject(owner, repoName, sinceCommits, sinceIssues, maxPages);
        if (project == null) {
            System.out.println("No se pudo obtener el proyecto desde GitHub");
            return null;
        }

        return GeneralParser.parseProject(project);
    }


}
