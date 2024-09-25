package com.example.hostelbookingapp.MyProfileFragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.example.hostelbookingapp.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class MyProfileFragment extends Fragment {

    ImageView img_my_profile, img_edit;
    TextView tv_my_profile_name, tv_my_profile_city_state_country_pincode, tv_my_profile_age, tv_my_profile_gender,
            tv_my_profile_email_id, tv_my_profile_mobile_no, tv_my_profile_aadhar_card_no, tv_my_profile_marrital_status,
            tv_my_profile_highest_education, tv_my_profile_parent_mobile_no, tv_my_profile_username;

    ProgressDialog progressDialog;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = preferences.edit();
        Toast.makeText(getActivity(), ""+preferences.getString("username",""), Toast.LENGTH_SHORT).show();

        img_my_profile = view.findViewById(R.id.img_my_profile_my_profile);
        img_edit = view.findViewById(R.id.img_my_profile_edit);
        tv_my_profile_name = view.findViewById(R.id.tv_my_profile_name);
        tv_my_profile_city_state_country_pincode = view.findViewById(R.id.tv_my_profile_city_state_country_pincode);
        tv_my_profile_age = view.findViewById(R.id.tv_my_profile_age);
        tv_my_profile_gender = view.findViewById(R.id.tv_my_profile_gender);
        tv_my_profile_email_id = view.findViewById(R.id.tv_my_profile_email_id);
        tv_my_profile_mobile_no = view.findViewById(R.id.tv_my_profile_mobile);
        tv_my_profile_aadhar_card_no = view.findViewById(R.id.tv_my_profile_aadhar_card_no);
        tv_my_profile_marrital_status = view.findViewById(R.id.tv_my_profile_marrital_status);
        tv_my_profile_highest_education = view.findViewById(R.id.tv_my_profile_highest_education);

        tv_my_profile_parent_mobile_no = view.findViewById(R.id.tv_my_profile_parent_mobile_no);
        tv_my_profile_username = view.findViewById(R.id.tv_my_profile_username);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("User Information Loading....");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        getMyProfile();

        return view;
    }

    private void getMyProfile() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("username", preferences.getString("username", null));
        client.post(Urls.urlGetMyProfile, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        try {
                            progressDialog.dismiss();
                            JSONArray jsonArray = response.getJSONArray("getMyProfile");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String name = jsonObject.getString("name");
                                String mobile_no = jsonObject.getString("mobile_no");
                                String email_id = jsonObject.getString("email_id");
                                String age = jsonObject.getString("age");
                                String gender = jsonObject.getString("gender");
                                String aadhar_card_no = jsonObject.getString("aadhar_card_no");
                                String country = jsonObject.getString("country");
                                String state = jsonObject.getString("state");
                                String city = jsonObject.getString("city");
                                String marrital_status = jsonObject.getString("marrital_status");
                                String highest_education = jsonObject.getString("highest_education");
                                String parent_contact_no = jsonObject.getString("parent_contact_no");
                                String pincode = jsonObject.getString("pincode");
                                String username = jsonObject.getString("username");
                                String password = jsonObject.getString("password");

                                Toast.makeText(getActivity(), "Name is " + name, Toast.LENGTH_SHORT).show();

                                tv_my_profile_name.setText(name);
                                tv_my_profile_mobile_no.setText(mobile_no);
                                tv_my_profile_email_id.setText(email_id);
                                tv_my_profile_age.setText(age);
                                tv_my_profile_gender.setText(gender);
                                tv_my_profile_aadhar_card_no.setText(aadhar_card_no);
                                tv_my_profile_city_state_country_pincode.setText(city+", "+state+", "+country+" - "+pincode );
                                tv_my_profile_marrital_status.setText(marrital_status);
                                tv_my_profile_highest_education.setText(highest_education);
//                                tv_my_profile_parent_mobile_no.setText(parent_contact_no);
//                                tv_my_profile_username.setText(username);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Toast.makeText(getActivity(), "Could Not Connect", Toast.LENGTH_SHORT).show();
                    }
                }

        );

    }

//    private void getMyProfile() {
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.urlGetMyProfile,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        try {
//                            JSONObject obj = new JSONObject(response);
//                            JSONArray jsonArray = obj.getJSONArray("getMyProfile");
//
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                progressDialog.dismiss();
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                String id = jsonObject.getString("id");
//                                String name = jsonObject.getString("name");
//                                String mobile_no = jsonObject.getString("mobile_no");
//                                String email_id = jsonObject.getString("email_id");
//                                String age = jsonObject.getString("age");
//                                String gender = jsonObject.getString("gender");
//                                String aadhar_card_no = jsonObject.getString("aadhar_card_no");
//                                String country = jsonObject.getString("country");
//                                String state = jsonObject.getString("state");
//                                String city = jsonObject.getString("city");
//                                String marrital_status = jsonObject.getString("marrital_status");
//                                String highest_education = jsonObject.getString("highest_education");
//                                String parent_contact_no = jsonObject.getString("parent_contact_no");
//                                String pincode = jsonObject.getString("pincode");
//                                String username = jsonObject.getString("username");
//                                String password = jsonObject.getString("password");
//
//                                Toast.makeText(getActivity(), "Name is " + name, Toast.LENGTH_SHORT).show();
//
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
//                        progressDialog.dismiss();
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("username", preferences.getString("username", ""));
//                return params;
//            }
//        };
//
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//        requestQueue.add(stringRequest);
//
//    }
}