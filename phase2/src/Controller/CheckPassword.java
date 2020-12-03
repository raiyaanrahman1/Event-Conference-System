package Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Checks the strength of users inputted password after clicking Sign Up.
 */

public class CheckPassword {
    int score = 0;

    public String scorePassword(String pass){
        boolean length = checkLength(pass);
        boolean variation = checkVariation(pass);
        checkUniqueLetters(pass);
        if (!length && !variation) {
            return "Weak Password";
        }else {
            if (score == 50) {
                return "Medium Password";
            } else {
                return "Strong Password";
            }
        }
    }

    public boolean checkLength(String pass){
        if (pass.matches("^(?=.{8,})")){
            score += 10;
            return true;
        }else{
            System.out.println("Password has to be longer than 8 characters");
            return false;
        }
    }

    public boolean checkVariation(String pass){
        if (!pass.matches("^(?=.*[a-z])") && !pass.matches("^(?=.*[A-Z])") &&
                !pass.matches("^(?=.*[0-9])") && !pass.matches("^(?=.[!@#$%^&])")){
            return false;
        }else {
            score += 40;
            return true;
        }
    }

    public void checkUniqueLetters(String pass) {
        List<Character> done = new ArrayList<>();
        if (pass.length() != 0) {
            for (char c : pass.toCharArray()) {
                if (!done.contains(c)) {
                    done.add(c);
                    score += 5;
                }
            }
        }
    }

}
