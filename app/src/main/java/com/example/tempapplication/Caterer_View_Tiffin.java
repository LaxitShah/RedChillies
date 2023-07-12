package com.example.tempapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Caterer_View_Tiffin extends AppCompatActivity {
    ListView listView;
    ArrayList<MenuDataHolder> menuDataHolderArrayList;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caterer_view_tiffin);

        listView=findViewById(R.id.listview_caterer_view_tiffin);
        firebaseDatabase=FirebaseDatabase.getInstance("https://tempapplication-104fd-default-rtdb.firebaseio.com/");

        SharedPreferences get_sp_caterer_registration=getSharedPreferences("MYPREF_CATER_REG",MODE_PRIVATE);
        String caterIDTxt=get_sp_caterer_registration.getString("CATER_LOG_PHONE","");

        DatabaseReference rootRef=FirebaseDatabase.getInstance().getReference();
        DatabaseReference childReference=rootRef.child("Menu");
        Query databaseReference=childReference.orderByChild("caterer_id").equalTo(caterIDTxt);
        menuDataHolderArrayList=new ArrayList<MenuDataHolder>();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    MenuDataHolder menuDataHolder=dataSnapshot.getValue(MenuDataHolder.class);
                    menuDataHolderArrayList.add(menuDataHolder);
                }
                MenuPackageAdapter menuPackageAdapter=new MenuPackageAdapter(Caterer_View_Tiffin.this,menuDataHolderArrayList);
                listView.setAdapter(menuPackageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str=menuDataHolderArrayList.get(position).getMenu_id();
                Intent intent=new Intent(Caterer_View_Tiffin.this,Caterer_Update_Delete_Tiffin.class);
                intent.putExtra("KEY_LIST_1",str);
                startActivity(intent);
            }
        });


    }
}