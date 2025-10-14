package pe.edu.utp.sistemaimprenta.model;

import java.time.LocalDateTime;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    private int id;
    private  String dni;
    private String name;
    private String lastName;
    private String telephoneNumber;
    private String email;
    private String address;
    private LocalDateTime createdAt;
    
    public Customer(String DNI, String name, String lastName, String telephoneNumber, String email, String address, LocalDateTime createdAt) {
        this.dni = DNI;
        this.name = name;
        this.lastName = lastName;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.address = address;
        this.createdAt= createdAt;
    } 
}
