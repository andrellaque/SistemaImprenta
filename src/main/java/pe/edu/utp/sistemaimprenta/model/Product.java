package pe.edu.utp.sistemaimprenta.model;

public class Product {
    private int id;
    private ProductType type;
    private String description;
    private double unitPrice;

    public Product(int id, ProductType name, String description, double unitPrice) {
        this.id = id;
        this.type = name;
        this.description = description;
        this.unitPrice = unitPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductType getName() {
        return type;
    }

    public void setName(ProductType name) {
        this.type = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + type + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
