package me.ajfleming.qikserve.controller;

import me.ajfleming.qikserve.dao.*;
import me.ajfleming.qikserve.exception.BasketException;
import me.ajfleming.qikserve.helpers.MathsOperations;
import me.ajfleming.qikserve.holder.BasketItem;
import me.ajfleming.qikserve.holder.ValidPromotion;
import me.ajfleming.qikserve.model.Basket;
import me.ajfleming.qikserve.model.BasketPromotion;
import me.ajfleming.qikserve.model.Item;
import me.ajfleming.qikserve.model.Promotion;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by andrew on 15/01/17.
 */
public class BasketController {

    private BasketDAO db;
    private BasketPromotionDAO basketPromotionDb;
    private PromotionDAO promotionDb;
    private ItemDAO itemDb;

    BasketController(DataSource ds){
        db = new BasketDAOImpl_JDBC(ds);
        basketPromotionDb = new BasketPromotionDAOImpl_JDBC(ds);
        promotionDb = new PromotionDAOImpl_JDBC(ds);
        itemDb = new ItemDAOImpl_JDBC(ds);
    }

    int createBasket()
    {
        return db.createBasket();
    }

    Basket updateBasket(Basket basket){ return db.updateBasket(basket); }

    Basket getBasket(int basketId) {
        return db.getBasket(basketId);
    }

    List<Item> getBasketItems(Basket basket) {
        return db.getBasketItems(basket);
    }

    boolean addItemToBasket(Basket basket, Item item) {
        return db.addItemToBasket(basket, item);
    }
    boolean removeItemToBasket(Basket basket, Item item) { return db.removeItemFromBasket(basket,item); }

    boolean addPromotionToBasket(Basket basket, Promotion promo, List<Item> items)
    {
        BasketPromotion basketPromotion = new BasketPromotion();
        basketPromotion.setBasketId(basket.getId());
        basketPromotion.setPromotion(promo);
        return basketPromotionDb.save(basketPromotion) != null;
    }

    //TODO Re-evaluate if I need this method
    boolean removePromotionFromBasket(BasketPromotion promotion)
    {
        return basketPromotionDb.deleteBasketPromotion(promotion);
    }

    boolean setUpValidPromotions(Basket basket) {
        List<BasketItem> itemsToCleanUp = new ArrayList<BasketItem>();

        List<ValidPromotion> promotions = db.getAllValidPromotionsInBasket(basket);
        for(ValidPromotion promo : promotions)
        {
            Promotion p = promotionDb.getPromotion(promo.getPromoId());
            if(p != null)
            {
                promo.setPromotion(p);

                Iterator<BasketItem> basketItems = db.getAllBasketItemsPartOfPromotion(basket, promo.getPromotion()).iterator();
                int noOfValidPromotions = promo.getNoOfItemsInBasket() / promo.getPromotion().getNoOfItemsRequired();
                for(int i = 0; i < noOfValidPromotions; i++)
                {
                    BasketPromotion basketPromotion = new BasketPromotion(promo.getPromotion(), basket.getId());
                    for(int j = 0; j < promo.getPromotion().getNoOfItemsRequired(); j++)
                    {
                        BasketItem bi = basketItems.next();
                        bi.setItem(itemDb.getItem(bi.getItemId()));
                        itemsToCleanUp.add(bi);
                        basketPromotionDb.addItemToBasketPromotion(basketPromotion, bi.getItem());
                        basketPromotion.addItem(bi.getItem());
                    }
                    basketPromotion.calculatePriceAndTotalSavings();
                    basketPromotionDb.save(basketPromotion);
                }
            }
        }
        cleanUpItemsFromBasket(basket, itemsToCleanUp);
        return true;
    }

    private boolean cleanUpItemsFromBasket(Basket basket, List<BasketItem> items)
    {
        for(BasketItem i : items)
        {
            db.removeItemFromBasket(basket,i.getItem());
        }
        return true;
    }

    Basket calculatePriceAndSavings(Basket basket) throws BasketException {
        List<Item> basketItems = db.getBasketItems(basket);
        basket.setItemsInBasket(basketItems);
        List<BasketPromotion> basketPromotions = basketPromotionDb.getBasketPromotions(basket);

        float finalTotal = MathsOperations.calculatePriceOfItems(basketItems);
        float totalSavings = 0;

        for(BasketPromotion bp : basketPromotions)
        {
            finalTotal += bp.getPromotionPrice();
            totalSavings += bp.getTotalSavings();
        }

        basket.setFinalTotal(finalTotal);
        basket.setTotalSavings(totalSavings);
        return basket;
    }
}
