package com.tkpraktikum.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tkpraktikum.BaseApplication;
import com.tkpraktikum.R;
import com.tkpraktikum.model.Response;
import com.tkpraktikum.network.NetworkUtil;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class LeaveTip extends AppCompatActivity {
    private CompositeSubscription mSubscriptions;
    String venueId;
    String email;
    String tip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubscriptions = new CompositeSubscription();
        Intent tipsIntent = getIntent();
        venueId = tipsIntent.getStringExtra("venueId");
        email = ((BaseApplication) getApplication()).getEmail();
        setContentView(R.layout.activity_leave_tip);
    }

    public void addTip(View view)
    {
        EditText editText = (EditText)findViewById(R.id.edtInput);
        tip = editText.getText().toString();
        addTipToVenue(email, venueId, tip);
    }

    private void addTipToVenue(String email, String venueId, String tip) {

        mSubscriptions.add(NetworkUtil.generic().addComment(email,venueId, tip)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::addTipResponse,this::handleError));
    }

    private void addTipResponse(Response response) {
        Button addTipButton = (Button)findViewById(R.id.addTip);
        addTipButton.setEnabled(false);
    }

    private void handleError(Throwable throwable) {
    }
}
