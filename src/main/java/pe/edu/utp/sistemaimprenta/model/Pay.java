package pe.edu.utp.sistemaimprenta.model;

import java.time.LocalDateTime;

public class Pay {
    private int id;
    private Order order;
    private double amount;
    private LocalDateTime payDate;
    private PayType method;

    public Pay(int id, Order order, double amount, LocalDateTime payDate, PayType method) {
        this.id = id;
        this.order = order;
        this.amount = amount;
        this.payDate = payDate;
        this.method = method;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getPayDate() {
        return payDate;
    }

    public void setPayDate(LocalDateTime payDate) {
        this.payDate = payDate;
    }

    public PayType getMethod() {
        return method;
    }

    public void setMethod(PayType method) {
        this.method = method;
    }
}

