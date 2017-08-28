package com.tkpraktikum.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tkpraktikum.R;
import com.tkpraktikum.model.Comment;

import java.util.Collections;
import java.util.List;

/**
 * Created by salam on 28.08.17.
 */

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<Comment> data= Collections.emptyList();

    // create constructor to innitilize context and data sent from MainActivity
    public CommentAdapter(Context context, List<Comment> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.container_comments, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        Comment comment = data.get(position);
        myHolder.comment.setText(comment.getTips());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView comment;

        public MyHolder(View view) {
            super(view);
            comment= (TextView) itemView.findViewById(R.id.comment);
        }
    }
}
