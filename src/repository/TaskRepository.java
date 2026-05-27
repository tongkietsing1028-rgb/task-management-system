package repository;

import model.Task;
import model.User;
import model.enums.TaskPriority;
import model.enums.TaskStatus;

import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

        private static final String TASK_FILE_PATH = "src/data/tasks.txt";
        private static Task fromLine(String line)
        {
            String[] parts = line.split("\\|");
            Task task = new Task(parts[0],parts[1],parts[2],parts[3],parts[4], TaskStatus.valueOf(parts[5]), TaskPriority.valueOf(parts[6]));
            return task;
        }

        private static String toLine(Task task)
        {
            return task.getTaskId() + "|" + task.getTitle() + "|" + task.getDescription() + "|"+ task.getOwnerId() + "|" + task.getGroupId() + "|" + task.getStatus()+"|"+task.getPriority() ;
        }

        public static void save(Task task)
        {
            FileStorage.appendLine(TASK_FILE_PATH,toLine(task));
        }

        public static List<Task> findAll()
        {
            List<String> lines = FileStorage.readAllLines(TASK_FILE_PATH);
            List<Task>tasks=new ArrayList<>();
            for(String line:lines)
            {
                tasks.add(fromLine(line));
            }
            return tasks;
        }

        public static Task findByTaskId(String taskId)
        {
            List<Task>tasks=findAll();
            for(Task task:tasks)
            {
                if(task.getTaskId().equals(taskId))
                {
                    return task;
                }
            }
            return null;
        }

        public static List<Task> findByUserId(String userId)//ownerId
        {
            List<Task>tasks=findAll();
            List<Task>userTasks=new ArrayList<>();
            for(Task task:tasks)
            {
                if(task.getOwnerId().equals(userId))
                {
                    userTasks.add(task);

                }
            }
            return userTasks;
        }

        public  static List<Task> findByGroupId(String groupId)
        {
            List<Task>tasks=findAll();
            List<Task>groupTasks=new ArrayList<>();
            for(Task task:tasks)
            {
                if(task.getGroupId().equals(groupId))
                {
                    groupTasks.add(task);

                }
            }
            return groupTasks;
        }

        public static void update(Task task)
        {
            List<Task>tasks = findAll();
            for(int i=0;i<tasks.size();i++)
            {
                if(tasks.get(i).getTaskId().equals(task.getTaskId()))
                {
                    tasks.set(i,task);
                    break;
                }
            }
            List<String> lines = new ArrayList<>();
            for(Task t:tasks)
            {
                lines.add(toLine(t));
            }
            FileStorage.writeAllLines(TASK_FILE_PATH,lines);
        }

        public static void delete(String taskId)
        {
            List<Task>tasks = findAll();
            for(int i=0;i<tasks.size();i++)
            {
                if(tasks.get(i).getTaskId().equals(taskId))
                {
                    tasks.remove(i);
                    break;
                }
            }
            List<String> lines = new ArrayList<>();
            for(Task t:tasks)
            {
                lines.add(toLine(t));
            }
            FileStorage.writeAllLines(TASK_FILE_PATH,lines);
        }

}
