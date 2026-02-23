package aiss.gitminer.graphql;

import aiss.gitminer.model.*;
import aiss.gitminer.repository.*;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Query implements GraphQLQueryResolver {
    private final ProjectRepository projectRepo;
    private final CommitRepository commitRepo;
    private final IssueRepository issueRepo;
    private final CommentRepository commentRepo;
    private final UserRepository userRepo;

    public Query(ProjectRepository projectRepo,
                 CommitRepository commitRepo,
                 IssueRepository issueRepo,
                 CommentRepository commentRepo,
                 UserRepository userRepo) {
        this.projectRepo = projectRepo;
        this.commitRepo = commitRepo;
        this.issueRepo = issueRepo;
        this.commentRepo = commentRepo;
        this.userRepo = userRepo;
    }

    public List<Project> getProjects() {
        return projectRepo.findAll();
    }

    public Project getProject(String id) {
        return projectRepo.findById(id).orElse(null);
    }

    public List<Commit> getCommits() {
        return commitRepo.findAll();
    }

    public Commit getCommit(String id) {
        return commitRepo.findById(id).orElse(null);
    }

    public List<Issue> getIssues() {
        return issueRepo.findAll();
    }

    public Issue getIssue(String id) {
        return issueRepo.findById(id).orElse(null);
    }

    public List<Comment> getComments() {
        return commentRepo.findAll();
    }

    public Comment getComment(String id) {
        return commentRepo.findById(id).orElse(null);
    }

    public List<User> getUsers() {
        return userRepo.findAll();
    }

    public User getUser(String id) {
        return userRepo.findById(id).orElse(null);
    }
}
