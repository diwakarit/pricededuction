package com.jlp.pricededuction.service;

import com.jlp.pricededuction.bean.Products;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReadJsonDataService {
    JSONObject readJsonData(String  url);
    List<Products> getProductsList(JSONArray jsonArray, String labelType);
}
