package Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Order implements Parcelable{
    private String ProductId;
    private String ProductName;
    private String Quantity;
    private String Price;

    public Order(){
    }
    public Order(String productId, String productName,String quantity,String price){
        ProductId = productId;
        ProductName = productName;
        Quantity = quantity;
        Price = price;

    }

    public Order(String productName) {
        ProductName = productName;
    }

    protected Order(Parcel in) {
        ProductId = in.readString();
        ProductName = in.readString();
        Quantity = in.readString();
        Price = in.readString();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ProductId);
        dest.writeString(ProductName);
        dest.writeString(Quantity);
        dest.writeString(Price);
    }
}