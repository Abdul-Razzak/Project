package com.tkpraktikum.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tkpraktikum.R;
import com.tkpraktikum.model.Response;
import com.tkpraktikum.model.Venue;
import com.tkpraktikum.network.NetworkUtil;

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

        TextView venueLocation = (TextView) findViewById (R.id.venueLocation);
        venueLocation.setText(response.getLocation());

        TextView contact = (TextView) findViewById (R.id.venueContact);
        contact.setText(response.getPhone());


        System.out.println(response);

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(VenueDetails.this, ListActivity.class));
        finish();

    }

    public void checkin(View view)
    {
        checkInVenue("RazzakQ@gmail.com","Venue");
    }

    public void leavetip(View view)
    {
        Intent intent1 = new Intent(VenueDetails.this, LeaveTip.class);
        startActivity(intent1);//Edited here
    }


    private void checkInVenue(String email, String venueId) {

        mSubscriptions.add(NetworkUtil.checkin().checkin(email,venueId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleCheckin,this::handleError));
    }

    private void handleCheckin(Response response) {
        Button checkinButton = (Button)findViewById(R.id.checkin);
        checkinButton.setEnabled(false);
    }

}
