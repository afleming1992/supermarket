package me.ajfleming.qikserve.holder;

import me.ajfleming.qikserve.model.Promotion;

/**
 *  Class: MathsOperations
 *  Purpose: This class hold a valid promotion which is calculated during the Basket Checkout Phase.
 *  Author: Andrew Fleming
 */
public class ValidPromotion {

    private Promotion promotion;
    private int promoId;
    private int noOfItemsInBasket;

    public ValidPromotion(Promotion promotion, int noOfItemsInBasket)
    {
        this.promotion = promotion;
        this.promoId = promotion.getId();
        this.noOfItemsInBasket = noOfItemsInBasket;
    }

    public ValidPromotion(int promoId, int noOfItemsInBasket)
    {
        this.promotion = null;
        this.promoId = promoId;
        this.noOfItemsInBasket = noOfItemsInBasket;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public int getNoOfItemsInBasket() {
        return noOfItemsInBasket;
    }

    public int getPromoId() {
        return promoId;
    }
}
