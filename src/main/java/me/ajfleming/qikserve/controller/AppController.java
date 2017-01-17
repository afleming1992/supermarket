package me.ajfleming.qikserve.controller;

import me.ajfleming.qikserve.exception.BasketException;
import me.ajfleming.qikserve.message.APIResponse;
import me.ajfleming.qikserve.model.*;
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
        Promotion promo = promotionController.getPromotion(promoId);
        promo.setValidItems(itemController.getPromotionItems(promo));
        return promo;
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
        int id =  basketController.createBasket();
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

    public APIResponse addItemToBasket(int basketId, String itemIdentifier, String identifierType)
    {
        Basket basket = basketController.getBasket(basketId);
        if(basket == null) return new APIResponse(HttpStatus.NOT_FOUND, "Could not find Basket");

        if(basket.isCompleted()) return new APIResponse(HttpStatus.BAD_REQUEST, "This basket has been closed. No further items can be completed");

        Item item;
        if(identifierType.equals("id")) {
            try {
                int id = Integer.parseInt(itemIdentifier);
                item = itemController.getItem(id);
            }
            catch(NumberFormatException e)
            {
                return new APIResponse(HttpStatus.BAD_REQUEST, "Id needs to be a number");
            }
        }
        else if(identifierType.equals("barcode")) {
            item = itemController.getItem(itemIdentifier);
        }
        else
        {
            return new APIResponse(HttpStatus.BAD_REQUEST, "Type parameter must be either barcode or id");
        }

        if(item == null)
        {
            return new APIResponse(HttpStatus.NOT_FOUND, "Could not find Item");
        }

        if(basketController.addItemToBasket(basket,item))
        {
            return new APIResponse(HttpStatus.OK, "Item added");
        }
        else
        {
            return new APIResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Could not add Item to Basket due to internal server error");
        }
    }

    public APIResponse removeItemFromBasket(int basketId, String itemIdentifier, String identifierType)
    {
        Basket basket = basketController.getBasket(basketId);
        if(basket == null) return new APIResponse(HttpStatus.NOT_FOUND, "Could not find Basket");

        Item item;
        if(identifierType.equals("id")) {
            try {
                int id = Integer.parseInt(itemIdentifier);
                item = itemController.getItem(id);
            }
            catch(NumberFormatException e)
            {
                return new APIResponse(HttpStatus.BAD_REQUEST, "Id needs to be a number");
            }
        }
        else if(identifierType.equals("barcode")) {
            item = itemController.getItem(itemIdentifier);
        }
        else
        {
            return new APIResponse(HttpStatus.BAD_REQUEST, "Type parameter must be either barcode or id");
        }

        if(item == null)
        {
            return new APIResponse(HttpStatus.NOT_FOUND, "Could not find Item");
        }

        if(basketController.removeItemToBasket(basket,item))
        {
            return new APIResponse(HttpStatus.OK, "Item Removed");
        }
        else
        {
            return new APIResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Could not remove Item to Basket due to internal server error");
        }
    }

    public Basket getBasket(int basketId) {
        Basket basket = basketController.getBasket(basketId);
        basket.setItemsInBasket(itemController.getBasketItems(basket));
        return basket;
    }


    public APIResponse addItemToPromotion(int promoId, int itemId) {
        Promotion promotion = promotionController.getPromotion(promoId);
        if(promotion == null) return new APIResponse(HttpStatus.NOT_FOUND, "Could not find Promotion");

        Item item = itemController.getItem(itemId);
        if(item == null) return new APIResponse(HttpStatus.NOT_FOUND, "Could not find Item");

        if(promotionController.addItemToPromotion(promotion, item))
        {
            return new APIResponse(HttpStatus.OK, "Item Added");
        }
        else
        {
            return new APIResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Could not add Item to Promotion due to internal server error");
        }
    }

    public APIResponse removeItemFromPromotion(int promoId, int itemId) {
        Promotion promotion = promotionController.getPromotion(promoId);
        if(promotion == null) return new APIResponse(HttpStatus.NOT_FOUND, "Could not find Promotion");

        Item item = itemController.getItem(itemId);
        if(item == null) return new APIResponse(HttpStatus.NOT_FOUND, "Could not find Item");

        if(promotionController.removeItemFromPromotion(promotion, item))
        {
            return new APIResponse(HttpStatus.OK, "Item Removed");
        }
        else
        {
            return new APIResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Could not remove Item to Promotion due to internal server error");
        }
    }

    public APIResponse finaliseAndCloseBasket(int basketId)
    {
        Basket basket = basketController.getBasket(basketId);
        if(basket == null)
        {
            return new APIResponse(HttpStatus.NOT_FOUND, "Could not find Item");
        }

        try {
            basket = basketController.calculatePriceAndSavings(basket);
            basket.setCompleted(true);
            basketController.updateBasket(basket);
            APIResponse response = new APIResponse(HttpStatus.OK, "Thank you for shopping with QikServe Supermarket! Your totals are show below");
            response.addValue("finalTotal", ""+ basket.getFinalTotal() + "");
            response.addValue("totalSavings", ""+ basket.getTotalSavings() + "");
            return response;
        }
        catch(BasketException e)
        {
            return new APIResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
