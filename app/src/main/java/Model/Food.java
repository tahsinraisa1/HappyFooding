package Model;

public class Food {
    private String Name, Image, Price, Description, MmenuId;

    public Food() {
    }

    public Food(String name, String image, String price, String description, String MenuId) {
        Name = name;
        Image = image;
        Price = price;
        Description = description;
        MmenuId = MenuId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getMenuId() {
        return MmenuId;
    }

    public void setMenuId(String mmenuId) {
        MmenuId = mmenuId;
    }
}
