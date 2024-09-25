package com.example.hostelbookingapp.HomeFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hostelbookingapp.CitywiseHostels.CitywiseHostelsAdapter;
import com.example.hostelbookingapp.CitywiseHostels.POJOCitywiseHostels;
import com.example.hostelbookingapp.Comman.Urls;
import com.example.hostelbookingapp.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class HomeFragment extends Fragment {

    CardView cardView11,cardView22,cardView33;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = preferences.edit();

        cardView11 = view.findViewById(R.id.cardview11);
        cardView22 = (CardView) view.findViewById(R.id.cardview22);
        cardView33 = (CardView) view.findViewById(R.id.cardview33);

        cardView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RoomsListActivity.class));

//                editor.putString("type","Private Hostel").commit();
//                editor.putString("city","jalna").commit();

                editor.putString("type","Room").commit();
            }
        });

        cardView22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), RoomsListActivity.class));
                editor.putString("type","Government Hostel").commit();
            }
        });

        cardView33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RoomsListActivity.class));
                editor.putString("type","Private Hostel").commit();
            }
        });

        return view;
    }
}