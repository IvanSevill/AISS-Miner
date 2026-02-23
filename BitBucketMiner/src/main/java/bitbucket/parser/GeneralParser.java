// src/main/java/bitbucket/parser/GeneralParser.java
package bitbucket.parser;

import bitbucket.model.structure.*;
import bitbucket.model.gitMiner.*;

import java.util.List;
import java.util.stream.Collectors;

public class GeneralParser {

    public static GitProject parseProject(Project bbProject) {
        GitProject project = new GitProject();

        if (bbProject != null) {
            project.setId(bbProject.getId().substring(1, bbProject.getId().length() - 1));
            project.setName(bbProject.getName());
            project.setWebUrl(bbProject.getWebUrl());

            // Parseo de las issues
            List<GitCommit> finalCommits =
                    bbProject.getCommits().stream()
                            .map(GeneralParser::parseCommit)
                            .toList();
            project.setCommits(finalCommits);

            // Parseo de los commits
            List<GitIssue> finalIssues =
                    bbProject.getIssues().stream()
                            .map(GeneralParser::parseIssue)
                            .toList();
            project.setIssues(finalIssues);
        }

        return project;
    }

    private static GitCommit parseCommit(Commit bbCommit) {
        GitCommit commit = new GitCommit();

        if (bbCommit != null) {
            commit.setId(bbCommit.getId());
            commit.setTitle(bbCommit.getTitle());
            commit.setMessage(bbCommit.getMessage());
            commit.setAuthorName(bbCommit.getAuthorName());
            commit.setAuthorEmail(bbCommit.getAuthorEmail());
            commit.setAuthoredDate(bbCommit.getAuthoredDate());
            commit.setWebUrl(bbCommit.getUrl());
        }

        return commit;
    }

    private static GitIssue parseIssue(Issue bbIssue) {
        GitIssue issue =  new GitIssue();

        if (bbIssue != null) {
            issue.setId(bbIssue.getId());
            issue.setTitle(bbIssue.getTitle());
            issue.setDescription(bbIssue.getDescription());
            issue.setState(bbIssue.getState());
            issue.setCreatedAt(bbIssue.getCreatedAt());
            issue.setUpdatedAt(bbIssue.getUpdatedAt());
            issue.setClosedAt(bbIssue.getClosedAt());
            issue.setLabels(bbIssue.getLabelNames());
            issue.setVotes(bbIssue.getTotalVotes());

            // Parseo de el Author del issue
            issue.setAuthor(parseaUser(bbIssue.getAuthor()));

            // Parseo del Assignee
            issue.setAssignee(parseaUser(bbIssue.getAssignee()));

            // Parseo de los comentarios
            List<GitComment> comments = bbIssue.getComments().stream()
                    .map(GeneralParser::parseComment)
                    .toList();
            issue.setComments(comments);
        }

        return issue;
    }

    private static GitUser parseaUser(User bbUser){

        if (bbUser == null){
            return null;
        }
        GitUser user = new GitUser();
        user.setId(bbUser.getId());
        user.setName(bbUser.getName());
        user.setUsername(bbUser.getUsername());
        user.setAvatarUrl(bbUser.getAvatarUrl());
        user.setWebUrl(bbUser.getWebUrl());
        return user;
    }

    private static GitComment parseComment(Comment bbComment) {
        GitComment comment = new GitComment();

        if (bbComment != null) {
            comment.setId(bbComment.getId());

            if (bbComment.getBody() == null) {
                comment.setBody(" ");
            } else {
                comment.setBody(bbComment.getBody());
            }
            comment.setCreatedAt(bbComment.getCreatedAt());
            comment.setUpdatedAt(bbComment.getUpdatedAt());

            // Parseo del Author del comentario
            comment.setAuthor(parseaUser(bbComment.getAuthor()));
        }

        return comment;
    }
}
