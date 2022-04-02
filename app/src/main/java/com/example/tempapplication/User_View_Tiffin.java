package com.example.tempapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class User_View_Tiffin extends AppCompatActivity {
    ListView listview1;
    String[] t_name = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday","Sunday"};
    int[] t_img = {R.drawable.tiffin_monday, R.drawable.tiffin_tuesday, R.drawable.tiffin_wednesday, R.drawable.tiffin_thursday, R.drawable.tiffin_friday, R.drawable.tiffin_saturday,R.drawable.tiffin_sunday};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_tiffin);

        listview1 = findViewById(R.id.lv_tif);
        ListAdapter viewAdapter= new ListAdapter();
        listview1.setAdapter(viewAdapter);
        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent= new Intent(getApplicationContext(),User_View_Menu.class);
                intent.putExtra("name",t_name[i]);
                intent.putExtra("image",t_img[i]);
                String str_user_tiffin=t_name[i];
                SharedPreferences sp_user_tiffin=getSharedPreferences("MYPREF_USER_TIFFIN",MODE_PRIVATE);
                SharedPreferences.Editor editor=sp_user_tiffin.edit();
                editor.putString("User_Tiffin_Name",str_user_tiffin);
                editor.commit();
                startActivity(intent);
            }
        });
    }

    private class ListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return t_img.length;
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
            View view2 = getLayoutInflater().inflate(R.layout.raw_tiffins_list,null);
            TextView tif_name=view2.findViewById(R.id.t_title);
            ImageView tif_image=view2.findViewById(R.id.t_icon);
            tif_name.setText(t_name[i]);
            tif_image.setImageResource(t_img[i]);
            return view2;
        }
    }
}