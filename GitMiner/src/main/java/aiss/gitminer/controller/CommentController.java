package aiss.gitminer.controller;

import aiss.gitminer.exception.CommentNotFoundException;
import aiss.gitminer.model.Comment;
import aiss.gitminer.repository.CommentRepository;
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

@Tag(name = "Comment",description = "Commit management API")
@RestController
@RequestMapping("/gitminer/comments")
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    @Operation(
            summary = "Retrieve a list of comments",
            description = "Lists every comment stored in the database",
            tags = {"comments","get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Comment.class),mediaType = "application/json")}),
    })
    @GetMapping()
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Operation(
            summary = "Retrieves a comment by its Id",
            description = "Gets a comment by specifying its id",
            tags = {"comments","get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Comment.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema =@Schema())})
    })
    @GetMapping("/{commentId}")
    public Comment findOne(@Parameter(description = "id of the comment to be searched") @PathVariable(value = "commentId") String commentId) throws CommentNotFoundException {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isPresent()) {
            return comment.get();
        }
        throw new CommentNotFoundException();
    }

    @Operation(
            summary = "Edits an existing comment in the database",
            description = "Changes the body of the comment object stored in the database that matches given id",
            tags = {"comments","put"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema()) }) })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@Valid @RequestBody Comment updatedComment,@Parameter(description = "id of the comment to be edited") @PathVariable String id) throws CommentNotFoundException {
        Optional<Comment> commentData = commentRepository.findById(id);
        if (commentData.isPresent()) {
            Comment comment = commentData.get();
            comment.setId(updatedComment.getId());
            comment.setBody(updatedComment.getBody());
            comment.setAuthor(updatedComment.getAuthor());
            comment.setCreatedAt(updatedComment.getCreatedAt());
            comment.setUpdatedAt(Instant.now().toString());
            commentRepository.save(comment);
        } else{
            throw new CommentNotFoundException();
        }
    }

    @Operation(
            summary = "Deletes a comment stored in the database",
            description = "Removes a comment given its id",
            tags = {"comments","delete"})
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@Parameter(description = "id of the project to be deleted") @PathVariable String id) throws CommentNotFoundException {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
        } else {
            throw new CommentNotFoundException();
        }

    }

}
