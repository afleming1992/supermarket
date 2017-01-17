package me.ajfleming.qikserve.dao;

import me.ajfleming.qikserve.model.Basket;
import me.ajfleming.qikserve.model.BasketPromotion;
import me.ajfleming.qikserve.model.Item;

import java.util.List;

/**
 * Created by andrew on 15/01/17.
 */
public interface BasketDAO {

    int createBasket();
    Basket editBasket(Basket basket);
    Basket getBasket(int id);
    List<Basket> getBaskets();

    boolean addItemToBasket(Basket basket, Item item);
    boolean removeItemToBasket(Basket basket, Item item);
}
