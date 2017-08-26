package com.learn2crack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.learn2crack.model.Venue;
import com.learn2crack.network.NetworkUtil;

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

        //requestVenues("breakfast", latitude, longitude);
        setContentView(R.layout.activity_venue_details);
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

        System.out.println(response);

    }
}
