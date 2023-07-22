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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobapplication.databinding.ActivityPostjobBinding;
import com.example.jobapplication.databinding.ActivityReviewJBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class reviewJ extends Drawerbase {
    revieadapter adapter;
    RecyclerView recyclerView;
    DatabaseReference reference;
    ActivityReviewJBinding activityReviewJBinding;
    TextView tess;
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(reviewJ.this);
        builder.setTitle("Are you sure you want to quit ?");
        builder.setMessage("Do you want to log out");
        builder.setIcon(R.drawable.ic_baseline_notification_important_24);

        builder.setPositiveButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(reviewJ.this,MainActivity.class));
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
        activityReviewJBinding = ActivityReviewJBinding.inflate(getLayoutInflater());
        setContentView(activityReviewJBinding.getRoot());
        allocateActivityTitle("Review Jobs");

        String uid= FirebaseAuth.getInstance().getUid();
        reference= FirebaseDatabase.getInstance().getReference("jobs applied");
        recyclerView=findViewById(R.id.recycleviewR);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager( this));

        String uid2=FirebaseAuth.getInstance().getCurrentUser().getUid();

        final TextView fullTextview=(TextView) findViewById(R.id.test);

        reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                jobsapplied userprofile = snapshot.getValue(jobsapplied.class);
                if (userprofile != null) {

                    String full = userprofile.empid;

                    fullTextview.setText(full);


                    FirebaseRecyclerOptions<jobsapplied> options=
                            new FirebaseRecyclerOptions.Builder<jobsapplied>()
                                    .setQuery(FirebaseDatabase.getInstance().getReference("jobs applied").orderByChild("empid").equalTo(uid2),jobsapplied.class)
                                    .build();

                    adapter=new revieadapter(options);
                    recyclerView.setAdapter(adapter);
                    adapter.startListening();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(reviewJ.this, "something wrong happened", Toast.LENGTH_SHORT).show();
            }
            });


            FirebaseRecyclerOptions<jobsapplied> options=
                new FirebaseRecyclerOptions.Builder<jobsapplied>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("jobs applied").orderByChild("empid").equalTo(uid2),jobsapplied.class)
                        .build();



        adapter=new revieadapter(options);
        recyclerView.setAdapter(adapter);

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_employer);

        bottomNavigationView.setSelectedItemId(R.id.Dreview);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.Dpost:
                        startActivity(new Intent(getApplicationContext(),postjob.class));
                        overridePendingTransition(0,0);
                        return  true;

                    case R.id.Dcompany:
                        startActivity(new Intent(getApplicationContext(),company.class));
                        overridePendingTransition(0,0);
                        return  true;
                }
                return false;
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(newState==recyclerView.SCROLL_STATE_IDLE){
                    bottomNavigationView.setVisibility(View.VISIBLE);
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy>0||dy<0 ){
                    bottomNavigationView.setVisibility(View.GONE);
                }
            }
        });



      /*  adapter =new revieadapter(this,list);
        recyclerView.setAdapter(adapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren() ){
                    postjobhelper job=dataSnapshot1.getValue(postjobhelper.class);
                    list.add(job);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
    }
}