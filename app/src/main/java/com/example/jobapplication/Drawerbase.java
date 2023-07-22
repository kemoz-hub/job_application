package com.example.jobapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobapplication.databinding.ActivityAvailblejobBinding;
import com.example.jobapplication.databinding.ActivityProfileBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Drawerbase extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    TextView email;
    View kview;
    ActivityAvailblejobBinding activityAvailblejobBinding;
    ActivityProfileBinding activityProfileBinding;
    FirebaseUser user;
    DatabaseReference reference;
    FirebaseDatabase rootNode;

    @Override
    public void setContentView(View view) {
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawerbase, null);
        FrameLayout container = drawerLayout.findViewById(R.id.activityContainer);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        NavigationView navigationView = drawerLayout.findViewById(R.id.nav_view);
        kview = navigationView.getHeaderView(0);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.menu_drawer_open, R.string.menu_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("users");
        String userid=user.getUid();


        final TextView emailTextview=(TextView)kview. findViewById(R.id.textView5);


        reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userhelper userprofile=snapshot.getValue(userhelper.class);
                if(userprofile!=null){

                    String name=userprofile.fullname;
                    emailTextview.setText("welcome, "+name+" !");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Drawerbase.this, "something wrong happened", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawerbase);

        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawerbase, null);
        FrameLayout container = drawerLayout.findViewById(R.id.activityContainer);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        NavigationView navigationView = drawerLayout.findViewById(R.id.nav_view);
        kview = navigationView.getHeaderView(0);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.menu_drawer_open, R.string.menu_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }



        protected void allocateActivityTitle (String titleString){
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(titleString);
            }
        }

        @Override
        public boolean onNavigationItemSelected (@NonNull MenuItem item){
            drawerLayout.closeDrawer(GravityCompat.START);
            switch(item.getItemId()) {
                case R.id.whatsapp:
                    Intent intent=new Intent();
                    intent.setPackage("com.whatsapp");
                    if (intent.resolveActivity(getPackageManager())!=null){
                        startActivity(intent);
                    }
                    break;
                case R.id.notification:
                    Intent intent1=new Intent(getApplicationContext(),notification.class);
                   startActivity(intent1);
                   break;
                case R.id.profile:
                    String uid1=FirebaseAuth.getInstance().getCurrentUser().getUid();
                    FirebaseDatabase firebaseDatabase1=FirebaseDatabase.getInstance();
                    firebaseDatabase1.getReference().child("users").child(uid1).child("usertype").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            int usertype = snapshot.getValue(Integer.class);


                            if (usertype == 0) {
                                AlertDialog.Builder builder=new AlertDialog.Builder(Drawerbase.this);
                                builder.setTitle("Not permitted to access this services");
                                builder.setMessage("please contact administrator ");
                                builder.show();
                            }
                            if (usertype == 1) {
                                Intent intent2=new Intent(getApplicationContext(),empreports.class);
                                startActivity(intent2);
                            }


                            if (usertype == 2) {
                                Intent intent2=new Intent(getApplicationContext(),empreports.class);
                                startActivity(intent2);

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }

                    });

                    break;
                case R.id.about:
                    Intent intent3=new Intent(getApplicationContext(),About.class);
                    startActivity(intent3);
                    break;
                case R.id.analysis:
                    String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                    firebaseDatabase.getReference().child("users").child(uid).child("usertype").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            int usertype = snapshot.getValue(Integer.class);


                            if (usertype == 0) {
                                AlertDialog.Builder builder=new AlertDialog.Builder(Drawerbase.this);
                                builder.setTitle("Not permitted to access this services");
                                builder.setMessage("please contact administrator ");
                                builder.show();
                            }
                            if (usertype == 1) {
                                Intent intent4 = new Intent(getApplicationContext(), Analysis.class);
                                startActivity(intent4);
                            }


                                if (usertype == 2) {
                                    Intent intent4 = new Intent(getApplicationContext(), Analysis.class);
                                    startActivity(intent4);

                                }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    break;
                case R.id.home:
                    AlertDialog.Builder builder=new AlertDialog.Builder(Drawerbase.this);
                    builder.setTitle("Are you sure you want to quit ?");
                    builder.setMessage("Do you want to log out");
                    builder.setIcon(R.drawable.ic_baseline_notification_important_24);

                    builder.setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(Drawerbase.this,MainActivity.class));
                        }
                    });
                    builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getApplicationContext(), "cancelled", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                    break;
            }
            return false;
        }
    }

