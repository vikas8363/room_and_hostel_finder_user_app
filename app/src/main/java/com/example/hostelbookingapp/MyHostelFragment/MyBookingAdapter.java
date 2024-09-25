package com.example.hostelbookingapp.MyHostelFragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostelbookingapp.Comman.Urls;
import com.example.hostelbookingapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyBookingAdapter extends RecyclerView.Adapter<MyBookingAdapter.ViewHolder> {
    Activity activity;
    ArrayList<String> idArrayList,hostelImageArrayList,hostelNameArrayList, hostelMobileNoArrayList,
            hostelEmailIdArrayList, hostelAddressArrayList,hostelCityArrayList,hostelStateList,hostelPincodeList,
            hostelLatitudeList,hostelLongitudeArrayList,hostelCategoryArrayList,hostelRentArrayList,hostelCapacityArrayList,
            hostelNumberofRoomArrayList,hostelMessIsAvailableArrayList,hostelParkingArrayList,hostelSpecialRulesArrayList,
            hostelratingArrayList,properytypeArrayList;

    LayoutInflater inflater;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public MyBookingAdapter(Activity activity, ArrayList<String> idArrayList, ArrayList<String> hostelImageArrayList,
                            ArrayList<String> hostelNameArrayList, ArrayList<String> hostelMobileNoArrayList,
                            ArrayList<String> hostelEmailIdArrayList, ArrayList<String> hostelAddressArrayList,
                            ArrayList<String> hostelCityArrayList, ArrayList<String> hostelStateList,
                            ArrayList<String> hostelPincodeList, ArrayList<String> hostelLatitudeList,
                            ArrayList<String> hostelLongitudeArrayList, ArrayList<String> hostelCategoryArrayList,
                            ArrayList<String> hostelRentArrayList, ArrayList<String> hostelCapacityArrayList,
                            ArrayList<String> hostelNumberofRoomArrayList, ArrayList<String> hostelMessIsAvailableArrayList,
                            ArrayList<String> hostelParkingArrayList, ArrayList<String> hostelSpecialRulesArrayList,
                            ArrayList<String> hostelratingArrayList,ArrayList<String> properytypeArrayList) {
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        this.idArrayList = idArrayList;
        this.hostelImageArrayList = hostelImageArrayList;
        this.hostelNameArrayList = hostelNameArrayList;
        this.hostelMobileNoArrayList = hostelMobileNoArrayList;
        this.hostelEmailIdArrayList = hostelEmailIdArrayList;
        this.hostelAddressArrayList = hostelAddressArrayList;
        this.hostelCityArrayList = hostelCityArrayList;
        this.hostelStateList = hostelStateList;
        this.hostelPincodeList = hostelPincodeList;
        this.hostelLatitudeList = hostelLatitudeList;
        this.hostelLongitudeArrayList = hostelLongitudeArrayList;
        this.hostelCategoryArrayList = hostelCategoryArrayList;
        this.hostelRentArrayList = hostelRentArrayList;
        this.hostelCapacityArrayList = hostelCapacityArrayList;
        this.hostelNumberofRoomArrayList = hostelNumberofRoomArrayList;
        this.hostelMessIsAvailableArrayList = hostelMessIsAvailableArrayList;
        this.hostelParkingArrayList = hostelParkingArrayList;
        this.hostelSpecialRulesArrayList = hostelSpecialRulesArrayList;
        this.hostelratingArrayList = hostelratingArrayList;
        this.properytypeArrayList = properytypeArrayList;

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_my_booking, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.tv_hostel_name.setText(hostelNameArrayList.get(position));
        holder.tv_hostel_address.setText((hostelAddressArrayList.get(position)));
        holder.tv_hostel_mobile_no.setText(hostelMobileNoArrayList.get(position));
        holder.tv_hostel_category.setText(hostelCategoryArrayList.get(position));
        holder.tv_hostel_city_state_pincode.setText(hostelCityArrayList.get(position)+","+hostelStateList.get(position)+","+hostelPincodeList.get(position));

        Picasso.with(activity).load(Urls.OnlineImageAddress+""+hostelImageArrayList.get(position)).placeholder(R.drawable.profileimage)
                .error(R.drawable.image_not_load).into(holder.img_hostel_image);

        holder.btn_view_my_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ViewMyBookingDetailsActivity.class);
                editor.putString("id", idArrayList.get(position)).commit();
                editor.putString("hostel_image",hostelImageArrayList.get(position)).commit();
                editor.putString("hostel_name", hostelNameArrayList.get(position)).commit();
                editor.putString("hostel_mobile_no", hostelMobileNoArrayList.get(position)).commit();
                editor.putString("hostel_email_id", hostelEmailIdArrayList.get(position)).commit();
                editor.putString("hostel_address", hostelAddressArrayList.get(position)).commit();
                editor.putString("hostel_state", hostelStateList.get(position)).commit();
                editor.putString("hostel_city", hostelCityArrayList.get(position)).commit();
                editor.putString("hostel_pincode", hostelPincodeList.get(position)).commit();
                editor.putString("hostel_latitude", hostelLatitudeList.get(position)).commit();
                editor.putString("hostel_longitude", hostelLongitudeArrayList.get(position)).commit();
                editor.putString("hostel_category", hostelCategoryArrayList.get(position)).commit();
                editor.putString("hostel_rent", hostelRentArrayList.get(position)).commit();
                editor.putString("hostel_capacity", hostelCapacityArrayList.get(position)).commit();
                editor.putString("hostel_number_of_room", hostelNumberofRoomArrayList.get(position)).commit();
                editor.putString("hostel_mess_available", hostelMessIsAvailableArrayList.get(position)).commit();
                editor.putString("hostel_parking", hostelParkingArrayList.get(position)).commit();
                editor.putString("hostel_special_rules", hostelSpecialRulesArrayList.get(position)).commit();
                editor.putString("hostel_rating", hostelratingArrayList.get(position)).commit();
                editor.putString("type", properytypeArrayList.get(position)).commit();
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return idArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        
        ImageView img_hostel_image;
        TextView tv_hostel_name, tv_hostel_mobile_no, tv_hostel_address, tv_hostel_city_state_pincode,
                tv_hostel_category;
        Button btn_view_my_booking;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_hostel_image = itemView.findViewById(R.id.rv_img_my_booking_hostel_profile);
            tv_hostel_name = itemView.findViewById(R.id.rv_tv_my_booking_hostel_name);
            tv_hostel_address = itemView.findViewById(R.id.rv_tv_my_booking_hostel_address);
            tv_hostel_mobile_no = itemView.findViewById(R.id.rv_tv_my_booking_hostel_mobile);
            tv_hostel_category = itemView.findViewById(R.id.rv_tv_my_booking_hostel_category);
            tv_hostel_city_state_pincode = itemView.findViewById(R.id.rv_tv_my_booking_hostel_city_state_pincode);
            btn_view_my_booking = itemView.findViewById(R.id.rv_btn_my_booking_view_my_booking);
        }
    }
}