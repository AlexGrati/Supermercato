package com.example.alex.supermercato;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by utente4.academy on 01/12/2017.
 */

public class JSONParser {
    public static int lastUserKey(String json){
        int i = 1;
        try{
            JSONObject jsonObject = new JSONObject(json);
            Iterator iterator = jsonObject.keys();
            while (iterator.hasNext()){
                Log.d("DEBUG", ""+i);
                i++;
                iterator.next();
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return  i;
    }
}

