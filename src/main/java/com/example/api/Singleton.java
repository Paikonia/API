package com.example.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Singleton {
    private static Singleton msingleton;
    private RequestQueue requestQueue;
    private static Context context;
    private JsonObjectRequest json;

    Singleton(Context at){
        context = at;
        requestQueue = getRequestQueue();


    }

    public static synchronized Singleton getInstance(Context ab){
        if(msingleton==null){
           msingleton = new Singleton(context);
        }
        return msingleton;
    }


    public RequestQueue getRequestQueue() {
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());

        }
        return requestQueue;
    }
    public<T> void addRequestQueue(Request res) {
       requestQueue.add(res);
    }

}
