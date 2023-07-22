package com.example.jobapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.jobapplication.databinding.ActivityCompanyBinding;
import com.example.jobapplication.databinding.ActivityManageusersBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Context;

import java.util.ArrayList;

public class Manageusers extends Drawerbase {
    ActivityManageusersBinding activityManageusersBinding;

    RecyclerView recyclerView;
    ArrayList<userhelper> list;
    DatabaseReference databaseReference;
    manageusersadapter adapter;
    Context mcontext;
    Activity mactivity;
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(Manageusers.this);
        builder.setTitle("Are you sure you want to quit ?");
        builder.setMessage("Do you want to log out");
        builder.setIcon(R.drawable.ic_baseline_notification_important_24);

        builder.setPositiveButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Manageusers.this,MainActivity.class));
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
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activityManageusersBinding = ActivityManageusersBinding.inflate(getLayoutInflater());
        setContentView(activityManageusersBinding.getRoot());
        allocateActivityTitle("Manage users");

        recyclerView=findViewById(R.id.recycleviewusers);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager( Manageusers.this));

        //it gets all data from userhelper and fills the data to the recyclervier
        FirebaseRecyclerOptions<userhelper> options=
                new FirebaseRecyclerOptions.Builder<userhelper>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("users"),userhelper.class)
                        .build();
        adapter=new manageusersadapter(options);
        recyclerView.setAdapter(adapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_admin);

        bottomNavigationView.setSelectedItemId(R.id.Musers);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.Mreports:
                        startActivity(new Intent(getApplicationContext(), Reports.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.Mjobs:
                        startActivity(new Intent(getApplicationContext(), Managejobs.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }
}