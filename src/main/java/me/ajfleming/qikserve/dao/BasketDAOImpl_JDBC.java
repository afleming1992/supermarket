package me.ajfleming.qikserve.dao;

import me.ajfleming.qikserve.helpers.RowMapperConverter;
import me.ajfleming.qikserve.model.Basket;
import me.ajfleming.qikserve.model.BasketPromotion;
import me.ajfleming.qikserve.model.Item;
import me.ajfleming.qikserve.model.Promotion;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by andrew on 15/01/17.
 */
public class BasketDAOImpl_JDBC implements BasketDAO {

    private JdbcTemplate jdbc;

    public BasketDAOImpl_JDBC(DataSource ds){
        jdbc = new JdbcTemplate(ds);
    }

    @Override
    public int createBasket() {
        SimpleJdbcInsert insertQuery = new SimpleJdbcInsert(jdbc).withTableName("basket").usingColumns("completed").usingGeneratedKeyColumns("id");
        Map parameters = new HashMap<String, String>();
        parameters.put("completed", false);
        return insertQuery.executeAndReturnKey(parameters).intValue();
    }

    @Override
    public Basket editBasket(Basket basket) {
        try
        {
            String sql = "UPDATE basket SET completed=?, finalTotal=?, totalSavings=?";
            jdbc.update(sql, basket.isCompleted(), basket.getFinalTotal(), basket.getTotalSavings());
            return basket;
        }
        catch (DataAccessException e)
        {
            return null;
        }
    }

    @Override
    public Basket getBasket(int id){
        String sql = "SELECT * FROM basket WHERE id = ?";
        try
        {
            return jdbc.queryForObject(sql, new Object[]{ id }, RowMapperConverter.getRowMapperForBasket());
        }
        catch(EmptyResultDataAccessException e)
        {
            return null;
        }
    }

    @Override
    public List<Basket> getBaskets(){
        String sql = "SELECT * FROM basket";
        try
        {
            return jdbc.query(sql, new Object[] {}, RowMapperConverter.getRowMapperForBasket());
        }
        catch(EmptyResultDataAccessException e)
        {
            return null;
        }
    }

    @Override
    public boolean addItemToBasket(Basket basket, Item item) {
        try
        {
            String sql = "INSERT INTO basketItem (basketId, itemId) VALUES (?,?)";
            jdbc.update(sql, basket.getId(), item.getId());
            return true;
        }
        catch (DataAccessException e)
        {
            return false;
        }
    }

    @Override
    public boolean removeItemToBasket(Basket basket, Item item) {
        try
        {
            String sql = "DELETE FROM basketItem WHERE basketId = ? AND itemId = ? LIMIT 1";
            jdbc.update(sql, basket.getId(), item.getId());
            return true;
        }
        catch (DataAccessException e)
        {
            return false;
        }
    }
}
