package com.jlp.pricededuction.controller;

import com.google.gson.Gson;
import com.jlp.pricededuction.bean.*;
import com.jlp.pricededuction.service.ReadJsonDataService;
import com.jlp.pricededuction.utililty.UtilityFile;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Example controller for /api requests.
 */

@SuppressWarnings("SpringJavaAutowiringInspection")
@RestController
public class PriceDeductionController {
    @Autowired
    private ReadJsonDataService readJsonDataService;

    /**
     * Controller to read json data.
     *
     * @return {@link ResponseEntity} a response reflecting the message sent.
     */
    @RequestMapping(value = "/api/readjson", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<String> readjsonurl() {
        String url = "https://jl-nonprod-syst.apigee.net/v1/categories/600001506/products?key=2ALHCAAs6ikGRBoy6eTHA58RaG097Fma";
        Map<String, Object> responseObj = new HashMap<String, Object>();
        String msg = "";
        try {
            JSONObject json = readJsonDataService.readJsonData(url);
            JSONArray jsonArray = null;

            if (json != null) {
                jsonArray = json.getJSONArray("products");
                List<Products> prodList = new ArrayList<Products>();
                List colorList = null;
                for (int ja = 0; ja < jsonArray.length(); ja++) {
                    Products prod = new Products();
                    PriceLabel pLabel = new PriceLabel();

                    JSONObject jsonObject = jsonArray.getJSONObject(ja);
                    HashMap m = new HashMap();
                    List priceLabelList = new ArrayList();
                    colorList = new ArrayList();
                    Iterator it1 = jsonObject.keys();
                    while (it1.hasNext()) {
                        String key = (String) it1.next();
                        String val = jsonObject.optString(key);

                        if (key.equals("productId")) {
                            prod.setProductId(val);
                        }
                        if (key.equals("title")) {
                            prod.setTitle(val);
                        }
                        if (key.equals("colorSwatches") && val.length() > 3) {
                            JSONArray jsonarray = new JSONArray(val);

                            for (int i = 0; i < jsonarray.length(); i++) {
                                ColorSwatches swatches = new ColorSwatches();

                                JSONObject jsonobject = jsonarray.getJSONObject(i);
                                String strSkuId = jsonobject.getString("skuId");
                                String strColor = jsonobject.getString("color");
                                String basicColor = jsonobject.getString("basicColor");

                                if (strColor != null) {
                                    swatches.setColor(strColor);
                                    swatches.setRgbcolor(UtilityFile.basicColorToRGB().get(basicColor));
                                }
                                if (strSkuId != null) {
                                    swatches.setSkuId(strSkuId);
                                }
                                colorList.add(swatches);
                            }
                        }
                        if (key.equals("price")) {
                            String now = "";
                            JSONObject resobj = new JSONObject(val);
                            Iterator it3 = resobj.keys();
                            while (it3.hasNext()) {
                                String pricekey = (String) it3.next();
                                String priceval = resobj.optString(pricekey);

                                if (pricekey.equals("now")) {
                                    Map<String,String> m1 =null;
                                    if(priceval.startsWith("{")) {
                                        m1 = UtilityFile.convertJsonToMap(priceval);
                                        if (m1 != null) {
                                            String to = m1.get("to");
                                            now=to;
                                        }
                                    }else{
                                        now = priceval;
                                    }
                                }
                                if (pricekey.equals("was") && !priceval.equals("")) {

                                    prod.setColorSwatches(colorList);
                                    pLabel.setShowWasNow("Was £" + priceval + "," + " now £" + now);

                                    // Since query param is not available
                                    /*priceLabel.put("showWasThenNow","");
                                    priceLabel.put("ShowPercDscount","");
                                     */

                                    priceLabelList.add(pLabel);
                                    prod.setNowPrice(now);
                                    prod.setPriceLabel(priceLabelList);

                                    prod.setPrice(priceval);
                                    prodList.add(prod);
                                }
                            }
                        }
                    }
                }

                Collections.sort(prodList, UtilityFile.DESCENDING_COMPARATOR);
                responseObj.put("products", prodList);
                msg = new Gson().toJson(responseObj);

            } else {
                msg = "FAIL DURING URL CALL";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
