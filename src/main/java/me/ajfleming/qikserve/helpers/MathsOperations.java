package me.ajfleming.qikserve.helpers;

import me.ajfleming.qikserve.model.Item;

import java.math.BigDecimal;
import java.util.List;

/**
 *  Class: MathsOperations
 *  Purpose: This class holds static methods which help do maths calculations on prices etc
 *  Author: Andrew Fleming
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
}
