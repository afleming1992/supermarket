package me.ajfleming.qikserve.holder;

import me.ajfleming.qikserve.model.Item;

/**
 *  Class: BasketItem
 *  Purpose: This class holds items in a Basket during the Basket Calculation Phase.
 *  Author: Andrew Fleming
 */

public class BasketItem {

    private int basketItemId;
    private Item item;
    private int itemId;

    public BasketItem(int basketItemId, Item item)
    {
        this.basketItemId = basketItemId;
        this.item = item;
        this.itemId = item.getId();
    }

    public BasketItem(int basketItemId, int itemId)
    {
        this.basketItemId = basketItemId;
        this.itemId = itemId;
        this.item = null;
    }

    public int getBasketItemId() {
        return basketItemId;
    }

    public void setBasketItemId(int basketItemId) {
        this.basketItemId = basketItemId;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
