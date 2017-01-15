package me.ajfleming.qikserve.model;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Inherited;
import java.util.ArrayList;

/**
 * Created by andrew on 14/01/17.
 */
@Table(name = "promotion")
public class MoneyOffPromotion extends Promotion {

    @NotNull(message = "Total Price must be set")
    private float totalPrice;
    public final String type = "MoneyOff";

    public MoneyOffPromotion()
    {
        super();
    }

    public MoneyOffPromotion(String name, boolean active, int noOfItemsRequired, float totalPrice)
    {
        super(name,active);
        this.totalPrice = totalPrice;
    }

    public MoneyOffPromotion(String name, boolean active, int noOfItemsRequired, float totalPrice, ArrayList<Item> validItems) {
        super(name, active, noOfItemsRequired, validItems);
        this.totalPrice = totalPrice;
    }

    public MoneyOffPromotion(int id, String name, boolean active, int noOfItemsRequired, int noOfItemsFree, float totalPrice, ArrayList<Item> validItems) {
        super(id, name, active, noOfItemsRequired, validItems);
        this.totalPrice = totalPrice;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
