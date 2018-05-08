package com.amanachintyanikhil.blogapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ResultActivity extends AppCompatActivity {

    public static final String URL = "http://ace86dfb.ngrok.io";
    public static final String TAG = "MA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        final TextView scoreTxtView = (TextView) findViewById(R.id.score);
        final RatingBar ratingBar = (RatingBar)findViewById(R.id.ratingBar1);
        final ImageView img = (ImageView)findViewById(R.id.img1);

        final com.android.volley.RequestQueue queue = Volley.newRequestQueue(this);
        final JSONObject jsonObject = new JSONObject();

        String tweet = getIntent().getExtras().getString("TWEET");


        try {
            jsonObject.put("msg", tweet);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String result = response.getString("result");
                    String str = "";
                    if (result.equals("1")){
                        str = "Positive";
                        img.setImageResource(R.drawable.score_5);
                        ratingBar.setRating(5);
                    }

                    else if(result.equals("0")){
                        str = "Negative";
                        img.setImageResource(R.drawable.score_1);
                        ratingBar.setRating(1);
                    }

                    scoreTxtView.setText(str);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.toString());
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjectRequest);
    }
}
