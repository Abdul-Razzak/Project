package com.tkpraktikum.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tkpraktikum.BaseApplication;
import com.tkpraktikum.R;
import com.tkpraktikum.adapter.CommentAdapter;
import com.tkpraktikum.adapter.VenueAdapter;
import com.tkpraktikum.model.Checkin;
import com.tkpraktikum.model.Comment;
import com.tkpraktikum.model.Response;
import com.tkpraktikum.model.Venue;
import com.tkpraktikum.network.NetworkUtil;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class VenueDetails extends AppCompatActivity {

    private CompositeSubscription mSubscriptions;
    RecyclerView commentView;
    CommentAdapter mAdapter;
    String venueId;
    String email;
    String venueName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubscriptions = new CompositeSubscription();
        Intent detailsIntent = getIntent();
        venueId = detailsIntent.getStringExtra("venueId");
        email = ((BaseApplication) getApplication()).getEmail();
        setContentView(R.layout.activity_venue_details);
        loadChildren();
        venueName = "Nazar";

    }

    private void loadChildren() {
        requestVenue(venueId);
        requestComments(venueId, email);
        requestCheckinInfo(venueId, email);
    }

    private void requestCheckinInfo(String venueId, String email) {
        mSubscriptions.add(NetworkUtil.generic().getCheckinInfo(venueId, email)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleCheckInInfoResponse,this::handleError));
    }

    private void handleCheckInInfoResponse(List<Checkin> response) {
        if (response.size() >= 1) {
            Button checkinButton = (Button)findViewById(R.id.checkin);
            checkinButton.setText("Already Visited");
            checkinButton.setEnabled(false);

        }
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
        TextView venueNameTextView = (TextView) findViewById (R.id.venueName);
        venueNameTextView.setText(response.getName());
        venueName = response.getName();
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
        checkInVenue(email,venueId,venueName);
    }

    public void leavetip(View view)
    {
        Intent intent1 = new Intent(VenueDetails.this, LeaveTip.class);
        intent1.putExtra("venueId",venueId);
        startActivity(intent1);//Edited here
    }


    private void checkInVenue(String email, String venueId, String venueName) {

        mSubscriptions.add(NetworkUtil.generic().checkin(email,venueId,venueName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleCheckin,this::handleError));
    }

    private void handleCheckin(Response response) {
        Button checkinButton = (Button)findViewById(R.id.checkin);
        checkinButton.setText("Already Visited");
        checkinButton.setEnabled(false);
    }


    private void requestComments(String venueId, String email) {

        mSubscriptions.add(NetworkUtil.generic().getComments(venueId, email)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleCommentResponse,this::handleError));
    }


    private void handleCommentResponse(List<Comment> response) {
        commentView = (RecyclerView)findViewById(R.id.commentList);
        mAdapter = new CommentAdapter(VenueDetails.this, response);
        commentView.setAdapter(mAdapter);
        commentView.setLayoutManager(new LinearLayoutManager(VenueDetails.this));
        System.out.println(response);

    }

}
