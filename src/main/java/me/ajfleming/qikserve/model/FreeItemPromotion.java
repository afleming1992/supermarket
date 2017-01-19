package me.ajfleming.qikserve.model;

import me.ajfleming.qikserve.helpers.ItemComparator;
import me.ajfleming.qikserve.helpers.MathsOperations;

import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by andrew on 14/01/17.
 */
@Table(name = "promotion")
public class FreeItemPromotion extends Promotion {

    @NotNull(message = "noOfItemsFree must be set")
    @Min(value = 1, message = "noOfItemsFree must be greater than 0")
    private int noOfItemsFree;
    public final String type = "MultiBuy";

    public FreeItemPromotion()
    {
        super();
    }

    public FreeItemPromotion(String name, boolean active, int noOfItemsRequired, int noOfItemsFree)
    {
        super(name,active);
        this.noOfItemsFree = noOfItemsFree;
    }

    public FreeItemPromotion(String name, boolean active, int noOfItemsRequired, int noOfItemsFree, ArrayList<Item> validItems) {
        super(name, active, noOfItemsRequired,validItems);
        this.noOfItemsFree = noOfItemsFree;
    }

    public FreeItemPromotion(int id, String name, boolean active, int noOfItemsRequired, int noOfItemsFree, ArrayList<Item> validItems) {
        super(id, name, active, noOfItemsRequired, validItems);
        this.noOfItemsFree = noOfItemsFree;
    }

    @Override
    public float calculateFinalPrice(List<Item> items) {
        if(items.size() < getNoOfItemsRequired())
        {
            return MathsOperations.calculatePriceOfItems(items);
        }

        Collections.sort(items, new ItemComparator());
        float runningTotal = 0;
        for(int i = noOfItemsFree; i < items.size();i++)
        {
            runningTotal += items.get(i).getPrice();
        }
        return runningTotal;
    }

    public int getNoOfItemsFree() {
        return noOfItemsFree;
    }

    public void setNoOfItemsFree(int noOfItemsFree) {
        this.noOfItemsFree = noOfItemsFree;
    }
}
