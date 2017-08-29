package com.tkpraktikum.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tkpraktikum.BaseApplication;
import com.tkpraktikum.R;
import com.tkpraktikum.adapter.CommentAdapter;
import com.tkpraktikum.adapter.HistoryAdapter;
import com.tkpraktikum.model.Checkin;
import com.tkpraktikum.network.NetworkUtil;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by salam on 29.08.17.
 */

public class HistoryDetails extends AppCompatActivity {
    String email;
    private CompositeSubscription mSubscriptions;
    HistoryAdapter historyAdapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubscriptions = new CompositeSubscription();
        email = ((BaseApplication) getApplication()).getEmail();
        setContentView(R.layout.activity_history);
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
        recyclerView = (RecyclerView) findViewById(R.id.historyList);
        historyAdapter = new HistoryAdapter(HistoryDetails.this, checkins);
        recyclerView.setAdapter(historyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(HistoryDetails.this));

    }

    private void handleError(Throwable throwable) {
    }

}
