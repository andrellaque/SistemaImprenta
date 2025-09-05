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

    public static String validateInputFieldsRegister(String username, String email, String password, String confirmPassword) {
        if (username == null || username.trim().isEmpty()) {
            return "El nombre de usuario es obligatorio";
        }
        if (email == null || email.trim().isEmpty()) {
            return "El correo electrónico es obligatorio";
        }
        if (!Validator.isValidEmail(email)) {
            return "El correo electrónico no es válido";
        }
        if (password == null || password.trim().isEmpty()) {
            return "La contraseña es obligatoria";
        }
        if (!password.equals(confirmPassword)) {
            return "Las contraseñas no coinciden";
        }
        return null;
    }

    public static String validateInputFieldsLogin(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            return "Debe ingresar su nombre de usuario";
        }
        if (password == null || password.trim().isEmpty()) {
            return "Debe ingresar su contraseña";
        }
        return null;
    }

    
}
