package com.jlp.pricededuction.service.impl;


import com.jlp.pricededuction.bean.ColorSwatches;
import com.jlp.pricededuction.bean.PriceLabel;
import com.jlp.pricededuction.bean.Products;
import com.jlp.pricededuction.service.ReadJsonDataService;
import com.jlp.pricededuction.utililty.UtilityFile;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ReadJsonDataServiceImpl implements ReadJsonDataService {
    JSONObject jObj = null;
    String json = "";
    RestTemplate restTemplate;

    public JSONObject readJsonData(String uri) {
        restTemplate = new RestTemplate();
        json = restTemplate.getForObject(uri, String.class);
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            System.out.println("error on parse data");
        }
        return jObj;
    }

    public List<Products> getProductsList(JSONArray jsonArray, String labelType) {
        List<Products> prodList = new ArrayList<>();
        jsonArray.forEach(item -> {
            JSONObject jsonObject = (JSONObject) item;
            Products prod = new Products();
            PriceLabel pLabel = new PriceLabel();
            List priceLabelList = new ArrayList();
            final List colorList = new ArrayList();
            jsonObject.keys().forEachRemaining(key ->
            {
                String val = jsonObject.optString(key);
                final String[] then = {null};

                if (key.equals("productId")) {
                    prod.setProductId(val);
                }
                if (key.equals("title")) {
                    prod.setTitle(val);
                }
                if (key.equals("colorSwatches") && val.length() > 3) {
                    JSONArray jsonarray = new JSONArray(val);
                    UtilityFile.getColorSwatchesList(jsonarray,colorList);
                }
                if (key.equals("price")) {
                    final String[] now = {null};
                    JSONObject resobj = new JSONObject(val);
                    resobj.keys().forEachRemaining(pricekey ->
                    {
                        String priceVal = resobj.optString(pricekey);
                        if (pricekey.equals("then2") && !priceVal.equals("")) {
                            then[0] = priceVal;
                        } else if (pricekey.equals("then1") && !priceVal.equals("")) {
                            then[0] = priceVal;
                        }
                        if (pricekey.equals("now")) {
                            Map<String, String> m1;
                            if (priceVal.startsWith("{")) {
                                m1 = UtilityFile.convertJsonToMap(priceVal);
                                if (m1 != null) {
                                    String to = m1.get("to");
                                    now[0] = to;
                                }
                            } else {
                                now[0] = priceVal;
                            }
                        }
                        if (pricekey.equals("was") && !priceVal.equals("")) {
                            prod.setColorSwatches(colorList);
                            UtilityFile.setByLabelType(labelType,then,now,priceVal, pLabel);
                            priceLabelList.add(pLabel);
                            prod.setNowPrice(now[0]);
                            prod.setPriceLabel(priceLabelList);

                            prod.setPrice(priceVal);
                            prodList.add(prod);
                        }
                    });
                }
            });
        });
        return prodList;
    }
}

