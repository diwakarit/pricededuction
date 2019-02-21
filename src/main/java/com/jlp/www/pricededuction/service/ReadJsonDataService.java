package com.jlp.www.pricededuction.service;

import org.json.JSONObject;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadJsonDataService {
    JSONObject readJsonData(String  url);
}
