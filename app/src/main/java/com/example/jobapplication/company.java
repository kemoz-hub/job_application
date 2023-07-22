package com.example.jobapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobapplication.databinding.ActivityCompanyBinding;
import com.example.jobapplication.databinding.ActivityProfileBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class company extends Drawerbase {
TextView employerid,companyname,companyadd,phoneno;
AutoCompleteTextView location;
Button regcomp;

FirebaseDatabase rootNode;
DatabaseReference reference;
FirebaseUser user;
ActivityCompanyBinding activityCompanyBinding;
String userid;

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(company.this);
        builder.setTitle("Are you sure you want to quit ?");
        builder.setMessage("Do you want to log out");
        builder.setIcon(R.drawable.ic_baseline_notification_important_24);

        builder.setPositiveButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(company.this,MainActivity.class));
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "cancelled", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCompanyBinding = ActivityCompanyBinding.inflate(getLayoutInflater());
        setContentView(activityCompanyBinding.getRoot());
        allocateActivityTitle("Company");

        employerid=findViewById(R.id.Memployerid);
        companyname=findViewById(R.id.Mcompanyname);
        companyadd=findViewById(R.id.Mcompanyadd);
        phoneno=findViewById(R.id.Mphoneno);
        location=findViewById(R.id.Mlocation);
        regcomp=findViewById(R.id.registercomp);

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_employer);

        bottomNavigationView.setSelectedItemId(R.id.Dcompany);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.Dpost:
                        startActivity(new Intent(getApplicationContext(),postjob.class));
                        overridePendingTransition(0,0);
                        return  true;

                    case R.id.Dreview:
                        startActivity(new Intent(getApplicationContext(),reviewJ.class));
                        overridePendingTransition(0,0);
                        return  true;
                }
                return false;
            }
        });

        String[] locationh= {"Nairobi","Eldoret","Nairobi","Kisumu","Meru","Isiolo","Uasin Gishu","Nandi","West Pokot","Turkana"};

        ArrayAdapter<String> kk = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,locationh);
        kk.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location.setAdapter(kk);

        location.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String location2=adapterView.getItemAtPosition(i).toString();

            }
        });

        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("users");
        userid=user.getUid();


        final TextView idTextview = (EditText)  findViewById(R.id.Memployerid);


        reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userhelper applicantsprofile = snapshot.getValue(userhelper.class);
                if (applicantsprofile != null) {

                    String id = applicantsprofile.idnumber;

                    idTextview.setText(id);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(company.this, "something wrong happened", Toast.LENGTH_SHORT).show();
            }
        });

        regcomp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                rootNode = FirebaseDatabase.getInstance();
                String company = companyname.getText().toString().trim();
                reference = rootNode.getReference("company").child(Uid);

                String empl=employerid.getText().toString().trim();
                String add=companyadd.getText().toString().trim();
                String phone=phoneno.getText().toString().trim();
                String locationh=location.getText().toString().trim();

                companyhelper companyhelper=new companyhelper(empl,company,add,phone,locationh);
                reference.setValue(companyhelper);
                Toast.makeText(company.this, "Registered successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }
}