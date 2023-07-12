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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.Random;

public class Caterer_Add_Tiffin extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance("https://tempapplication-104fd-default-rtdb.firebaseio.com/");
    DatabaseReference databaseReference=firebaseDatabase.getReference("Menu");

    Button browse;
    ImageView img;
    Uri filepath;
    Bitmap bitmap;
    String caterIDTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caterer_add_tiffin);
        img=(ImageView) findViewById(R.id.ctadd_package_image);
        browse=(Button) findViewById(R.id.ctadd_upload_button);


        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withActivity(Caterer_Add_Tiffin.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
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

        final Button AddButton=findViewById(R.id.ctadd_add_button);
        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadToFireBase1();
            }
        });


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

    private void uploadToFireBase1() {
        ProgressDialog dialog=new ProgressDialog(this);
        dialog.setTitle("File Uploader");
        dialog.show();

        SharedPreferences get_sp_caterer_registration=getSharedPreferences("MYPREF_CATER_REG",MODE_PRIVATE);


        caterIDTxt=get_sp_caterer_registration.getString("CATER_LOG_PHONE","");

        final EditText tiffinName=(EditText) findViewById(R.id.ctadd_et_tname);
        final EditText menuPrice=(EditText) findViewById(R.id.ctadd_et_tprice);
        final EditText menuName=(EditText) findViewById(R.id.ctadd_et_menu_name);
        final EditText menuDesc=(EditText) findViewById(R.id.ctadd_et_menudesc);

        final String tiffinNameTxt=tiffinName.getText().toString();
        final String menuPriceTxt=menuPrice.getText().toString();
        final String menuNameTxt=menuName.getText().toString();
        final String menuDescTxt=menuDesc.getText().toString();

        String menu_id=databaseReference.push().getKey();


        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference uploader=storage.getReference("Image2"+new Random().nextInt(50));

        uploader.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                dialog.dismiss();

                uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        dialog.dismiss();
                        if(tiffinNameTxt.isEmpty()||menuPriceTxt.isEmpty()||menuNameTxt.isEmpty()||menuDescTxt.isEmpty())
                        {
                            Toast.makeText(Caterer_Add_Tiffin.this,"Please Fill all the Details",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    MenuDataHolder menuDataHolder=new MenuDataHolder();
                                    menuDataHolder.setCaterer_id(caterIDTxt);
                                    menuDataHolder.setMenu_id(menu_id);
                                    menuDataHolder.setTiffin_name(tiffinNameTxt);
                                    menuDataHolder.setMenu_price(menuPriceTxt);
                                    menuDataHolder.setMenu_desc(menuDescTxt);
                                    menuDataHolder.setMenu_name(menuNameTxt);
                                    menuDataHolder.setP_image(uri.toString());

                                    databaseReference.child(menu_id).setValue(menuDataHolder);
                                    menuName.setText("");
                                    menuDesc.setText("");
                                    menuDesc.setText("");
                                    tiffinName.setText("");

                                    img.setImageResource(R.drawable.ic_launcher_background);
                                    Toast.makeText(getApplicationContext(),"Uploaded",Toast.LENGTH_SHORT).show();


                                    Toast.makeText(Caterer_Add_Tiffin.this, "Tiffin Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(Caterer_Add_Tiffin.this,Caterer_View_Tiffin.class);
                                    startActivity(intent);

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                    }
                });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                float percent=(100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                dialog.setMessage("Uploaded" +(int)percent+" % ");
            }
        });






    }

}