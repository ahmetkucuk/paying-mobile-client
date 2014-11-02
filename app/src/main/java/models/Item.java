package models;

/**
 * Created by ahmetkucuk on 01/11/14.
 */
public class Item {

    private String name;
    private int quantity;
    private double price;

    @Override
    public String toString() {
        return name + " " + quantity + " " + price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
