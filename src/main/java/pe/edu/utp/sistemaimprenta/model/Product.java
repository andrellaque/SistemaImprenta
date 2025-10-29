package pe.edu.utp.sistemaimprenta.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private int id;
    private String name;
    private String description;
    private ProductType type;
    private Double basePrice;
    private String state;
    private boolean isActive; 

    public Product(String name, String description, ProductType type, Double basePrice, String state) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.basePrice = basePrice;
        this.state = state;
        isActive=state.equalsIgnoreCase("activo") ? true : false;
    }
}
