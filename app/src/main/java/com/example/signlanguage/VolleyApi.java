package com.example.signlanguage;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.signlanguage.model.Subcategory;
import com.example.signlanguage.model.Tab;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VolleyApi {
    Context context;
    List<Subcategory> subcategories = new ArrayList<>();

    public VolleyApi(Context context) {
        this.context = context;
    }

    public AlertDialog.Builder buildDialog(Context c) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("Không có kết nối Internet");
        builder.setMessage("Bạn cần phải bật dữ liệu di động hoặc wifi để truy cập ứng dụng này. Nhấn OK để thoát");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((MainActivity)context).finish();
                System.exit(0);
            }
        });
        return builder;
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
                                JSONObject subCategories = array.getJSONObject(i);
                                // Get the current student (json object) data
                                String name = subCategories.getString("name");
                                String image = subCategories.getString("image");
                                String id = subCategories.getString("id");
                                Tab tab = new Tab(id, name, image);
                                tabs.add(tab);

                            }
                            listener.onResponse(tabs);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            buildDialog(context);
                            Log.d("err", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        buildDialog(context);
//                        Toast.makeText(context, "Errr không kết nối mạng" + error,
//                                Toast.LENGTH_LONG).show();

                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }


    public interface OnTabResponse {
        void onResponse(List<Tab> tabs);
    }


    public void getPosts(final String urlJsonArry, final String name, final OnSubCategoryResponse listener) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlJsonArry, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Get the JSON array
                            JSONArray array = response.getJSONArray(name);//posts
                            // Loop through the array elements

                            for (int i = 0; i < array.length(); i++) {
                                // Get current json object
                                JSONObject posts = array.getJSONObject(i);
                                // Get the current student (json object) data
                                String subCategory_id = posts.getString("subCategoryId");
                                String id = posts.getString("id");
                                String keyword = posts.getString("keyword");
                                String image = posts.getString("image");
                                String video = posts.getString("video");
                                Subcategory subcategory = new Subcategory(subCategory_id, id, keyword, image, video);
                                subcategories.add(subcategory);

                            }

                            listener.OnSubCategoryResponse(subcategories);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            buildDialog(context);
                            Log.d("err", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        buildDialog(context);
//                        Toast.makeText(context, "Errr không kết nối mạng" + error,
//                                Toast.LENGTH_LONG).show();
                    }
                }
        );


        requestQueue.add(jsonObjectRequest);
    }


    public interface OnSubCategoryResponse {
        void OnSubCategoryResponse(List<Subcategory> subcategories);
    }

}
