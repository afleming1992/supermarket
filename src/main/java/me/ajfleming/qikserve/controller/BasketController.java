package me.ajfleming.qikserve.controller;

import me.ajfleming.qikserve.dao.BasketDAO;
import me.ajfleming.qikserve.dao.BasketDAOImpl_JDBC;
import me.ajfleming.qikserve.dao.BasketPromotionDAO;
import me.ajfleming.qikserve.dao.BasketPromotionDAOImpl_JDBC;
import me.ajfleming.qikserve.exception.BasketException;
import me.ajfleming.qikserve.model.Basket;
import me.ajfleming.qikserve.model.BasketPromotion;
import me.ajfleming.qikserve.model.Item;
import me.ajfleming.qikserve.model.Promotion;

import javax.sql.DataSource;

/**
 * Created by andrew on 15/01/17.
 */
public class BasketController {

    private BasketDAO db;
    private BasketPromotionDAO promoDb;

    BasketController(DataSource ds){
        db = new BasketDAOImpl_JDBC(ds);
        promoDb = new BasketPromotionDAOImpl_JDBC(ds);
    }

    int createBasket()
    {
        return db.createBasket();
    }

    Basket updateBasket(Basket basket){ return db.editBasket(basket); }

    Basket getBasket(int basketId) {
        return db.getBasket(basketId);
    }

    boolean addItemToBasket(Basket basket, Item item) {
        return db.addItemToBasket(basket, item);
    }
    boolean removeItemToBasket(Basket basket, Item item) { return db.removeItemToBasket(basket,item); }

    boolean addPromotionToBasket(Basket basket, Promotion promo)
    {
        BasketPromotion basketPromotion = new BasketPromotion();
        basketPromotion.setBasket(basket);
        basketPromotion.setPromo(promo);
        if(promoDb.save(basketPromotion) != null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    boolean removePromotionFromBasket(BasketPromotion promotion)
    {
        return promoDb.deleteBasketPromotion(promotion);
    }

    public Basket calculatePriceAndSavings(Basket basket) throws BasketException {
        return basket;
    }
}
