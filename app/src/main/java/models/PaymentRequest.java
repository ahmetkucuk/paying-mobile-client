package models;

/**
 * Created by ahmetkucuk on 01/11/14.
 */
public class PaymentRequest {

    private CreditCard creditCard;
    private String tableId;
    private double amountToPay;
    private int type;

    public PaymentRequest(CreditCard creditCard, String tableId, double amountToPay, int type) {
        this.creditCard = creditCard;
        this.tableId = tableId;
        this.amountToPay = amountToPay;
        this.type = type;
    }
}
