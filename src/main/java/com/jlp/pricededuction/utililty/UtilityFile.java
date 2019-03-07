package com.jlp.pricededuction.utililty;

import com.google.gson.Gson;
import com.jlp.pricededuction.bean.ColorSwatches;
import com.jlp.pricededuction.bean.PriceLabel;
import com.jlp.pricededuction.bean.Products;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        public static final Comparator<Products> DESCENDING_COMPARATOR = (prod1, prod2) -> {

            int discount1 = Math.round(Float.parseFloat(prod1.getPrice())) - Math.round(Float.parseFloat(prod1.getNowPrice()));
            int discount2 = Math.round(Float.parseFloat(prod2.getPrice())) - Math.round(Float.parseFloat(prod2.getNowPrice()));
            return discount1 > discount2 ? -1: 1;
        };

    public static Map<String , String> convertJsonToMap(String strJson){

        return new Gson().fromJson(strJson,Map.class);
    }

    public static void getColorSwatchesList(JSONArray jsonArray, List colorList){

        jsonArray.forEach(coloritem -> {
            JSONObject colorJsonObject = (JSONObject) coloritem;
            ColorSwatches swatches = new ColorSwatches();
            String strSkuId = colorJsonObject.getString("skuId");
            String strColor = colorJsonObject.getString("color");
            String basicColor = colorJsonObject.getString("basicColor");

            if (strColor != null) {
                swatches.setColor(strColor);
                swatches.setRgbcolor(UtilityFile.basicColorToRGB().get(basicColor));
            }
            if (strSkuId != null) {
                swatches.setSkuId(strSkuId);
            }
            colorList.add(swatches);
        });

    }

    public static void setByLabelType(String labelType,String[] then,String[] now,String priceval,PriceLabel pLabel){

        if (!StringUtils.isEmpty(labelType)) {
            if (labelType.equals("ShowPercDscount")) {
                int discount = Math.round(Float.parseFloat(priceval)) - Math.round(Float.parseFloat(now[0]));
                int percent = discount * 100 / Math.round(Float.parseFloat(priceval));
                pLabel.setShowPercDscount(percent + "% off - now £" + now[0]);
            } else if (labelType.equals("ShowWasThenNow")) {
                if (!StringUtils.isEmpty(then[0])) {
                    pLabel.setShowWasThenNow("was £" + priceval + ", then £" + then[0] + ", now £" + now[0]);
                } else {
                    pLabel.setShowWasThenNow("was £" + priceval + ", now £" + now[0]);
                }
            } else {
                pLabel.setShowWasNow("Was £" + priceval + ", now £" + now[0]);
            }
        } else {
            pLabel.setShowWasNow("Was £" + priceval + "," + " now £" + now[0]);
        }
    }
}
