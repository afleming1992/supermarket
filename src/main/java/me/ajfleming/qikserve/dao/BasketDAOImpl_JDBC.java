package me.ajfleming.qikserve.dao;

import me.ajfleming.qikserve.helpers.RowMapperConverter;
import me.ajfleming.qikserve.holder.BasketItem;
import me.ajfleming.qikserve.holder.ValidPromotion;
import me.ajfleming.qikserve.model.Basket;
import me.ajfleming.qikserve.model.BasketPromotion;
import me.ajfleming.qikserve.model.Item;
import me.ajfleming.qikserve.model.Promotion;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.util.ArrayList;
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
    public Basket updateBasket(Basket basket) {
        try
        {
            String sql = "UPDATE basket SET completed=?, finalTotal=?, totalSavings=? WHERE id = ?";
            jdbc.update(sql, basket.isCompleted(), basket.getFinalTotal(), basket.getTotalSavings(), basket.getId());
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
    public List<Item> getBasketItems(Basket basket){
        String sql = "SELECT i.* FROM item i, basketItem bi WHERE bi.itemId = i.id AND bi.basketId = ?";
        try
        {
            return jdbc.query(sql, new Object[] { basket.getId() }, RowMapperConverter.getRowMapperForItem());
        }
        catch(EmptyResultDataAccessException e)
        {
            return null;
        }
    }

    @Override
    public boolean removeItemFromBasket(Basket basket, Item item) {
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

    public List<ValidPromotion> getAllValidPromotionsInBasket(Basket basket)
    {
        String sql = "SELECT p.promo_id as id, p.noOfItemsRequired, count(i.id) AS numberOfItemsInBasket FROM promotion_v p, item i, promotionItem pi, basketItem bi\n" +
                "        WHERE\n" +
                "          bi.itemId = i.id\n" +
                "          AND pi.itemId = i.id\n" +
                "          AND p.promo_id = pi.promotionId\n" +
                "          AND p.active = TRUE\n" +
                "          AND bi.basketId = ?\n" +
                "        GROUP BY promotionId\n" +
                "        HAVING numberOfItemsInBasket >= p.noOfItemsRequired;";
        try
        {
            SqlRowSet rows = jdbc.queryForRowSet(sql, basket.getId());
            List<ValidPromotion> promos = new ArrayList<>();
            while (rows.next()) {
                ValidPromotion p = new ValidPromotion(rows.getInt("id"), rows.getInt("numberOfItemsInBasket"));
                promos.add(p);
            }
            return promos;
        }
        catch(EmptyResultDataAccessException e)
        {
            return null;
        }
        catch(DataAccessException e)
        {
            return null;
        }
    }

    @Override
    public List<BasketItem> getAllBasketItemsPartOfPromotion(Basket basket, Promotion promotion) {
        String sql = "SELECT DISTINCT bi.id AS basketItemId, item.id AS itemId FROM basketItem bi, item, basket b, promotion p, promotionItem pi\n" +
                "WHERE\n" +
                "  bi.itemId = item.id\n" +
                "  AND pi.itemId = item.id\n" +
                "  AND pi.promotionId = p.id\n" +
                "  AND p.id = ?\n" +
                "  AND bi.basketId = ?\n" +
                "ORDER BY item.price ASC";
        try
        {
            SqlRowSet rows = jdbc.queryForRowSet(sql, promotion.getId(), basket.getId());
            List<BasketItem> items = new ArrayList<>();
            while (rows.next()) {
                BasketItem i = new BasketItem(rows.getInt("basketItemId"), rows.getInt("itemId"));
                items.add(i);
            }
            return items;
        }
        catch(EmptyResultDataAccessException e)
        {
            return new ArrayList<>();
        }
        catch(DataAccessException e)
        {
            return null;
        }
    }
}
