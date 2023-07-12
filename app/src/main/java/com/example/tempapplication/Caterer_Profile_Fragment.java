package com.example.tempapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;


public class Caterer_Profile_Fragment extends Fragment {
    View view;
    FirebaseAuth mAuth;
    TextView accounttv,contacttv,privacytv;
    Button userSignOut;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_caterer_profile,container,false);
        mAuth= FirebaseAuth.getInstance();

        accounttv=(TextView) view.findViewById(R.id.profile_account);
        contacttv=(TextView) view.findViewById(R.id.profile_contact);
        privacytv=(TextView) view.findViewById(R.id.profile_privacy_policy);

        accounttv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Caterer_Profile_Update_Delete.class);
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


        userSignOut = (Button)view.findViewById(R.id.userSignOut);
        userSignOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Confirm SignOut?");
                builder.setMessage("Are you sure you want to SignOut?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mAuth.signOut();
                                signOutUser();
                            }
                        }).setNegativeButton("Cancel", null);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        return view;
    }
    private void signOutUser()
    {
        Intent signOutFragment = new Intent(getActivity(),Welcome_Button.class);
        signOutFragment.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(signOutFragment);
        getActivity().finish();
    }
}