package me.ajfleming.qikserve.dao;

import me.ajfleming.qikserve.model.FreeItemPromotion;
import me.ajfleming.qikserve.model.Item;
import me.ajfleming.qikserve.model.MoneyOffPromotion;
import me.ajfleming.qikserve.model.Promotion;
import me.ajfleming.qikserve.type.DeleteStatus;

import java.util.List;

/**
 *  Class: PromotionDAO
 *  Purpose: Interface for the saving and accessing of Promotion Objects.
 *  Author: Andrew Fleming
 */

public interface PromotionDAO {

    FreeItemPromotion save(FreeItemPromotion promo);
    MoneyOffPromotion save(MoneyOffPromotion promo);

    List<Promotion> getPromotions();
    Promotion getPromotion(int id);

    List<Promotion> getAnItemsPromotions(Item item);
    List<Item> getPromotionItems(Promotion promo);

    boolean addItemToPromotion(Promotion promo, Item item);
    boolean removeItemFromPromotion(Promotion promo, Item item);

    DeleteStatus deletePromotion(int id);
}
