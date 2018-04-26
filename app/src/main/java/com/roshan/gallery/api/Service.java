package com.roshan.gallery.api;

import android.app.ProgressDialog;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.roshan.gallery.activity.MainActivity;
import com.roshan.gallery.model.Image;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Service extends Volley {

    private static final String url = "https://api.unsplash.com/photos/?client_id=cc506a9dd764cf4cc5bbe085fd95b7961afded842cd02c6ab3ea75bd5fea5514&per_page=20";
    private JsonArrayRequest arrayRequest;
    private ArrayList<Image> images;
    private ProgressDialog pDialog;
    private String TAG = MainActivity.class.getSimpleName();

    public ArrayList<Image> getRandomImages() {
        arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());
                pDialog.hide();

                images.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        Image image = new Image();
                        //image.setName(object.getString("name"));
                        image.setLike(object.getInt("likes"));

                        JSONObject url = object.getJSONObject("urls");
                        image.setSmall(url.getString("small"));
                        image.setRegular(url.getString("regular"));
                        image.setFull(url.getString("full"));
                        //image.setTimestamp(object.getString("timestamp"));

                        images.add(image);

                    } catch (JSONException e) {
                        Log.e(TAG, "Json parsing error: " + e.getMessage());
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                pDialog.hide();
            }

        });
        return images;

    }


}
