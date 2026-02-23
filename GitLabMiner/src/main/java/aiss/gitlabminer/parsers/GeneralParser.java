package aiss.gitlabminer.parsers;

import aiss.gitlabminer.model.gitlabminer.*;
import aiss.gitlabminer.model.gitminer.*;

import java.util.List;

public class GeneralParser {

    public static GitProject parseProject(Project glProject) {
        GitProject project = new GitProject();

        if (glProject != null) {
            project.setId(glProject.getId());
            project.setName(glProject.getName());
            project.setWebUrl(glProject.getWebUrl());

            // Parseo de las issues
            List<GitCommit> finalCommits =
                    glProject.getCommits().stream()
                            .map(GeneralParser::parseCommit)
                            .toList();
            project.setCommits(finalCommits);

            // Parseo de los commits
            List<GitIssue> finalIssues =
                    glProject.getIssues().stream()
                            .map(GeneralParser::parseIssue)
                            .toList();
            project.setIssues(finalIssues);
        }

        return project;
    }

    private static GitCommit parseCommit(Commit ghCommmit) {
        GitCommit commit = new GitCommit();

        if (ghCommmit != null) {
            commit.setId(ghCommmit.getId());
            commit.setTitle(ghCommmit.getTitle());
            commit.setMessage(ghCommmit.getMessage());
            commit.setAuthorName(ghCommmit.getAuthor_name());
            commit.setAuthorEmail(ghCommmit.getAuthor_email());
            commit.setAuthoredDate(ghCommmit.getAuthored_date());
            commit.setWebUrl(ghCommmit.getWeb_url());
        }

        return commit;
    }

    private static GitIssue parseIssue(Issue ghIssue) {
        GitIssue issue =  new GitIssue();

        if (ghIssue != null) {
            issue.setId(ghIssue.getId());
            issue.setTitle(ghIssue.getTitle());
            issue.setDescription(ghIssue.getDescription());
            issue.setState(ghIssue.getState());
            issue.setCreatedAt(ghIssue.getCreatedAt());
            issue.setUpdatedAt(ghIssue.getUpdatedAt());
            issue.setClosedAt(ghIssue.getClosedAt());
            issue.setLabels(ghIssue.getLabelNames());
            issue.setVotes(ghIssue.getTotalVotes());

            // Parseo de el Author del issue
            issue.setAuthor(parseaUser(ghIssue.getAuthor()));

            // Parseo del Assignee
            issue.setAssignee(parseaUser(ghIssue.getAssignee()));

            // Parseo de los comentarios
            List<GitComment> comments = ghIssue.getComments().stream()
                    .map(GeneralParser::parseComment)
                    .toList();
            issue.setComments(comments);
        }

        return issue;
    }

    private static GitUser parseaUser(User ghUser){

        if (ghUser == null){
            return null;
        }

        GitUser user = new GitUser();
        user.setId(ghUser.getId());
        user.setName(ghUser.getName());
        user.setUsername(ghUser.getUsername());
        user.setAvatarUrl(ghUser.getAvatarUrl());
        user.setWebUrl(ghUser.getWebUrl());


        return user;
    }

    private static GitComment parseComment(Comment ghComment) {
        GitComment comment = new GitComment();

        if (ghComment != null) {
            comment.setId(ghComment.getId());
            comment.setBody(ghComment.getBody());
            comment.setCreatedAt(ghComment.getCreatedAt());
            comment.setUpdatedAt(ghComment.getUpdatedAt());

            // Parseo del Author del comentario
            comment.setAuthor(parseaUser(ghComment.getAuthor()));
        }

        return comment;
    }

}