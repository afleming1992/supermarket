package me.ajfleming.qikserve.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by andrew on 14/01/17.
 */
@Entity
@Table(name = "basket")
public class Basket {

    @Id
    @GeneratedValue
    private int id;

    private boolean completed = false;
    private Timestamp timestamp;
    private float finalTotal = 0;
    private float totalSavings = 0;

    private HashMap<Integer, Item> itemsInBasket;
    private HashSet<BasketPromotion> promotionsInBasket;

    public Basket(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public float getFinalTotal() {
        return finalTotal;
    }

    public void setFinalTotal(float finalTotal) {
        this.finalTotal = finalTotal;
    }

    public float getTotalSavings() {
        return totalSavings;
    }

    public void setTotalSavings(float totalSavings) {
        this.totalSavings = totalSavings;
    }

    public HashMap<Integer, Item> getItemsInBasket() {
        return itemsInBasket;
    }

    public void setItemsInBasket(HashMap<Integer, Item> itemsInBasket) {
        this.itemsInBasket = itemsInBasket;
    }

    public HashSet<BasketPromotion> getPromotionsInBasket() {
        return promotionsInBasket;
    }

    public void setPromotionsInBasket(HashSet<BasketPromotion> promotionsInBasket) {
        this.promotionsInBasket = promotionsInBasket;
    }
}
