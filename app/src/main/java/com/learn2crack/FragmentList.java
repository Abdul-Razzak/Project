package com.learn2crack;

/**
 * Created by Abu on 8/14/2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learn2crack.model.Venue;
import com.learn2crack.network.NetworkUtil;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class FragmentList extends Fragment {
    String query = "breakfast";
    double lat = 0.0;
    double lng = 0.0;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    RecyclerView venueView;
    AdapterVenue mAdapter;
    private CompositeSubscription mSubscriptions;


    public static FragmentList newInstance() {
        FragmentList fragment = new FragmentList();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lat = getArguments().getDouble("lat");
        lng = getArguments().getDouble("lng");
        query = "breakfast";
      //  requestVenues(query, lat, lng);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        venueView = (RecyclerView) view.findViewById(R.id.venueList);
        return view;
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
        mAdapter = new AdapterVenue(this.getActivity(), response);
        venueView.setAdapter(mAdapter);
        venueView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }

}