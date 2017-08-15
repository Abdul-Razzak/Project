package com.learn2crack;

/**
 * Created by Abu on 8/14/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.learn2crack.model.Venue;
import com.learn2crack.network.NetworkUtil;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class ItemOneFragment extends Fragment {
    private CompositeSubscription mSubscriptions;
    public static ItemOneFragment newInstance() {
        ItemOneFragment fragment = new ItemOneFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mSubscriptions = new CompositeSubscription();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_one, container, false);

        Button button = (Button)view.findViewById(R.id.breakfast);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), VenueDetails.class);
                requestVenues("breakfast", Double.valueOf(49.872677), Double.valueOf(8.632473));
                startActivity(intent1);//Edited here
            }
        });

        Button button2 = (Button)view.findViewById(R.id.lunch);
        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                Intent intent1 = new Intent(getActivity(), VenueDetails.class);
                startActivity(intent1);//Edited here
            }
        });

        Button button3 = (Button)view.findViewById(R.id.dinner);
        button3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                Intent intent1 = new Intent(getActivity(), VenueDetails.class);
                startActivity(intent1);//Edited here
            }
        });

        Button button4 = (Button)view.findViewById(R.id.nightlife);
        button4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                Intent intent1 = new Intent(getActivity(), VenueDetails.class);
                startActivity(intent1);//Edited here
            }
        });

        Button button5 = (Button)view.findViewById(R.id.coffee);
        button5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                Intent intent1 = new Intent(getActivity(), VenueDetails.class);
                startActivity(intent1);//Edited here
            }
        });

        Button button6 = (Button)view.findViewById(R.id.things);
        button6.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                Intent intent1 = new Intent(getActivity(), VenueDetails.class);
                startActivity(intent1);//Edited here
            }
        });
        return view;
    }

    private void requestVenues(String query, Double lat, Double lng) {

        mSubscriptions.add(NetworkUtil.getVenues().getVenues(query, lat, lng)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private void handleResponse(List<Venue> response) {

        System.out.println(response);

    }

    private void handleError(Throwable error) {

    }
}
