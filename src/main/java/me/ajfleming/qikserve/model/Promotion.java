package me.ajfleming.qikserve.model;

import me.ajfleming.qikserve.helpers.ListToHashMapConverter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by andrew on 14/01/17.
 */
@Table(name = "promotion")
public abstract class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @NotBlank(message = "Promotion Name must not be blank")
    private String name;
    private boolean active = true;

    @NotNull(message = "You must set noOfItemsRequired to the Number of items you need for this promotion to be valid")
    @Min(value = 1, message = "You must set noOfItemsRequired to a Non-Zero value")
    private int noOfItemsRequired;

    private HashMap<Integer, Item> validItems;
    public final String type = "";

    public Promotion(){}

    public Promotion(String name, boolean active)
    {
        this.name=name;
        this.active=active;
    }

    public Promotion(String name, boolean active, int noOfItemsRequired, ArrayList<Item> validItems) {
        int id = 0;
        setupPromotion(name,active,noOfItemsRequired, validItems);
    }

    public Promotion(int id, String name, boolean active, int noOfItemsRequired, ArrayList<Item> validItems) {
        this.id = id;
        setupPromotion(name,active, noOfItemsRequired, validItems);
    }

    private void setupPromotion(String name, boolean active, int noOfItemsRequired, ArrayList<Item> validItems) {
        this.name = name;
        this.active = active;
        this.validItems = ListToHashMapConverter.convertItemListToHashMap(validItems);
    }

    public void addItem(Item item){
        validItems.put(item.getId(), item);
    }

    public void removeItem(Item item){
        validItems.remove(item.getId());
    }

    //Getters and Setters

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public HashMap<Integer, Item> getValidItems() {
        return validItems;
    }

    public void setValidItems(List<Item> validItems) {
        this.validItems = ListToHashMapConverter.convertItemListToHashMap(validItems);
    }

    public int getNoOfItemsRequired() {
        return noOfItemsRequired;
    }

    public void setNoOfItemsRequired(int noOfItemsRequired) {
        this.noOfItemsRequired = noOfItemsRequired;
    }
}
