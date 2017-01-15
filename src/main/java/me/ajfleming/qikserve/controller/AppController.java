package me.ajfleming.qikserve.controller;

import me.ajfleming.qikserve.message.APIResponse;
import me.ajfleming.qikserve.model.FreeItemPromotion;
import me.ajfleming.qikserve.model.Item;
import me.ajfleming.qikserve.model.MoneyOffPromotion;
import me.ajfleming.qikserve.model.Promotion;
import me.ajfleming.qikserve.type.DeleteStatus;
import org.springframework.http.HttpStatus;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by andrew on 15/01/17.
 */
public class AppController {

    private ItemController itemController;
    private PromotionController promotionController;
    private BasketController basketController;

    public AppController(DataSource ds){
        itemController = new ItemController(ds);
        promotionController = new PromotionController(ds);
        basketController = new BasketController(ds);
    }

    /**
     * Item
     */

    public List<Item> getAllItems() {
        return itemController.getAllItems();
    }

    public Item save(Item item) {
        return itemController.save(item);
    }

    public Item getItem(int itemId) {
        return itemController.getItem(itemId);
    }

    public Item getItem(String barcode) {
        return itemController.getItem(barcode);
    }

    public Item editItem(int itemId, Item updatedItem) {
        Item originalItem = getItem(itemId);
        updatedItem.setId(originalItem.getId());
        return save(updatedItem);
    }

    public APIResponse deleteItem(int itemId) {
        if(itemController.deleteItem(itemId) == DeleteStatus.DEACTIVATED)
        {
            return new APIResponse(HttpStatus.OK, "Item "+ itemId + " has been deactivated");
        }
        else if(itemController.deleteItem(itemId) == DeleteStatus.SERVER_ERROR)
        {
            return new APIResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
        else
        {
            return new APIResponse(HttpStatus.NOT_FOUND, "Cannot Find Item to Delete");
        }
    }

    /**
     * Promotion
     */

    public List<Promotion> getAllPromotions() {
        return promotionController.getAllPromotions();
    }

    public Promotion getPromotion(int promoId) {
        return promotionController.getPromotion(promoId);
    }

    public MoneyOffPromotion save(MoneyOffPromotion promo) {
        return promotionController.save(promo);
    }

    public FreeItemPromotion save(FreeItemPromotion promo){
        return promotionController.save(promo);
    }

    public APIResponse deletePromotion(int promoId){
        if(promotionController.deletePromotion(promoId) == DeleteStatus.DEACTIVATED)
        {
            return new APIResponse(HttpStatus.OK, "Promotion "+ promoId + " has been deactivated");
        }
        else if(promotionController.deletePromotion(promoId) == DeleteStatus.SERVER_ERROR)
        {
            return new APIResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
        else
        {
            return new APIResponse(HttpStatus.NOT_FOUND, "Cannot Find Promotion to Deactivate");
        }
    }

    /**
     * Basket
     */

    public APIResponse createBasket()
    {
        int id =  basketController.createOrder();
        if(id != 0) {
            APIResponse response = new APIResponse(HttpStatus.CREATED, "An Basket has been created. Use the Basket Number below to add items to your order");
            response.addValue("Basket Number", "" + id + "");
            return response;
        }
        else
        {
            return new APIResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create new basket due to a server error");
        }
    }
}
