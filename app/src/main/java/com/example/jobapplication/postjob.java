package com.example.jobapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jobapplication.databinding.ActivityPostjobBinding;
import com.example.jobapplication.databinding.ActivityProfileBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class postjob extends Drawerbase {
AutoCompleteTextView locationk,jobsalary,company,jobcategory,jobtype;
DatabaseReference reference,reference3,reference4;
Button register;
FirebaseDatabase rootNode;
EditText description,requirements,closing,cphone,jobtitle;
ActivityPostjobBinding activityPostjobBinding;
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(postjob.this);
        builder.setTitle("Are you sure you want to quit ?");
        builder.setMessage("Do you want to log out");
        builder.setIcon(R.drawable.ic_baseline_notification_important_24);

        builder.setPositiveButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(postjob.this,MainActivity.class));
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
        activityPostjobBinding = ActivityPostjobBinding.inflate(getLayoutInflater());
        setContentView(activityPostjobBinding.getRoot());
        allocateActivityTitle("post Job");


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_employer);

        bottomNavigationView.setSelectedItemId(R.id.Dpost);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.Dcompany:
                        startActivity(new Intent(getApplicationContext(), company.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.Dreview:
                        startActivity(new Intent(getApplicationContext(), reviewJ.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });


        company = findViewById(R.id.pcompanyname);
        description = findViewById(R.id.pjobescription);
        requirements = findViewById(R.id.prequirements);
        closing = findViewById(R.id.pclosingdate);
        cphone = findViewById(R.id.pcompanypheone);
        locationk = findViewById(R.id.plocation);
        register = findViewById(R.id.registercomp);
        jobsalary = findViewById(R.id.pjobsalary);
        jobcategory = findViewById(R.id.pjobcategory);
        jobtype = findViewById(R.id.pjobtype);
        jobtitle = findViewById(R.id.pjobtitle);

        Calendar calender = Calendar.getInstance();
        final int Year = calender.get(Calendar.YEAR);
        final int month = calender.get(Calendar.MONTH);
        final int Day = calender.get(Calendar.DAY_OF_MONTH);

        closing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        postjob.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        setListener, Year, month, Day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-10000);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = year + "/" + month + "/" + dayOfMonth;
                closing.setText(date);
            }
        };


        String[] location= {"Nairobi","Eldoret","Nairobi","Kisumu","Meru","Isiolo","Uasin Gishu","Nandi","West Pokot","Turkana"};

        ArrayAdapter<String> kk = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,location);
        kk.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationk.setAdapter(kk);

        locationk.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String location2=adapterView.getItemAtPosition(i).toString();

            }
        });

        String[] salary= {"Ksh 10,000","<10,000ksh >20,000ksh","<30,000ksh >40,000ksh","<30,000ksh >40,000ksh","<40,000ksh >50,000ksh","<50,000ksh >60,000ksh","<60,000ksh >70,000ksh","<70,000ksh >80,000ksh","<80,000ksh >90,000ksh","<100,000ksh "};

        ArrayAdapter<String> sal = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,salary);
        sal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jobsalary.setAdapter(sal);

        jobsalary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String location2=adapterView.getItemAtPosition(i).toString();

            }
        });

        String[] categoryk= {"sales and marketing","Business admissions","engineering and technical","community development","ict and technology","health and medical","economics and statistics","accounting and finance","communications and media","HR and administrations","project management"};

        ArrayAdapter<String> cat = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,categoryk);
        cat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jobcategory.setAdapter(cat);

        jobcategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String location2=adapterView.getItemAtPosition(i).toString();

            }
        });

        String[] jobt= {"Part time","full time","intern"};

        ArrayAdapter<String> jj = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,jobt);
        jj.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jobtype.setAdapter(jj);

        jobtype.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String location2=adapterView.getItemAtPosition(i).toString();

            }
        });

        reference4=FirebaseDatabase.getInstance().getReference("company");

        user = FirebaseAuth.getInstance().getCurrentUser();

        try {

            reference4.child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String companyk = snapshot.child("companyname").getValue(String.class);
                    if(companyk==null){
                        AlertDialog.Builder builder = new AlertDialog.Builder(postjob.this);
                        builder.setTitle("Register your company");
                        builder.setMessage("please register company before you post job");
                        builder.show();
                        Toast.makeText(postjob.this, "Register company", Toast.LENGTH_SHORT).show();
                    }
                    else {


                        final String[] comp = {companyk};
                        ArrayAdapter<String> jj = new ArrayAdapter<String>(postjob.this, android.R.layout.simple_spinner_dropdown_item, comp);
                        jj.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        company.setAdapter(jj);


                        company.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String location2 = adapterView.getItemAtPosition(i).toString();

                            }
                        });
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }catch(Exception e){

        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String companyk=company.getText().toString().trim();
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("job post").child(companyk);

                String descriptionk=description.getText().toString().trim();
                String requirementsk = requirements.getText().toString().trim();
                String closingk=closing.getText().toString().trim();
                String cphonek=cphone.getText().toString().trim();
                String location=locationk.getText().toString().trim();
                String title=jobtitle.getText().toString().trim();
                String salary=jobsalary.getText().toString().trim();
                String category=jobcategory.getText().toString().trim();
                String type=jobtype.getText().toString().trim();

                if(companyk.isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(postjob.this);
                    builder.setTitle("Register your company");
                    builder.setMessage("please register company before you post job");
                    builder.show();
                    Toast.makeText(postjob.this, "Register company", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(descriptionk.isEmpty()){
                    description.setError("enter job description");
                    description.requestFocus();
                    return;
                }
                if (requirementsk.isEmpty()){
                    requirements.setError("enter requirements");
                    requirements.requestFocus();
                    return;
                }
                if(closingk.isEmpty()){
                    closing.setError("enter deadline date");
                    closing.requestFocus();
                    return;
                }
                if(cphonek.isEmpty()){
                    cphone.setError("enter company phone number");
                    cphone.requestFocus();
                    return;
                }
                if(location.isEmpty()){
                    locationk.setError("enter company location");
                    locationk.requestFocus();
                    return;
                }
                if(title.isEmpty()){
                    jobtitle.setError("enter job title");
                    jobtitle.requestFocus();
                    return;
                }
                if(salary.isEmpty()){
                    jobsalary.setError("enter salary to be paid");
                    jobsalary.requestFocus();
                    return;
                }
                if(category.isEmpty()){
                    jobcategory.setError("enter job category");
                    jobcategory.requestFocus();
                    return;
                }
                if(type.isEmpty()){
                    jobtype.setError("enter job type");
                    jobtype.requestFocus();
                }
                else {

                    String message = "Dear applicant new job has been posted  by " + companyk + " company   of job catagory " + category + "  be the first to apply";
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(postjob.this)
                            .setSmallIcon(R.drawable.ic_baseline_notifications_none_24)
                            .setContentTitle("Job application")
                            .setContentText(message)
                            .setAutoCancel(true);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel channel = new NotificationChannel("My notification", "My notification", NotificationManager.IMPORTANCE_DEFAULT);
                        NotificationManager manager = postjob.this.getSystemService(NotificationManager.class);
                        manager.createNotificationChannel(channel);
                        builder.setChannelId("My notification");
                    }

                    NotificationManagerCompat managerCompat = NotificationManagerCompat.from(postjob.this);
                    managerCompat.notify(0, builder.build());

                    Intent intent = new Intent(postjob.this, notification.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("message4", "Dear applicant new job has been posted  by " + companyk + " company   of job catagory " + category + "  be the first applicant  to apply");

                    PendingIntent pendingIntentk = PendingIntent.getActivity(postjob.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(pendingIntentk);

                    NotificationManager notificationManager = (NotificationManager) postjob.this.getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.notify(0, builder.build());

                    user = FirebaseAuth.getInstance().getCurrentUser();
                    reference3 = FirebaseDatabase.getInstance().getReference("notifications");
                    String userid3 = user.getUid();

                    notifyfirebase notify = new notifyfirebase(message, userid3);
                    reference3.child(userid3).setValue(notify);

                    postjobhelper postjobhelper = new postjobhelper(companyk, descriptionk, requirementsk, closingk, cphonek, location, Uid, "", "", "", "", title, salary, category, type);
                    reference.setValue(postjobhelper);
                    Toast.makeText(postjob.this, "job posted successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}