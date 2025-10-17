package pe.edu.utp.sistemaimprenta.util;

import java.util.regex.Pattern;

public class Validator {

    private static final String DNI_REGEX = "^\\d{8}$";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9._-]{4,12}$";
    private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,12}$";

    private Validator() {
    }

    public static boolean isValidUsername(String username) {
        return Pattern.matches(USERNAME_REGEX, username);
    }

    public static boolean isValidPassword(String password) {
        return Pattern.matches(PASSWORD_REGEX, password);
    }

    public static boolean isValidDNI(String dni) {
        return Pattern.matches(DNI_REGEX, dni);
    }

    public static boolean isValidEmail(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }
}
