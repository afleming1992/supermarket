package me.ajfleming.qikserve.controller;

import me.ajfleming.qikserve.dao.*;
import me.ajfleming.qikserve.model.FreeItemPromotion;
import me.ajfleming.qikserve.model.Item;
import me.ajfleming.qikserve.model.MoneyOffPromotion;
import me.ajfleming.qikserve.model.Promotion;
import me.ajfleming.qikserve.type.DeleteStatus;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 *  Class: AppController
 *  Purpose: This Class manages all the Promotion Operations. This includes communicating with the Data Access Object
 *  Author: Andrew Fleming
 */

class PromotionController {

    private PromotionDAO db;

    PromotionController()
    {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("Spring-Module.xml");

        db = (PromotionDAOImpl_JDBC) ctx.getBean("promotionDAO");
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
        return db.getPromotion(id);
    }

    DeleteStatus deletePromotion(int promoId) {
        return db.deletePromotion(promoId);
    }

    boolean addItemToPromotion(Promotion promotion, Item item) {
        return db.addItemToPromotion(promotion,item);
    }

    boolean removeItemFromPromotion(Promotion promotion, Item item) {
        return db.removeItemFromPromotion(promotion, item);
    }

    List<Item> getPromotionItems(Promotion promo) {
        return db.getPromotionItems(promo);
    }
}
