package com.example.tempapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class User_View_Event extends AppCompatActivity {
    GridView gridView1;
    String[] event_package = {"Birthday", "Wedding", "Graduation", "BabyShower"};
    int[] event_img = {R.drawable.event_birthday, R.drawable.event_wedding, R.drawable.event_graduation, R.drawable.event_baby_shower};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_event);
        gridView1= findViewById(R.id.gv_event);

        GridAdapter gridAdapter1= new GridAdapter();
        gridView1.setAdapter(gridAdapter1);
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),User_View_Package.class);
                intent.putExtra("name",event_package[i]);
                intent.putExtra("event",event_img[i]);
                String str_user_package=event_package[i];
                SharedPreferences sp_cater_registeration=getSharedPreferences("MYPREF_USER_PACKAGE",MODE_PRIVATE);
                SharedPreferences.Editor editor=sp_cater_registeration.edit();
                editor.putString("User_Package_Name",str_user_package);
                editor.commit();
                startActivity(intent);

            }
        });
    }

    private class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return event_img.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1= getLayoutInflater().inflate(R.layout.raw_event_grid,null);
            TextView name= view1.findViewById(R.id.event_title);
            ImageView event=view1.findViewById(R.id.event_icon);

            name.setText(event_package[i]);
            event.setImageResource(event_img[i]);
            return view1;
        }
    }


}