package me.ajfleming.qikserve.controller;

import me.ajfleming.qikserve.exception.BadRequestException;
import me.ajfleming.qikserve.exception.BasketException;
import me.ajfleming.qikserve.holder.APIResponse;
import me.ajfleming.qikserve.model.*;
import me.ajfleming.qikserve.type.DeleteStatus;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 *  Class: AppController
 *  Purpose: This Class does orchestration between the API Layer and the individual Controllers managing each entity
 *  Author: Andrew Fleming
 */

public class AppController {

    private ItemController itemController;
    private PromotionController promotionController;
    private BasketController basketController;

    public AppController(){
        itemController = new ItemController();
        promotionController = new PromotionController();
        basketController = new BasketController();
    }

    /**
     * Item Operations
     */

    public List<Item> getAllItems() {
        return itemController.getAllItems();
    }

    public Item save(Item item) {
        return itemController.save(item);
    }

    public Item getItem(String itemIdentifier, String identifierType) throws BadRequestException {
        Item item;
        switch (identifierType) {
            case "id":
                int id = Integer.parseInt(itemIdentifier);
                item = itemController.getItem(id);
                break;
            case "barcode":
                item = itemController.getItem(itemIdentifier);
                break;
            default:
                throw new BadRequestException("Type parameter must be barcode or id");
        }

        return item;
    }

    public Item getItem(String barcode) {
        return itemController.getItem(barcode);
    }

    public Item editItem(int itemId, Item updatedItem) {
        Item originalItem = itemController.getItem(itemId);
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
     * Promotion Operations
     */

    public List<Promotion> getAllPromotions() {
        return promotionController.getAllPromotions();
    }

    public Promotion getPromotion(int promoId) {
        Promotion promo = promotionController.getPromotion(promoId);
        promo.setValidItems(promotionController.getPromotionItems(promo));
        return promo;
    }

    public MoneyOffPromotion save(MoneyOffPromotion promo) {
        return promotionController.save(promo);
    }

    public FreeItemPromotion save(FreeItemPromotion promo){
        return promotionController.save(promo);
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
     * Basket Operations
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
        switch (identifierType) {
            case "id":
                try {
                    int id = Integer.parseInt(itemIdentifier);
                    item = itemController.getItem(id);
                } catch (NumberFormatException e) {
                    return new APIResponse(HttpStatus.BAD_REQUEST, "Id needs to be a number");
                }
                break;
            case "barcode":
                item = itemController.getItem(itemIdentifier);
                break;
            default:
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
        switch (identifierType) {
            case "id":
                try {
                    int id = Integer.parseInt(itemIdentifier);
                    item = itemController.getItem(id);
                } catch (NumberFormatException e) {
                    return new APIResponse(HttpStatus.BAD_REQUEST, "Id needs to be a number");
                }
                break;
            case "barcode":
                item = itemController.getItem(itemIdentifier);
                break;
            default:
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
        if(basket != null) {
            basket.setItemsInBasket(basketController.getBasketItems(basket));
            basket.setPromotionsInBasket(basketController.getBasketPromotions(basket));
        }
        else
        {
            return null;
        }
        return basket;
    }

    public APIResponse finaliseAndCloseBasket(int basketId)
    {
        Basket basket = basketController.getBasket(basketId);
        if(basket == null)
        {
            return new APIResponse(HttpStatus.NOT_FOUND, "Could not find Basket");
        }

        try {
            basketController.setUpValidPromotions(basket);
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
