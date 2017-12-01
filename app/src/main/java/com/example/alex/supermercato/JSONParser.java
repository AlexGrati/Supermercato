package com.example.alex.supermercato;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by utente4.academy on 01/12/2017.
 */

public class JSONParser {

    public static List<Prodotto> getListaProdotti(String json){
        List<Prodotto> listaProdotti = new ArrayList<>();
            try{
                JSONObject jsonObject = new JSONObject(json);
                Iterator iteratorProdotti = jsonObject.keys();
                while (iteratorProdotti.hasNext()){
                    String key = (String)iteratorProdotti.next();
                    Log.d("DEBUG","key: " + key);
                    JSONObject jsonObject1 =  jsonObject.getJSONObject(key);
                    Iterator iterator2 = jsonObject1.keys();
                    while (iterator2.hasNext()){
                        String key2 = (String)iterator2.next();
                        JSONObject tempObject = jsonObject1.getJSONObject(key2);
                        Iterator iterator3 = tempObject.keys();
                        Prodotto prodotto;
                        switch (key){
                            case "Carne":
                                prodotto = new Carne();
                                break;
                            case "Pesce":
                                prodotto = new Pesce();
                                break;
                            case "Latte":
                                prodotto = new Latte();
                                break;
                            default:
                                prodotto = new Prodotto();
                                break;
                        }

                        while (iterator3.hasNext()){
                            String key3 = (String)iterator3.next();
                            String tempKey2 = tempObject.getString(key3);
                            switch (key3){
                                case "Marca":
                                    prodotto.setMarca(tempKey2);
                                    break;
                                case "Prezzo":
                                    prodotto.setPrezzo(Double.parseDouble(tempKey2));
                            }
                        }
                        Log.d("DEBUG",""+listaProdotti.size());
                        listaProdotti.add(prodotto);
                    }
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        return listaProdotti;
    }


    public static String lastUserKey(String json){
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

        String key = "";
        if(i< 10){
            key = "0"+i;
        }else {
            key = ""+i;
        }

        return  key;
    }
}

