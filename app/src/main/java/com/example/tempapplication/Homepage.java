package com.example.tempapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Homepage extends AppCompatActivity  {

    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        toolbar=findViewById(R.id.toolbar);
        bottomNavigationView=findViewById(R.id.bottom_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListner);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame,new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListner=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment=null;

            switch(item.getItemId())
            {
                case  R.id.home:
                    selectedFragment=new HomeFragment();
                    break;
//                case R.id.search:
//                    selectedFragment=new SearchFragment();
//                    break;
                case R.id.setting:
                    selectedFragment=new SettingFragment();
                    break;
                case R.id.logout:
                    selectedFragment=new SignOutFragment();
                    break;


            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frame,selectedFragment).commit();

            return true;
        }
    };
}