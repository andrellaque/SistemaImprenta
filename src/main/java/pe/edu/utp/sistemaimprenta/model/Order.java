package pe.edu.utp.sistemaimprenta.model;

import java.time.LocalDateTime;
import java.util.List;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private int id;                      
    private Customer customer;              
    private User user;                     
    private OrderState state;              
    private LocalDateTime createdAt;      
    private LocalDateTime deliveryDate;   
    private double totalAmount;           
    private List<OrderDetail> details;     

    public Order(Customer customer, User user, OrderState state,
                 LocalDateTime createdAt, LocalDateTime deliveryDate,
                 double totalAmount, List<OrderDetail> details) {
        this.customer = customer;
        this.user = user;
        this.state = state;
        this.createdAt = createdAt;
        this.deliveryDate = deliveryDate;
        this.totalAmount = totalAmount;
        this.details = details;
    }
}
