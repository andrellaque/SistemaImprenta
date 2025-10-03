package pe.edu.utp.sistemaimprenta.model;

public class Customer {

    private int id;
    private  String dni;
    private String name;
    private String lastName;
    private String telephoneNumber;
    private String email;
    private String address;

    public Customer(int id, String DNI, String name, String lastName, String telephoneNumber, String email, String address) {
        this.id = id;
        this.dni = DNI;
        this.name = name;
        this.lastName = lastName;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.address = address;
    }

    public Customer(String DNI, String name, String lastName, String telephoneNumber, String email, String address) {
        this.dni = DNI;
        this.name = name;
        this.lastName = lastName;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.address = address;
    }

    public Customer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String DNI) {
        this.dni = DNI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    
    
}
