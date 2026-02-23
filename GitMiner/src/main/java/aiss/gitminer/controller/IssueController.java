package aiss.gitminer.controller;

import aiss.gitminer.exception.IssueNotFoundException;
import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Issue;
import aiss.gitminer.repository.CommentRepository;
import aiss.gitminer.repository.IssueRepository;
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

@Tag(name = "Issue",description = "Issue management API")
@RestController
@RequestMapping("/gitminer/issues")
public class IssueController {
    @Autowired
    IssueRepository issueRepository;

    @Autowired
    CommentRepository commentRepository;

    @Operation(
            summary = "Retrieve an issue by Id",
            description = "Get an issue by specifying its id",
            tags = {"issues","get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Issue.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema =@Schema())})
    })
    @GetMapping("/{issueId}")
    public Issue findOne(@Parameter(description = "id of the issue to be searched") @PathVariable(value = "issueId") String issueId) throws IssueNotFoundException {
        Optional<Issue> issue = issueRepository.findById(issueId);
        if (issue.isPresent()) {
            return issue.get();
        }
        throw new IssueNotFoundException();
    }


    @Operation(
            summary = "Retrieve a list of issues, can be filtered",
            description = "Lists every issue stored in the database. It can be filtered using the params state and/or authorId",
            tags = {"issues","get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Issue.class),mediaType = "application/json")}),
    })
    @GetMapping()
    public List<Issue> findAll(@RequestParam(value = "state", required = false) String state,
                               @RequestParam(value = "authorId", required = false) String authorId) {
        List<Issue> issues = issueRepository.findAll();

        if (state != null && !state.isEmpty()) {
            issues = issues.stream()
                    .filter(issue -> issue.getState() != null && issue.getState().equals(state))
                    .toList();
        }

        if (authorId != null && !authorId.isEmpty()) {
            issues = issues.stream()
                    .filter(issue -> issue.getAuthor() != null && issue.getAuthor().getId() != null && issue.getAuthor().getId().equals(authorId))
                    .toList();
        }

        return issues;
    }

    @Operation(
            summary = "Retrieve all comments of an issue",
            description = "Get all comments of an issue by its id",
            tags = {"issues","get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Comment.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema =@Schema())})
    })
    @GetMapping("/{issueId}/comments")
    public List<Comment> getIssuesComments(@Parameter(description = "id of the issue that holds the comments") @PathVariable(value = "issueId") String issueId) throws IssueNotFoundException {
        Optional<Issue> issue = issueRepository.findById(issueId);
        if (issue.isPresent()) {
            return issue.get().getComments();
        }
        throw new IssueNotFoundException();
    }

    @Operation(
            summary = "Edits an existing issue in the database",
            description = "Changes the body of the issue object stored in the database that matches given id",
            tags = {"issues","put"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema()) }) })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{issueId}")
    public void update(@Valid @RequestBody Issue updatedIssue, @Parameter(description = "id of the issue to be updated") @PathVariable String issueId) throws IssueNotFoundException {
        Optional<Issue> issueData = issueRepository.findById(issueId);
        if (issueData.isPresent()) {
            Issue issue = issueData.get();
            issue.setId(updatedIssue.getId());
            issue.setTitle(updatedIssue.getTitle());
            issue.setDescription(updatedIssue.getDescription());
            issue.setState(updatedIssue.getState());
            issue.setCreatedAt(updatedIssue.getCreatedAt());
            issue.setUpdatedAt(Instant.now().toString());
            issue.setClosedAt(updatedIssue.getClosedAt());
            issue.setLabels(updatedIssue.getLabels());
            issue.setAuthor(updatedIssue.getAuthor());
            issue.setAssignee(updatedIssue.getAssignee());
            issue.setVotes(updatedIssue.getVotes());
            issue.setComments(updatedIssue.getComments());
            issueRepository.save(issue);
        } else {
            throw new IssueNotFoundException();
        }
    }

    @Operation(
            summary = "Creates a comment",
            description = "Adds a new comment with the body specified into the database",
            tags = {"comments","post"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = Comment.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema()) }) })

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{issueId}/comments")
    public Comment create(@PathVariable(value = "issueId") String issueId, @Valid @RequestBody Comment comment) throws IssueNotFoundException {
        Optional<Issue> issue = issueRepository.findById(issueId);

        if (issue.isPresent()) {
            comment.setIssue(issue.get());
            return commentRepository.save(comment);
        } else {
            throw new IssueNotFoundException();
        }
    }

}
