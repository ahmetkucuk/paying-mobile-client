package models;

import java.io.Serializable;

/**
 * Created by ahmetkucuk on 01/11/14.
 */
public class CreditCard implements Serializable{

    private String cardNumber;
    private String userName;
    private String expireDate;
    private String ccv;

    public CreditCard(String cardNumber, String userName, String expireDate, String ccv, String cardName) {
        this.cardNumber = cardNumber;
        this.userName = userName;
        this.expireDate = expireDate;
        this.ccv = ccv;
        this.cardName = cardName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getCcv() {
        return ccv;
    }

    public void setCcv(String ccv) {
        this.ccv = ccv;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    private String cardName;
}
