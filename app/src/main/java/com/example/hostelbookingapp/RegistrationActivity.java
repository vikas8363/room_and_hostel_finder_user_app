package com.example.hostelbookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RegistrationActivity extends AppCompatActivity {
    TextInputEditText tie_name,tie_mobile_no,tie_email_id,tie_age,tie_aadhar_card_no,tie_highest_education,
            tie_parent_mobile_no,tie_pincode, tie_username,tie_password;

    RadioGroup rg_gender;
    RadioButton rb_gender;
    Spinner spinner_coutry,spinner_state,spinner_city;
    RadioGroup rg_marrital_status;
    RadioButton rb_marrital_status;

    Button btn_register;
    ProgressDialog progressDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        setTitle("Register Here");
        getSupportActionBar().hide();

        tie_name = findViewById(R.id.tie_register_name);
        tie_mobile_no = findViewById(R.id.tie_register_mobile_no);
        tie_email_id = findViewById(R.id.tie_register_email_id);
        tie_age = findViewById(R.id.tie_register_age);
        tie_aadhar_card_no = findViewById(R.id.tie_register_aadhar_card_no);
        tie_highest_education = findViewById(R.id.tie_register_highest_education);
        tie_parent_mobile_no = findViewById(R.id.tie_register_parent_mobile_no);
        tie_pincode = findViewById(R.id.tie_register_pincode);
        tie_username = findViewById(R.id.tie_register_username);
        tie_password = findViewById(R.id.tie_register_password);
        spinner_coutry = findViewById(R.id.spinner_register_city);
        spinner_state = findViewById(R.id.spinner_register_state);
        spinner_city = findViewById(R.id.spinner_register_city);
        rg_gender = findViewById(R.id.rg_register_gender);
        rg_marrital_status = findViewById(R.id.rg_register_marrital);
        btn_register = findViewById(R.id.btn_register_register);
        progressDialog = new ProgressDialog(this);

        // on below line we are adding check change listener for our radio group.
        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // on below line we are getting radio button from our group.
                 rb_gender = findViewById(checkedId);

            }
        });

        // on below line we are adding check change listener for our radio group.
        rg_marrital_status.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // on below line we are getting radio button from our group.
                rb_marrital_status = findViewById(checkedId);

            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(tie_name.getText().toString()))
                {
                    tie_name.setError("Please Enter Your Name");
                }
                else if (TextUtils.isEmpty(tie_mobile_no.getText().toString()))
                {
                    tie_mobile_no.setError("Please Enter Your Mobile Number");
                }
                else if (tie_mobile_no.getText().toString().length()!=10)
                {
                    tie_mobile_no.setError("Enter 10 Digit Mobile Number");
                }
                else if (TextUtils.isEmpty(tie_email_id.getText().toString()))
                {
                    tie_email_id.setError("Please Enter Your Email Id");
                }
                else if (!tie_email_id.getText().toString().contains(".com") || !tie_email_id.getText().toString().contains("@"))
                {
                    tie_mobile_no.setError("Please Enter Valid Email Id");
                }
                else if (TextUtils.isEmpty(tie_aadhar_card_no.getText().toString()))
                {
                    tie_aadhar_card_no.setError("Please Enter Your Aadhar Card Number");
                }
                else if (TextUtils.isEmpty(tie_highest_education.getText().toString()))
                {
                    tie_highest_education.setError("Please Enter Your Highest Education");
                }
                else if (TextUtils.isEmpty(tie_parent_mobile_no.getText().toString()))
                {
                    tie_parent_mobile_no.setError("Please Enter Your Parent Mobile Number");
                }
                else if (TextUtils.isEmpty(tie_pincode.getText().toString()))
                {
                    tie_pincode.setError("Please Enter Your Pincode");
                }
                else if (TextUtils.isEmpty(tie_username.getText().toString()))
                {
                    tie_username.setError("Please Enter Your Username");
                }
                else if (TextUtils.isEmpty(tie_password.getText().toString()))
                {
                    tie_password.setError("Please Enter Your Passsword");
                }
                else if (spinner_coutry.getSelectedItem().toString().equals("Select Your Country"))
                {
                    ((TextView)spinner_coutry.getSelectedView()).setError("Please Select Your Country");
                }
                else if (spinner_state.getSelectedItem().toString().equals("Select Your State"))
                {
                    ((TextView)spinner_state.getSelectedView()).setError("Please Select Your State");
                }
                else if (spinner_city.getSelectedItem().toString().equals("Select Your City"))
                {
                    ((TextView)spinner_city.getSelectedView()).setError("Please Select Your City");
                }
                else
                {
                    progressDialog = new ProgressDialog(RegistrationActivity.this);
                    progressDialog.setTitle("Registering User");
                    progressDialog.setCancelable(false);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setMessage("Please Wait...");
                    progressDialog.show();
                    registerUser();
                }
            }
        });
    }

    private void registerUser() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.urlRegisterUser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);
                            String success = obj.getString("success");

                            if (success.equals("1"))
                            {
                                Toast.makeText(RegistrationActivity.this, response,Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                                startActivity(i);
                                finish();
                                Toast.makeText(RegistrationActivity.this,"Registration Successfully Done",Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }else {
                                String message = obj.getString("message");
                                Toast.makeText(RegistrationActivity.this,message,Toast.LENGTH_SHORT).show();
                                Toast.makeText(RegistrationActivity.this, response,Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegistrationActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("name", tie_name.getText().toString());
                params.put("mobile_no", tie_mobile_no.getText().toString());
                params.put("email_id", tie_email_id.getText().toString());
                params.put("age", tie_age.getText().toString());
                params.put("gender", rb_gender.getText().toString());
                params.put("aadhar_card_no", tie_aadhar_card_no.getText().toString());
                params.put("country", spinner_coutry.getSelectedItem().toString());
                params.put("state", spinner_state.getSelectedItem().toString());
                params.put("city", spinner_city.getSelectedItem().toString());
                params.put("marrital_status",rb_marrital_status.getText().toString());
                params.put("highest_education", tie_highest_education.getText().toString());
                params.put("parent_mobile_no", tie_parent_mobile_no.getText().toString());
                params.put("pincode", tie_pincode.getText().toString());
                params.put("username", tie_username.getText().toString());
                params.put("password", tie_password.getText().toString());
                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        RequestQueue requestQueue = Volley.newRequestQueue(RegistrationActivity.this);
        requestQueue.add(stringRequest);


    }
}