package me.ajfleming.qikserve.model;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;

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
    private List<Item> items;

    public BasketPromotion(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public Promotion getPromotion() {
        return promo;
    }

    public void setPromo(Promotion promo) {
        this.promo = promo;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
