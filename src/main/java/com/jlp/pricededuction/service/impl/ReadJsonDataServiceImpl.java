package com.jlp.pricededuction.service.impl;


import com.jlp.pricededuction.service.ReadJsonDataService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReadJsonDataServiceImpl implements ReadJsonDataService {

    static JSONObject jObj = null;
    static String json = "";
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
}

