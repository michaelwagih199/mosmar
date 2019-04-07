
package entities;

public class custom_BuyTable {

    int idProduct;
    String productName;
    float quantity, price, total;

    public custom_BuyTable(int idProduct, String productName, float quantity, float price, float total) {
        this.idProduct = idProduct;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

}
