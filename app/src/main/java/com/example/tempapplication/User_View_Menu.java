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

public class User_View_Menu extends AppCompatActivity {
    ListView listView;
    ArrayList<MenuDataHolder> menuDataHolderArrayList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_menu);

        listView=findViewById(R.id.listview_user_menu);
        firebaseDatabase=FirebaseDatabase.getInstance("https://tempapplication-104fd-default-rtdb.firebaseio.com/");


        SharedPreferences get_sp_caterer_registration=getSharedPreferences("MYPREF_CATERER_INFO",MODE_PRIVATE);
        String caterIDTxt=get_sp_caterer_registration.getString("CATERER_INFO_Phone","");


        DatabaseReference rootRef=FirebaseDatabase.getInstance().getReference();
        DatabaseReference childReference=rootRef.child("Menu");
        Query databaseReference=childReference.orderByChild("caterer_id").equalTo(caterIDTxt);
        menuDataHolderArrayList=new ArrayList<MenuDataHolder>();

        SharedPreferences get_user_tiffin=getSharedPreferences("MYPREF_USER_TIFFIN",MODE_PRIVATE);
        String tiffinNameSel=get_user_tiffin.getString("User_Tiffin_Name","");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    MenuDataHolder menuDataHolder=dataSnapshot.getValue(MenuDataHolder.class);
                    if(menuDataHolder.getTiffin_name().equals(tiffinNameSel)) {
                        menuDataHolderArrayList.add(menuDataHolder);
                    }
                }
                MenuPackageAdapter menuPackageAdapter=new MenuPackageAdapter(User_View_Menu.this,menuDataHolderArrayList);
                listView.setAdapter(menuPackageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                String strTiffinName=menuDataHolderArrayList.get(i).getTiffin_name();
                String strTiffinCatererID=menuDataHolderArrayList.get(i).getCaterer_id();
                String strTiffinMenuID=menuDataHolderArrayList.get(i).getMenu_id();
                String strTiffinMenuPrice=menuDataHolderArrayList.get(i).getMenu_price();
                String strTiffinMenuDesc=menuDataHolderArrayList.get(i).getMenu_desc();
                String strTiffinMenuName=menuDataHolderArrayList.get(i).getMenu_name();

                SharedPreferences sp_share=getSharedPreferences("USER_SHARE_MENU_DETAILS",MODE_PRIVATE);
                SharedPreferences.Editor editor=sp_share.edit();
                editor.putString("USER_TIFFIN_NAME",strTiffinName);
                editor.putString("USER_TIFFIN_CATERER_ID",strTiffinCatererID);
                editor.putString("USER_TIFFIN_MENU_ID",strTiffinMenuID);
                editor.putString("USER_TIFFIN_MENU_PRICE",strTiffinMenuPrice);
                editor.putString("USER_TIFFIN_MENU_DESC",strTiffinMenuDesc);
                editor.putString("USER_TIFFIN_MENU_NAME",strTiffinMenuName);

                editor.commit();

                Intent intent=new Intent(User_View_Menu.this,User_View_Tiffin_Info.class);
//                intent.putExtra("KeyList",str);
                startActivity(intent);

            }
        });

    }
}