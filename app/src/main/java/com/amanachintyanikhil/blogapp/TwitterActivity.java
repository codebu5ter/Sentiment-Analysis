package com.amanachintyanikhil.blogapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Demonstrates how to use a twitter application keys to access a user's timeline
 */
public class TwitterActivity extends Activity {

    String ScreenName;
    Button logout;
    TextView username;
    Spinner options;
    EditText hashtag;
    Button go;
    ArrayList<String> al_text = new ArrayList<>();
    String[] twitterOptions = { "Select an option", "My Posts", "My Mentions", "Search Hashtag" };
    private static final String TAG = "TwitterActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ScreenName = getIntent().getStringExtra("username");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter);

        logout = (Button) findViewById(R.id.button5);
        username = (TextView) findViewById(R.id.textView9);
        options = (Spinner) findViewById(R.id.spinner);
        hashtag = (EditText) findViewById(R.id.editText2);
        go = (Button) findViewById(R.id.button8);

        hashtag.setVisibility(View.INVISIBLE);
        go.setVisibility(View.INVISIBLE);

        username.setText("Welcome " + ScreenName);
        options.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==1){
                    Intent in=new Intent(TwitterActivity.this,TweetActivity.class);
                    in.putExtra("username",ScreenName);
                    startActivity(in);
                }
                else if(i==2){
                    Intent inte=new Intent(TwitterActivity.this,UserActivity.class);
                    inte.putExtra("user",ScreenName);
                    startActivity(inte);
                }
                if(i==3){
                    hashtag.setVisibility(View.VISIBLE);
                    go.setVisibility(View.VISIBLE);
                    go.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final String hash = hashtag.getText().toString();
                            Intent intent=new Intent(TwitterActivity.this,HashtagActivity.class);
                            intent.putExtra("hashtag",hash);
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,twitterOptions);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        options.setAdapter(aa);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutTwitter();
            }
        });

    }
    public void logoutTwitter() {
        Intent intent=new Intent(TwitterActivity.this,LoginActivity.class);
        Toast.makeText(TwitterActivity.this, "Successfully logged out!", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}