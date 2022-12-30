package com.example.wagba.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public static boolean isInvalidEmail(String email) {
            Pattern pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@eng\\.asu\\.edu\\.eg$");
        Matcher matcher = pattern.matcher(email);
        return !matcher.matches();
    }
    public static boolean isInvalidPassword(String password) {
        return password.length() < 6;
    }
    public static boolean isValidName(String password) {
        return password.length() >= 3;
    }

}
