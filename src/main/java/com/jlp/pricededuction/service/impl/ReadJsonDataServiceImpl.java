package com.jlp.pricededuction.service.impl;


import com.jlp.pricededuction.bean.PriceLabel;
import com.jlp.pricededuction.bean.ProductLists;
import com.jlp.pricededuction.bean.Products;
import com.jlp.pricededuction.service.ReadJsonDataService;
import com.jlp.pricededuction.utililty.UtilityFile;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReadJsonDataServiceImpl implements ReadJsonDataService {
    RestTemplate restTemplate;

    public ProductLists readProductData(String uri) {
        restTemplate = new RestTemplate();
        ProductLists productLists = restTemplate.getForObject(uri, ProductLists.class);
        return productLists;
    }

    public List<Products> getProductsList(ProductLists jsonArray, String labelType) {
        List<Products> prodList = new ArrayList<>();

        jsonArray.getProducts().forEach(key ->
        {
            Products prod = new Products();
            PriceLabel pLabel = new PriceLabel();
            List priceLabelList = new ArrayList();
            final ArrayList colorList = new ArrayList();
            prod.setProductId(key.getProductId());
            prod.setTitle(key.getTitle());
            if (key.getColorSwatches().size() > 0) {
                UtilityFile.setColorSwatchesList(key.getColorSwatches(), colorList);
            }
            Map priceMap = (LinkedHashMap) key.getPrice();
            final String[] then = {null};
            final String[] now = {null};
            priceMap.forEach((pricekey, priceVal) ->
            {

                if (pricekey.equals("then2") && !priceVal.equals("")) {
                    then[0] = priceVal.toString();
                } else if (pricekey.equals("then1") && !priceVal.equals("")) {
                    then[0] = priceVal.toString();
                }
                if (pricekey.equals("was") && !priceVal.equals("")) {
                    prod.setColorSwatches(colorList);
                    prod.setPrice(priceVal.toString());
                }

                if (pricekey.equals("now")) {

                    Map<String, String> m1;
                    if (priceVal.toString().startsWith("{")) {
                        m1 = (LinkedHashMap) priceVal;
                        if (m1 != null) {
                            String to = m1.get("to").toString();
                            now[0] = to;
                        }
                    } else {
                        now[0] = priceVal.toString();
                    }

                    if (!StringUtils.isEmpty(prod.getPrice())) {
                        UtilityFile.setByLabelType(labelType, then, now, prod.getPrice().toString(), pLabel);
                        priceLabelList.add(pLabel);
                        prod.setNowPrice(now[0]);
                        prod.setPriceLabel(priceLabelList);
                        prodList.add(prod);
                    }
                }
            });
        });
        return prodList;
    }
}

