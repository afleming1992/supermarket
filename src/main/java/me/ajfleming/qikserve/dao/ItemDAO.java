package me.ajfleming.qikserve.dao;

import me.ajfleming.qikserve.model.Basket;
import me.ajfleming.qikserve.model.Item;
import me.ajfleming.qikserve.model.Promotion;
import me.ajfleming.qikserve.type.DeleteStatus;

import java.util.List;

/**
 *  Class: ItemDAO
 *  Purpose: Interface for Accessing and Saving Items to Database
 *  Author: Andrew Fleming
 */

public interface ItemDAO {

    Item save(Item item);

    List<Item> getItems();
    Item getItem(int id);
    Item getItem(String barcode);
    DeleteStatus deleteItem(int id);
}
