package com.amanachintyanikhil.blogapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class CommentNewActivity extends AppCompatActivity {

    RecyclerView rvCommentList;
    DatabaseReference dbCommentPost;
    String postId;
    ArrayList<Comment> arrayList;
    Context context;
    DatabaseReference mUser,mComment,blogid;
    String name,photo,cmmnt;
    ImageButton send;
    EditText commentbox;
    String pushId;
    android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.tb);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Comments");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().show();
        toolbar.setNavigationIcon(R.drawable.navigationicon);

        commentbox=(EditText)findViewById(R.id.editText);
        arrayList = new ArrayList<>();
        postId = getIntent().getStringExtra("postkey");
        context = this;

        send=(ImageButton)findViewById(R.id.imageButton3);

        mUser = FirebaseDatabase.getInstance().getReference().child("User");
        dbCommentPost = FirebaseDatabase.getInstance().getReference().child("Comment").child(postId);
        rvCommentList = findViewById(R.id.commentview);
        rvCommentList.setLayoutManager(new LinearLayoutManager(context));
        mComment= FirebaseDatabase.getInstance().getReference().child("Comment");


        mUser.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                name=dataSnapshot.child("name").getValue().toString();
                photo=dataSnapshot.child("image").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blogid=mComment.child(postId).push();
                cmmnt=commentbox.getText().toString();
                blogid.child("comment").setValue(cmmnt);
                blogid.child("username").setValue(name);
                blogid.child("image").setValue(photo);
                commentbox.setText("");
            }
        });


        dbCommentPost.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot commentSnapShot : dataSnapshot.getChildren())
                {
                    Comment comment = commentSnapShot.getValue(Comment.class);
                    arrayList.add(comment);
                    Collections.reverse(arrayList);
                    CommentAdapter commentAdapter = new CommentAdapter(arrayList,context);
                    rvCommentList.setAdapter(commentAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
