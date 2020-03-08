package com.example.api;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
   EditText input;
   Button search;
   String server_url = "https://www.accuweather.com/";
   AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search = findViewById(R.id.btn);
        input = findViewById(R.id.input);
        builder = new AlertDialog.Builder(MainActivity.this);


    }


    public void seach(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String city;
                        city = search.getText().toString();
                        StringRequest request = new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                builder.setMessage("Weather: " + response);
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        input.setText("");
                                    }
                                });
                                AlertDialog diag = builder.create();
                                diag.show();

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(getApplicationContext(),"Something went wrong", Toast.LENGTH_LONG);
                                error.printStackTrace();
                            }
                        }){
                            protected Map<String, String> getparams() throws AuthFailureError{
                                Map<String, String> params = new HashMap<>();
                                params.put("query", city);
                                return params;
                            }
                        };
                        Singleton.getInstance(MainActivity.this).addRequestQueue(request);
                    }
                });
            }
        }).start();
    }


    public void Retrieve(View view) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, server_url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                          TextView view = findViewById(R.id.JSON);
                                try {
                                    view.setText(response.getString("view"));
                                    view.setVisibility(View.VISIBLE);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                          Toast.makeText(getApplicationContext(), "An error occured while retrieving data....",Toast.LENGTH_LONG);
                          error.printStackTrace();
                            }
                        });
                Singleton.getInstance(getApplication()).addRequestQueue(jsonObjectRequest);
            }
        }).start();
    }
}