package me.ajfleming.qikserve.dao;

import me.ajfleming.qikserve.helpers.RowMapperConverter;
import me.ajfleming.qikserve.model.Basket;
import me.ajfleming.qikserve.model.Item;
import me.ajfleming.qikserve.model.Promotion;
import me.ajfleming.qikserve.type.DeleteStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Class: ItemDAOImpl_JDBC
 *  Purpose: Implementation of the ItemDAO interface. This uses MySQL for persistence.
 *  Author: Andrew Fleming
 */

public class ItemDAOImpl_JDBC implements ItemDAO {

    private JdbcTemplate jdbc;
    public ItemDAOImpl_JDBC(DataSource dataSource) {
        jdbc = new JdbcTemplate(dataSource);
    }

    @Override
    public Item save(Item item) {
        if(item.getId() > 0) {
            String sql = "UPDATE item SET name=?, description=?, barcode=?, price=?, active=? WHERE id=?";
            jdbc.update(sql, item.getName(), item.getDescription(), item.getBarcode(), item.getPrice(), item.isActive(), item.getId());
            return item;
        }
        else
        {
            SimpleJdbcInsert insertQuery = new SimpleJdbcInsert(jdbc).withTableName("item").usingColumns("name","description","barcode","price","active").usingGeneratedKeyColumns("id");
            Map parameters = new HashMap();
            parameters.put("name", item.getName());
            parameters.put("description", item.getDescription());
            parameters.put("barcode", item.getBarcode());
            parameters.put("price", item.getPrice());
            parameters.put("active", item.isActive());
            int id = insertQuery.executeAndReturnKey(parameters).intValue();
            item.setId(id);
            return item;
        }
    }

    @Override
    public List<Item> getItems() {
        String sql = "SELECT * FROM item WHERE active = TRUE";
        return jdbc.query(sql, RowMapperConverter.getRowMapperForItem());
    }

    @Override
    public Item getItem(int id) {
        String sql = "SELECT * FROM item WHERE id = ?";
        try
        {
            return jdbc.queryForObject(sql, new Object[]{ id }, RowMapperConverter.getRowMapperForItem());
        }
        catch(EmptyResultDataAccessException e)
        {
            return null;
        }
    }

    @Override
    public Item getItem(String barcode) {
        String sql = "SELECT * FROM item WHERE barcode = ? AND active = TRUE";
        try {
            return jdbc.queryForObject(sql, new Object[]{barcode}, RowMapperConverter.getRowMapperForItem());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public DeleteStatus deleteItem(int id)
    {
        //Only doing a soft delete to keep past orders intact
        Item item = getItem(id);
        if(item != null)
        {
            try
            {
                item.setActive(false);
                save(item);
                return DeleteStatus.DEACTIVATED;
            }
            catch(Exception e)
            {
                return DeleteStatus.SERVER_ERROR;
            }
        }
        else
        {
            return DeleteStatus.NOT_FOUND;
        }
    }
}
