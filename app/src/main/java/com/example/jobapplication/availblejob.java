package com.example.jobapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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

import com.example.jobapplication.databinding.ActivityAvailblejobBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class availblejob extends Drawerbase {
    availableadapter adapter;
    RecyclerView recyclerView;
    DatabaseReference reference;
    SearchView searchView;
    ActivityAvailblejobBinding activityAvailblejobBinding;
    TextView countr;

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(availblejob.this);
        builder.setTitle("Are you sure you want to quit ?");
        builder.setMessage("Do you want to log out");
        builder.setIcon(R.drawable.ic_baseline_notification_important_24);

        builder.setPositiveButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(availblejob.this,MainActivity.class));
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
        activityAvailblejobBinding = ActivityAvailblejobBinding.inflate(getLayoutInflater());
        setContentView(activityAvailblejobBinding.getRoot());
        allocateActivityTitle("available jobs");



        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_employee);

        bottomNavigationView.setSelectedItemId(R.id.Pavailable);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.Pstatus:
                        startActivity(new Intent(getApplicationContext(),statusactivity.class));
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


        reference= FirebaseDatabase.getInstance().getReference("job post");
        recyclerView=findViewById(R.id.recycleview3);
        countr=findViewById(R.id.counta);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager( this));
        recyclerView.setAdapter(adapter);
        searchView=findViewById(R.id.search);

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

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int counter=(int)snapshot.getChildrenCount();
                String usercount=String.valueOf(counter);
                countr.setText(usercount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



       FirebaseRecyclerOptions<postjobhelper> options=
                new FirebaseRecyclerOptions.Builder<postjobhelper>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("job post"),postjobhelper.class)
                        .build();
        adapter=new availableadapter(options);
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });

    }

    private void processsearch(String s) {
        FirebaseRecyclerOptions<postjobhelper> options=
                new FirebaseRecyclerOptions.Builder<postjobhelper>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("job post").orderByChild("category").startAt(s).endAt(s+"\uf8ff"),postjobhelper.class)
                        .build();
        adapter=new availableadapter(options);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}