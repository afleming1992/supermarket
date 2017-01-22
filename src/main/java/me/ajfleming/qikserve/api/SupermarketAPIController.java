package me.ajfleming.qikserve.api;

import com.google.gson.Gson;
import com.mysql.fabric.Response;
import me.ajfleming.qikserve.controller.AppController;
import me.ajfleming.qikserve.exception.BadRequestException;
import me.ajfleming.qikserve.message.APIResponse;
import me.ajfleming.qikserve.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by andrew on 14/01/17.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class SupermarketAPIController {

    private AppController appController;

    public SupermarketAPIController(){
        appController = new AppController();
    }

    /**
     * ITEM API Calls
     */

    @RequestMapping(value = "/item", method = RequestMethod.GET, produces="application/json")
    public List<Item> getAllItems(){
        return appController.getAllItems();
    }

    @RequestMapping(value = "/item", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity addItem(@RequestBody Item item){
        try {
            Item result = appController.save(item);
            if(result.getId() == 0)
            {
                APIResponse response = new APIResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Can not add Item");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<Item>(result, HttpStatus.CREATED);
        }
        catch(Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/item/{itemIdentifier}", method = RequestMethod.GET, produces="application/json")
    public ResponseEntity getItem(@PathVariable String itemIdentifier, String type){
        if(type == null) type = "id";
        try {
            Item result = appController.getItem(itemIdentifier, type);
            if (result == null)
                return new ResponseEntity<String>(toJSON("No Item Found using "+ type +": " + itemIdentifier), HttpStatus.NOT_FOUND);
            else
                return new ResponseEntity<Item>(result, HttpStatus.OK);
        }
        catch(BadRequestException e)
        {
            return new ResponseEntity<String>(toJSON("Type parameter must be either barcode or id"), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/item/{itemId}", method = RequestMethod.PUT, produces="application/json")
    public Item editItem(@PathVariable int itemId, @RequestBody Item updatedItem)
    {
        return appController.editItem(itemId, updatedItem);
    }

    @RequestMapping(value = "/item/{itemId}", method = RequestMethod.DELETE, produces="application/json")
    public ResponseEntity deleteItem(@PathVariable int itemId)
    {
        APIResponse result = appController.deleteItem(itemId);
        return new ResponseEntity<String>(toJSON(result), result.getStatus());
    }

    /**
     * Promotions API Calls
     */

    @RequestMapping(value = "/promotion", method = RequestMethod.GET, produces="application/json")
    public List<Promotion> getPromotions()
    {
        return appController.getAllPromotions();
    }

    @RequestMapping(value = "/promotion/{promoId}", method = RequestMethod.GET, produces="application/json")
    public ResponseEntity getPromotionById(@PathVariable int promoId)
    {
        Promotion result = appController.getPromotion(promoId);
        if(result != null)
            return new ResponseEntity<>(result, HttpStatus.OK);
        else
            return new ResponseEntity<>(new APIResponse(HttpStatus.NOT_FOUND,"Promotion Not Found: "+ promoId), HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/promotion/money", method = RequestMethod.POST, headers = "Accept=application/json")
    public MoneyOffPromotion createNewMoneyOffPromotion(@RequestBody MoneyOffPromotion promo)
    {
        return appController.save(promo);
    }

    @RequestMapping(value = "/promotion/multi", method = RequestMethod.POST,  headers = "Accept=application/json")
    public FreeItemPromotion createNewFreeItemPromotion(@RequestBody FreeItemPromotion promo)
    {
        return appController.save(promo);
    }

    @RequestMapping(value = "/promotion/{promoId}", method = RequestMethod.DELETE, produces="application/json")
    public ResponseEntity deletePromotion(@PathVariable int promoId)
    {
        APIResponse result = appController.deletePromotion(promoId);
        return new ResponseEntity<>(toJSON(result), result.getStatus());
    }


    @RequestMapping(value = "/promotion/{promoId}/item/{itemId}", method = RequestMethod.POST, produces="application/json")
    public ResponseEntity addItemToPromotion(@PathVariable int promoId, @PathVariable int itemId)
    {
        APIResponse result = appController.addItemToPromotion(promoId, itemId);
        return new ResponseEntity<>(toJSON(result), result.getStatus());
    }

    @RequestMapping(value = "/promotion/{promoId}/item/{itemId}", method = RequestMethod.DELETE, produces="application/json")
    public ResponseEntity removeItemFromPromotion(@PathVariable int promoId, @PathVariable int itemId)
    {
        APIResponse result = appController.removeItemFromPromotion(promoId, itemId);
        return new ResponseEntity<>(toJSON(result), result.getStatus());
    }


    /**
     * Basket API Calls
     */

    @RequestMapping(value = "/basket", method = RequestMethod.POST, produces="application/json")
    public ResponseEntity openBasket()
    {
        APIResponse result = appController.createBasket();
        return new ResponseEntity<>(toJSON(result), result.getStatus());
    }

    @RequestMapping(value = "/basket/{basketId}", method = RequestMethod.GET, produces="application/json")
    public ResponseEntity getBasket(@PathVariable int basketId)
    {
        Basket result = appController.getBasket(basketId);
        if(result != null)
            return new ResponseEntity<>(result, HttpStatus.OK);
        else
            return new ResponseEntity<>(new APIResponse(HttpStatus.NOT_FOUND,"Basket Not Found"), HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/basket/{basketId}", method = RequestMethod.POST, produces="application/json")
    public ResponseEntity finaliseAndCloseBasket(@PathVariable int basketId)
    {
        APIResponse result = appController.finaliseAndCloseBasket(basketId);
        return new ResponseEntity<>(toJSON(result), result.getStatus());
    }

    @RequestMapping(value = "/basket/{basketId}/item/{itemIdentifier}", method = RequestMethod.POST, produces="application/json")
    public ResponseEntity addItemToBasket(@PathVariable int basketId, @PathVariable String itemIdentifier, String type)
    {
        if(type == null){ type = "barcode"; }
        APIResponse result = appController.addItemToBasket(basketId, itemIdentifier, type);
        return new ResponseEntity<>(toJSON(result), result.getStatus());
    }

    @RequestMapping(value = "/basket/{basketId}/item/{itemIdentifier}", method = RequestMethod.DELETE, produces="application/json")
    public ResponseEntity deleteItemFromBasket(@PathVariable int basketId, @PathVariable String itemIdentifier, String type)
    {
        if(type == null){ type = "barcode"; }
        APIResponse result = appController.removeItemFromBasket(basketId, itemIdentifier, type);
        return new ResponseEntity<>(toJSON(result), result.getStatus());
    }

    /**
     * Helper Methods
     **/

    private String toJSON(Object obj){
        Gson gson = new Gson();
        return gson.toJson(obj);
    }
}
