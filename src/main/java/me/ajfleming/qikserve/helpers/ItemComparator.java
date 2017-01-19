package me.ajfleming.qikserve.helpers;

import me.ajfleming.qikserve.model.Item;

import java.util.Comparator;

/**
 * Created by andrew on 19/01/17.
 */
public class ItemComparator implements Comparator<Item> {
    @Override
    public int compare(Item one, Item another) {
        if(one.getPrice() < another.getPrice()){
            return -1;
        }
        else if(one.getPrice() > another.getPrice())
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }
}
