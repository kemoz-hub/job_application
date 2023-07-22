package com.example.jobapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registerusers extends AppCompatActivity {
EditText mFullName,mEmail,mPassword,mid,mPhone;
TextView login;
AutoCompleteTextView mconfirm,usertype;
Button button,btnRegLogin;
DatabaseReference reference;
FirebaseDatabase rootNode;
FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerusers);

        mFullName = findViewById(R.id.rFullName);
        mEmail = findViewById(R.id.rEmail);
        mPassword = findViewById(R.id.rpassword);
        mconfirm = findViewById(R.id.rlocation);
        usertype = findViewById(R.id.rusertype);
        mid = findViewById(R.id.id_no);
        mPhone = findViewById(R.id.rPhone);
        button = findViewById(R.id.rsregister);
        login=findViewById(R.id.loginreg);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        ProgressBar progressBar;

        String[] location= {"kianjai","nchiru","gundune","karumo","miathene"};

        ArrayAdapter<String> ss = new ArrayAdapter<String>(this,R.layout.dropdown,location);
        ss.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mconfirm.setAdapter(ss);

        mconfirm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String location2=adapterView.getItemAtPosition(i).toString();

            }
        });

        String[] usertypek= {"applicant","employer"};

        ArrayAdapter<String> kk = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,usertypek);
        kk.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        usertype.setAdapter(kk);

        usertype.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String location3=adapterView.getItemAtPosition(i).toString();

            }
        });

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.INVISIBLE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");


                String fullname=mFullName.getText().toString().trim();
                String email=mEmail.getText().toString().trim();
                String password=mPassword.getText().toString().trim();
                String phone=mPhone.getText().toString().trim();
                String confirm=mconfirm.getText().toString().trim();
                String id=mid.getText().toString().trim();
                String usert=usertype.getText().toString().trim();

                progressBar.setVisibility(View.VISIBLE);

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("password is required");
                    return;
                }
                if (mPassword.length() < 6) {
                    mPassword.setError(("password must be>=6 characters"));
                    return;
                }


                if(TextUtils.isEmpty(id)) {
                    mid.setError("please enter id number");
                    return;
                }
                if(mid.length()!=8){
                    mid.setError("please enter valid id number");
                    return;
                }
                if(TextUtils.isEmpty(phone)) {
                    mPhone.setError("please enter phone number");
                    return;
                }
                if(mPhone.length()!=10){
                    mid.setError("please enter valid phone number");

                }


                else{
                    progressBar.setVisibility(View.VISIBLE);
                    fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                String Uid = task.getResult().getUser().getUid();
                                if (usert.equals("applicant")) {
                                    userhelper userhelper = new userhelper(id, fullname, email, password, confirm, 0, phone, Uid,"","","","","");
                                    reference.child(Uid).setValue(userhelper);
                                    Toast.makeText(Registerusers.this, "user created", Toast.LENGTH_SHORT).show();
                                }
                                if (usert.equals("employer")) {
                                    userhelper userhelper = new userhelper(id, fullname, email, password, confirm, 1, phone, Uid,"","","","","");
                                    reference.child(Uid).setValue(userhelper);
                                    Toast.makeText(Registerusers.this, "user created", Toast.LENGTH_SHORT).show();
                                }
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {
                                Toast.makeText(Registerusers.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }

                        }
                    });
                }
            }



        });

    }
}