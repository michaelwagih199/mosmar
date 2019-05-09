package entities;
public class Custom_OrderDetails {
    private String ProductName;
    private float price,quantity,total;

    public Custom_OrderDetails(String ProductName, float price, float quantity, float total) {
        this.ProductName = ProductName;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String ProductName) {
        this.ProductName = ProductName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
    
    
}
