package com.example.hostelbookingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.example.hostelbookingapp.CitywiseHostels.HostelInDetailsActivity;
import com.example.hostelbookingapp.Comman.Urls;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ViewHostelInMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private GoogleMap map;
    private final float DEFAULT_ZOOM = 16;

    private MarkerOptions hostel_marker,shop_marker;

    String address;
    Double latitude,longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_hostel_in_map);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        setTitle(""+preferences.getString("hostel_name","")+" Location");
        address = preferences.getString("hostel_address","");
        latitude = Double.valueOf(preferences.getString("hostel_latitude",""));
        longitude = Double.valueOf(preferences.getString("hostel_longitude",""));
        Toast.makeText(this, "Latitude is "+latitude+"Longitude is " +longitude, Toast.LENGTH_SHORT).show();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.hostel_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.getUiSettings().isCompassEnabled();
        map.getUiSettings().isZoomGesturesEnabled();
        map.getUiSettings().isZoomControlsEnabled();



        //Initialize Google Play Serviceso
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                map.setMyLocationEnabled(true);
            } else {
                map.setMyLocationEnabled(true);
            }

            LatLng latLng = new LatLng(latitude,longitude);
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,DEFAULT_ZOOM));
            hostel_marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title(String.valueOf("Hostel Address: "+address));
            hostel_marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.hostel_marker));
            map.addMarker(hostel_marker);
            getShopInMap();

        }
    }

    private void getShopInMap() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("city", preferences.getString("city", null));
        client.post(Urls.urlGetCitywiseHostels, params, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("getShopLocation");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String address = jsonObject.getString("address");
                                Double latitude = Double.valueOf(jsonObject.getString("latitude"));
                                Double longitude = Double.valueOf(jsonObject.getString("longitude"));
                                Toast.makeText(ViewHostelInMapActivity.this, ""+latitude, Toast.LENGTH_SHORT).show();


                                LatLng latLng = new LatLng(latitude,longitude);
                                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,DEFAULT_ZOOM));
                                shop_marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title(String.valueOf("Shop Address: "+address));
                                shop_marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.hostel_marker));
                                map.addMarker(shop_marker);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                    }
                }
        );
    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(ViewHostelInMapActivity.this, HostelInDetailsActivity.class));
        finish();
    }
}