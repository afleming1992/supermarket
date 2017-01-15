package me.ajfleming.qikserve.controller;

import me.ajfleming.qikserve.dao.ItemDAO;
import me.ajfleming.qikserve.dao.ItemDAOImpl_JDBC;
import me.ajfleming.qikserve.dao.PromotionDAO;
import me.ajfleming.qikserve.dao.PromotionDAOImpl_JDBC;
import me.ajfleming.qikserve.model.Item;
import me.ajfleming.qikserve.type.DeleteStatus;
import org.hibernate.sql.Delete;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by andrew on 14/01/17.
 */
public class ItemController {

    private ItemDAO itemDAO;

    ItemController(DataSource ds) {
        itemDAO = new ItemDAOImpl_JDBC(ds);
    }

    List<Item> getAllItems()
    {
        return itemDAO.getItems();
    }

    public Item save(Item item) {
        return itemDAO.save(item);
    }

    public Item getItem(int id)
    {
        return itemDAO.getItem(id);
    }

    public Item getItem(String barcode)
    {
        return itemDAO.getItem(barcode);
    }

    public DeleteStatus deleteItem(int id)
    {
        return itemDAO.deleteItem(id);
    }

    public void setItemDAO(ItemDAO dao){
        this.itemDAO = dao;
    }
}