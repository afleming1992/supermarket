package me.ajfleming.qikserve.helpers;

import me.ajfleming.qikserve.model.FreeItemPromotion;
import me.ajfleming.qikserve.model.Item;
import me.ajfleming.qikserve.model.MoneyOffPromotion;
import me.ajfleming.qikserve.model.Promotion;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by andrew on 15/01/17.
 */
public class RowMapperConverter {

    public static RowMapper<Promotion> getRowMapperForPromotions(){
        return new RowMapper<Promotion>(){
            public Promotion mapRow(ResultSet rs, int rowNum) throws SQLException {
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

            private Promotion setPromotionCommonValues(Promotion promotion, ResultSet rs) throws SQLException
            {
                promotion.setId(rs.getInt("promo_id"));
                promotion.setName(rs.getString("name"));
                promotion.setActive(rs.getBoolean("active"));
                promotion.setNoOfItemsRequired(rs.getInt("noOfItemsRequired"));
                return promotion;
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
}
