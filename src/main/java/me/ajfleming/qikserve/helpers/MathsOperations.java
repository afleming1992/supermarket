package me.ajfleming.qikserve.helpers;

import me.ajfleming.qikserve.model.Item;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by andrew on 19/01/17.
 */
public class MathsOperations {

    public static float calculatePriceOfItems(List<Item> items)
    {
        float runningTotal = 0;
        for(Item i : items)
        {
            runningTotal += i.getPrice();
        }
        return runningTotal;
    }

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
}
