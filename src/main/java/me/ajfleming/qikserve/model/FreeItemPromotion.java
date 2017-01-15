package me.ajfleming.qikserve.model;

import org.joda.time.DateTime;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

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

    public int getNoOfItemsFree() {
        return noOfItemsFree;
    }

    public void setNoOfItemsFree(int noOfItemsFree) {
        this.noOfItemsFree = noOfItemsFree;
    }
}
