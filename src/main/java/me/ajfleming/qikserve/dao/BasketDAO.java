package me.ajfleming.qikserve.dao;

import me.ajfleming.qikserve.holder.BasketItem;
import me.ajfleming.qikserve.holder.ValidPromotion;
import me.ajfleming.qikserve.model.Basket;
import me.ajfleming.qikserve.model.BasketPromotion;
import me.ajfleming.qikserve.model.Item;
import me.ajfleming.qikserve.model.Promotion;

import java.util.List;

/**
 * Created by andrew on 15/01/17.
 */
public interface BasketDAO {

    int createBasket();
    Basket updateBasket(Basket basket);
    Basket getBasket(int id);
    List<Basket> getBaskets();

    List<Item> getBasketItems(Basket basket);

    boolean addItemToBasket(Basket basket, Item item);
    boolean removeItemFromBasket(Basket basket, Item item);

    List<ValidPromotion> getAllValidPromotionsInBasket(Basket basket);
    List<BasketItem> getAllBasketItemsPartOfPromotion(Basket basket, Promotion promotion);
}
