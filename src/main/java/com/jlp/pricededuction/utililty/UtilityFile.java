package com.jlp.pricededuction.utililty;

import com.google.gson.Gson;
import com.jlp.pricededuction.bean.Products;

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

    public static final Comparator<Products> DESCENDING_COMPARATOR = new Comparator<Products>() {
        // Overriding the compare method to sort by price and nowprice
        public int compare(Products p1, Products p2) {

            int diff1 = Math.round(Float.parseFloat(p1.getPrice())) - Math.round(Float.parseFloat(p1.getNowPrice()));
            int diff2 = Math.round(Float.parseFloat(p2.getPrice())) - Math.round(Float.parseFloat(p2.getNowPrice()));
            return diff1 > diff2? -1: 1;
        }
    };

    public static Map<String , String> convertJsonToMap(String strJson){

        return new Gson().fromJson(strJson,Map.class);
    }
}
