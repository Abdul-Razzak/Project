package com.learn2crack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.learn2crack.model.Venue;
import com.learn2crack.network.NetworkUtil;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by Abu on 8/18/2017.
 */
public class ListActivity extends AppCompatActivity{

    /*
     * Define a request code to send to Google Play services This code is
     * returned in Activity.onActivityResult
     */
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    RecyclerView venueView;
    AdapterVenue mAdapter;
    double lat;
    double lng;
    String query;
    private CompositeSubscription mSubscriptions;
    private ToggleButton togglebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubscriptions = new CompositeSubscription();
        Intent listIntent = getIntent();
        setContentView(R.layout.activity_list);
        query = listIntent.getStringExtra("query");
        lat = listIntent.getDoubleExtra("lat",0.0);
        lng = listIntent.getDoubleExtra("lng",0.0);
        togglebutton = (ToggleButton) findViewById(R.id.togglebutton);
        requestVenues(query, lat, lng);
    }

    private void requestVenues(String query, Double lat, Double lng) {
        mSubscriptions.add(NetworkUtil.getVenues().getVenues(query, lat, lng)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private void handleError(Throwable throwable) {
    }

    private void handleResponse(List<Venue> response) {
        venueView = (RecyclerView)findViewById(R.id.venueList);
        mAdapter = new AdapterVenue(ListActivity.this, response);
        venueView.setAdapter(mAdapter);
        venueView.setLayoutManager(new LinearLayoutManager(ListActivity.this));
    }

    public void toggleclick(View v){
        if(togglebutton.isChecked())
            Toast.makeText(ListActivity.this, "ON", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(ListActivity.this, "OFF", Toast.LENGTH_SHORT).show();
    }
}

