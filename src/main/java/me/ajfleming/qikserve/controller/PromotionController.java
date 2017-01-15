package me.ajfleming.qikserve.controller;

import me.ajfleming.qikserve.dao.ItemDAO;
import me.ajfleming.qikserve.dao.ItemDAOImpl_JDBC;
import me.ajfleming.qikserve.dao.PromotionDAO;
import me.ajfleming.qikserve.dao.PromotionDAOImpl_JDBC;
import me.ajfleming.qikserve.helpers.ListToHashMapConverter;
import me.ajfleming.qikserve.model.FreeItemPromotion;
import me.ajfleming.qikserve.model.Item;
import me.ajfleming.qikserve.model.MoneyOffPromotion;
import me.ajfleming.qikserve.model.Promotion;
import me.ajfleming.qikserve.type.DeleteStatus;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by andrew on 15/01/17.
 */
public class PromotionController {

    private PromotionDAO db;
    private ItemDAO itemDb;

    PromotionController(DataSource ds)
    {
        db = new PromotionDAOImpl_JDBC(ds);
        itemDb = new ItemDAOImpl_JDBC(ds);
    }

    MoneyOffPromotion save(MoneyOffPromotion promotion){
        return db.save(promotion);
    }

    FreeItemPromotion save(FreeItemPromotion promotion){
        return db.save(promotion);
    }

    List<Promotion> getAllPromotions(){
        return db.getPromotions();
    }

    Promotion getPromotion(int id){
        Promotion promo = db.getPromotion(id);
        if(promo != null)
        {
            List<Item> items = itemDb.getPromotionItems(promo);
            promo.setValidItems(ListToHashMapConverter.convertItemListToHashMap(items));
        }
        return promo;
    }

    DeleteStatus deletePromotion(int promoId) {
        return db.deletePromotion(promoId);
    }
}
