package aiss.gitminer.controller;

import aiss.gitminer.exception.CommitNotFoundException;
import aiss.gitminer.exception.ProjectNotFoundException;
import aiss.gitminer.model.Commit;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.CommitRepository;
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
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Tag(name = "Commit",description = "Commit management API")
@RestController
@RequestMapping("/gitminer/commits")
public class CommitController {
    @Autowired
    CommitRepository commitRepository;

    @Operation(
            summary = "Retrieves a list of commits",
            description = "Lists every commit stored in the database",
            tags = {"commits","get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Commit.class),mediaType = "application/json")}),
    })
    @GetMapping()
    public List<Commit> findAll() {
        return commitRepository.findAll();
    }

    @Operation(
            summary = "Retrieves a commit by its Id",
            description = "Gets a commit by specifying its id",
            tags = {"commits","get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Commit.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema =@Schema())})
    })
    @GetMapping("/{commitId}")
    public Commit findOne(@Parameter(description = "id of the commit to be searched") @PathVariable(value = "commitId") String commitId) throws CommitNotFoundException {
        Optional<Commit> commit = commitRepository.findById(commitId);
        if (commit.isPresent()) {
            return commit.get();
        }
        throw new CommitNotFoundException();
    }

    @Operation(
            summary = "Edits an existing commit in the database",
            description = "Changes the body of the commit object stored in the database that matches given id",
            tags = {"commits","put"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema()) }) })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@Valid @RequestBody Commit updatedCommit, @Parameter(description = "id of the commit to be edited") @PathVariable String id) throws CommitNotFoundException {
        Optional<Commit> commitData = commitRepository.findById(id);
        if (commitData.isPresent()) {
            Commit commit = commitData.get();
            commit.setId(updatedCommit.getId());
            commit.setProject(updatedCommit.getProject());
            commit.setTitle(updatedCommit.getTitle());
            commit.setMessage(updatedCommit.getMessage());
            commit.setAuthorName(updatedCommit.getAuthorName());
            commit.setAuthorEmail(updatedCommit.getAuthorEmail());
            commit.setAuthoredDate(updatedCommit.getAuthoredDate());
            commit.setWebUrl(Instant.now().toString());
            commitRepository.save(commit);
        } else{
            throw new CommitNotFoundException();
        }
    }

    @Operation(
            summary = "Deletes a commit stored in the database",
            description = "Removes a commit given its id",
            tags = {"commits","delete"})
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@Parameter(description = "id of the commit to be deleted") @PathVariable String id) throws CommitNotFoundException {
        if (commitRepository.existsById(id)) {
            commitRepository.deleteById(id);
        } else {
            throw new CommitNotFoundException();
        }

    }

}
