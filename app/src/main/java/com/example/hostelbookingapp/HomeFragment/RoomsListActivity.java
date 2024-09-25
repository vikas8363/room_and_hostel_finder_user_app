package com.example.hostelbookingapp.HomeFragment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import androidx.appcompat.widget.SearchView;
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

public class RoomsListActivity extends AppCompatActivity {


    List<POJOCitywiseHostels> list;
    CitywiseHostelsAdapter adapter;
    SearchView searchView_hostel;
    TextView tv_no_record;
    ListView lv_hostels;
    ProgressBar progress;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String type;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms_list);


        preferences = PreferenceManager.getDefaultSharedPreferences(RoomsListActivity.this);
        editor = preferences.edit();

        list = new ArrayList<>();
        searchView_hostel = findViewById(R.id.searchview_hostel_by_name_or_cateogry_or_city);
        tv_no_record = findViewById(R.id.tv_no_record);
        lv_hostels = findViewById(R.id.lv_hostels);
        progress = findViewById(R.id.progress);

        searchView_hostel.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchHostels(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchHostels(newText);
                return false;
            }
        });




        getHostel(preferences.getString("city",""),preferences.getString("type",""));
        setTitle(preferences.getString("type",""));
    }

        private void searchHostels(String query) {

        List<POJOCitywiseHostels> tempcenterlist = new ArrayList();
        tempcenterlist.clear();

        for (POJOCitywiseHostels d : list) {
            if (d.getHostel_name().toUpperCase().contains(query.toUpperCase()) || d.getHostel_category().toUpperCase().contains(query
                    .toUpperCase()) || d.getHostel_city().toUpperCase().contains(query
                    .toUpperCase()))
                tempcenterlist.add(d);
        }

        adapter = new CitywiseHostelsAdapter(tempcenterlist, RoomsListActivity.this, tv_no_record);
        lv_hostels.setAdapter(adapter);
    }

    private void getHostel(String user_city, String type) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("type", type);
        params.put("city", user_city);
//
//        Toast.makeText(this, type, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "city"+user_city, Toast.LENGTH_SHORT).show();

        client.post(Urls.urlGetRoomandHostels, params, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                progress.setVisibility(View.VISIBLE);
                super.onStart();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                //Toast.makeText(RoomsListActivity.this, "onSuceess called!", Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.GONE);
                try {
                    JSONArray jsonArray = response.getJSONArray("getRoomandHostels");
                    if (jsonArray.isNull(0))
                    {
                        tv_no_record.setVisibility(View.VISIBLE);
                    }
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String hostel_image = jsonObject.getString("hostel_image");
                        String hostel_name = jsonObject.getString("hostel_name");
                        String hostel_mobile_no = jsonObject.getString("hostel_mobile_no");
                        String hostel_email_id = jsonObject.getString("hostel_email_id");
                        String hostel_address = jsonObject.getString("hostel_address");
                        String hostel_state = jsonObject.getString("hostel_state");
                        String hostel_city = jsonObject.getString("hostel_city");
                        String hostel_pincode = jsonObject.getString("hostel_pincode");
                        String hostel_latitude = jsonObject.getString("hostel_latitude");
                        String hostel_longitude = jsonObject.getString("hostel_longitude");
                        String hostel_category = jsonObject.getString("hostel_category");
                        String hostel_rent = jsonObject.getString("hostel_rent");
                        String hostel_capacity = jsonObject.getString("hostel_capacity");
                        String hostel_number_of_room = jsonObject.getString("hostel_number_of_room");
                        String hostel_mess_available = jsonObject.getString("hostel_mess_available");
                        String hostel_parking = jsonObject.getString("hostel_parking");
                        String hostel_special_rules = jsonObject.getString("hostel_special_rules");
                        String hostel_rating = jsonObject.getString("hostel_rating");
                        String type = jsonObject.getString("type");

                        list.add(new POJOCitywiseHostels(id, hostel_image, hostel_name, hostel_mobile_no, hostel_email_id, hostel_address, hostel_state, hostel_city,
                                hostel_pincode, hostel_latitude, hostel_longitude, hostel_category, hostel_rent, hostel_capacity,
                                hostel_number_of_room, hostel_mess_available, hostel_parking, hostel_special_rules, hostel_rating,type));
                    }
                    adapter = new CitywiseHostelsAdapter(list, RoomsListActivity.this, tv_no_record);
                    lv_hostels.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(RoomsListActivity.this, "Could Not Connect", Toast.LENGTH_SHORT).show();
            }
        });

    }

}