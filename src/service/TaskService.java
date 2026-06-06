package service;

import model.Task;
import model.User;
import model.enums.TaskPriority;
import model.enums.TaskStatus;
import repository.TaskRepository;
import util.IdGenerator;
import util.Validator;

import java.util.ArrayList;
import java.util.List;

public  class TaskService {
    public static Task createTask(String title, String description, TaskPriority priority, User user,String groupId)
    {
        if(title==null || title.isEmpty() || description==null || description.isEmpty() || priority==null)
        {
            return null;
        }
        Task task = new Task(IdGenerator.generateTaskId(),title, description, user.getUserId(), groupId, TaskStatus.TODO,priority);
        TaskRepository.save(task);

        return task;
    }

    public static List<Task> getTaskByUser(String userId)
    {
        return TaskRepository.findByUserId(userId);
    }

    public static List<Task> getTasksByGroup(String groupId)
    {
        return TaskRepository.findByGroupId(groupId);
    }

    public static Task findTaskById(String taskId)
    {
        return TaskRepository.findByTaskId(taskId);
    }

    public static boolean updateTaskStatus(String taskId, TaskStatus status)
    {
        Task task = findTaskById(taskId);
        if(task==null)
        {
            return false;
        }
        task.setStatus(status);
        TaskRepository.update(task);
        return true;
    }

    public static boolean updateTaskPriority(String taskId, TaskPriority priority)
    {
        Task task = findTaskById(taskId);
        if(task==null)
        {
            return false;
        }
        task.setPriority(priority);
        TaskRepository.update(task);
        return true;
    }

    public static boolean editTask(String taskId, String title, String desc)
    {
        Task task = findTaskById(taskId);
        if(task==null)
        {
            return false;
        }
        if(Validator.isValidTaskTitle(title))
        {
            task.setTitle(title);
        }
        else
        {
            return false;
        }
        if(desc.length()<=200 && desc.length()>=3)
        {
            task.setDescription(desc);
        }
        else
        {
            return false;
        }
        TaskRepository.update(task);
        return true;
    }

    public static boolean deleteTask(String taskId)
    {
        Task task = findTaskById(taskId);
        if(task==null)
        {
            return false;
        }
        TaskRepository.delete(task.getTaskId());
        return true;
    }


    public static List<Task> searchTasks(String keyword)
    {
        List<Task>tasks = TaskRepository.findAll();
        List<Task>searchTasks = new ArrayList<>();
        for(var task:tasks)
        {
            if(task.getDescription().contains(keyword)||task.getTitle().contains(keyword))
            {
                searchTasks.add(task);
            }
        }
        return searchTasks;
    }

    public static void assignGroupTasksToMember(String groupId,String newMemberId)
    {
        List<Task> groupTasks = getTasksByGroup(groupId);
        List <Task> memberExistTasks = getTaskByUser(newMemberId);
        for(var task:groupTasks)
        {
           if(!task.getMemberIds().contains(newMemberId))
           {
               task.addMemberId(newMemberId);
               TaskRepository.update(task);
           }
        }
    }

    public static void removeGroupTasksFromMember(String groupId,String newMemberId)
    {
        List<Task> memberTasks = getTaskByUser(newMemberId);
        for (Task task : memberTasks) {
            task.removeMemberId(newMemberId);
            TaskRepository.update(task);
        }
    }









}
