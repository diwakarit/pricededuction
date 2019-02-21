package com.jlp.www.pricededuction.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jlp.www.pricededuction.Bean.Products;
import com.jlp.www.pricededuction.service.ReadJsonDataService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;

@Service
public class ReadJsonDataServiceImpl implements ReadJsonDataService {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";


    public JSONObject readJsonData(String url) {

        try {
            URL strurl = new URL(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(strurl.openStream(),"iso-8859-1"), 8);
            /*BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);*/
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

