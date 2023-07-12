package com.example.tempapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    ListView listView;
    View view;


    ArrayList<dataholder> catererModelArrayList;
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_home,container,false);


        listView=view.findViewById(R.id.listview_caterer);
        firebaseDatabase=FirebaseDatabase.getInstance("https://tempapplication-104fd-default-rtdb.firebaseio.com/");
        databaseReference=firebaseDatabase.getReference("caterers");

        catererModelArrayList=new ArrayList<dataholder>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {

                    dataholder dataholder=dataSnapshot.getValue(dataholder.class);
                    catererModelArrayList.add(dataholder);
                }
                CatererAdapter catererAdapter=new CatererAdapter(getActivity(),catererModelArrayList);
                listView.setAdapter(catererAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String catFN=catererModelArrayList.get(i).getFirst_name();
                String catLN=catererModelArrayList.get(i).getLast_name();
                String catPass=catererModelArrayList.get(i).getPassword();
                String catPhone=catererModelArrayList.get(i).getPhone();
                String catEmail=catererModelArrayList.get(i).getEmail();
                String catAddress=catererModelArrayList.get(i).getAddress();
                String catCity=catererModelArrayList.get(i).getCity();

                SharedPreferences sp_cater_info= getActivity().getSharedPreferences("MYPREF_CATERER_INFO", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sp_cater_info.edit();
                editor.putString("CATERER_INFO_FN",catFN);
                editor.putString("CATERER_INFO_LN",catLN);
                editor.putString("CATERER_INFO_Pass",catPass);
                editor.putString("CATERER_INFO_Phone",catPhone);
                editor.putString("CATERER_INFO_Email",catEmail);
                editor.putString("CATERER_INFO_Address",catAddress);
                editor.putString("CATERER_INFO_CITY",catCity);
                editor.commit();


                Intent intent=new Intent(getActivity(),HomePageCatererInfo.class);

                startActivity(intent);
            }
        });

        return view;
    }
}
