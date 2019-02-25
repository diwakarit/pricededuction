package com.jlp.pricededuction.utililty;

import com.google.gson.Gson;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class UtilityFile {

    private UtilityFile() {

    }

    public static HashMap<String,String> basicColorToRGB(){

        HashMap<String,String> colorMap = new HashMap<>();
        colorMap.put("Blue","#F0A1C2");
        colorMap.put("Red","#F3B4CE");
        colorMap.put("White","#FFFFFF");
        colorMap.put("Grey","#FFFFFF");
        colorMap.put("Purple","#808080");
        colorMap.put("Black","#000000");
        colorMap.put("Orange","#808080");
        colorMap.put("Yellow","#f5f5cc");

        return colorMap;
    }

    public static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
        Comparator<K> valueComparator =  new Comparator<K>() {
            public int compare(K k1, K k2) {
                int compare = map.get(k2).compareTo(map.get(k1));
                if (compare == 0) return 1;
                else return compare;
            }
        };
        Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
        sortedByValues.putAll(map);

        return sortedByValues;
    }

    public static Map<String , String> convertJsonToMap(String strJson){

        return new Gson().fromJson(strJson,Map.class);
    }
}
