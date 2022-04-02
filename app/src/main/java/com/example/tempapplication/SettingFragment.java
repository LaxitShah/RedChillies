package com.example.tempapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingFragment  extends Fragment {
    View view;
    TextView accounttv,contacttv,privacytv;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_setting,container,false);


        accounttv=(TextView) view.findViewById(R.id.user_profile_account);
        contacttv=(TextView) view.findViewById(R.id.user_profile_contact);
        privacytv=(TextView) view.findViewById(R.id.user_profile_privacy_policy);

        accounttv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),User_Profile_Update_Delete.class);
                startActivity(intent);
            }
        });

        contacttv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),ContactUs.class);
                startActivity(intent);
            }
        });

        privacytv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),PrivacyPolicy.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
