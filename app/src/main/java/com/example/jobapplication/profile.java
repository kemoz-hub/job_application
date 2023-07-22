package com.example.jobapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobapplication.databinding.ActivityAvailblejobBinding;
import com.example.jobapplication.databinding.ActivityProfileBinding;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class profile extends Drawerbase {
    FirebaseUser user;
    DatabaseReference reference;
    String userid;
    AutoCompleteTextView educationlevel;
    StorageReference storageReference;
    EditText graduate,jobcategory,skills;
    FirebaseDatabase rootNode;


    RadioGroup genderk;
    RadioButton male,female;
    Button update,upload;
    ActivityProfileBinding activityProfileBinding;

    DatePickerDialog.OnDateSetListener setListener;

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(profile.this);
        builder.setTitle("Are you sure you want to quit ?");
        builder.setMessage("Do you want to log out");
        builder.setIcon(R.drawable.ic_baseline_notification_important_24);

        builder.setPositiveButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(profile.this,MainActivity.class));
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
        activityProfileBinding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(activityProfileBinding.getRoot());
        allocateActivityTitle("profile");

        jobcategory=findViewById(R.id.job_category);
        educationlevel=findViewById(R.id.education);
        graduate=findViewById(R.id.date_picker);
        skills=findViewById(R.id.skills);
        genderk=(RadioGroup) findViewById(R.id.gender);
        male=findViewById(R.id.radioButton);
        female=findViewById(R.id.radioButton2);
        upload=findViewById(R.id.upload);

        storageReference= FirebaseStorage.getInstance().getReference();

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectfiles();
            }
        });

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_employee);

        bottomNavigationView.setSelectedItemId(R.id.Pprofile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.Pavailable:
                        startActivity(new Intent(getApplicationContext(),availblejob.class));
                        overridePendingTransition(0,0);
                        return  true;

                    case R.id.Pstatus:
                        startActivity(new Intent(getApplicationContext(),statusactivity.class));
                        overridePendingTransition(0,0);
                        return  true;
                }
                return false;
            }
        });

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");

        Calendar calender=Calendar.getInstance();
        final int Year=calender.get(Calendar.YEAR);
        final int month=calender.get(Calendar.MONTH);
        final int Day=calender.get(Calendar.DAY_OF_MONTH);

        graduate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        profile.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        setListener,Year,month,Day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()-10000);
                datePickerDialog.show();
            }
        });
        setListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String date=dayOfMonth+"/"+month+"/"+year;
                graduate.setText(date);
            }
        };


        String[] education= {"DIPLOMA","DEGREE","MASTERS","PHD"};

        ArrayAdapter<String> pp = new ArrayAdapter<String>(this,R.layout.dropdown,education);
        pp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        educationlevel.setAdapter(pp);

        educationlevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String location2=adapterView.getItemAtPosition(i).toString();

            }
        });


        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("users");
        userid=user.getUid();

        final TextView emailTextview = (EditText) findViewById(R.id.mEmail2);
        final TextView fullTextview = (EditText) findViewById(R.id.mFullName2);
        final TextView phoneTextview = (EditText) findViewById(R.id.mphone);
        final TextView idTextview = (EditText) findViewById(R.id.id_no1);
        final TextView skillTextview = (EditText) findViewById(R.id.skills);
        final TextView resumeTextview = (EditText) findViewById(R.id.job_category);
        final TextView educTextview = (AutoCompleteTextView) findViewById(R.id.education);
        final TextView dateTextview = (EditText) findViewById(R.id.date_picker);



        reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userhelper userprofile = snapshot.getValue(userhelper.class);
                if (userprofile != null) {
                    String email = userprofile.Email;
                    String full = userprofile.fullname;
                    String phone = userprofile.phone;
                    String id = userprofile.idnumber;
                    String skil=userprofile.skills;
                    String resume=userprofile.jobcategory;
                    String educ=userprofile.education;
                    String date=userprofile.dategraduated;


                    fullTextview.setText(full);
                    idTextview.setText(id);
                    emailTextview.setText(email);
                    phoneTextview.setText(phone);
                    skillTextview.setText(skil);
                    resumeTextview.setText(resume);
                    educTextview.setText(educ);
                    dateTextview.setText(date);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(profile.this, "something wrong happened", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void selectfiles() {
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"select pdf file"),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && data !=null && data.getData() !=null)
        {
            uploadfile(data.getData());
        }


    }

    private void uploadfile(Uri data) {

        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("uploading");
        progressDialog.show();

        FirebaseStorage.getInstance().getReference("images/"+ UUID.randomUUID().toString()).putFile(data).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    task.getResult().getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if(task.isSuccessful())  {
                                updatecomplain(task.getResult().toString());
                            }
                        }
                    });

                    Toast.makeText(profile.this,"resume uploaded successfully",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(profile.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

//progress bar showing the progress while reporting the case
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress =100.0 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount();
                progressDialog.setMessage("uploading resume" + (int)progress + "%");
            }
        });
      /*  final ProgressDialog progressBar=new ProgressDialog(this);
        progressBar.setTitle("uploading resume");
        progressBar.show();
        StorageReference reference=storageReference.child("Resume/"+System.currentTimeMillis()+"pdf");
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri>uriTask=taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isComplete());
                        Uri url=uriTask.getResult();

                        String grad = graduate.getText().toString().trim();
                        String jobcat = jobcategory.getText().toString().trim();
                        String educ = educationlevel.getText().toString().trim();
                        String skil = skills.getText().toString().trim();
                        String m1 = male.getText().toString().trim();
                        String m2 = female.getText().toString().trim();

                        if (male.isChecked()) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("dategraduated", grad);
                            map.put("jobcategory", jobcat);
                            map.put("education", educ);
                            map.put("gender", m1);
                            map.put("skills", skil);
                            map.put("jobcategory", url);
                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(userid).updateChildren(map)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(profile.this, "profile updated  successfully", Toast.LENGTH_SHORT).show();
                                            //dialogPlus.dismiss();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(profile.this, "Error occurred while updating try again", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            return;
                        } else {

                            Map<String, Object> map = new HashMap<>();
                            map.put("dategraduated", grad);
                            map.put("jobcategory", jobcat);
                            map.put("Education", educ);
                            map.put("gender", m2);
                            map.put("skills", url);
                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(userid).updateChildren(map)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(profile.this, "profile updated  successfully", Toast.LENGTH_SHORT).show();
                                            //dialogPlus.dismiss();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(profile.this, "Error occurred while updating try again", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        }


                        Toast.makeText(profile.this, "updated successfully", Toast.LENGTH_SHORT).show();
                        progressBar.dismiss();
                    }

                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                double progress=(100.0*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressBar.setMessage("uploaded");
            }
        });*/

    }

    private void updatecomplain(String url) {
        String grad = graduate.getText().toString().trim();
        String jobcat = jobcategory.getText().toString().trim();
        String educ = educationlevel.getText().toString().trim();
        String skil = skills.getText().toString().trim();
        String m1 = male.getText().toString().trim();
        String m2 = female.getText().toString().trim();


        if (male.isChecked()) {
            Map<String, Object> map = new HashMap<>();
            map.put("dategraduated", grad);
            map.put("jobcategory", jobcat);
            map.put("education", educ);
            map.put("gender", m1);
            map.put("skills", skil);
            map.put("jobcategory", url);

            FirebaseDatabase.getInstance().getReference("users")
                    .child(userid).updateChildren(map)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(profile.this, "profile updated  successfully", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(profile.this, "Error occurred while updating try again", Toast.LENGTH_SHORT).show();
                        }
                    });
            return;
        } else {

            Map<String, Object> map = new HashMap<>();
            map.put("dategraduated", grad);
            map.put("jobcategory", url);
            map.put("education", educ);
            map.put("gender", m2);
            map.put("skills", skil);


            FirebaseDatabase.getInstance().getReference("users")
                    .child(userid).updateChildren(map)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(profile.this, "profile updated  successfully", Toast.LENGTH_SHORT).show();
                            //dialogPlus.dismiss();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(profile.this, "Error occurred while updating try again", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
