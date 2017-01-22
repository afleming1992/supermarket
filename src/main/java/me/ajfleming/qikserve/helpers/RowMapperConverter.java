package me.ajfleming.qikserve.helpers;

import me.ajfleming.qikserve.model.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  Class: RowMapperConverter
 *  Purpose: This class holds static methods for the creation of Row Mappers used by the DAO Implementations to map rows in the db to objects
 *  Author: Andrew Fleming
 */

public class RowMapperConverter {

    public static RowMapper<Promotion> getRowMapperForPromotions(){
        return new RowMapper<Promotion>(){
            public Promotion mapRow(ResultSet rs, int rowNum) throws SQLException {
                return RowMapperConverter.mapPromotionRow(rs, rowNum);
            }
        };
    }

    public static RowMapper<Item> getRowMapperForItem(){
        return new RowMapper<Item>(){
            public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
                Item item = new Item();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setBarcode(rs.getString("barcode"));
                item.setDescription(rs.getString("description"));
                item.setPrice(rs.getFloat("price"));
                item.setActive(rs.getBoolean("active"));
                return item;
            }
        };
    }

    public static RowMapper<Basket> getRowMapperForBasket() {
        return new RowMapper<Basket>() {
            public Basket mapRow(ResultSet rs, int rowNum) throws SQLException {
                Basket basket = new Basket();
                basket.setId(rs.getInt("id"));
                basket.setCompleted(rs.getBoolean("completed"));
                basket.setTimestamp(rs.getTimestamp("timestamp"));
                basket.setFinalTotal(rs.getFloat("finalTotal"));
                basket.setTotalSavings(rs.getFloat("totalSavings"));
                return basket;
            }
        };
    }

    public static RowMapper<BasketPromotion> getRowMapperForBasketPromotions() {
        return new RowMapper<BasketPromotion>(){
            public BasketPromotion mapRow(ResultSet rs, int rowNum) throws SQLException {
                BasketPromotion basketPromotion = new BasketPromotion();
                basketPromotion.setId(rs.getInt("id"));
                basketPromotion.setPromotionPrice(rs.getFloat("price"));
                basketPromotion.setTotalSavings(rs.getFloat("totalSavings"));
                Promotion p = mapPromotionRow(rs, rowNum);

                basketPromotion.setPromotion(p);
                return basketPromotion;
            }
        };
    }

    private static Promotion mapPromotionRow(ResultSet rs, int rowNum) throws SQLException{
        int noOfFreeItems = rs.getInt("noOfFreeItems");
        float totalPrice = rs.getFloat("totalPrice");
        if(noOfFreeItems == 0 && totalPrice != 0)
        {
            //MoneyOffPromo
            MoneyOffPromotion promotion = new MoneyOffPromotion();
            setPromotionCommonValues(promotion, rs);
            promotion.setTotalPrice(totalPrice);
            return promotion;
        }
        else if(noOfFreeItems != 0 & totalPrice == 0f)
        {
            //FreeItemPromo
            FreeItemPromotion promotion = new FreeItemPromotion();
            setPromotionCommonValues(promotion, rs);
            promotion.setNoOfItemsFree(noOfFreeItems);
            return promotion;
        }
        else
        {
            //Promotion Sub-Type Table Record is missing for this record :S
            return null;
        }
    }

    private static Promotion setPromotionCommonValues(Promotion promotion, ResultSet rs) throws SQLException
    {
        promotion.setId(rs.getInt("promo_id"));
        promotion.setName(rs.getString("name"));
        promotion.setActive(rs.getBoolean("active"));
        promotion.setNoOfItemsRequired(rs.getInt("noOfItemsRequired"));
        return promotion;
    }
}
