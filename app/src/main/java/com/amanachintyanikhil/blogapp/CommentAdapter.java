package com.amanachintyanikhil.blogapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{
    ArrayList<Comment> commentArrayList;
    Context context;

    public CommentAdapter(ArrayList<Comment> arrayList,Context context)
    {
        this.commentArrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutType = R.layout.comment_row;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(layoutType,parent,false);
        return new CommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CommentViewHolder holder, int position) {
        Comment comment = commentArrayList.get(position);
        holder.comment.setText(comment.getComment());
        holder.userName.setText(comment.getUsername());
        Glide.with(context).load(comment.getImage()).into(holder.civ);
        holder.btnAnalyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ResultActivity.class);
                intent.putExtra("TWEET", holder.comment.getText().toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return commentArrayList.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        TextView comment,userName;
        CircleImageView civ;
        Button btnAnalyze;

        public CommentViewHolder(View itemView) {
            super(itemView);
            civ = itemView.findViewById(R.id.civ);
            comment = itemView.findViewById(R.id.comment);
            userName = itemView.findViewById(R.id.username);
            btnAnalyze = itemView.findViewById(R.id.button3);
        }
    }
}
