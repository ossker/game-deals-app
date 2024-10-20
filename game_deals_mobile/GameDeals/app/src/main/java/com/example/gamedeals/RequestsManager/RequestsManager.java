package com.example.gamedeals.RequestsManager;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RequestsManager {

    public interface VolleyCallback {
        void onSuccess(JSONObject result) throws JSONException;
        void onError(VolleyError error);
    }
    public void sendCall(String url, Context context, JSONObject headerPayload, JSONObject bodyPayload, final VolleyCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    String responseString = new String(response.getBytes("ISO-8859-1"), "UTF-8");

                    JSONObject resp = new JSONObject(responseString);
                    callback.onSuccess(resp);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Błąd" + e, Toast.LENGTH_LONG).show();
                    callback.onError(new VolleyError("Błąd" + e));
                    Log.d("Volley Response2", e.toString());

                } catch (UnsupportedEncodingException e) {
                    Toast.makeText(context, "Błąd2: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    Log.d("Volley Response3", e.toString());

                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                /** method to handle errors. **/
                Log.d("Volley Response", error.toString());

                callback.onError(error);
            }
        }) {
            @Override
            public byte[] getBody() {
                JSONObject jsonParams = new JSONObject();
                if (bodyPayload == null){
                    return jsonParams.toString().getBytes();
                }
                for (Iterator<String> it = bodyPayload.keys(); it.hasNext(); ) {
                    String key = it.next();
                    try {
                        jsonParams.put(key, (String) bodyPayload.get(key));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return jsonParams.toString().getBytes();
            }
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<String, String>();
                if (headerPayload == null){
                    return headers;
                }
                for (Iterator<String> it = headerPayload.keys(); it.hasNext(); ) {
                    String key = it.next();
                    try {
                        headers.put(key, (String) headerPayload.get(key));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.d("headers", headers.toString());

                return headers;


            }
        };

        queue.add(request);

    }


}

