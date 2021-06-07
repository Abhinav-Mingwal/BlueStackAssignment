package com.abhinav;

import java.util.regex.PatternSyntaxException;

public class Utils {

    //Validation check over Username String
    public static boolean isValidUsername(String input){
        try{
            String pattern = "^[a-zA-Z0-9._-]{5,10}$";
            return input.matches(pattern);
        }catch (PatternSyntaxException e){
            e.printStackTrace();
        }
        return false;
    }

    //Validation check over Password String
    public static boolean isValidPassword(String input){
        try{
            String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
            return input.matches(pattern);
        }catch (PatternSyntaxException e){
            e.printStackTrace();
        }
        return false;
    }
}
