package me.ajfleming.qikserve.dao;

import me.ajfleming.qikserve.helpers.RowMapperConverter;
import me.ajfleming.qikserve.model.Basket;
import me.ajfleming.qikserve.model.BasketPromotion;
import me.ajfleming.qikserve.model.Item;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by andrew on 17/01/17.
 */
public class BasketPromotionDAOImpl_JDBC implements BasketPromotionDAO {

    private JdbcTemplate jdbc;

    public BasketPromotionDAOImpl_JDBC(DataSource ds)
    {
        jdbc = new JdbcTemplate(ds);
    }

    @Override
    public BasketPromotion save(BasketPromotion basketPromo) {
        if (basketPromo.getId() > 0) {
            String sql = "UPDATE basketPromotion SET basketId = ?, promotionId = ?, price = ?, totalSavings = ? WHERE id = ?";
            jdbc.update(sql, basketPromo.getBasketId(), basketPromo.getPromotion().getId(), basketPromo.getPromotionPrice(), basketPromo.getTotalSavings());
            return basketPromo;
        }
        else
        {
            SimpleJdbcInsert insertQuery = new SimpleJdbcInsert(jdbc).withTableName("basketPromotion").usingGeneratedKeyColumns("id");
            Map parameters = new HashMap();
            parameters.put("basketId", basketPromo.getBasketId());
            parameters.put("promotionId", basketPromo.getPromotion().getId());
            parameters.put("price", basketPromo.getPromotionPrice());
            parameters.put("totalSavings", basketPromo.getTotalSavings());
            int id = insertQuery.executeAndReturnKey(parameters).intValue();
            basketPromo.setId(id);
            return basketPromo;
        }
    }

    @Override
    public boolean deleteBasketPromotion(BasketPromotion basketPromo) {
        try
        {
            String sql = "DELETE FROM basketPromotion WHERE id = ?";
            jdbc.update(sql, basketPromo.getId());
            return true;
        }
        catch(DataAccessException e)
        {
            return false;
        }
    }

    @Override
    public BasketPromotion getBasketPromotion(int basketPromotionId)
    {
        String sql = "SELECT bp.* FROM basketPromotion bp, promotion_v p WHERE bp.basketId = ? AND bp.promotionId = p.promo_id";
        try
        {
            return jdbc.queryForObject(sql, new Object[] { basketPromotionId }, RowMapperConverter.getRowMapperForBasketPromotions());
        }
        catch(EmptyResultDataAccessException e)
        {
            return null;
        }
    }

    @Override
    public List<BasketPromotion> getBasketPromotions(Basket basket) {
        String sql = "SELECT bp.id as id, bp.price as price, bp.totalSavings as totalSavings, p.* FROM basketPromotion bp, promotion_v p WHERE bp.basketId = ? AND bp.promotionId = p.promo_id";
        try
        {
            return jdbc.query(sql, new Object[] { basket.getId() }, RowMapperConverter.getRowMapperForBasketPromotions());
        }
        catch(EmptyResultDataAccessException e)
        {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Item> getBasketPromotionItems(BasketPromotion promo)
    {
        String sql = "SELECT * FROM item, basketPromotionItem bpi WHERE bpi.basketPromotion = ? AND item.id = bpi.itemId";
        try
        {
            return jdbc.query(sql, new Object[] { promo.getId() }, RowMapperConverter.getRowMapperForItem());
        }
        catch(EmptyResultDataAccessException e)
        {
            return null;
        }
    }

    @Override
    public boolean addItemToBasketPromotion(BasketPromotion promo, Item item) {
        try
        {
            String sql = "INSERT INTO basketPromotionItem (basketPromotionId, itemId) VALUES (?,?)";
            jdbc.update(sql, promo.getId(), item.getId());
            return true;
        }
        catch (DataAccessException e)
        {
            return false;
        }
    }

    @Override
    public boolean deleteItemToBasketPromotion(BasketPromotion promo, Item item) {
        try
        {
            String sql = "DELETE FROM basketPromotionItem WHERE basketPromotionId = ? AND itemId = ? LIMIT 1";
            jdbc.update(sql, promo.getId(), item.getId());
            return true;
        }
        catch (DataAccessException e)
        {
            return false;
        }
    }
}
