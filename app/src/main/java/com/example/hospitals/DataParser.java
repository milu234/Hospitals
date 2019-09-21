package com.example.hospitals;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class DataParser
{
    private HashMap<String,String> getPlace(JSONObject googlePlaceJSON){
        HashMap<String,String> googlePlaceMap = new HashMap<>();
        String NameOfPlace = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longitude = "";
        String refernce= "";

        try
        {
            NameOfPlace = googlePlaceJSON.getString("name");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
