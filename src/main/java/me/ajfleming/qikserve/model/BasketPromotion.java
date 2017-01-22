package me.ajfleming.qikserve.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import me.ajfleming.qikserve.helpers.ItemComparator;
import me.ajfleming.qikserve.helpers.MathsOperations;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *  Class: BasketPromotion
 *  Purpose: This class holds our model representation of a Promotion within a Basket.
 *  Author: Andrew Fleming
 */
public class BasketPromotion {

    @Id
    @GeneratedValue
    private int id;

    @JsonIgnore
    private int basketId;
    private Promotion promo;
    private float promotionPrice;
    private float totalSavings;
    private List<Item> items;

    public BasketPromotion(){}

    public BasketPromotion(Promotion promotion, int basketId) {
        this.promo = promotion;
        this.basketId = basketId;
        this.items = new ArrayList<>();
    }

    public void calculatePriceAndTotalSavings() {
        Collections.sort(items, new ItemComparator());
        promotionPrice = promo.calculateFinalPrice(items);
        totalSavings = MathsOperations.calculatePriceOfItems(items) - promotionPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Promotion getPromotion() {
        return promo;
    }

    public void setPromotion(Promotion promo) {
        this.promo = promo;
    }

    public float getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(float promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public int getBasketId() {
        return basketId;
    }

    public void setBasketId(int basketId) {
        this.basketId = basketId;
    }

    public void addItem(Item item)
    {
        items.add(item);
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public float getTotalSavings() {
        return totalSavings;
    }

    public void setTotalSavings(float totalSavings){
        this.totalSavings = totalSavings;
    }
}
