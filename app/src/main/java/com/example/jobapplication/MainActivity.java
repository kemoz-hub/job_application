package com.example.jobapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
Button login;
TextView employer,registeruser,forgot;
EditText mPassword2,mEmail2;
FirebaseAuth fAuth;



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.this.finish();
        System.exit(0);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPassword2 = findViewById(R.id.Mpassword);
        mEmail2 = findViewById(R.id.MEmail);
        login=findViewById(R.id.buttonl1);
        registeruser=findViewById(R.id.btnRegLogin);
        forgot=findViewById(R.id.btnforgot);
        fAuth = FirebaseAuth.getInstance();

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),forgotpassword.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progressDialog=new ProgressDialog(MainActivity.this);
                progressDialog.setTitle("please wait");
                progressDialog.show();
                String email1 = mEmail2.getText().toString().trim();
                String password2 = mPassword2.getText().toString().trim();

                if(TextUtils.isEmpty(email1))
                {
                    mEmail2.setError("Email is required");
                    return;

                }
                if(TextUtils.isEmpty(password2))
                {
                    mPassword2.setError("password is required");
                    return;
                }
                if(password2.length()< 6) {
                    mPassword2.setError(("password must be>=6 characters"));
                    return;

                };

                fAuth.signInWithEmailAndPassword(email1, password2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            String uid=task.getResult().getUser().getUid();
                            FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                            firebaseDatabase.getReference().child("users").child(uid).child("usertype").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    int usertype=snapshot.getValue(Integer.class);


                                    if (usertype==0)
                                    {


                                        Toast.makeText(MainActivity.this, "logged in successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(getApplicationContext(),profile.class);
                                        startActivity(intent);
                                    }
                                    if (usertype==1)
                                    {


                                        Toast.makeText(MainActivity.this, "logged in successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(getApplicationContext(),company.class);
                                        startActivity(intent);
                                    }
                                    if (usertype==2)
                                    {


                                        Toast.makeText(MainActivity.this, "logged in successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(getApplicationContext(),Manageusers.class);
                                        startActivity(intent);
                                    }

                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }

                            });



                        } else
                        {
                            Toast.makeText(MainActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }

                        progressDialog.dismiss();
                    }
                });


            }



        });

        registeruser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Registerusers.class));
            }
        });
    }
}