package com.jlp.pricededuction.service.impl;


import com.jlp.pricededuction.service.ReadJsonDataService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Scanner;

@Service
public class ReadJsonDataServiceImpl implements ReadJsonDataService {

    static JSONObject jObj = null;
    static String json = "";

    public JSONObject readJsonData(String url) {

        try {
           URL strUrl = new URL(url);

           json = new Scanner(strUrl.openStream(), "UTF-8").useDelimiter("\\A").next();

        } catch (Exception e) {
           System.out.println(e.toString());
        }

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            System.out.println("error on parse data");
        }

        // return JSON String
        return jObj;

    }
}

