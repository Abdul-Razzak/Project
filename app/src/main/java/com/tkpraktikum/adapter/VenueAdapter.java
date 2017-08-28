package com.tkpraktikum.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tkpraktikum.R;
import com.tkpraktikum.model.Venue;

import java.util.Collections;
import java.util.List;

/**
 * Created by Abu on 8/19/2017.
 */

public class VenueAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<Venue> data= Collections.emptyList();
    int currentPos=0;

    public interface OnItemClickListener {
        void onItemClick(Venue item);
    }

    private final OnItemClickListener listener;

    // create constructor to innitilize context and data sent from MainActivity
    public VenueAdapter(Context context, List<Venue> data, OnItemClickListener listener){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
        this.listener = listener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.container_venue, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder= (MyHolder) holder;
        Venue current=data.get(position);
        myHolder.bind(data.get(position), listener);
        myHolder.textVenueName.setText(current.name);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        TextView textVenueName;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            textVenueName= (TextView) itemView.findViewById(R.id.textVenueName);
        }

        public void bind(final Venue item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }

    }

}
