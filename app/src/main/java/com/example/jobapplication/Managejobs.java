package com.example.jobapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobapplication.databinding.ActivityManagejobsBinding;
import com.example.jobapplication.databinding.ActivityManageusersBinding;
import com.example.jobapplication.databinding.ActivityReviewJBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Managejobs extends Drawerbase {
    revieadapter adapter;
    RecyclerView recyclerView;
    DatabaseReference reference;
    ActivityReviewJBinding activityReviewJBinding;
    TextView tess;
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(Managejobs.this);
        builder.setTitle("Are you sure you want to quit ?");
        builder.setMessage("Do you want to log out");
        builder.setIcon(R.drawable.ic_baseline_notification_important_24);

        builder.setPositiveButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Managejobs.this,MainActivity.class));
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
        ActivityManagejobsBinding activityManagejobsBinding;
        super.onCreate(savedInstanceState);
        activityManagejobsBinding = ActivityManagejobsBinding.inflate(getLayoutInflater());
        setContentView(activityManagejobsBinding.getRoot());
        allocateActivityTitle("Manage jobs");

        String uid= FirebaseAuth.getInstance().getUid();
        reference= FirebaseDatabase.getInstance().getReference("jobs applied");
        recyclerView=findViewById(R.id.recycleviewjobs);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager( this));

        FirebaseRecyclerOptions<jobsapplied> options=
                new FirebaseRecyclerOptions.Builder<jobsapplied>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("jobs applied"),jobsapplied.class)
                        .build();

        adapter=new revieadapter(options);
        recyclerView.setAdapter(adapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_admin);

        bottomNavigationView.setSelectedItemId(R.id.Mjobs);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.Musers:
                        startActivity(new Intent(getApplicationContext(), Manageusers.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.Mreports:
                        startActivity(new Intent(getApplicationContext(), Reports.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }
}