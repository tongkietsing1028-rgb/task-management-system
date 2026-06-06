# Task Management System

A desktop task management application built with Java Swing, supporting personal and group-based task tracking with file-based persistent storage.

---

## Features

### User Management
- User registration and login
- Credentials stored in local text file

### Task Management
- Create, edit, delete personal tasks
- Assign tasks to a group
- Change task status: `TODO` → `IN_PROGRESS` → `COMPLETED`
- Change task priority: `HIGH` / `MEDIUM`
- Search tasks by keyword

### Group Management
- Create and delete groups
- Add and remove group members
- View group details
- Manage group tasks

---

## Tech Stack

- **Language**: Java
- **UI**: Java Swing
- **Storage**: File-based (`.txt` files with `|` delimiter)
- **Architecture**: Layered — `gui` / `service` / `repository` / `model`

---

## Project Structure

```
src/
├── gui/
│   ├── LoginFrame.java
│   ├── MainFrame.java
│   ├── TaskPanel.java
│   ├── GroupPanel.java
│   ├── AddTaskDialog.java
│   ├── AddGroupDialog.java
│   └── ManageGroupDialog.java
├── model/
│   ├── User.java
│   ├── Task.java
│   ├── Group.java
│   └── enums/
│       ├── TaskStatus.java
│       └── TaskPriority.java
├── service/
│   ├── AuthService.java
│   ├── TaskService.java
│   └── GroupService.java
├── repository/
│   ├── UserRepository.java
│   ├── TaskRepository.java
│   ├── GroupRepository.java
│   └── FileStorage.java
└── util/
    └── IdGenerator.java

data/
├── users.txt
├── tasks.txt
└── groups.txt
```

---

## Getting Started

### Requirements
- Java 17 or above
- No external dependencies required

### Run

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/your-repo-name.git
   ```

2. Open the project in your IDE (IntelliJ IDEA or Eclipse recommended).

3. Run `gui/LoginFrame.java` as the entry point.

4. Register a new account and log in.

---

## Data Storage

All data is stored as plain text files under `src/data/`. Each record is stored as a single line with fields separated by `|`.

| File | Format |
|------|--------|
| `users.txt` | `userId\|username\|password` |
| `tasks.txt` | `taskId\|title\|description\|priority\|status\|ownerId\|groupId` |
| `groups.txt` | `groupId\|groupName\|ownerId\|memberId1,memberId2,...` |

---

## Known Limitations

- No encryption on stored passwords
- No real-time sync between users
- Single-user session only (no concurrent access handling)
