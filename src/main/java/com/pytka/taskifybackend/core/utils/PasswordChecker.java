package com.pytka.taskifybackend.core.utils;

public class PasswordChecker {

    public static boolean isValidPassword(String password){

        boolean containsLowercase = false;
        boolean containsUppercase = false;
        boolean containsDigit = false;

        if(password.length() < 8){
            return false;
        }

        for(int i = 0; i < password.length(); i++){
            if(Character.isLowerCase(password.charAt(i))){
                containsLowercase = true;
            }

            if(Character.isUpperCase(password.charAt(i))){
                containsUppercase = true;
            }

            if(Character.isDigit(password.charAt(i))){
                containsDigit = true;
            }

            if(containsLowercase && containsUppercase && containsDigit){
                return true;
            }
        }

        return false;
    }
}
