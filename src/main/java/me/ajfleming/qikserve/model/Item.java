package me.ajfleming.qikserve.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by andrew on 14/01/17.
 */
@Entity
@Table(name = "item")
public class Item {

    @Id
    private int id = 0;

    @NotNull(message = "Items require a name!")
    private String name;

    @NotNull(message = "Items require a description!")
    private String description;

    @NotNull(message = "Items require a barcode!")
    private String barcode;

    @NotNull(message = "Items need a price!")
    private float price;

    private boolean active = true;

    public Item() {}

    public Item(String name, String description, String barcode, float price, boolean active){
        this.active = active;
        this.id = 0;
        this.name = name;
        this.description = description;
        this.barcode = barcode;
        this.price = price;
    }

    public Item(int id, String name, String description, String barcode, float price, boolean active){
        this.id = id;
        this.name = name;
        this.description = description;
        this.barcode = barcode;
        this.price = price;
        this.active = active;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
