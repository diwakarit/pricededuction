package com.jlp.www.pricededuction.controller;

import com.jlp.www.pricededuction.service.ReadJsonDataService;
import com.jlp.www.pricededuction.utililty.UtilityFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "Internal Single Step APIs")
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
    @ApiOperation(value = "Read Json")
    public ResponseEntity<String> readjsonurl() {
        String url = "https://jl-nonprod-syst.apigee.net/v1/categories/600001506/products?key=2ALHCAAs6ikGRBoy6eTHA58RaG097Fma";
        Map<String, Object> responseObj = new HashMap<String, Object>();
        String msg = "";
        try {
            JSONObject json = readJsonDataService.readJsonData(url);
            JSONArray jsonArray = null;

            if (json != null) {
                jsonArray = json.getJSONArray("products");
                List prodList = new ArrayList();
                List colorList = null;
                for (int ja = 0; ja < jsonArray.length(); ja++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(ja);
                    HashMap m = new HashMap();
                    Map<String, Object> colorSwatchesObj = null;
                    Map<String, Object> priceLabel = new HashMap<>();
                    List priceLabelList = new ArrayList();
                    colorList = new ArrayList();
                    Iterator it1 = jsonObject.keys();
                    while (it1.hasNext()) {
                        String key = (String) it1.next();
                        String val = jsonObject.optString(key);

                        if (key.equals("productId")) {
                            m.put("productId", val);
                        }
                        if (key.equals("title")) {
                            m.put("title", val);
                        }
                        if (key.equals("colorSwatches") && val.length() > 3) {
                            JSONArray jsonarray = new JSONArray(val);

                            for (int i = 0; i < jsonarray.length(); i++) {
                                colorSwatchesObj = new HashMap<String, Object>();
                                JSONObject jsonobject = jsonarray.getJSONObject(i);
                                String strSkuId = jsonobject.getString("skuId");
                                String strColor = jsonobject.getString("color");
                                String basicColor = jsonobject.getString("basicColor");

                                if (strColor != null) {
                                    colorSwatchesObj.put("color", strColor);
                                    colorSwatchesObj.put("rgbcolor", UtilityFile.basicColorToRGB().get(basicColor));
                                }
                                if (strSkuId != null) {
                                    colorSwatchesObj.put("skuId", strSkuId);
                                }
                                colorList.add(colorSwatchesObj);
                            }
                        }
                        if (key.equals("price")) {
                            String now = "";
                            JSONObject resobj = new JSONObject(val);
                            Iterator it3 = resobj.keys();
                            while (it3.hasNext()) {
                                String s3 = (String) it3.next();
                                String val3 = resobj.optString(s3);

                                if (s3.equals("now")) {
                                    Map<String,String> m1 =null;
                                    if(val3.startsWith("{")) {
                                        m1 = UtilityFile.convertJsonToMap(val3);
                                        if (m1 != null) {
                                            String to = m1.get("to");
                                            now=to;
                                        }
                                    }else{
                                        now = val3;
                                    }
                                }
                                if (s3.equals("was") && !val3.equals("")) {
                                    m.put("colorSwatches", colorList);
                                    priceLabel.put("showWasNow", "Was £" + val3 + "," + " now £" + now);
                                    // Since query param is not available
                                    /*priceLabel.put("showWasThenNow","");
                                    priceLabel.put("ShowPercDscount","");
                                     */
                                    priceLabelList.add(priceLabel);
                                    m.put("nowPrice", now);
                                    m.put("priceLabel", priceLabelList);
                                    m.put(key, val3);
                                    prodList.add(m);
                                }
                            }
                        }
                    }
                }
                responseObj.put("products", prodList);
                System.out.println(new JSONObject(responseObj));
                msg = new JSONObject(responseObj).toString();
            } else {
                msg = "FAIL DURING URL CALL";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
