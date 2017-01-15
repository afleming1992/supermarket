package me.ajfleming.qikserve.dao;

import me.ajfleming.qikserve.model.FreeItemPromotion;
import me.ajfleming.qikserve.model.Item;
import me.ajfleming.qikserve.model.MoneyOffPromotion;
import me.ajfleming.qikserve.model.Promotion;
import me.ajfleming.qikserve.type.DeleteStatus;

import java.util.List;

/**
 * Created by andrew on 15/01/17.
 */
public interface PromotionDAO {

    FreeItemPromotion save(FreeItemPromotion promo);
    MoneyOffPromotion save(MoneyOffPromotion promo);

    List<Promotion> getPromotions();
    Promotion getPromotion(int id);

    List<Promotion> getAnItemsPromotions(Item item);

    DeleteStatus deletePromotion(int id);
}
