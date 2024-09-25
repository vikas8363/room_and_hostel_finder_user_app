package com.example.hostelbookingapp;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hostelbookingapp.CitywiseHostels.CitywiseHostelsFragment;
import com.example.hostelbookingapp.HomeFragment.HomeFragment;
import com.example.hostelbookingapp.MyHostelFragment.MyHostelFragment;
import com.example.hostelbookingapp.MyProfileFragment.MyProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        preferences = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);
        editor = preferences.edit();

        Toast.makeText(HomeActivity.this, ""+preferences.getString("username",""), Toast.LENGTH_SHORT).show();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firsttime = prefs.getBoolean("firsttime",true);

        if (firsttime)
        {
            welcome();
        }
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.menu_home_home);
    }

    private void welcome() {
        AlertDialog.Builder ad = new AlertDialog.Builder(HomeActivity.this);
        ad.setTitle("Hostel Booking App");
        ad.setMessage("Welcome to Hostel Booking App");
        ad.setPositiveButton("Thank you", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create().show();

        SharedPreferences preferences = getSharedPreferences("prefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("firsttime",false);
        editor.apply();

    }

    HomeFragment homeFragment = new HomeFragment();
    CitywiseHostelsFragment citywiseFragment = new CitywiseHostelsFragment();
    MyHostelFragment myHostelFragment = new MyHostelFragment();
    MyProfileFragment myProfileFragment = new MyProfileFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, homeFragment).commit();
                return true;

            case R.id.menu_home_citywise_hostels:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, citywiseFragment).commit();
                return true;

            case R.id.menu_home_my_hostels:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, myHostelFragment).commit();
                return true;

            case R.id.menu_home_my_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, myProfileFragment).commit();
                return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_home_all_hostels_near_me:
                Intent intent = new Intent(HomeActivity.this,AllHostelsNearMeActivity.class);
                startActivity(intent);
                return true;

            case R.id.menu_home_Logout:
                logout();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {
        logout();
    }

    private void logout() {
        AlertDialog.Builder ad = new AlertDialog.Builder(HomeActivity.this);
        ad.setTitle("Hostel Booking App");
        ad.setMessage("Are you sure you want to logout");
        ad.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        ad.setNegativeButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
                startActivity(intent);
                editor.putBoolean("isLogin",false).commit();
                finish();

            }
        }).create().show();
    }
}