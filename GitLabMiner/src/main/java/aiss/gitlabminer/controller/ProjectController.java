package aiss.gitlabminer.controller;

import aiss.gitlabminer.model.gitlabminer.Project;
import aiss.gitlabminer.model.gitminer.GitProject;
import aiss.gitlabminer.parsers.GeneralParser;
import aiss.gitlabminer.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gitlab")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/{owner}/{repoName}")
    public GitProject getProjectFromGitLab(
            @PathVariable String owner,
            @PathVariable String repoName ) {
        System.out.println("Owner: " + " - " + "RepoName: " + repoName);
        Project project = projectService.getProject(owner, repoName);

        if (project == null) {
            System.out.println("No se pudo obtener el proyecto desde GitLab");
            return null;
        }

        return GeneralParser.parseProject(project);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{owner}/{repoName}")
    public ResponseEntity<String> postProjectToGitMiner(
            @PathVariable String owner,
            @PathVariable String repoName ){

        System.out.println("Owner: " + " - " + "RepoName: " + repoName);
        Project project = projectService.getProject(owner, repoName);
        GitProject gitProject = GeneralParser.parseProject(project);

        if (project == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No se pudo obtener el proyecto desde GitLab");
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


}
