package com.tkpraktikum.fragments;

/**
 * Created by Abu on 8/14/2017.
 */

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import com.tkpraktikum.R;
import com.tkpraktikum.activity.ListActivity;
import com.tkpraktikum.activity.MapDemoActivity;
import com.tkpraktikum.activity.VenueDetails;

public class ItemOneFragment extends Fragment{

    String  searchText;
    String query;
    public static ItemOneFragment newInstance() {
        ItemOneFragment fragment = new ItemOneFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_one, container, false);
        Button searchListButton = (Button)view.findViewById(R.id.searchList);
        searchListButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                Intent intent1 = new Intent(getActivity(), ListActivity.class);
                EditText editText = (EditText)view.findViewById(R.id.search_box);
                searchText = editText.getText().toString();
                if(searchText == null || searchText.equals("")) {
                    searchText = "breakfast";
                }
                intent1.putExtra("query",searchText);
                startActivity(intent1);//Edited here
            }
        });

        Button searchMapButton = (Button)view.findViewById(R.id.searchMap);
        searchMapButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                EditText editText = (EditText)view.findViewById(R.id.search_box);
                searchText = editText.getText().toString();
                if(searchText == null || searchText.equals("")) {
                    searchText = "breakfast";
                }
                Intent intent1 = new Intent(getActivity(), MapDemoActivity.class);
                intent1.putExtra("query",searchText);
                startActivity(intent1);//Edited here
            }
        });

        RadioGroup radioCategoryGroup;
        RadioButton radioButton;
        radioCategoryGroup = (RadioGroup) view.findViewById(R.id.category);
        int selectedId = 0;
        radioButton = (RadioButton) view.findViewById(selectedId);

        radioCategoryGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
               if(i == R.id.breakfastradio) {
                   query = "breakfast";
               } else if(i == R.id.lunchradio) {
                   query = "lunch";
               } else if(i == R.id.dinnerradio) {
                   query = "dinner";
               } else if(i == R.id.coffeetea) {
                   query = "coffee";
               }
            }
        });


        Button listRadioButton = (Button)view.findViewById(R.id.listRadio);
        listRadioButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                Intent intent1 = new Intent(getActivity(), ListActivity.class);
                intent1.putExtra("query",query);
                startActivity(intent1);//Edited here
            }
        });

        Button mapRadioButton = (Button)view.findViewById(R.id.MapRadio);
        mapRadioButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                Intent intent1 = new Intent(getActivity(), MapDemoActivity.class);
                intent1.putExtra("query",query);
                startActivity(intent1);//Edited here
            }
        });

        return view;
    }
}
