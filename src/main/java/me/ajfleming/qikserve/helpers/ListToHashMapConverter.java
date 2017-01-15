package me.ajfleming.qikserve.helpers;

import me.ajfleming.qikserve.model.Item;

import java.util.HashMap;
import java.util.List;

/**
 * Created by andrew on 15/01/17.
 */
public class ListToHashMapConverter {

    public static HashMap<Integer, Item> convertItemListToHashMap(List<Item> itemList)
    {
        HashMap<Integer, Item> map = new HashMap<>();
        for(Item i : itemList) map.put(i.getId(), i);
        return map;
    }
}
