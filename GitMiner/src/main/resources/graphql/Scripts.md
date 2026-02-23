# GraphQL Scripts

This document contains example GraphQL operations for mutations, queries (for all entities), and schema introspection.

To start GraphQL interface run from the root of the project:
```powershell
.\mvnw spring-boot:run
```

---

## Mutations

### 1. Add User

```graphql
mutation AddUser {
  addUser(input: {
    id: "user-1",
    username: "alice",
    name: "Alice Example",
    avatarUrl: "https://example.com/alice.png",
    webUrl: "https://github.com/alice"
  }) {
    id
    username
    name
  }
}
```

### 2. Add Project

```graphql
mutation AddProject {
  addProject(input: {
    id: "proj-1",
    name: "Demo Project",
    webUrl: "https://github.com/alice/demo"
  }) {
    id
    name
    webUrl
  }
}
```

### 3. Add Commit

```graphql
mutation AddCommit {
  addCommit(input: {
    id: "commit-1",
    title: "Setup repo",
    message: "Initial repository setup",
    authorName: "Alice Example",
    authoredDate: "2025-05-09T10:00:00Z",
    webUrl: "https://github.com/alice/demo/commit/commit-1",
    projectId: "proj-1"
  }) {
    id
    title
    authoredDate
  }
}
```

### 4. Add Issue

```graphql
mutation AddIssue {
  addIssue(input: {
    id: "issue-1",
    projectId: "proj-1",
    title: "First issue",
    description: "Description of the first issue",
    state: "OPEN",
    createdAt: "2025-05-09T11:00:00Z",
    authorId: "user-1",
    assigneeId: "user-1"
  }) {
    id
    title
    state
  }
}
```

### 5. Add Comment

```graphql
mutation AddComment {
  addComment(input: {
    id: "comment-1",
    issueId: "issue-1",
    body: "Checked and it works",
    createdAt: "2025-05-09T12:00:00Z",
    authorId: "user-1"
  }) {
    id
    body
    createdAt
  }
}
```

---

## Queries (Fetch All)

### Fetch All Users

```graphql
query GetAllUsers {
  allUsers {
    id
    username
    name
    avatarUrl
    webUrl
  }
}
```

### Fetch All Projects

```graphql
query GetAllProjects {
  allProjects {
    id
    name
    webUrl
  }
}
```

### Fetch All Commits

```graphql
query GetAllCommits {
  allCommits {
    id
    title
    message
    authorName
    authoredDate
    webUrl
    projectId
  }
}
```

### Fetch All Issues

```graphql
query GetAllIssues {
  allIssues {
    id
    title
    description
    state
    createdAt
    updatedAt
    closedAt
    labels
    votes
    author { id username }
    assignee { id username }
    comments { id body }
  }
}
```

### Fetch All Comments

```graphql
query GetAllComments {
  allComments {
    id
    body
    createdAt
    updatedAt
    author { id username }
    issue { id title }
  }
}
```

---

## Schema Introspection

```graphql
query IntrospectionQuery {
  __schema {
    queryType { name }
    mutationType { name }
    types {
      kind
      name
      fields { name }
      inputFields { name }
      interfaces { name }
      enumValues { name }
      possibleTypes { name }
    }
  }
}
```
