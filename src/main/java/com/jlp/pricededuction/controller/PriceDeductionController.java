package com.jlp.pricededuction.controller;


import com.google.gson.Gson;
import com.jlp.pricededuction.bean.ProductLists;
import com.jlp.pricededuction.bean.Products;
import com.jlp.pricededuction.service.ReadJsonDataService;
import com.jlp.pricededuction.utililty.UtilityFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@SuppressWarnings("SpringJavaAutowiringInspection")
@RestController
public class PriceDeductionController {
    @Autowired
    private ReadJsonDataService readJsonDataService;

    @Value("${url.jlp}")
    private String url;

    /**
     * Controller to read products.
     *
     * @return {@link ResponseEntity} a response reflecting the new products lists.
     */
    @RequestMapping(value = "/priceReductionList", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<String> priceReductionList(@RequestParam("labelType") String labelType) {
        Map<String, Object> responseObj = new HashMap<String, Object>();
        String msg = "";
        try {
            ProductLists json = readJsonDataService.readProductData(url);
            if (json != null) {
                List<Products> prodList = readJsonDataService.getProductsList(json, labelType);

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
