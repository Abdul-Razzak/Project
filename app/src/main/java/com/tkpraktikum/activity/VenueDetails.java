package com.tkpraktikum.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tkpraktikum.R;
import com.tkpraktikum.adapter.CommentAdapter;
import com.tkpraktikum.adapter.VenueAdapter;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubscriptions = new CompositeSubscription();
        double latitude = 49.872677;
        double longitude= 8.632473;
        Intent detailsIntent = getIntent();
        venueId = detailsIntent.getStringExtra("venueId");
        requestVenue(venueId);
        setContentView(R.layout.activity_venue_details);
        requestComments(venueId,"angular@js.com");
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
        intent1.putExtra("venueId",venueId);
        startActivity(intent1);//Edited here
    }


    private void checkInVenue(String email, String venueId) {

        mSubscriptions.add(NetworkUtil.generic().checkin(email,venueId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleCheckin,this::handleError));
    }

    private void handleCheckin(Response response) {
        Button checkinButton = (Button)findViewById(R.id.checkin);
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
