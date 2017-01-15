package me.ajfleming.qikserve.controller;

import me.ajfleming.qikserve.dao.BasketDAO;
import me.ajfleming.qikserve.dao.BasketDAOImpl_JDBC;

import javax.sql.DataSource;

/**
 * Created by andrew on 15/01/17.
 */
public class BasketController {

    private BasketDAO db;

    BasketController(DataSource ds){
        db = new BasketDAOImpl_JDBC(ds);
    }

    public int createOrder()
    {
        return db.createBasket();
    }
}
