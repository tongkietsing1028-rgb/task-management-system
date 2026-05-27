package service;

import model.User;
import repository.UserRepository;
import util.IdGenerator;
import util.Validator;

public class AuthService {
    public static boolean userExists(String username)
    {
        User user = UserRepository.findByUsername(username);
        return user!=null;
    }
    public static boolean register(String username, String password)
    {
        if(userExists(username) || !Validator.isValidUsername(username) || !Validator.isValidPassword(password))
        {
            return false;
        }
        User user = new User(IdGenerator.generateUserId(), username, password);
        UserRepository.save(user);
        return true;
    }

    public static User login(String username, String password)
    {
        User user = UserRepository.findByUsername(username);
        if(user==null || !user.getPassword().equals(password))
        {
            return null;
        }
        return user;
    }

}
