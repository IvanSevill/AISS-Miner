package aiss.gitminer.graphql;

import aiss.gitminer.model.*;
import aiss.gitminer.repository.*;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;

@Component
public class Mutation implements GraphQLMutationResolver {
    private final ProjectRepository projectRepo;
    private final CommitRepository commitRepo;
    private final IssueRepository issueRepo;
    private final CommentRepository commentRepo;
    private final UserRepository userRepo;

    public Mutation(ProjectRepository projectRepo,
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

    public Project addProject(ProjectInput input) {
        Project p = new Project();
        p.setId(input.getId());
        p.setName(input.getName());
        p.setWebUrl(input.getWebUrl());
        return projectRepo.save(p);
    }

    public Commit addCommit(CommitInput input) {
        Commit c = new Commit();
        c.setId(input.getId());
        c.setTitle(input.getTitle());
        c.setMessage(input.getMessage());
        c.setAuthorName(input.getAuthorName());
        c.setAuthorEmail(input.getAuthorEmail());
        c.setAuthoredDate(input.getAuthoredDate());
        c.setWebUrl(input.getWebUrl());

        projectRepo.findById(input.getProjectId()).ifPresent(proj -> {
            proj.getCommits().add(c);
            c.setProject(proj);
        });
        return commitRepo.save(c);
    }

    public Issue addIssue(IssueInput input) {
        Issue i = new Issue();
        i.setId(input.getId());
        i.setTitle(input.getTitle());
        i.setDescription(input.getDescription());
        i.setState(input.getState());
        i.setCreatedAt(input.getCreatedAt());
        i.setUpdatedAt(input.getUpdatedAt());
        i.setClosedAt(input.getClosedAt());
        i.setLabels(input.getLabels());
        i.setVotes(input.getVotes());

        userRepo.findById(input.getAuthorId()).ifPresent(i::setAuthor);
        userRepo.findById(input.getAssigneeId()).ifPresent(i::setAssignee);
        projectRepo.findById(input.getProjectId()).ifPresent(proj -> {
            proj.getIssues().add(i);
            i.setProject(proj);
        });
        return issueRepo.save(i);
    }

    public Comment addComment(CommentInput input) {
        Comment c = new Comment();
        c.setId(input.getId());
        c.setBody(input.getBody());
        c.setCreatedAt(input.getCreatedAt());
        c.setUpdatedAt(input.getUpdatedAt());

        userRepo.findById(input.getAuthorId()).ifPresent(c::setAuthor);

        issueRepo.findById(input.getIssueId()).ifPresent(issue -> {
            issue.getComments().add(c);
            c.setIssue(issue);
        });
        return commentRepo.save(c);
    }

    public User addUser(UserInput input) {
        User u = new User();
        u.setId(input.getId());
        u.setUsername(input.getUsername());
        u.setName(input.getName());
        u.setAvatarUrl(input.getAvatarUrl());
        u.setWebUrl(input.getWebUrl());
        return userRepo.save(u);
    }
}
