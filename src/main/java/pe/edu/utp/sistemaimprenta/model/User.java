package pe.edu.utp.sistemaimprenta.model;
import java.time.LocalDateTime;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private UserType type;
    private LocalDateTime createdAt;

    public User(String username, String password, String email,UserType type) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.type = type;
    } 
}

