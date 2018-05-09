package com.amanachintyanikhil.blogapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Demonstrates how to use a twitter application keys to access a user's timeline
 */
public class HashtagActivity extends Activity {

    String hashtag;
    final static String LOG_TAG = "rnc";
    ListView lv_list;
    Button analyze_all;
    ArrayList<String> al_text = new ArrayList<>();
    TweetAdapter obj_adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        hashtag = getIntent().getStringExtra("hashtag");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);
        lv_list = (ListView) findViewById(R.id.lv_list);
        analyze_all = (Button) findViewById(R.id.button5);

        downloadTweets();
    }

    // download twitter timeline after first checking to see if there is a network connection
    public void downloadTweets() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadTwitterTask().execute();
        } else {
            Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private class DownloadTwitterTask extends AsyncTask<String, Void, String> {
        final ProgressDialog dialog = new ProgressDialog(HashtagActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setTitle("Loading");
            dialog.setMessage("Please wait");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... screenNames) {
            String result = "hello";
            try {
                al_text.clear();
                ConfigurationBuilder cb = new ConfigurationBuilder();
                cb.setDebugEnabled(true)
                        .setOAuthConsumerKey("y5Bg6YO2fM9Y9S3gwBrdnNIH7")
                        .setOAuthConsumerSecret("Omi4lTpahtod9qYshJqPSyEJBpX3ZmApjxIsiqfbWIiXrDkf32")
                        .setOAuthAccessToken("970702870881361920-QhETWfuuLQ5k3f08CsuMSsyEKVj5Sri")
                        .setOAuthAccessTokenSecret("UALezWaAcH2UFXcfauDvFKWE0UCwrzgGEFa0StoUyU0ZP");

                Twitter twitter = new TwitterFactory(cb.build()).getInstance();
                String hash = hashtag;
                Log.d(LOG_TAG, "doInBackground: ");
                Query query = new Query(hashtag);
                QueryResult res = twitter.search(query);
                for (twitter4j.Status status : res.getTweets()) {
                    al_text.add(status.getText());
                }
            } catch (TwitterException e){
                Log.d("Twitter Update Error", e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();

            obj_adapter = new TweetAdapter(getApplicationContext(), al_text);
            lv_list.setAdapter(obj_adapter);

            lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String item = (String) lv_list.getItemAtPosition(i);
                    Intent intent = new Intent(HashtagActivity.this, ResultActivity.class);
                    intent.putExtra("TWEET", item);
                    startActivity(intent);
                }
            });

            analyze_all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String item = "";
                    for (int i = 0; i < lv_list.getCount(); i++) {
                        String iter = (String) lv_list.getItemAtPosition(i);
                        item = item + iter;
                    }
                    Intent intent = new Intent(HashtagActivity.this, ResultActivity.class);
                    intent.putExtra("TWEET", item);
                    startActivity(intent);
                }
            });
        }
    }
}