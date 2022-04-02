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
import java.util.Locale;

public class User_View_Package extends AppCompatActivity {

    ListView listView;
    ArrayList<PackageDataHolder> packageDataHolderArrayList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_package);


        listView=findViewById(R.id.listview_user_package);
        firebaseDatabase=FirebaseDatabase.getInstance("https://tempapplication-104fd-default-rtdb.firebaseio.com/");


        SharedPreferences get_sp_caterer_registration=getSharedPreferences("MYPREF_CATERER_INFO",MODE_PRIVATE);
        String caterIDTxt=get_sp_caterer_registration.getString("CATERER_INFO_Phone","");


        DatabaseReference rootRef=FirebaseDatabase.getInstance().getReference();
        DatabaseReference childReference=rootRef.child("Package");
        Query databaseReference=childReference.orderByChild("caterer_id").equalTo(caterIDTxt);
        packageDataHolderArrayList=new ArrayList<PackageDataHolder>();

        SharedPreferences get_user_package=getSharedPreferences("MYPREF_USER_PACKAGE",MODE_PRIVATE);
        String packageNameSel=get_user_package.getString("User_Package_Name","");



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    PackageDataHolder packageDataHolder=dataSnapshot.getValue(PackageDataHolder.class);
                    if(packageDataHolder.getPackage_event_name().equals(packageNameSel)) {
                        packageDataHolderArrayList.add(packageDataHolder);
                    }
                }
                CatererPackageAdapter catererPackageAdapter=new CatererPackageAdapter(User_View_Package.this,packageDataHolderArrayList);
                listView.setAdapter(catererPackageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String strPackageName=packageDataHolderArrayList.get(i).getPackage_name();
                String strPackagePrice=packageDataHolderArrayList.get(i).getPackage_price();
                String strPackageId=packageDataHolderArrayList.get(i).getPackage_id();
                String strPackageEventName=packageDataHolderArrayList.get(i).getPackage_event_name();
                String strPackageDesc=packageDataHolderArrayList.get(i).getPackage_desc();
                String strCaterId=packageDataHolderArrayList.get(i).getCaterer_id();


                SharedPreferences sp_share=getSharedPreferences("USER_SHARE_PACKAGE_DETAILS",MODE_PRIVATE);
                SharedPreferences.Editor editor=sp_share.edit();
                editor.putString("USER_PACKAGE_NAME",strPackageName);
                editor.putString("USER_PACKAGE_PRICE",strPackagePrice);
                editor.putString("USER_PACKAGE_ID",strPackageId);
                editor.putString("USER_PACKAGE_EVENT_NAME",strPackageEventName);
                editor.putString("USER_PACKAGE_DESC",strPackageDesc);
                editor.putString("USER_PACKAGE_CATER_ID",strCaterId);
                editor.commit();

                Intent intent=new Intent(User_View_Package.this,User_View_Package_Info.class);
//                intent.putExtra("KeyList",str);
                startActivity(intent);
            }
        });

    }
}