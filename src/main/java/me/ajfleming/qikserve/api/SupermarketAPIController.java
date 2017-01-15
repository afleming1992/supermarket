package me.ajfleming.qikserve.api;

import com.google.gson.Gson;
import me.ajfleming.qikserve.controller.AppController;
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
@RestController
@RequestMapping("/api")
public class SupermarketAPIController {

    private AppController appController;

    public SupermarketAPIController(DataSource db){
        appController = new AppController(db);
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
            appController.save(item);
            return new ResponseEntity(HttpStatus.CREATED);
        }
        catch(Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/item/{itemId}", method = RequestMethod.GET, produces="application/json")
    public ResponseEntity getItem(@PathVariable int itemId){
        Item result = appController.getItem(itemId);
        if(result == null)
        {
            return new ResponseEntity<String>(toJSON("No Item Found using ID: " + itemId), HttpStatus.NOT_FOUND);
        }
        else
        {
            return new ResponseEntity<Item>(result, HttpStatus.OK);
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

    @RequestMapping(value = "/item/barcode/{barcode}", method = RequestMethod.GET, produces="application/json")
    public ResponseEntity getItemByBarcode(@PathVariable String barcode)
    {
        Item result = appController.getItem(barcode);
        if(result == null)
        {
            return new ResponseEntity<String>(toJSON("No Item Found using Barcode: " + barcode), HttpStatus.NOT_FOUND);
        }
        else
        {
            return new ResponseEntity<Item>(result, HttpStatus.OK);
        }
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
        return new ResponseEntity<String>(toJSON(result), result.getStatus());
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

    //Helper Methods
    private String toJSON(Object obj){
        Gson gson = new Gson();
        return gson.toJson(obj);
    }
}