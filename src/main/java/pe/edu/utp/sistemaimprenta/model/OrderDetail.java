package pe.edu.utp.sistemaimprenta.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
    private int id;                
    private Product product;      
    private int quantity;         
    private double unitPrice;      
    private double subtotal;      

    public OrderDetail(Product product, int quantity, double unitPrice) {
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = quantity * unitPrice;
    }
}
