package com.example.hostelbookingapp.MyHostelFragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.provider.Telephony;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hostelbookingapp.Comman.Urls;
import com.example.hostelbookingapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyHostelFragment extends Fragment {

    ArrayList<String> idArrayList,hostelImageArrayList,hostelNameArrayList, hostelMobileNoArrayList,
            hostelEmailIdArrayList, hostelAddressArrayList,hostelCityArrayList,hostelStateList,hostelPincodeList,
            hostelLatitudeList,hostelLongitudeArrayList,hostelCategoryArrayList,hostelRentArrayList,hostelCapacityArrayList,
            hostelNumberofRoomArrayList,hostelMessIsAvailableArrayList,hostelParkingArrayList,hostelSpecialRulesArrayList,
            hostelratingArrayList,properytypeArrayList;
    TextView tv_no_booking;
    RecyclerView rv_my_hostel_booking_list;
    ProgressDialog progressDialog;
    MyBookingAdapter myBookingAdapter;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_hostel, container, false);

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = preferences.edit();
        tv_no_booking = view.findViewById(R.id.tv_no_booking);
        rv_my_hostel_booking_list = view.findViewById(R.id.rv_my_hostel_booking_list);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        idArrayList = new ArrayList<>();
        hostelImageArrayList = new ArrayList<>();
        hostelNameArrayList = new ArrayList<>();
        hostelMobileNoArrayList = new ArrayList<>();
        hostelEmailIdArrayList = new ArrayList<>();
        hostelAddressArrayList = new ArrayList<>();
        hostelCityArrayList = new ArrayList<>();
        hostelStateList = new ArrayList<>();
        hostelPincodeList = new ArrayList<>();
        hostelLatitudeList = new ArrayList<>();
        hostelLongitudeArrayList = new ArrayList<>();
        hostelCategoryArrayList = new ArrayList<>();
        hostelRentArrayList = new ArrayList<>();
        hostelCapacityArrayList = new ArrayList<>();
        hostelNumberofRoomArrayList = new ArrayList<>();
        hostelMessIsAvailableArrayList = new ArrayList<>();
        hostelParkingArrayList = new ArrayList<>();
        hostelSpecialRulesArrayList = new ArrayList<>();
        hostelratingArrayList = new ArrayList<>();
        properytypeArrayList = new ArrayList<>();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Your Booking Loading");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();
        getMyShop();
    }

    private void getMyShop() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.urlGetMyBooking,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDialog.dismiss();
                            JSONObject jsonObjectSuccess = new JSONObject(response);
                            JSONArray jsonArray = jsonObjectSuccess.getJSONArray("getMyBooking");

                            if (jsonArray.isNull(0))
                            {
                             tv_no_booking.setVisibility(View.VISIBLE);
                            }

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObjectProducts = jsonArray.getJSONObject(i);
                                String id = jsonObjectProducts.getString("id");
                                idArrayList.add(id);

                                String hostel_image = jsonObjectProducts.getString("hostel_image");
                                hostelImageArrayList.add(hostel_image);

                                String hostel_name = jsonObjectProducts.getString("hostel_name");
                                hostelNameArrayList.add(hostel_name);

                                String hostel_mobile_no = jsonObjectProducts.getString("hostel_mobile_no");
                                hostelMobileNoArrayList.add(hostel_mobile_no);

                                String hostel_email_id = jsonObjectProducts.getString("hostel_email_id");
                                hostelEmailIdArrayList.add(hostel_email_id);

                                String hostel_address = jsonObjectProducts.getString("hostel_address");
                                hostelAddressArrayList.add(hostel_address);

                                String hostel_state = jsonObjectProducts.getString("hostel_state");
                                hostelStateList.add(hostel_state);

                                String hostel_city = jsonObjectProducts.getString("hostel_city");
                                hostelCityArrayList.add(hostel_city);

                                String hostel_pincode = jsonObjectProducts.getString("hostel_pincode");
                                hostelPincodeList.add(hostel_pincode);

                                String hostel_latitude = jsonObjectProducts.getString("hostel_latitude");
                                hostelLongitudeArrayList.add(hostel_latitude);

                                String hostel_longitude = jsonObjectProducts.getString("hostel_longitude");
                                hostelLatitudeList.add(hostel_longitude);

                                String hostel_category = jsonObjectProducts.getString("hostel_category");
                                hostelCategoryArrayList.add(hostel_category);

                                String hostel_rent = jsonObjectProducts.getString("hostel_rent");
                                hostelRentArrayList.add(hostel_rent);

                                String hostel_capacity = jsonObjectProducts.getString("hostel_capacity");
                                hostelCapacityArrayList.add(hostel_capacity);

                                String hostel_number_of_room = jsonObjectProducts.getString("hostel_number_of_room");
                                hostelNumberofRoomArrayList.add(hostel_number_of_room);

                                String hostel_mess_available = jsonObjectProducts.getString("hostel_mess_available");
                                hostelMessIsAvailableArrayList.add(hostel_mess_available);

                                String hostel_parking = jsonObjectProducts.getString("hostel_parking");
                                hostelParkingArrayList.add(hostel_parking);

                                String hostel_special_rules = jsonObjectProducts.getString("hostel_special_rules");
                                hostelSpecialRulesArrayList.add(hostel_special_rules);

                                String hostel_rating = jsonObjectProducts.getString("hostel_rating");
                                hostelratingArrayList.add(hostel_rating);

                                String type = jsonObjectProducts.getString("type");
                                properytypeArrayList.add(type);

                                rv_my_hostel_booking_list.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                                myBookingAdapter = new MyBookingAdapter(getActivity(), idArrayList,hostelImageArrayList,hostelNameArrayList, hostelMobileNoArrayList,
                                        hostelEmailIdArrayList, hostelAddressArrayList,hostelCityArrayList,hostelStateList,hostelPincodeList,
                                        hostelLatitudeList,hostelLongitudeArrayList,hostelCategoryArrayList,hostelRentArrayList,hostelCapacityArrayList,
                                        hostelNumberofRoomArrayList,hostelMessIsAvailableArrayList,hostelParkingArrayList,hostelSpecialRulesArrayList,
                                        hostelratingArrayList,properytypeArrayList);
                                rv_my_hostel_booking_list.setAdapter(myBookingAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(preferences.getInt("user_id", 0)));
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 2000, 1, 1.0f));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}