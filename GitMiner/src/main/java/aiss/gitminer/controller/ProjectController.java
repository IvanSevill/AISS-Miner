package aiss.gitminer.controller;

import aiss.gitminer.exception.ProjectNotFoundException;
import aiss.gitminer.model.Commit;
import aiss.gitminer.model.Issue;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.CommitRepository;
import aiss.gitminer.repository.IssueRepository;
import aiss.gitminer.repository.ProjectRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Tag(name = "Project",description = "Project management API")
@RestController
@RequestMapping("/gitminer/projects")
public class ProjectController {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    IssueRepository issueRepository;

    @Autowired
    CommitRepository commitRepository;


    @Operation(
            summary = "Retrieves a list of projects",
            description = "Lists every project stored in the database",
            tags = {"projects","get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Project.class),mediaType = "application/json")}),
    })
    @GetMapping
    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    @Operation(
            summary = "Retrieves a project by its Id",
            description = "Gets a project by specifying its id",
            tags = {"projects","get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Project.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema =@Schema())})
    })
    @GetMapping("/{id}")
    public Project findOne(@Parameter(description = "id of the project to be searched") @PathVariable String id) throws ProjectNotFoundException {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            return project.get();
        }
        throw new ProjectNotFoundException();
    }

    @Operation(
            summary = "Creates a project",
            description = "Adds a new project with the body specified into the database",
            tags = {"projects","post"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = Project.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema()) }) })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Project create(@Valid @RequestBody Project project) {
        Project _project = new Project();
        _project.setId(project.getId());
        _project.setName(project.getName());
        _project.setWebUrl(project.getWebUrl());
        _project.setCommits(project.getCommits());
        _project.setIssues(project.getIssues());
        projectRepository.save(_project);
        return _project;
    }

    @Operation(
            summary = "Edits an existing project in the database",
            description = "Changes the body of the project object stored in the database that matches given id",
            tags = {"projects","put"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema()) }) })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@Valid @RequestBody Project updatedProject,@Parameter(description = "id of the project to be edited") @PathVariable String id) throws ProjectNotFoundException {
        Optional<Project> projectData = projectRepository.findById(id);
        if (projectData.isPresent()) {
            Project _project = projectData.get();
            _project.setId(updatedProject.getId());
            _project.setName(updatedProject.getName());
            _project.setWebUrl(updatedProject.getWebUrl());
            _project.setCommits(updatedProject.getCommits());
            _project.setIssues(updatedProject.getIssues());
            projectRepository.save(_project);
        } else {
            throw new ProjectNotFoundException();
        }
    }

    @Operation(
            summary = "Deletes a project stored in the database",
            description = "Removes a project given its id",
            tags = {"projects","delete"})
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@Parameter(description = "id of the project to be deleted") @PathVariable String id) throws ProjectNotFoundException {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
        } else {
            throw new ProjectNotFoundException();
        }

    }


    @Operation(
            summary = "Creates an issue",
            description = "Adds a new issue with the body specified into the database",
            tags = {"issues","post"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = Issue.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema()) }) })

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{projectId}/issues")
    public Issue create(@Parameter(description = "id of the project to create the issue in")@PathVariable(value = "projectId") String projectId, @Valid @RequestBody Issue issue) throws ProjectNotFoundException {
        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isPresent()) {
            issue.setProject(project.get());
            return issueRepository.save(issue);
        } else {
            throw new ProjectNotFoundException();
        }

    }

    @Operation(
            summary = "Creates a commit",
            description = "Adds a new commit with the body specified into the database",
            tags = {"commits","post"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = Commit.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema()) }) })

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{projectId}/commits")
    public Commit create(@Parameter(description = "id of the project to create the commit in") @PathVariable("projectId") String projectId, @Valid @RequestBody Commit commit) throws ProjectNotFoundException {

        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isPresent()) {
            commit.setProject(project.get());
            return commitRepository.save(commit);

        } else {
            throw new ProjectNotFoundException();
        }

    }
}
