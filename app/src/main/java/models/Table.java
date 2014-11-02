package models;

import java.util.List;
import java.util.SimpleTimeZone;

/**
 * Created by ahmetkucuk on 01/11/14.
 */
public class Table {

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    private String id;
    private String restaurantID;
    private double totalAmount;
    private double paidAmount;
    private List<Item> itemList;


    public Table(String id, String restaurantID, double totalAmount, double paidAmount) {
        this.id = id;
        this.restaurantID = restaurantID;
        this.totalAmount = totalAmount;
        this.paidAmount = paidAmount;
    }

    @Override
    public String toString() {
        String result = "id: " + id + " resID: " + restaurantID + " total Amount: " + totalAmount + " paidAmount: " + paidAmount + "\n";
        for(Item i : itemList)
            result += i.toString() + "\n";
        return result;
    }
}
