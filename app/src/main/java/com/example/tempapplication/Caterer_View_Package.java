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

public class Caterer_View_Package extends AppCompatActivity {
    ListView listView;
    ArrayList<PackageDataHolder> packageDataHolderArrayList;
    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caterer_view_package);

        listView=findViewById(R.id.listview_caterer_package);
        firebaseDatabase=FirebaseDatabase.getInstance("https://tempapplication-104fd-default-rtdb.firebaseio.com/");


        SharedPreferences get_sp_caterer_registration=getSharedPreferences("MYPREF_CATER_REG",MODE_PRIVATE);
        String caterIDTxt=get_sp_caterer_registration.getString("CATER_LOG_PHONE","");

        DatabaseReference rootRef=FirebaseDatabase.getInstance().getReference();
        DatabaseReference childReference=rootRef.child("Package");
        Query databaseReference=childReference.orderByChild("caterer_id").equalTo(caterIDTxt);
        packageDataHolderArrayList=new ArrayList<PackageDataHolder>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    PackageDataHolder packageDataHolder=dataSnapshot.getValue(PackageDataHolder.class);
                    packageDataHolderArrayList.add(packageDataHolder);
                }
                CatererPackageAdapter catererPackageAdapter=new CatererPackageAdapter(Caterer_View_Package.this,packageDataHolderArrayList);
                listView.setAdapter(catererPackageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String str=packageDataHolderArrayList.get(i).getPackage_id();
                Intent intent=new Intent(Caterer_View_Package.this,Caterer_Update_Delete_Package.class);
                intent.putExtra("KeyList",str);
                startActivity(intent);
            }
        });



    }
}