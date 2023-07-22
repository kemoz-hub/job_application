package com.example.jobapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobapplication.databinding.ActivityCompanyBinding;
import com.example.jobapplication.databinding.ActivityNotificationBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class notification extends Drawerbase {
    ListView mylist;
    List<notifyfirebase> notifyfirebases;
    DatabaseReference reference,reference2;
ActivityNotificationBinding activityNotificationBinding;
EditText message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityNotificationBinding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(activityNotificationBinding.getRoot());
        allocateActivityTitle("Notification");

        mylist=findViewById(R.id.mylistview);
        notifyfirebases=new ArrayList<>();

        user= FirebaseAuth.getInstance().getCurrentUser();
        reference2=FirebaseDatabase.getInstance().getReference("users");
        String userid=user.getUid();


        final TextView idTextview = (TextView)  findViewById(R.id.not);


        reference2.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userhelper applicantsprofile = snapshot.getValue(userhelper.class);
                if (applicantsprofile != null) {

                    String id = applicantsprofile.uid;

                    idTextview.setText(id);
                    reference= FirebaseDatabase.getInstance().getReference("notifications");
                    reference.orderByChild("uid").equalTo(id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            notifyfirebases.clear();
                            for (DataSnapshot datasnap :snapshot.getChildren()){

                                notifyfirebase r=datasnap.getValue(notifyfirebase.class);
                                notifyfirebases.add(r);

                            }
                            notifyadapter adapter=new notifyadapter(notification.this,notifyfirebases);
                            mylist.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(notification.this, "something wrong happened", Toast.LENGTH_SHORT).show();
            }
        });


        notifyadapter adapter=new notifyadapter(notification.this,notifyfirebases);
        mylist.setAdapter(adapter);
    }
}