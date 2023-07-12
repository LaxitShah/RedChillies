package com.example.tempapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Caterer_Registration extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://tempapplication-104fd-default-rtdb.firebaseio.com/");

    String[] cityNames={"--Select Your City---","Amreli", "Baroda", "Surat"};
    String item;
    Spinner spin;
    Button browse;
    ImageView img;
    Uri filepath;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caterer_registration);

        img=(ImageView) findViewById(R.id.img);
        browse=(Button) findViewById(R.id.browse);

        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(Caterer_Registration.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent=new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Please Select an Image"),1);

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();

            }
        });
        final Button regButton=findViewById(R.id.creg_reg_button);
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SaveValue(item);
                uploadtofirebase();


            }
        });


        spin = (Spinner) findViewById(R.id.simpleSpinner);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter aa=new ArrayAdapter(this, android.R.layout.simple_spinner_item,cityNames);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);



        final TextView loginClick=(TextView) findViewById(R.id.creg_tv2);
        loginClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Caterer_Registration.this,Caterer_Login.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item=spin.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    void SaveValue(String item)
    {
        if (item=="--select your city---")
        {
            Toast.makeText(this,"please select your city",Toast.LENGTH_SHORT).show();
        }
        else{
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1&&resultCode==RESULT_OK)
        {
            filepath=data.getData();

            try{
                InputStream inputStream= getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);

            }catch (Exception ex)
            {

            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadtofirebase() {

        ProgressDialog dialog=new ProgressDialog(this);
        dialog.setTitle("File Uploader");
        dialog.show();
        final EditText firstName=(EditText)findViewById(R.id.creg_et_fname);
        final EditText lastName=(EditText)findViewById(R.id.creg_et_lname);
        final EditText phoneNo=(EditText)findViewById(R.id.creg_et_phone);
        final EditText address=(EditText)findViewById(R.id.creg_et_address);
        final EditText password=(EditText)findViewById(R.id.creg_et_password);
        final EditText email=(EditText)findViewById(R.id.creg_et_email);
        final Spinner spinner_1=(Spinner)findViewById(R.id.simpleSpinner);



        final String firstNameTxt=firstName.getText().toString();
        final String lastNameTxt=lastName.getText().toString();
        final String phoneNoTxt=phoneNo.getText().toString();
        final String passwordTxt=password.getText().toString();
        final String emailTxt=email.getText().toString();
        final String addressTxt=address.getText().toString();
        final String spinnerTxt=spinner_1.getSelectedItem().toString();



        SharedPreferences sp_cater_registeration=getSharedPreferences("MYPREF_CATER_REG",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp_cater_registeration.edit();
        editor.putString("CATER_REG_FN",firstNameTxt);
        editor.putString("CATER_REG_LN",lastNameTxt);
        editor.putString("CATER_REG_PHONE",phoneNoTxt);
        editor.putString("CATER_REG_EMAIL",emailTxt);
        editor.putString("CATER_REG_PASSWORD",passwordTxt);
        editor.putString("CATER_REG_ADDRESS",addressTxt);
        editor.putString("CATER_REG_SPINNER",spinnerTxt);
        editor.commit();

        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference uploader= storage.getReference("Image1"+new Random().nextInt(50));
        uploader.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                dialog.dismiss();

                uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        dialog.dismiss();
                        if(firstNameTxt.isEmpty()||lastNameTxt.isEmpty()||phoneNoTxt.isEmpty()||passwordTxt.isEmpty()||emailTxt.isEmpty()||addressTxt.isEmpty())
                        {
                            Toast.makeText(Caterer_Registration.this, "Please fill all details", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            databaseReference.child("caterers").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.hasChild(phoneNoTxt))
                                    {
                                        Toast.makeText(Caterer_Registration.this, "Already Registered,Try Login", Toast.LENGTH_SHORT).show();

                                    }
                                    else{
//                                        databaseReference.child("caterers").child(phoneNoTxt).child("first_name").setValue(firstNameTxt);
//                                        databaseReference.child("caterers").child(phoneNoTxt).child("last_name").setValue(lastNameTxt);
//                                        databaseReference.child("caterers").child(phoneNoTxt).child("email").setValue(emailTxt);
//                                        databaseReference.child("caterers").child(phoneNoTxt).child("password").setValue(passwordTxt);
//                                        databaseReference.child("caterers").child(phoneNoTxt).child("address").setValue(addressTxt);
//                                        databaseReference.child("caterers").child(phoneNoTxt).child("city").setValue(spinnerTxt);

                                        dataholder obj=new dataholder(addressTxt,spinnerTxt,emailTxt,firstNameTxt,lastNameTxt,passwordTxt,uri.toString(),phoneNoTxt);
                                        databaseReference.child("caterers").child(phoneNo.getText().toString()).setValue(obj);

                                        firstName.setText("");
                                        lastName.setText("");
                                        password.setText("");
                                        email.setText("");
                                        address.setText("");
                                        phoneNo.setText("");

                                        img.setImageResource(R.drawable.ic_launcher_background);
                                        Toast.makeText(getApplicationContext(),"Uploaded",Toast.LENGTH_SHORT).show();



                                        Toast.makeText(Caterer_Registration.this, "Registeration Success", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(Caterer_Registration.this,Caterer_Login.class);
                                        startActivity(intent);

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                });
            }
        })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float percent=(100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        dialog.setMessage("Uploaded" +(int)percent+" % ");
                    }
                });

    }


}