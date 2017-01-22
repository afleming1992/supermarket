package me.ajfleming.qikserve.model;

import me.ajfleming.qikserve.helpers.MathsOperations;

import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 *  Class: MoneyOffPromotion
 *  Purpose: This class holds the specific specialisations of an Money Off Offer (E.g Buy 2 for £5 etc)
 *  Author: Andrew Fleming
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

    @Override
    public float calculateFinalPrice(List<Item> items) {
        if(items.size() >= getNoOfItemsRequired())
        {
            return totalPrice;
        }
        else
        {
            return MathsOperations.calculatePriceOfItems(items);
        }
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
