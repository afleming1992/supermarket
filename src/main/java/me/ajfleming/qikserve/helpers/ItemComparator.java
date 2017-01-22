package me.ajfleming.qikserve.helpers;

import me.ajfleming.qikserve.model.Item;

import java.util.Comparator;

/**
 *  Class: ItemComparator
 *  Purpose: This Comparator is used to help sort items into ascending order based on price.
 *  Author: Andrew Fleming
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
