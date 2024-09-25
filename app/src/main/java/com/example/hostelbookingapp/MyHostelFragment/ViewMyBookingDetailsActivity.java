package com.example.hostelbookingapp.MyHostelFragment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hostelbookingapp.CitywiseHostels.HostelInDetailsActivity;
import com.example.hostelbookingapp.Comman.Urls;
import com.example.hostelbookingapp.HomeActivity;
import com.example.hostelbookingapp.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ViewMyBookingDetailsActivity extends AppCompatActivity {

    ImageView img_hostel_profilel;
    TextView tv_property_type,tv_hostel_name, tv_hostel_mobile_no, tv_hostel_email_id, tv_hostel_address,
            tv_hostel_city_state_pincode, tv_hostel_category, tv_hostel_rent, tv_hostel_capacity,
            tv_hostel_number_of_room, tv_hostel_mess_available, tv_hostel_parking, tv_hostel_special_rules,
            tv_hostel_rating;

    Button btn_cancel_booking;
    ProgressBar progress;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    String id,property_type,hostel_image, hostel_name, hostel_mobile_no, hostel_email_id, hostel_address, hostel_state, hostel_city,
            hostel_pincode, hostel_latitude, hostel_longitude, hostel_category, hostel_rent, hostel_capacity,
            hostel_number_of_room, hostel_mess_available, hostel_parking, hostel_special_rules, hostel_rating;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_booking_details);


        preferences = PreferenceManager.getDefaultSharedPreferences(ViewMyBookingDetailsActivity.this);
        editor = preferences.edit();

        img_hostel_profilel = findViewById(R.id.img_view_booking_in_details_profile);
        tv_property_type = findViewById(R.id.tv_view_booking_in_details_type);
        tv_hostel_name = findViewById(R.id.tv_view_booking_in_details_name);
        tv_hostel_mobile_no = findViewById(R.id.tv_view_booking_in_details_mobile_no);
        tv_hostel_email_id = findViewById(R.id.tv_view_booking_in_details_email_id);
        tv_hostel_address = findViewById(R.id.tv_view_booking_in_details_address);
        tv_hostel_city_state_pincode = findViewById(R.id.tv_view_booking_in_details_cite_state_pincode);
        tv_hostel_category = findViewById(R.id.tv_view_booking_in_details_category);
        tv_hostel_rent = findViewById(R.id.tv_view_booking_in_details_shop_rent);
        tv_hostel_capacity = findViewById(R.id.tv_view_booking_in_details_capacity);
        tv_hostel_number_of_room = findViewById(R.id.tv_view_booking_in_details_number_of_room);
        tv_hostel_mess_available = findViewById(R.id.tv_view_booking_in_details_mess_available);
        tv_hostel_parking = findViewById(R.id.tv_view_booking_in_details_parking);
        tv_hostel_special_rules = findViewById(R.id.tv_view_booking_in_details_special_rules);
        tv_hostel_rating = findViewById(R.id.tv_view_booking_in_details_rating);
        btn_cancel_booking = findViewById(R.id.btn_view_booking_in_details_cancel_booking);
        progress = findViewById(R.id.progress);

        id = preferences.getString("id", "");
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

        Picasso.with(ViewMyBookingDetailsActivity.this).load(Urls.OnlineImageAddress + "" + hostel_image).placeholder(R.drawable.profileimage)
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

        btn_cancel_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setVisibility(View.VISIBLE);
                AlertDialog.Builder ad = new AlertDialog.Builder(ViewMyBookingDetailsActivity.this);
                ad.setTitle("")
                        .setMessage("Are You Sure You Want To Delete")
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        progress.setVisibility(View.VISIBLE);
                                        deleteBooking(id);
                                    }
                                },2000);

                            }
                        });

                AlertDialog alertDialog = ad.create();
                alertDialog.show();
                alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.darker_gray);

            }
        });
    }

    private void deleteBooking(String id) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("id", id);
        client.post(Urls.urlDeleteBooking, params, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String aa = response.getString("success");

                    if (aa.equals("1")) {
                        Intent intent = new Intent(ViewMyBookingDetailsActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(ViewMyBookingDetailsActivity.this, "Unable to delete Booking of Hostel", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                // progress.setVisibility(View.GONE);
                 Toast.makeText(ViewMyBookingDetailsActivity.this, "Could Not Connect", Toast.LENGTH_SHORT).show();
            }
        });

    }
}