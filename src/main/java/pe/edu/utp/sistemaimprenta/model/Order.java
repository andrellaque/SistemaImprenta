package pe.edu.utp.sistemaimprenta.model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private int id;
    private Customer customer;
    private List<Product> products;
    private LocalDateTime initDate;
    private LocalDateTime deliveryDate;
    private ProductionState state;
    private double totalPrice;

    public Order(int id, Customer customer, List<Product> products,
                 LocalDateTime initDate, LocalDateTime deliveryDate,
                 ProductionState state) {
        this.id = id;
        this.customer = customer;
        this.products = products;
        this.initDate = initDate;
        this.deliveryDate = deliveryDate;
        this.state = state;
        setTotalPrice();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public LocalDateTime getInitDate() {
        return initDate;
    }

    public void setInitDate(LocalDateTime initDate) {
        this.initDate = initDate;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public ProductionState getState() {
        return state;
    }

    public void setState(ProductionState state) {
        this.state = state;
    }

    public void setTotalPrice() {
        this.totalPrice = products.stream()
                .mapToDouble(Product::getUnitPrice)
                .sum();;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customer=" + customer +
                ", products=" + products +
                ", initDate=" + initDate +
                ", deliveryDate=" + deliveryDate +
                ", state=" + state +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
