package me.ajfleming.qikserve.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by andrew on 14/01/17.
 */
@Entity
public class Basket {

    @Id
    @GeneratedValue
    private int id;

    private boolean completed = false;
    @JsonIgnore
    private Timestamp timestamp;
    private String orderCreatedTimeStamp;
    private float finalTotal = 0;
    private float totalSavings = 0;

    @Transient
    private List<Item> itemsInBasket;

    @Transient
    private List<BasketPromotion> promotionsInBasket;

    public Basket(){}

    public Basket(boolean completed, Timestamp timestamp) {
        this.completed = completed;
        setTimestamp(timestamp);
    }

    public Basket(boolean completed, Timestamp timestamp, float finalTotal, float totalSavings) {
        this.completed = completed;
        setTimestamp(timestamp);
        this.finalTotal = finalTotal;
        this.totalSavings = totalSavings;
    }

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
        setOrderCreatedTimeStamp(new SimpleDateFormat("dd/MM/yyyy hh:mm").format(timestamp));
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

    public String getOrderCreatedTimeStamp() {
        return orderCreatedTimeStamp;
    }

    private void setOrderCreatedTimeStamp(String orderCreatedTimeStamp) {
        this.orderCreatedTimeStamp = orderCreatedTimeStamp;
    }

    public List<Item> getItemsInBasket() {
        return itemsInBasket;
    }

    public void setItemsInBasket(List<Item> itemsInBasket) {
        this.itemsInBasket = itemsInBasket;
    }

    public List<BasketPromotion> getPromotionsInBasket() {
        return promotionsInBasket;
    }

    public void setPromotionsInBasket(List<BasketPromotion> promotionsInBasket) {
        this.promotionsInBasket = promotionsInBasket;
    }
}
