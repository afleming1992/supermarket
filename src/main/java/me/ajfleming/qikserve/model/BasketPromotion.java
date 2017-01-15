package me.ajfleming.qikserve.model;

import javax.persistence.*;
import java.util.HashMap;

/**
 * Created by andrew on 14/01/17.
 */
public class BasketPromotion {

    @Id
    @GeneratedValue
    private int id;
    private Basket basket;
    private Promotion promo;
    private float price;
    private HashMap<Integer, Item> items;

    public BasketPromotion(){}

}
