package Controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Checks the strength of users inputted password after clicking Sign Up.
 */
public class PasswordChecker {

    /**
     *
     * @param password the password to be checked.
     * @return a string determining if the password is weak, medium or strong.
     */
    public String scorePassword(String password) {

        String regexStrong = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=)(])"
                + "(?=\\S+$).{8,20}$";

        String regexMedium = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=\\S+$).{8,20}$";


        Pattern p1 = Pattern.compile(regexStrong);
        Pattern p2 = Pattern.compile(regexMedium);

        if (password == null) {
            return "Weak Password";
        }

        Matcher m1 = p1.matcher(password);
        Matcher m2 = p2.matcher(password);

        if (m1.matches()){
            return "Strong Password";
        }
        else if (m2.matches()){
            return "Medium Password";
        }
        else {
            return "Weak Password";
        }
    }
}
