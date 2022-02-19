package com.example.greatbreak;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.bluetooth.le.AdvertiseData;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URL;

public class Menuadd extends AppCompatActivity {
    Button Add,upld,list;
    EditText itname,itprice;
    ImageButton InputProductImage;
    DatabaseReference reff;
    Item item;
    StorageReference storageRef;
    Uri imguri;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuadd);
        storageRef= FirebaseStorage.getInstance().getReference("Images");
        reff = FirebaseDatabase.getInstance().getReference("Products");
        item=new Item();
        upld=findViewById(R.id.upload);

        Add=findViewById(R.id.add);
        InputProductImage=findViewById(R.id.select_product_image);
        itname=findViewById(R.id.item_name);
        itprice=findViewById(R.id.item_price);
        list=findViewById(R.id.list);
        progressDialog = new ProgressDialog(Menuadd.this);

        InputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Filechooser();

            }
        });
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Menuadd.this,Breakfast.class);
                startActivity(intent);
            }
        });
        upld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage(imguri);
            }
        });

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setIname(itname.getText().toString().trim());
                item.setIprice(itprice.getText().toString().trim());
                //item.setImageUrl(imageid);

                reff.push().setValue(item);
                Toast.makeText(Menuadd.this, "Product Uploaded Successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Filechooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imguri = data.getData();
            InputProductImage.setImageURI(imguri);
        }
    }

    private void uploadImage(Uri imguri) {

        if (imguri != null) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            String imageid = System.currentTimeMillis()+"."+getExtension(imguri);
            StorageReference ref = storageRef.child(imageid);

            ref.putFile(imguri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    item.setImageUrl(uri.toString());
                                    progressDialog.dismiss();
                                    Toast.makeText(Menuadd.this, "Image Uploaded", Toast.LENGTH_SHORT).show();



                                }
                            });


                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure( Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Menuadd.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(
                                UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }

    private String getExtension(Uri imguri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(imguri));
    }
}