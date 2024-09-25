package com.example.hostelbookingapp.CitywiseHostels;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hostelbookingapp.Comman.Urls;
import com.example.hostelbookingapp.LoginActivity;
import com.example.hostelbookingapp.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;

public class CitywiseHostelsFragment extends Fragment {

    List<POJOCitywiseHostels> list;
    CitywiseHostelsAdapter adapter;
    SearchView searchView_hostel;
    TextView tv_no_record;
    ListView lv_hostels;
    ProgressBar progress;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    Spinner spinner_city_list;
    ArrayList<String> arrayIdList, arrayCityList;

    int checkstate = 0;
    String user_city;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_citywise, container, false);

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = preferences.edit();
        user_city = preferences.getString("city","");
        Toast.makeText(getActivity(), "user Id is "+preferences.getInt("user_id",0), Toast.LENGTH_SHORT).show();

        Toast.makeText(getActivity(), "" + preferences.getString("username", ""), Toast.LENGTH_SHORT).show();

        list = new ArrayList<>();
        spinner_city_list = view.findViewById(R.id.spinner_select_city);
        searchView_hostel = view.findViewById(R.id.searchview_hostel_by_name_or_cateogry_or_city);
        tv_no_record = view.findViewById(R.id.tv_no_record);
        lv_hostels = view.findViewById(R.id.lv_hostels);
        progress = view.findViewById(R.id.progress);

        arrayIdList = new ArrayList<>();
        arrayCityList = new ArrayList<>();
        getCity();

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

        getHostel(user_city);

        return view;
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

        adapter = new CitywiseHostelsAdapter(tempcenterlist, getActivity(), tv_no_record);
        lv_hostels.setAdapter(adapter);
    }

    private void getCity() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.urlGetCity,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray = obj.getJSONArray("getCity");
                            arrayCityList.clear();
                            arrayIdList.add("-1");
                            arrayCityList.add("Select Your City");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < jsonArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject getlistObject = jsonArray.getJSONObject(i);

                                String city = getlistObject.getString("city");
                                arrayCityList.add(city);


                            }
                            @SuppressLint("UseRequireInsteadOfGet") ArrayAdapter<String> adapter = new ArrayAdapter<String>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_item, arrayCityList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner_city_list.setAdapter(adapter);
                            spinner_city_list.setVisibility(View.VISIBLE);

                            spinner_city_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (checkstate++ > 0) {
                                        try {
                                            list.clear();
                                            getHostel(arrayCityList.get(position));
                                            Toast.makeText(getActivity(), ""+arrayCityList.get(position), Toast.LENGTH_SHORT).show();
                                        } catch (Exception e) {
                                            Toast.makeText(getActivity(), "" + e.toString(), Toast.LENGTH_SHORT).show();
                                            Log.i("Error in State Spinner", e.toString());
                                        }
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }

                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Shop Service Exception" + e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void getHostel(String user_city) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("city", user_city);
        client.post(Urls.urlGetCitywiseHostels, params, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                progress.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                progress.setVisibility(View.GONE);
                try {
                    JSONArray jsonArray = response.getJSONArray("getCitywiseHostels");
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
                    adapter = new CitywiseHostelsAdapter(list, getActivity(), tv_no_record);
                    lv_hostels.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getActivity(), "Could Not Connect", Toast.LENGTH_SHORT).show();
            }
        });
    }

}