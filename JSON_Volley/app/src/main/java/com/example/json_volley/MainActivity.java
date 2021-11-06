package com.example.json_volley;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DownloadManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {
    JSONArray contacts = null;
    TextView output,outputarray ;
    String loginURL="http://api.androidhive.info/contacts/";
    String url="http://api.androidhive.info/volley/person_array.json";
    String data = "";
    String data2 = "";
    // JSON Node names
    private static final String TAG_CONTACTS = "contacts";
    private static final String TAG_ID = "id";

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState!=null){
            Log.d("STATE",savedInstanceState.toString());
        }
        requestQueue = Volley.newRequestQueue(this);
        output = (TextView) findViewById(R.id.jsonData);
        outputarray = (TextView) findViewById(R.id.heading);

        // if string contains { first character then string contain JSONObject as root container

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, loginURL, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("RESPONSE", String.valueOf(response));
                        try{
                            // Getting JSON Array node
                            contacts = response.getJSONArray(TAG_CONTACTS);
                            for (int i = 0; i < contacts.length(); i++) {
                                JSONObject c = contacts.getJSONObject(i);
                                String id = c.getString(TAG_ID);
                                data += "id "+id +"\n";
                            }
                            output.setText(data);
                        }catch(JSONException e){e.printStackTrace();}
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley","Error");

                    }
                }
        );
        requestQueue.add(jor);

        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        outputarray.setText("data2");
                        try{ outputarray.setText("data4");
                            contacts = response;
                            outputarray.setText("data3");
                            // Getting JSON Array node
                            for (int i = 0; i < contacts.length(); i++) {
                                JSONObject c = contacts.getJSONObject(i);
                                outputarray.setText("data5");
                                String id = c.getString("name");
                                data2 += "id "+id +"\n";
                            }
                            outputarray.setText(data2);
                        }catch(JSONException e){e.printStackTrace();}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley","Error2");
            }
        });
        requestQueue.add(req);

    }

}