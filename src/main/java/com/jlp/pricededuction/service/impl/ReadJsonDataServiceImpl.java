package com.jlp.pricededuction.service.impl;


import com.jlp.pricededuction.service.ReadJsonDataService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;

@Service
public class ReadJsonDataServiceImpl implements ReadJsonDataService {

    static JSONObject jObj = null;
    static String json = "";

    public JSONObject readJsonData(String url) {

        try {
            URL strurl = new URL(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(strurl.openStream(),"iso-8859-1"), 8);

            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            json = sb.toString();

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

