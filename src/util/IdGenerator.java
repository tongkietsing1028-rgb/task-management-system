package util;

import repository.GroupRepository;
import repository.TaskRepository;
import repository.UserRepository;

public class IdGenerator {
    public static String generateUserId()
    {
        int max = 0;
       for(var user : UserRepository.findAll())
       {
           String id = user.getUserId();
           int num = Integer.parseInt(id.substring(1));
           if(max<num)
           {
               max = num;
           }
       }
        return String.format("U%03d",max+1);
    }

    public static String generateTaskId()
    {
        int max = 0;
        for(var task : TaskRepository.findAll())
        {
            String id = task.getTaskId();
            int num = Integer.parseInt(id.substring(1));
            if(max<num)
            {
                max = num;
            }
        }
        return String.format("T%03d",max+1);
    }

    public static String generateGroupId()
    {
        int max = 0;
        for(var group : GroupRepository.findAll())
        {
            String id = group.getGroupId();
            int num = Integer.parseInt(id.substring(1));
            if(max<num)
            {
                max = num;
            }
        }
        return String.format("G%03d",max+1);
    }


}
