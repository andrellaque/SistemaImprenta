package pe.edu.utp.sistemaimprenta.model;

public class Customer {

    private int id;
    private final String DNI;
    private String name;
    private String telephoneNumber;
    private String email;
    private String address;

    public Customer(int id, String DNI, String name, String telephoneNumber, String email, String address) {
        this.id = id;
        this.DNI = DNI;
        this.name = name;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.address = address;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDNI() {
        return DNI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", DNI='" + DNI + '\'' +
                ", name='" + name + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
