package com.example.wagba.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public static String isValidEmail(String email) {
            Pattern pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@eng\\.asu\\.edu\\.eg$");
        Matcher matcher = pattern.matcher(email);
         if(!matcher.matches()){
             return "We only accept emails from @eng.asu.edu.eg";
         }
         return null;
    }
    public static String isValidPassword(String password) {
        if(password.length() < 6){
            return "Password should be at least 6 characters";
        }
        return null;
    }
    public static String isValidName(String name) {
        if(name.length() < 3){
            return "Name should be at least 3 characters";
        }
        return null;
    }

    public static String isValidConfirmPassword(String password, String confirmPassword) {
        if(!password.equals(confirmPassword)){
            return "Should be same as password";
        }
        return null;
    }

}
