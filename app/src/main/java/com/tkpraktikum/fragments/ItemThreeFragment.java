package com.tkpraktikum.fragments;

/**
 * Created by Abu on 8/14/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.tkpraktikum.BaseApplication;
import com.tkpraktikum.R;
import com.tkpraktikum.activity.MapDemoActivity;
import com.tkpraktikum.adapter.HistoryAdapter;
import com.tkpraktikum.model.Checkin;
import com.tkpraktikum.network.NetworkUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class ItemThreeFragment extends Fragment {

    String email;
    private CompositeSubscription mSubscriptions;
    HistoryAdapter historyAdapter;
    RecyclerView recyclerView;
    List<Checkin> checkinsList = Collections.emptyList();


    public static ItemThreeFragment newInstance() {
        ItemThreeFragment fragment = new ItemThreeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        email = ((BaseApplication) getActivity().getApplication()).getEmail();
        mSubscriptions = new CompositeSubscription();
        getUserHistory(email);

    }

    private void getUserHistory(String email) {
        mSubscriptions.add(NetworkUtil.generic().getUserCheckinInfo(email)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleCheckInInfoResponse,this::handleError));

    }

    private void handleCheckInInfoResponse(List<Checkin> checkins) {
        System.out.println(checkins);
        checkinsList = checkins;
        historyAdapter = new HistoryAdapter(this.getActivity(), checkinsList);
        recyclerView.setAdapter(historyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


    }

    private void handleError(Throwable throwable) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_three, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.historyList1);
       /* Checkin checkin = new Checkin();
        checkin.setVenue_name("bitsches");
        List<Checkin> checkins = new ArrayList<>();
        checkins.add(checkin);*/

        return rootView;
    }


}
