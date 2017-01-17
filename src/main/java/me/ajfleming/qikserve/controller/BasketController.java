package me.ajfleming.qikserve.controller;

import me.ajfleming.qikserve.dao.BasketDAO;
import me.ajfleming.qikserve.dao.BasketDAOImpl_JDBC;
import me.ajfleming.qikserve.model.Basket;
import me.ajfleming.qikserve.model.Item;

import javax.sql.DataSource;

/**
 * Created by andrew on 15/01/17.
 */
public class BasketController {

    private BasketDAO db;

    BasketController(DataSource ds){
        db = new BasketDAOImpl_JDBC(ds);
    }

    int createOrder()
    {
        return db.createBasket();
    }

    Basket getBasket(int basketId) {
        return db.getBasket(basketId);
    }

    boolean addItemToBasket(Basket basket, Item item) {
        return db.addItemToBasket(basket, item);
    }
    boolean removeItemToBasket(Basket basket, Item item) { return db.removeItemToBasket(basket,item); }

}
