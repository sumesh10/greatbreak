package com.example.greatbreak;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signup extends AppCompatActivity {
    EditText username,email,password,confirm;
    Button signupp;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        username=findViewById(R.id.name1);
        email=findViewById(R.id.emailid);
        password=findViewById(R.id.pass);
        confirm=findViewById(R.id.conf);
        signupp=findViewById(R.id.sign);

        auth=FirebaseAuth.getInstance();
        signupp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String susername=username.getText().toString().trim();
                String semail=email.getText().toString().trim();
                String spassword=password.getText().toString().trim();
                String sconfirm=confirm.getText().toString().trim();

                if(!sconfirm.equals(spassword)){
                    confirm.setError("passwords doesnot match");
                    confirm.requestFocus();
                    return;
                }

                auth.createUserWithEmailAndPassword(semail,spassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(signup.this, "sign in successfull", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(signup.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

    }
}