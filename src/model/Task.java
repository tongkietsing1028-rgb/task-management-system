package model;
import model.enums.TaskPriority;
import model.enums.TaskStatus;

public class Task
{
    private String taskId;
    private String title;
    private String description;
    private String ownerId;
    private String groupId;
    TaskStatus status;
    TaskPriority priority;

    //constructor
    public Task(String taskId, String title, String description, String ownerId, String groupId, TaskStatus status, TaskPriority priority) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.ownerId = ownerId;
        this.groupId = groupId;
        this.status = status;
        this.priority = priority;
    }
    //getter and setter
    public String getTaskId() {
        return taskId;
    }
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getOwnerId() {
        return ownerId;
    }
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
    public String getGroupId() {
        return groupId;
    }
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    public TaskStatus getStatus() {
        return status;
    }
    public void setStatus(TaskStatus status) {
        this.status = status;
    }
    public TaskPriority getPriority() {
        return priority;
    }
    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }
}
