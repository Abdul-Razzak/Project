package com.tkpraktikum.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tkpraktikum.R;
import com.tkpraktikum.model.Checkin;

import java.util.Collections;
import java.util.List;

/**
 * Created by salam on 29.08.17.
 */

public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    List<Checkin> data= Collections.emptyList();

    public HistoryAdapter(Context context , List<Checkin> data) {
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.container_history, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        Checkin checkin = data.get(position);
        myHolder.venueName.setText(checkin.getVenue_name());
        myHolder.createdAt.setText(checkin.getCreated_at());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView venueName;
        TextView createdAt;

        public MyHolder(View view) {
            super(view);
            venueName= (TextView) itemView.findViewById(R.id.venueName);
            createdAt = (TextView) itemView.findViewById(R.id.createdAt);
        }
    }
}
