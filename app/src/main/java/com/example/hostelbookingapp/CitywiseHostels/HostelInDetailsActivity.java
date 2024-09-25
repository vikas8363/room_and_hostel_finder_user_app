package com.example.hostelbookingapp.CitywiseHostels;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.example.hostelbookingapp.HomeActivity;
import com.example.hostelbookingapp.LoginActivity;
import com.example.hostelbookingapp.PermissionActivity;
import com.example.hostelbookingapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HostelInDetailsActivity extends AppCompatActivity {

    ImageView img_hostel_profilel;
    TextView tv_property_type,tv_hostel_name, tv_hostel_mobile_no, tv_hostel_email_id, tv_hostel_address,
            tv_hostel_city_state_pincode, tv_hostel_category, tv_hostel_rent, tv_hostel_capacity,
            tv_hostel_number_of_room, tv_hostel_mess_available, tv_hostel_parking, tv_hostel_special_rules,
            tv_hostel_rating;

    Button btn_view_hostel_in_map, btn_book_hostel;
    ProgressBar progress;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    String property_type,hostel_image, hostel_name, hostel_mobile_no, hostel_email_id, hostel_address, hostel_state, hostel_city,
            hostel_pincode, hostel_latitude, hostel_longitude, hostel_category, hostel_rent, hostel_capacity,
            hostel_number_of_room, hostel_mess_available, hostel_parking, hostel_special_rules, hostel_rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostel_in_details);

        preferences = PreferenceManager.getDefaultSharedPreferences(HostelInDetailsActivity.this);
        editor = preferences.edit();

        img_hostel_profilel = findViewById(R.id.img_view_in_details_profile);
        tv_property_type = findViewById(R.id.tv_view_hostel_in_details_type);
        tv_hostel_name = findViewById(R.id.tv_view_hostel_in_details_name);
        tv_hostel_mobile_no = findViewById(R.id.tv_view_hostel_in_details_mobile_no);
        tv_hostel_email_id = findViewById(R.id.tv_view_hostel_in_details_email_id);
        tv_hostel_address = findViewById(R.id.tv_view_hostel_in_details_address);
        tv_hostel_city_state_pincode = findViewById(R.id.tv_view_hostel_in_details_city_state_pincode);
        tv_hostel_category = findViewById(R.id.tv_view_hostel_in_details_category);
        tv_hostel_rent = findViewById(R.id.tv_view_hostel_in_details_rent);
        tv_hostel_capacity = findViewById(R.id.tv_view_hostel_in_details_capacity);
        tv_hostel_number_of_room = findViewById(R.id.tv_view_hostel_in_details_number_of_room);
        tv_hostel_mess_available = findViewById(R.id.tv_view_hostel_in_details_mess_available);
        tv_hostel_parking = findViewById(R.id.tv_view_hostel_in_details_parking);
        tv_hostel_special_rules = findViewById(R.id.tv_view_hostel_in_details_special_rule);
        tv_hostel_rating = findViewById(R.id.tv_view_hostel_in_details_rating);
        btn_view_hostel_in_map = findViewById(R.id.btn_view_hostel_in_details_in_google_map);
        btn_book_hostel = findViewById(R.id.btn_view_hostel_in_details_book_hostel);
        progress = findViewById(R.id.progress);

        hostel_image = preferences.getString("hostel_image", "");
        property_type = preferences.getString("type", "");
        hostel_name = preferences.getString("hostel_name", "");
        hostel_mobile_no = preferences.getString("hostel_mobile_no", "");
        hostel_email_id = preferences.getString("hostel_email_id", "");
        hostel_address = preferences.getString("hostel_address", "");
        hostel_city = preferences.getString("hostel_city", "");
        hostel_state = preferences.getString("hostel_state", "");
        hostel_pincode = preferences.getString("hostel_pincode", "");
        hostel_latitude = preferences.getString("hostel_latitude", "");
        hostel_longitude = preferences.getString("hostel_longitude", "");
        hostel_category = preferences.getString("hostel_category", "");
        hostel_rent = preferences.getString("hostel_rent", "");
        hostel_capacity = preferences.getString("hostel_capacity", "");
        hostel_number_of_room = preferences.getString("hostel_number_of_room", "");
        hostel_mess_available = preferences.getString("hostel_mess_available", "");
        hostel_parking = preferences.getString("hostel_parking", "");
        hostel_special_rules = preferences.getString("hostel_special_rules", "");
        hostel_rating = preferences.getString("hostel_rating", "");

        Toast.makeText(this, ""+hostel_category, Toast.LENGTH_SHORT).show();

        Picasso.with(HostelInDetailsActivity.this).load(Urls.OnlineImageAddress + "" + hostel_image).placeholder(R.drawable.profileimage)
                .error(R.drawable.image_not_load).into(img_hostel_profilel);

        setTitle("" + hostel_name);

        tv_property_type.setText(property_type);
        tv_hostel_name.setText(hostel_name);
        tv_hostel_mobile_no.setText(hostel_mobile_no);
        tv_hostel_email_id.setText(hostel_email_id);
        tv_hostel_address.setText(hostel_address);
        tv_hostel_city_state_pincode.setText(hostel_city + "," + hostel_state + "," + hostel_pincode);
        tv_hostel_category.setText(hostel_category);
        tv_hostel_rent.setText("\u20B9" + hostel_rent);
        tv_hostel_capacity.setText(hostel_capacity + " Students");
        tv_hostel_number_of_room.setText(hostel_number_of_room + " Rooms");
        tv_hostel_mess_available.setText(hostel_mess_available);
        tv_hostel_parking.setText(hostel_parking);
        tv_hostel_special_rules.setText(hostel_special_rules);
        tv_hostel_rating.setText(hostel_rating + " Star");

        btn_view_hostel_in_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setVisibility(View.VISIBLE);
                Intent i = new Intent(HostelInDetailsActivity.this, PermissionActivity.class);
                editor.putString("hostel_name", hostel_name).commit();
                editor.putString("hostel_address", hostel_address).commit();
                editor.putString("hostel_latitude", hostel_latitude).commit();
                editor.putString("hostel_longitude", hostel_longitude).commit();
                startActivity(i);
                finish();
            }
        });

        btn_book_hostel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setVisibility(View.VISIBLE);
                addBookingforHostel();
            }
        });

    }

    private void addBookingforHostel() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.urlBookMyHostel,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);
                            String success = obj.getString("success");

                            if (success.equals("1")) {
                                Toast.makeText(HostelInDetailsActivity.this, response, Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(HostelInDetailsActivity.this, HomeActivity.class);
                                startActivity(i);
                                finish();
                                Toast.makeText(HostelInDetailsActivity.this, "Booking Successfully Done", Toast.LENGTH_SHORT).show();
                                progress.setVisibility(View.GONE);
                            } else {
                                String message = obj.getString("message");
                                Toast.makeText(HostelInDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
                                Toast.makeText(HostelInDetailsActivity.this, response, Toast.LENGTH_SHORT).show();
                                progress.setVisibility(View.GONE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HostelInDetailsActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        progress.setVisibility(View.GONE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", String.valueOf(preferences.getInt("user_id", 0)));
                params.put("hostel_id", preferences.getString("id", ""));
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        RequestQueue requestQueue = Volley.newRequestQueue(HostelInDetailsActivity.this);
        requestQueue.add(stringRequest);


    }
}
