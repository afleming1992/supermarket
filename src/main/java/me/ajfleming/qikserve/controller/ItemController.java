package me.ajfleming.qikserve.controller;

import me.ajfleming.qikserve.dao.*;
import me.ajfleming.qikserve.model.Basket;
import me.ajfleming.qikserve.model.Item;
import me.ajfleming.qikserve.model.Promotion;
import me.ajfleming.qikserve.type.DeleteStatus;
import org.hibernate.sql.Delete;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.util.List;

/**
 *  Class: ItemController
 *  Purpose: This Class manages all the Item Operations. This includes communicating with the Data Access Object
 *  Author: Andrew Fleming
 */

class ItemController {

    private ItemDAO itemDAO;

    ItemController() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("Spring-Module.xml");
        itemDAO = (ItemDAOImpl_JDBC) ctx.getBean("itemDAO");
    }

    List<Item> getAllItems()
    {
        return itemDAO.getItems();
    }

    Item save(Item item) {
        return itemDAO.save(item);
    }

    Item getItem(int id)
    {
        return itemDAO.getItem(id);
    }

    Item getItem(String barcode)
    {
        return itemDAO.getItem(barcode);
    }

    DeleteStatus deleteItem(int id)
    {
        return itemDAO.deleteItem(id);
    }
}
