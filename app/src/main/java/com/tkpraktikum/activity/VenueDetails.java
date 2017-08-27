package com.tkpraktikum.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.tkpraktikum.R;
import com.tkpraktikum.model.Venue;
import com.tkpraktikum.network.NetworkUtil;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class VenueDetails extends AppCompatActivity {
    private CompositeSubscription mSubscriptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubscriptions = new CompositeSubscription();
        double latitude = 49.872677;
        double longitude= 8.632473;
        Intent detailsIntent = getIntent();
        String query = detailsIntent.getStringExtra("venueId");
        requestVenue(query);
        setContentView(R.layout.activity_venue_details);
    }

    private void requestVenue(String query) {

        mSubscriptions.add(NetworkUtil.getVenue().getVenue(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private void handleError(Throwable throwable) {
    }

    private void handleResponse(Venue response) {
        TextView venueName = (TextView) findViewById (R.id.venueName);
        venueName.setText(response.getName());

        System.out.println(response);

    }
}
