package bitbucket.controller;


import bitbucket.model.gitMiner.GitProject;
import bitbucket.model.structure.Project;
import bitbucket.parser.GeneralParser;
import bitbucket.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/bitbucket")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    RestTemplate restTemplate;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{owner}/{repo}")
    public ResponseEntity<String> postProjectToGitminer(
            @PathVariable String owner,
            @PathVariable String repo,
            @RequestParam(defaultValue = "2") Integer nCommits,
            @RequestParam(defaultValue = "20") Integer nIssues,
            @RequestParam(defaultValue = "2") Integer maxPages){

        System.out.println("Parametros: " +
                "\nnCommits: " + nCommits +
                "\nnIssues: " + nIssues +
                "\nmaxPages: " + maxPages);

        //Llamada a la api de bitbucket
        Project project = projectService.getProject(owner,repo,nCommits,nIssues,maxPages);
        GitProject gitProject = GeneralParser.parseProject(project);


        if(gitProject.getId() == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No se pudo obtener el proyecto desde BitBucket");
        }


        try{

            projectService.sendProjectToGitMiner(gitProject);
            return ResponseEntity.status(201).body("Proyecto enviado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
        }

    }

    @GetMapping("/{owner}/{repoName}")
    public GitProject getProjectToGitminer(
            @PathVariable String owner,
            @PathVariable String repoName,
            @RequestParam(defaultValue = "2") Integer nCommits,
            @RequestParam(defaultValue = "20") Integer nIssues,
            @RequestParam(defaultValue = "2") Integer maxPages
    ){
        System.out.println("Parametros (" +
                "\tsinceCommits: " + nCommits +
                "\t, sinceIssues: " + nIssues +
                "\t, maxPages: " + maxPages + "\t)");

        //Obtengo el proyecto de la API
        Project project = projectService.getProject(owner,repoName,nCommits,nIssues,maxPages);
        if(project == null){
            System.out.println("Error: No se encontró el proyecto en BitBucket");
            return null;
        }

        return GeneralParser.parseProject(project);
    }





}
