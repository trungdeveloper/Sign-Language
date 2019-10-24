package com.example.signlanguage;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.signlanguage.model.Tab;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VolleyApi {
    Context context;

    public VolleyApi(Context context) {
        this.context = context;
    }


    public void makeObjectArrayRequest(String urlJsonArry, final OnTabResponse listener) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlJsonArry, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Get the JSON array
                            JSONArray array = response.getJSONArray("subCategories");

                            // Loop through the array elements
                            List<Tab> tabs = new ArrayList<>();
                            for (int i = 0; i < array.length(); i++) {
                                // Get current json object
                                JSONObject student = array.getJSONObject(i);
                                // Get the current student (json object) data
                                String name = student.getString("name");
                                String image = student.getString("image");
                                String id = student.getString("id");
                                Tab tab = new Tab(id, name, image);
                                tabs.add(tab);
                            }
                            listener.onResponse(tabs);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Errr" + e,
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Errr" + error,
                                Toast.LENGTH_LONG).show();

                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }


    public interface OnTabResponse {
        void onResponse(List<Tab> tabs);
    }
}
