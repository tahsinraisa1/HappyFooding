package Model;

public class Category {
    private String Name;
    private String Image;
    private String Price;

    public Category() {
    }

    public Category(String name, String image, String price) {
        this.Name = name;
        this.Image = image;
        this.Price = price;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        this.Image = image;
    }
}
