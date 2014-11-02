package core;

import java.util.ArrayList;
import java.util.List;

import models.CreditCard;

/**
 * Created by ahmetkucuk on 02/11/14.
 */
public class CreditCardHolder {

    private List<CreditCard> creditCardList = new ArrayList<CreditCard>();


    private static CreditCardHolder instance = new CreditCardHolder();

    private CreditCardHolder() {
        creditCardList.add(new CreditCard("46087717752", "Ahmet", "0611", "238", "Ing Bank"));
        creditCardList.add(new CreditCard("43214325232", "alper cem", "23119", "700", "Ing Bank"));
    }

    public static CreditCardHolder getInstance() {
        return instance;
    }

    public List<CreditCard> getCreditCardList() {
        return creditCardList;
    }

    public void addCreditCard(CreditCard creditCard) {
        creditCardList.add(creditCard);
    }
}
