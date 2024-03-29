package me.ajfleming.qikserve.dao;

import me.ajfleming.qikserve.model.Basket;
import me.ajfleming.qikserve.model.BasketPromotion;
import me.ajfleming.qikserve.model.Item;
import me.ajfleming.qikserve.model.Promotion;

import java.util.List;

/**
 *  Class: BasketPromotionDAO
 *  Purpose: Interface for Accessing and Saving Basket Promotions
 *  Author: Andrew Fleming
 */

public interface BasketPromotionDAO {

    BasketPromotion save(BasketPromotion basketPromo);
    boolean deleteBasketPromotion(BasketPromotion basketPromo);

    BasketPromotion getBasketPromotion(int basketPromotionId);
    List<BasketPromotion> getBasketPromotions(Basket basket);

    List<Item> getBasketPromotionItems(BasketPromotion promo);

    boolean addItemToBasketPromotion(BasketPromotion promo, Item item);
}
