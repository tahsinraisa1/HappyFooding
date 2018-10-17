package Model;

public class User {
    private String Name;
    private String Password;
    private String Conpass;
    private String Address;

    public User() {
    }

    public User(String name, String address, String password, String conpass) {
        Name = name;
        Password = password;
        Conpass = conpass;
        Address = address;
    }

    public String getName() {
        return Name;
    }

    public String getConpass() {
        return Conpass;
    }

    public void setConpass(String conpass) {
        Conpass = conpass;
    }

    public void setName(String name) {

        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}


