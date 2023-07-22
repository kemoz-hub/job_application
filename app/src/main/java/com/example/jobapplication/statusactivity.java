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

import com.example.jobapplication.databinding.ActivityProfileBinding;
import com.example.jobapplication.databinding.ActivityStatusactivityBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class statusactivity extends Drawerbase {
    statusadapter adapter;
    RecyclerView recyclerView;
    DatabaseReference reference;
    ActivityStatusactivityBinding activityStatusactivityBinding;

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(statusactivity.this);
        builder.setTitle("Are you sure you want to quit ?");
        builder.setMessage("Do you want to log out");
        builder.setIcon(R.drawable.ic_baseline_notification_important_24);

        builder.setPositiveButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(statusactivity.this,MainActivity.class));
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
        activityStatusactivityBinding = ActivityStatusactivityBinding.inflate(getLayoutInflater());
        setContentView(activityStatusactivityBinding.getRoot());
        allocateActivityTitle("My status");

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_employee);

        bottomNavigationView.setSelectedItemId(R.id.Pstatus);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.Pavailable:
                        startActivity(new Intent(getApplicationContext(),availblejob.class));
                        overridePendingTransition(0,0);
                        return  true;

                    case R.id.Pprofile:
                        startActivity(new Intent(getApplicationContext(),profile.class));
                        overridePendingTransition(0,0);
                        return  true;
                }
                return false;
            }
        });

        recyclerView=findViewById(R.id.recycleview4);
        recyclerView.setHasFixedSize(true);
        reference= FirebaseDatabase.getInstance().getReference("users");
        recyclerView.setLayoutManager(new LinearLayoutManager( this));
        String uid= FirebaseAuth.getInstance().getUid();

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

        final TextView fullTextview=(TextView) findViewById(R.id.test1);
        final TextView fullTextview2=(TextView) findViewById(R.id.test2);

        String empid=fullTextview2.toString().toString().trim();
        empid=FirebaseAuth.getInstance().getUid();

        reference.child(empid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userhelper userprofile = snapshot.getValue(userhelper.class);
                if (userprofile != null) {

                    String full = userprofile.uid;
                    String full1=userprofile.uid;

                    fullTextview.setText(full);
                    fullTextview2.setText(full1);

                    FirebaseRecyclerOptions<jobsapplied> options=
                            new FirebaseRecyclerOptions.Builder<jobsapplied>()
                                    .setQuery(FirebaseDatabase.getInstance().getReference("jobs applied").orderByChild("uid").equalTo(full1),jobsapplied.class)
                                    .build();

                    adapter=new statusadapter(options);
                    recyclerView.setAdapter(adapter);
                    adapter.startListening();

                    /*reference.child(full1).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            FirebaseRecyclerOptions<jobsapplied> options=
                                    new FirebaseRecyclerOptions.Builder<jobsapplied>()
                                            .setQuery(FirebaseDatabase.getInstance().getReference("jobs applied").orderByChild("uid").equalTo(full),jobsapplied.class)
                                            .build();

                            adapter=new statusadapter(options);
                            recyclerView.setAdapter(adapter);
                            adapter.startListening();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });*/

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
            });


            FirebaseRecyclerOptions<jobsapplied> options=
                new FirebaseRecyclerOptions.Builder<jobsapplied>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("jobs applied"),jobsapplied.class)
                        .build();

        adapter=new statusadapter(options);
        recyclerView.setAdapter(adapter);
    }
}