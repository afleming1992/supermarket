package me.ajfleming.qikserve.dao;

import me.ajfleming.qikserve.helpers.RowMapperConverter;
import me.ajfleming.qikserve.model.FreeItemPromotion;
import me.ajfleming.qikserve.model.Item;
import me.ajfleming.qikserve.model.MoneyOffPromotion;
import me.ajfleming.qikserve.model.Promotion;
import me.ajfleming.qikserve.type.DeleteStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Class: PromotionDAOImpl_JDBC
 *  Purpose: Implementation of the PromotionDAO interface. This uses MySQL for persistence.
 *  Author: Andrew Fleming
 */

public class PromotionDAOImpl_JDBC implements PromotionDAO {

    private JdbcTemplate jdbc;

    public PromotionDAOImpl_JDBC(DataSource dataSource) {
        jdbc = new JdbcTemplate(dataSource);
    }

    private Promotion save(Promotion promo) {
        if(promo instanceof FreeItemPromotion)
        {
            return save((FreeItemPromotion) promo);
        }
        else
        {
            return save((MoneyOffPromotion) promo);
        }
    }

    @Override
    public FreeItemPromotion save(FreeItemPromotion promo) {
        if(promo.getId() > 0) {
            boolean result = updatePromotionTable(promo);
            if(result) {
                String sql = "UPDATE freeItemPromotion SET noOfFreeItems = ? WHERE promotionId = ?";
                jdbc.update(sql, promo.getNoOfItemsFree(), promo.getId());
                return promo;
            }
            else
            {
                return null;
            }
        }
        else
        {
            promo = (FreeItemPromotion) insertIntoPromotionTable(promo);

            SimpleJdbcInsert insertQuery = new SimpleJdbcInsert(jdbc).withTableName("freeItemPromotion");
            Map parameters = new HashMap();
            parameters.put("promotionId", promo.getId());
            parameters.put("noOfFreeItems", promo.getNoOfItemsFree());
            insertQuery.execute(parameters);
            return promo;
        }
    }

    @Override
    public MoneyOffPromotion save(MoneyOffPromotion promo) {
        if(promo.getId() > 0) {
            boolean result = updatePromotionTable(promo);
            if(result)
            {
                String sql = "UPDATE moneyOffPromotion SET totalPrice = ? WHERE promotionId = ?";
                jdbc.update(sql, promo.getTotalPrice(), promo.getId());
                return promo;
            }
            else
            {
                return null;
            }
        }
        else
        {
            MoneyOffPromotion promotion = (MoneyOffPromotion) insertIntoPromotionTable(promo);

            SimpleJdbcInsert insertQuery = new SimpleJdbcInsert(jdbc).withTableName("moneyOffPromotion");
            Map parameters = new HashMap();
            parameters.put("promotionId", promo.getId());
            parameters.put("totalPrice", promo.getTotalPrice());
            insertQuery.execute(parameters);
            return promo;
        }
    }

    private boolean updatePromotionTable(Promotion promo)
    {
        try
        {
            String sql = "UPDATE promotion SET name=?, active=?, noOfItemsRequired=? WHERE id=?";
            jdbc.update(sql, promo.getName(), promo.isActive(), promo.getNoOfItemsRequired(), promo.getId());
            return true;
        }
        catch(DataAccessException e)
        {
            return false;
        }
    }

    private Promotion insertIntoPromotionTable(Promotion promo)
    {
        try {
            SimpleJdbcInsert insertQuery = new SimpleJdbcInsert(jdbc).withTableName("promotion").usingColumns("name","active","noOfItemsRequired").usingGeneratedKeyColumns("id");
            Map parameters = new HashMap();
            parameters.put("name", promo.getName());
            parameters.put("active", promo.isActive());
            parameters.put("noOfItemsRequired", promo.getNoOfItemsRequired());
            int id = insertQuery.executeAndReturnKey(parameters).intValue();
            promo.setId(id);
            return promo;
        }
        catch(DataAccessException e)
        {
            return null;
        }
    }

    @Override
    public List<Promotion> getPromotions() {
        String sql = "SELECT * FROM promotion_v WHERE active = TRUE";
        return jdbc.query(sql, RowMapperConverter.getRowMapperForPromotions());
    }

    @Override
    public Promotion getPromotion(int id) {
        try {
            String sql = "SELECT * FROM promotion_v WHERE active = TRUE AND promo_id = ?";
            return jdbc.queryForObject(sql, new Object[]{id}, RowMapperConverter.getRowMapperForPromotions());
        }
        catch(EmptyResultDataAccessException e)
        {
            return null;
        }
    }

    @Override
    public List<Item> getPromotionItems(Promotion promo) {
        String sql = "SELECT DISTINCT i.* FROM item i, promotion p, promotionItem pi WHERE pi.itemId = i.id AND pi.promotionId = ?";
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
    public List<Promotion> getAnItemsPromotions(Item item) {
        try {
            String sql = "SELECT p.* FROM promotion_v p, promotionItem pi WHERE p.active = TRUE AND p.promo_id = pi.promotion AND pi.item = ?";
            return jdbc.query(sql, new Object[]{ item.getId() }, RowMapperConverter.getRowMapperForPromotions());
        }
        catch(EmptyResultDataAccessException e)
        {
            return null;
        }
    }

    @Override
    public boolean addItemToPromotion(Promotion promo, Item item) {
        try
        {
            String sql = "INSERT INTO promotionItem (promotionId,itemId) VALUES (?,?)";
            jdbc.update(sql, promo.getId(), item.getId());
            return true;
        }
        catch(DataAccessException e)
        {
            return false;
        }
    }

    @Override
    public boolean removeItemFromPromotion(Promotion promo, Item item) {
        try
        {
            String sql = "DELETE FROM promotionItem WHERE promotionId = ? AND itemId = ? LIMIT 1";
            jdbc.update(sql, promo.getId(), item.getId());
            return true;
        }
        catch(DataAccessException e)
        {
            return false;
        }
    }

    @Override
    public DeleteStatus deletePromotion(int id) {
        Promotion promo = getPromotion(id);
        if(promo != null)
        {
            try
            {
                promo.setActive(false);
                save(promo);
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
