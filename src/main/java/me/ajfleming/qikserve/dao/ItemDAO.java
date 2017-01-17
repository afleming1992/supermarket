package me.ajfleming.qikserve.dao;

import me.ajfleming.qikserve.model.Basket;
import me.ajfleming.qikserve.model.Item;
import me.ajfleming.qikserve.model.Promotion;
import me.ajfleming.qikserve.type.DeleteStatus;

import java.util.List;

/**
 * Created by andrew on 14/01/17.
 */
public interface ItemDAO {

    Item save(Item item);

    List<Item> getItems();
    Item getItem(int id);
    Item getItem(String barcode);
    DeleteStatus deleteItem(int id);

    List<Item> getPromotionItems(Promotion promo);
    List<Item> getBasketItems(Basket basket);
}
