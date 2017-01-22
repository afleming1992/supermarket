package me.ajfleming.qikserve.holder;

/**
 * Created by andrew on 22/01/17.
 */
public class PromotionRequest {
    private int id;
    private String name;
    private int noOfItemsRequired;
    private int noOfItemsFree;
    private float totalPrice;

    public PromotionRequest(int id, String name, int noOfItemsRequired, int noOfItemsFree, float totalPrice) {
        this.id = id;
        this.name = name;
        this.noOfItemsRequired = noOfItemsRequired;
        this.noOfItemsFree = noOfItemsFree;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNoOfItemsRequired() {
        return noOfItemsRequired;
    }

    public void setNoOfItemsRequired(int noOfItemsRequired) {
        this.noOfItemsRequired = noOfItemsRequired;
    }

    public int getNoOfItemsFree() {
        return noOfItemsFree;
    }

    public void setNoOfItemsFree(int noOfItemsFree) {
        this.noOfItemsFree = noOfItemsFree;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
