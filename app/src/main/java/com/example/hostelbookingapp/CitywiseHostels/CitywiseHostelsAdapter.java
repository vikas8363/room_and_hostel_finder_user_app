package com.example.hostelbookingapp.CitywiseHostels;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hostelbookingapp.Comman.Urls;
import com.example.hostelbookingapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CitywiseHostelsAdapter extends BaseAdapter {
    List<POJOCitywiseHostels> list;
    Activity activity;
    TextView tv_no_record;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public CitywiseHostelsAdapter(List<POJOCitywiseHostels> list, Activity activity, TextView tv_no_record) {
        this.list = list;
        this.activity = activity;
        this.tv_no_record = tv_no_record;

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        final LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.lv_home_city_hostel, null);

            holder.img_hostel_image = view.findViewById(R.id.rv_img_city_hostel_profile);
            holder.tv_hostel_name = view.findViewById(R.id.rv_tv_hostel_name);
            holder.tv_hostel_address = view.findViewById(R.id.rv_tv_hostel_address);
            holder.tv_hostel_mobile_no = view.findViewById(R.id.rv_tv_hostel_mobile);
            holder.tv_hostel_category = view.findViewById(R.id.rv_tv_hostel_category);
            holder.tv_hostel_city_state_pincode = view.findViewById(R.id.rv_tv_hostel_city_state_pincode);
            holder.btn_view_in_details = view.findViewById(R.id.rv_btn_view_hostel);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final POJOCitywiseHostels obj = list.get(position);
        holder.tv_hostel_name.setText(obj.getHostel_name());
        holder.tv_hostel_address.setText("Address: " + obj.getHostel_address());
        holder.tv_hostel_mobile_no.setText("Mobile No: "+obj.getHostel_mobile_no());
        holder.tv_hostel_category.setText("Category: "+obj.getHostel_category());
        holder.tv_hostel_city_state_pincode.setText(obj.getHostel_city()+", "+obj.getHostel_state()+", "+obj.getHostel_pincode());

        Picasso.with(activity).load(Urls.OnlineImageAddress + "" + obj.getHostel_image()).placeholder(R.drawable.profileimage)
                .error(R.drawable.image_not_load).into(holder.img_hostel_image);

        holder.btn_view_in_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(activity, HostelInDetailsActivity.class);
                editor.putString("id", obj.getId()).commit();
                editor.putString("hostel_image", obj.getHostel_image()).commit();
                editor.putString("hostel_name", obj.getHostel_name()).commit();
                editor.putString("hostel_mobile_no", obj.getHostel_mobile_no()).commit();
                editor.putString("hostel_email_id", obj.getHostel_email_id()).commit();
                editor.putString("hostel_address", obj.getHostel_address()).commit();
                editor.putString("hostel_state", obj.getHostel_state()).commit();
                editor.putString("hostel_city", obj.getHostel_city()).commit();
                editor.putString("hostel_pincode", obj.getHostel_pincode()).commit();
                editor.putString("hostel_latitude", obj.getHostel_latitude()).commit();
                editor.putString("hostel_longitude", obj.getHostel_longitude()).commit();
                editor.putString("hostel_category", obj.getHostel_category()).commit();
                editor.putString("hostel_rent", obj.getHostel_rent()).commit();
                editor.putString("hostel_capacity", obj.getHostel_capacity()).commit();
                editor.putString("hostel_number_of_room", obj.getHostel_number_of_room()).commit();
                editor.putString("hostel_mess_available", obj.getHostel_mess_available()).commit();
                editor.putString("hostel_parking", obj.getHostel_parking()).commit();
                editor.putString("hostel_special_rules", obj.getHostel_special_rules()).commit();
                editor.putString("hostel_rating", obj.getHostel_rating()).commit();
                editor.putString("type", obj.getType()).commit();
                activity.startActivity(intent);
            }
        });

        return view;
    }

    class ViewHolder {
        ImageView img_hostel_image;
        TextView tv_hostel_name, tv_hostel_mobile_no, tv_hostel_address, tv_hostel_city_state_pincode,
                tv_hostel_category;
        Button btn_view_in_details;
    }
}