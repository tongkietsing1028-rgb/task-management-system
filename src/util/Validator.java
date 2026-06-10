package util;

public class Validator {
    private static boolean isNotEmpty(String s)
    {
        return s != null && !s.trim().isEmpty();
    }
    public static boolean isValidUsername(String username)
    {
        return isNotEmpty(username)&&username.length()<= 50&&username.length()>= 3 ;
    }



    public static boolean isValidPassword(String password)
    {
        return isNotEmpty(password)&&password.length() >= 6;
    }



    public static boolean isValidTaskTitle(String title)
    {
        return isNotEmpty(title)&&title.length()<= 20&&title.length()>= 3 ;
    }
}
