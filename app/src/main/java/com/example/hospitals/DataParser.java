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
            if (!googlePlaceJSON.isNull("name"))
            {
                NameOfPlace = googlePlaceJSON.getString("name");
            }


            if (!googlePlaceJSON.isNull("vicinity"))
            {
                vicinity = googlePlaceJSON.getString("vicinity");
            }
            latitude = googlePlaceJSON.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = googlePlaceJSON.getJSONObject("geometry").getJSONObject("location").getString("lng");



        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
