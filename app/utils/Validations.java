package utils;

import java.util.regex.Matcher;

public class Validations {

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = Patterns.VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

}
