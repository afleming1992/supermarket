package me.ajfleming.qikserve.dao;

import me.ajfleming.qikserve.model.Basket;
import me.ajfleming.qikserve.model.BasketPromotion;
import me.ajfleming.qikserve.model.Item;
import me.ajfleming.qikserve.model.Promotion;

import java.util.List;

/**
 * Created by andrew on 17/01/17.
 */
public interface BasketPromotionDAO {

    public BasketPromotion save(BasketPromotion basketPromo);
    public boolean deleteBasketPromotion(BasketPromotion basketPromo);

    List<Item> getBasketPromotionItems(BasketPromotion promo);

    public boolean addItemToBasketPromotion(BasketPromotion promo, Item item);
    public boolean deleteItemToBasketPromotion(BasketPromotion promo, Item item);
}
