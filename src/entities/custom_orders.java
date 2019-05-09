
package entities;

import java.util.Date;

public class custom_orders {
    private int orderId;
    private Date orderDate;
    private String customerName;
   
    private float totslCost,paid,remaining,orderDiscount;

    public custom_orders(int orderId, Date orderDate, String customerName, float totslCost, float paid, float remaining, float orderDiscount) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerName = customerName;
        this.totslCost = totslCost;
        this.paid = paid;
        this.remaining = remaining;
        this.orderDiscount = orderDiscount;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public float getTotslCost() {
        return totslCost;
    }

    public void setTotslCost(float totslCost) {
        this.totslCost = totslCost;
    }

    public float getPaid() {
        return paid;
    }

    public void setPaid(float paid) {
        this.paid = paid;
    }

    public float getRemaining() {
        return remaining;
    }

    public void setRemaining(float remaining) {
        this.remaining = remaining;
    }

    public float getOrderDiscount() {
        return orderDiscount;
    }

    public void setOrderDiscount(float orderDiscount) {
        this.orderDiscount = orderDiscount;
    }
    
    

  

   
    
}
