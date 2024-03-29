package com.example.tempapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Caterer_Manage_Fragment extends Fragment {
    private Button button,button2;
    View view;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_caterer_manage,container,false);
        button = (Button) view.findViewById(R.id.cmanage_button1);
        button2 = (Button) view.findViewById(R.id.cmanage_button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }

        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });

        return view;
    }
    public void openActivity2() {
        Intent intent = new Intent(getActivity(), Caterer_View_Package.class);
        startActivity(intent);
    }
    public void openActivity3() {
        Intent intent = new Intent(getActivity(), Caterer_View_Tiffin.class);
        startActivity(intent);
    }
}
