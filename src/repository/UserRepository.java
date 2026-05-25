package repository;

import model.User;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static final String USER_FILE_PATH = "data/users.txt";
    private static User fromLine(String line)
    {
        String[] parts = line.split("\\|");
        User user = new User(parts[0],parts[1],parts[2]);
        return user;
    }

    private static String toLine(User user)
    {
        return user.getUserId() + "|" + user.getUsername() + "|" + user.getPassword();
    }

    public static void save(User user)
    {
        FileStorage.appendLine(USER_FILE_PATH,toLine(user));
    }

    public static List<User> findAll()
    {
        List<String> lines = FileStorage.readAllLines(USER_FILE_PATH);
        List<User>users=new ArrayList<>();
        for(String line:lines)
        {
            users.add(fromLine(line));
        }
        return users;
    }

    public static User findByUserId(String userId)
    {
        List<User>users=findAll();
        for(User user:users)
        {
            if(user.getUserId().equals(userId))
            {
                return user;
            }
        }
        return null;
    }

    public static User findByUsername(String username)
    {
        List<User>users=findAll();
        for(User user:users)
        {
            if(user.getUsername().equals(username))
            {
                return user;
            }
        }
        return null;
    }

    public static void update(User user)
    {
        List<User>users = findAll();
        for(int i=0;i<users.size();i++)
        {
            if(users.get(i).getUserId().equals(user.getUserId()))
            {
                users.set(i,user);
                break;
            }
        }
        List<String> lines = new ArrayList<>();
        for(User u:users)
        {
            lines.add(toLine(u));
        }
        FileStorage.writeAllLines(USER_FILE_PATH,lines);
    }

    public static void delete(String userId)
    {
        List<User>users = findAll();
        for(int i=0;i<users.size();i++)
        {
            if(users.get(i).getUserId().equals(userId))
            {
                users.remove(i);
                break;
            }
        }
        List<String> lines = new ArrayList<>();
        for(User u:users)
        {
            lines.add(toLine(u));
        }
        FileStorage.writeAllLines(USER_FILE_PATH,lines);
    }
}
