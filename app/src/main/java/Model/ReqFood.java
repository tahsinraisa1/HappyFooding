package Model;

public class ReqFood {
    private String ProductName, Price, Quantity, ProductId;

    public ReqFood() {
    }

    public ReqFood(String productName, String price, String productId, String quantity) {
        ProductId = productId;
        ProductName = productName;
        Price = price;
        Quantity = quantity;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }
}
