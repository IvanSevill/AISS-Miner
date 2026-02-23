package githubminer.parsers;

import githubminer.model.githubminer.Comment;
import githubminer.model.githubminer.Project;
import githubminer.model.githubminer.User;
import githubminer.model.githubminer.commitPackage.Commit;
import githubminer.model.gitminer.*;
import githubminer.model.githubminer.issuePackage.Issue;

import java.util.List;

public class GeneralParser {

    public static GitProject parseProject(Project ghProject) {
        GitProject project = new GitProject();

        if (ghProject != null) {
            project.setId(ghProject.getId());
            project.setName(ghProject.getName());
            project.setWebUrl(ghProject.getWebUrl());

            // Parseo de las issues
            List<GitCommit> finalCommits =
                    ghProject.getCommits().stream()
                            .map(GeneralParser::parseCommit)
                            .toList();
            project.setCommits(finalCommits);

            // Parseo de los commits
            List<GitIssue> finalIssues =
                    ghProject.getIssues().stream()
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
            commit.setAuthorName(ghCommmit.getAuthorName());
            commit.setAuthorEmail(ghCommmit.getAuthorEmail());
            commit.setAuthoredDate(ghCommmit.getAuthoredDate());
            commit.setWebUrl(ghCommmit.getUrl());
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