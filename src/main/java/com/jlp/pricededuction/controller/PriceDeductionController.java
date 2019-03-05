package com.jlp.pricededuction.controller;

import com.google.gson.Gson;
import com.jlp.pricededuction.bean.ColorSwatches;
import com.jlp.pricededuction.bean.PriceLabel;
import com.jlp.pricededuction.bean.Products;
import com.jlp.pricededuction.service.ReadJsonDataService;
import com.jlp.pricededuction.utililty.UtilityFile;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Value("${url.jlp}")
    private String url;

    /**
     * Controller to read json data.
     *
     * @return {@link ResponseEntity} a response reflecting the message sent.
     */
    @RequestMapping(value = "/priceReductionList", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<String> readjsonurl(@RequestParam("labelType") String labelType) {
        Map<String, Object> responseObj = new HashMap<String, Object>();
        String msg = "";
        try {
            JSONObject json = readJsonDataService.readJsonData(url);
            JSONArray jsonArray;

            if (json != null) {
                jsonArray = json.getJSONArray("products");
                List<Products> prodList = new ArrayList<Products>();

                jsonArray.forEach(item -> {
                    JSONObject jsonObject = (JSONObject) item;
                    Products prod = new Products();
                    PriceLabel pLabel = new PriceLabel();
                    List priceLabelList = new ArrayList();
                    final List colorList = new ArrayList();

                    jsonObject.keys().forEachRemaining(key ->
                    {
                        String val = jsonObject.optString(key);
                        String then="";

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
                                if(pricekey.equals("then2") && !priceval.equals("")){
                                    then = priceval;
                                }else if(pricekey.equals("then1") && !priceval.equals("")){
                                    then = priceval;
                                }
                                if (pricekey.equals("now")) {
                                    Map<String, String> m1 = null;
                                    if (priceval.startsWith("{")) {
                                        m1 = UtilityFile.convertJsonToMap(priceval);
                                        if (m1 != null) {
                                            String to = m1.get("to");
                                            now = to;
                                        }
                                    } else {
                                        now = priceval;
                                    }
                                }
                                if (pricekey.equals("was") && !priceval.equals("")) {
                                    prod.setColorSwatches(colorList);
                                    if (!StringUtils.isEmpty(labelType)){
                                        if(labelType.equals("ShowPercDscount")){
                                            int discount = Math.round(Float.parseFloat(priceval)) - Math.round(Float.parseFloat(now));
                                            int percent = discount*100/Math.round(Float.parseFloat(priceval));
                                            pLabel.setShowPercDscount(percent+"% off - now £"+now);
                                        }
                                        else if(labelType.equals("ShowWasThenNow")){
                                            if(!then.equals("")){
                                                pLabel.setShowWasThenNow("was £"+priceval+", then £"+ then+", now £"+now);
                                            }else{
                                                pLabel.setShowWasThenNow("was £"+priceval+", now £"+now);
                                            }
                                        }else{
                                            pLabel.setShowWasNow("Was £" + priceval + ", now £" + now);
                                        }
                                    }else {
                                        pLabel.setShowWasNow("Was £" + priceval + "," + " now £" + now);
                                    }
                                    priceLabelList.add(pLabel);
                                    prod.setNowPrice(now);
                                    prod.setPriceLabel(priceLabelList);

                                    prod.setPrice(priceval);
                                    prodList.add(prod);
                                }
                            }
                        }

                    });
                });
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
