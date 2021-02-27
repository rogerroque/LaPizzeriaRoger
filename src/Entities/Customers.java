package Entities;


import java.io.Serializable;

public class Customers implements Serializable {
    private int idCustomer;
    private String name;
    private String address;
    private String email;
    private String phone;

    public Customers() {
    }

    public Customers(int idCustomer, String name, String address, String email, String phone) {
        this.idCustomer = idCustomer;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Customers{" +
                "idCustomer=" + idCustomer +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
