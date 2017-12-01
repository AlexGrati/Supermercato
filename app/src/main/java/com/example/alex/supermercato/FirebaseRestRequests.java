package com.example.alex.supermercato;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by utente4.academy on 01/12/2017.
 */

public class FirebaseRestRequests {
    private static final String BASE_URL = "https://supermercato-b4c0d.firebaseio.com/";
    private static final AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler){
        client.get(BASE_URL + url +".json", params, responseHandler);
    }
    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler){
        client.get(BASE_URL + url +".json", params, responseHandler);
    }
}
