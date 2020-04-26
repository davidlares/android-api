package com.davidlares.apiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private final static String URL = "https://jsonplaceholder.typicode.com/posts";
    private final static String STRING_URL = "http://magadistudio.com/complete-android-developer-course-source-files/string.html";
    private final static String RAW_JSON = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2014-01-02";
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(this);
        // getStringObject();
        // [{}]
        // getArrayObjects();
        // {} -> direct
        getJsonObject();
    }

    public void getArrayObjects() {
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, URL, new Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // response have all the JSON response
                for(int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject postObject = response.getJSONObject(i);
                        Log.d("Items", postObject.getString("title"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error", error.getMessage());
            }
        });

        queue.add(arrayRequest); // adding to the queue
    }

    // Strings
    public void getStringObject() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, STRING_URL, new Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("String", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error", error.getMessage());
            }
        });

        queue.add(stringRequest);
    }

    public void getJsonObject() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, RAW_JSON, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("Object", response.getString("type").toString());
                    JSONObject metadata = response.getJSONObject("metadata"); // getting specific data
                    Log.d("Metadata", metadata.toString());
                    Log.d("Metadata - Info", metadata.getString("generated").toString());
                    // going deeper in hierarchy - must need a JsonArrayObject
                    JSONArray features = response.getJSONArray("features");
                    for(int i = 0; i < features.length(); i++) {
                        JSONObject propertiesObj = features.getJSONObject(i).getJSONObject("properties");
                        Log.d("Places", propertiesObj.getString("place").toString());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error", error.getMessage());
            }
        });

        queue.add(jsonObjectRequest);
    }
}
