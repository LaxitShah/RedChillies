package com.example.tempapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

public class SignOutFragment extends Fragment {
    View view;
    FirebaseAuth mAuth;
    Button signOut;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view= inflater.inflate(R.layout.fragment_signout,container,false);
        mAuth = FirebaseAuth.getInstance();
//        signOut = (Button)view.findViewById(R.id.signOut);
//        signOut.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
////            mAuth.signOut();
////            signOutUser();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Confirm SignOut?");
        builder.setMessage("Are you sure you want to SignOut?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAuth.signOut();
                        signOutUser();
                    }
                }).setNegativeButton("Cancel",null);
        AlertDialog alert = builder.create();
        alert.show();

//            }
//        });

        return view;
    }
    private void signOutUser()
    {
        Intent signOutFragment = new Intent(getActivity(),Welcome_Button.class);
        signOutFragment.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(signOutFragment);
        getActivity().finish();
    }
//
//    @Override
//    public void onBackPressed()
//    {
//        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//        builder.setTitle("Confirm Exit?");
//        builder.setMessage("Are you sure you want to Exit the App?")
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                }).setNegativeButton("Cancel",null);
//        AlertDialog alert = builder.create();
//        alert.show();
//    }
}
