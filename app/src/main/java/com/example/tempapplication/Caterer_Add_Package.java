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

public class Caterer_Add_Package extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance("https://tempapplication-104fd-default-rtdb.firebaseio.com/");
    DatabaseReference databaseReference=firebaseDatabase.getReference("Package");



    Button browse;
    ImageView img;
    Uri filepath;
    Bitmap bitmap;
    String caterIDTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caterer_add_package);
        img=(ImageView) findViewById(R.id.cadd_package_image);
        browse=(Button) findViewById(R.id.cadd_upload_button);

        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withActivity(Caterer_Add_Package.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
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

        final Button AddButton=findViewById(R.id.cadd_add_button);

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


        caterIDTxt=get_sp_caterer_registration.getString("CATER_REG_PHONE","");

        if(caterIDTxt=="")
        {
            caterIDTxt=get_sp_caterer_registration.getString("CATER_LOG_PHONE","");
        }

        final EditText packageName=(EditText) findViewById(R.id.cadd_et_pname);
        final EditText packagePrice=(EditText) findViewById(R.id.cadd_et_pprice);
        final EditText packageEventName=(EditText) findViewById(R.id.cadd_et_ename);
        final EditText packageEventDesc=(EditText) findViewById(R.id.cadd_et_pdesc);

        final String packageNameTxt=packageName.getText().toString();
        final String packagePriceTxt=packagePrice.getText().toString();
        final String packageEventNameTxt=packageEventName.getText().toString();
        final String packageEventDescTxt=packageEventDesc.getText().toString();

        String packageId=databaseReference.push().getKey();

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
                        if(packageNameTxt.isEmpty()||packagePriceTxt.isEmpty()||packageEventNameTxt.isEmpty()||packageEventDescTxt.isEmpty())
                        {
                            Toast.makeText(Caterer_Add_Package.this,"Please Fill all the Details",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    PackageDataHolder packageDataHolder=new PackageDataHolder();
                                    packageDataHolder.setCaterer_id(caterIDTxt);
                                    packageDataHolder.setPackage_id(packageId);
                                    packageDataHolder.setPackage_name(packageNameTxt);
                                    packageDataHolder.setPackage_price(packagePriceTxt);
                                    packageDataHolder.setPackage_desc(packageEventDescTxt);
                                    packageDataHolder.setPackage_event_name(packageEventNameTxt);
                                    packageDataHolder.setPackage_image(uri.toString());

                                    databaseReference.child(packageId).setValue(packageDataHolder);
                                    packageName.setText("");
                                    packagePrice.setText("");
                                    packageEventDesc.setText("");
                                    packageEventName.setText("");

                                    img.setImageResource(R.drawable.ic_launcher_background);
                                    Toast.makeText(getApplicationContext(),"Uploaded",Toast.LENGTH_SHORT).show();


                                    Toast.makeText(Caterer_Add_Package.this, "Package Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(Caterer_Add_Package.this,Caterer_View_Package.class);
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