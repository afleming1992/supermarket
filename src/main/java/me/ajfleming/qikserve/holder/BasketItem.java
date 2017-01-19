package me.ajfleming.qikserve.holder;

import me.ajfleming.qikserve.model.Item;

/**
 * Created by andrew on 19/01/17.
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
