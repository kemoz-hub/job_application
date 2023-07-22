package com.example.jobapplication;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class availableadapter extends  FirebaseRecyclerAdapter<postjobhelper,availableadapter.MyviewHolder> {


    public availableadapter(@NonNull FirebaseRecyclerOptions<postjobhelper> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull availableadapter.MyviewHolder holder, int position, @NonNull postjobhelper model) {
        holder.cName.setText(model.getCompany());
        holder.jdescr.setText(model.getDescription());
        holder.jreq.setText(model.getRequirements());
        holder.dead.setText(model.getClosing());
        holder.contact.setText(model.getPhone());
        holder.location.setText(model.getLocation());
        holder.applyby.setText(model.getAppliedby());
        holder.title.setText(model.getTitle());
        holder.salary.setText(model.getSalary());
        holder.category.setText(model.getCategory());
        holder.type.setText(model.getType());
        holder.empuid.setText(model.getUId());

      /*  holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.setExpandable(model.isExpandable());
            }
        });*/

       /* boolean isexpandable= model.isExpandable();
        holder.relative.setVisibility(isexpandable ?View.VISIBLE:View.GONE);*/


        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.contact.getContext())
                        .setContentHolder(new ViewHolder(R.layout.morea))
                        .setExpanded(true, 1000)
                        .setGravity(Gravity.CENTER)
                        .create();
                View v = dialogPlus.getHolderView();
                dialogPlus.show();

                TextView cname = v.findViewById(R.id.Mtextcompanyname);
                TextView title=v.findViewById(R.id.Mtexttitle);
                TextView description = v.findViewById(R.id.Mtextdescription);
                TextView requirements=v.findViewById(R.id.Mtextrequirements);
                TextView closing = v.findViewById(R.id.Mtextclosing);
                TextView cphone=v.findViewById(R.id.Mtextphone);
                TextView salary=v.findViewById(R.id.Mtextsalary);
                TextView category=v.findViewById(R.id.Mtextcategory);
                TextView type=v.findViewById(R.id.Mtextjobtype);
                TextView location=v.findViewById(R.id.Mtextlocation);


                cname.setText(model.getCompany());
                description.setText(model.getDescription());
                requirements.setText(model.getRequirements());
                closing.setText(model.getClosing());
                cphone.setText(model.getPhone());
                location.setText(model.getLocation());
                type.setText(model.getType());
                category.setText(model.getCategory());
                salary.setText(model.getSalary());
                title.setText(model.getTitle());
            }

        });

        holder.apply.setOnClickListener(new View.OnClickListener() {
            FirebaseUser user;
            DatabaseReference reference,reference3,reference4;
            String userid;
            AutoCompleteTextView educationlevel;
            EditText graduate,skills,companyname,jobcategory,Empid;
            FirebaseDatabase rootNode,rootnode2;

            RadioGroup genderk;
            RadioButton male,female;
            Button update;

            DatePickerDialog.OnDateSetListener setListener;
            @Override
            public void onClick(View view) {

                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.contact.getContext())
                        .setContentHolder(new ViewHolder(R.layout.profileapply))
                        .setExpanded(true, 1000)
                        .setGravity(Gravity.CENTER)
                        .create();
                View v = dialogPlus.getHolderView();
                dialogPlus.show();

                jobcategory=v.findViewById(R.id.job_categoryA);
                skills=v.findViewById(R.id.skillsA);
                update=v.findViewById(R.id.pupdateA);
                companyname=v.findViewById(R.id.companyU);
                Empid=v.findViewById(R.id.empid);

                companyname.setText(model.getCompany());
                skills.setText(model.getClosing());
                jobcategory.setText(model.getCategory());
                Empid.setText(model.getUId());

                rootNode = FirebaseDatabase.getInstance();
               reference = rootNode.getReference("users");


                user= FirebaseAuth.getInstance().getCurrentUser();
                //reference= FirebaseDatabase.getInstance().getReference("applicants profile");
                userid=user.getUid();

                final TextView emailTextview = (EditText) v.findViewById(R.id.mEmailA);
                final TextView fullTextview = (EditText) v.findViewById(R.id.mFullNameA);
                final TextView phoneTextview = (EditText) v.findViewById(R.id.mphoneA);
                final TextView idTextview = (EditText) v.findViewById(R.id.id_noA);
                final TextView date1=(EditText)v.findViewById(R.id.date_pickerA);
                final TextView skills2=v.findViewById(R.id.myskills);
                final TextView gender=v.findViewById(R.id.gender);
                final TextView educlevel=v.findViewById(R.id.educationA);
                final TextView cover=v.findViewById(R.id.coverletter);
                final TextView Employer=v.findViewById(R.id.empUid);



                reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        userhelper applicantsprofile = snapshot.getValue(userhelper.class);
                        if (applicantsprofile != null) {
                            String email = applicantsprofile.Email;
                            String full = applicantsprofile.fullname;
                            String phone = applicantsprofile.phone;
                            String id = applicantsprofile.idnumber;
                            String date=applicantsprofile.dategraduated;
                            String gend=applicantsprofile.gender;
                            String skills=applicantsprofile.skills;
                            String educ=applicantsprofile.education;
                            String letter=applicantsprofile.jobcategory;



                            fullTextview.setText(full);
                            idTextview.setText(id);
                            emailTextview.setText(email);
                            phoneTextview.setText(phone);
                            date1.setText(date);
                            skills2.setText(skills);
                            gender.setText(gend);
                            educlevel.setText(educ);
                            cover.setText(letter);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(v.getContext(), "something wrong happened", Toast.LENGTH_SHORT).show();
                    }
                });

                reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        userhelper applicantsprofile = snapshot.getValue(userhelper.class);
                        if (applicantsprofile != null) {

                        }
                    }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(v.getContext(), "something wrong happened", Toast.LENGTH_SHORT).show();
                        }
                    });

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        /*FirebaseDatabase.getInstance().getReference("applicants profile");
                        rootNode = FirebaseDatabase.getInstance();
                        reference = rootNode.getReference("applicants profile ");

                        String fullname=fullTextview.getText().toString().trim();
                        String email=emailTextview.getText().toString().trim();
                        String phone = phoneTextview.getText().toString().trim();
                        String grad=graduate.getText().toString().trim();
                        String jobcat=jobcategory.getText().toString().trim();
                        String educ=educationlevel.getText().toString().trim();
                        String company=companyname.getText().toString().trim();
                        String skil=skills.getText().toString().trim();
                        String id=idTextview.getText().toString().trim();
                        String m1=male.getText().toString().trim();
                        String m2=female.getText().toString().trim();

                        final int min=100007;
                        final int max=200007;
                        final String random=new Random().nextInt(+(max-min)+1)+"COM"+min+1+"KENY";

                        if (male.isChecked()){
                            applicantsprofile applicantsprofile=new applicantsprofile(id,fullname, email, skil, phone,grad,jobcat,educ,m1);
                            reference.setValue(applicantsprofile);
                            Toast.makeText(v.getContext(), "updated successfully", Toast.LENGTH_SHORT).show();
                            return;
                        }else {
                            applicantsprofile applicantsprofile=new applicantsprofile(id,fullname, email, skil, phone,grad,jobcat,educ,m2);
                            reference.setValue(applicantsprofile);
                            Toast.makeText(v.getContext(), "updated successfully", Toast.LENGTH_SHORT).show();
                        }


                        Toast.makeText(v.getContext(), "updated successfully", Toast.LENGTH_SHORT).show();

                         */

                        String fullname=fullTextview.getText().toString().trim();
                        String email=emailTextview.getText().toString().trim();
                        String phone = phoneTextview.getText().toString().trim();
                        String jobcat=jobcategory.getText().toString().trim();
                        String educ=educlevel.getText().toString().trim();
                        String company=companyname.getText().toString().trim();
                        String skil2=skills.getText().toString().trim();
                        String dated=date1.getText().toString().trim();
                        String skil=skills2.getText().toString().trim();
                        String id=idTextview.getText().toString().trim();
                        String genderm=gender.getText().toString().trim();
                        String coverl=cover.getText().toString().trim();
                        String empi=Empid.getText().toString().trim();


                        final int min = 100007;
                        final int max = 200007;
                        final String random = new Random().nextInt(+(max - min) + 1) + "COM" + min + 1 + "KENY";

                        try {
                            Date textViewDate = new SimpleDateFormat("yyyy/MM/dd").parse(skil2);
                            Date currentDate = new Date();

                            if (currentDate.after(textViewDate)) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                builder.setTitle("This job vacant has been closed ");
                                builder.setMessage("please apply any other job");
                                builder.show();
                                Toast.makeText(v.getContext(), "job has expired", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if(coverl.isEmpty()){
                                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                builder.setTitle("please upload resume to apply this job  ");
                                builder.setMessage("kindly update your profile first");
                                builder.show();
                                Toast.makeText(v.getContext(), "update your profile", Toast.LENGTH_SHORT).show();
                                return;
                            }else {

                                user = FirebaseAuth.getInstance().getCurrentUser();
                                reference4 = FirebaseDatabase.getInstance().getReference("jobs applied");
                                String userid = user.getUid();

                                String message="Dear " + fullname + " , the job applied for in " + company + " is successfully submitted please wait for feedback ";
                                NotificationCompat.Builder builder = new NotificationCompat.Builder(v.getContext())
                                        .setSmallIcon(R.drawable.ic_baseline_notifications_none_24)
                                        .setContentTitle("Job application")
                                        .setContentText(message)
                                        .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                                        .setAutoCancel(true);

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    NotificationChannel channel = new NotificationChannel("My notification", "My notification", NotificationManager.IMPORTANCE_DEFAULT);
                                    NotificationManager manager = v.getContext().getSystemService(NotificationManager.class);
                                    manager.createNotificationChannel(channel);
                                    builder.setChannelId("My notification");
                                }

                                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(v.getContext());
                                managerCompat.notify(0, builder.build());


                                Intent intent = new Intent(v.getContext(), notification.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("message3", "Dear " + fullname + " , the job applied for in  " + company + " company of job category" + jobcat + "is successfully submitted wait for feedback.");

                                PendingIntent pendingIntentk = PendingIntent.getActivity(v.getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                builder.setContentIntent(pendingIntentk);

                                NotificationManager notificationManager = (NotificationManager) v.getContext().getSystemService(NOTIFICATION_SERVICE);

                                notificationManager.notify(0, builder.build());

                                user= FirebaseAuth.getInstance().getCurrentUser();
                                reference3= FirebaseDatabase.getInstance().getReference("notifications");
                                String userid3=user.getUid();

                                notifyfirebase notify=new notifyfirebase(message,userid3);
                                reference3.child(userid3).setValue(notify);


                                jobsapplied companyhelper = new jobsapplied(userid, id, fullname, company, phone, jobcat, educ, coverl, empi, dated, skil, genderm, "pending", random);
                                reference4.child(random).setValue(companyhelper);
                            }


                        } catch (ParseException e) {
                            e.printStackTrace();
                        }




                      /*  if (TextUtils.isEmpty((CharSequence) skills2)){
                            AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                            builder.setTitle("update your profile before applying ");
                            builder.setMessage("kindly update profile");
                            builder.show();
                            Toast.makeText(v.getContext(), "profile not updated", Toast.LENGTH_SHORT).show();
                        }

                        else {*/


                            try {
                                Date textViewDate = new SimpleDateFormat("yyyy/MM/dd").parse(skil2);
                                Date currentDate = new Date();

                                if (currentDate.after(textViewDate)) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                    builder.setTitle("This job vacant has been closed ");
                                    builder.setMessage("please apply any other job");
                                    builder.show();
                                    Toast.makeText(v.getContext(), "job has expired", Toast.LENGTH_SHORT).show();
                                } else {
                                    Map<String, Object> map = new HashMap<>();
                                    map.put("appliedby", phone);
                                    map.put("status", "pending");
                                    map.put("batchno", random);
                                    map.put("applicantsid", userid);



                                    FirebaseDatabase.getInstance().getReference("job post")
                                            .child(Objects.requireNonNull(getRef(holder.getAdapterPosition()).getKey())).updateChildren(map)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(v.getContext(), "job applied  successfully", Toast.LENGTH_SHORT).show();
                                                    //dialogPlus.dismiss();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(v.getContext(), "Error occurred while updating try again", Toast.LENGTH_SHORT).show();
                                                }
                                            });


                                }


                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                    }
                });
            }
        });
    }

    @NonNull
    @Override
    public availableadapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.jobsavailable,parent,false);
        return  new MyviewHolder(view);
    }

    public class MyviewHolder extends RecyclerView.ViewHolder  {
        TextView cName,jdescr,jreq,dead,contact,location,applyby,title,salary,category,type,empuid;
        Button apply,more;
        LinearLayout linear;
        RelativeLayout relative;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            cName=itemView.findViewById(R.id.textcname);
            jdescr=itemView.findViewById(R.id.textcdescr);
            jreq=itemView.findViewById(R.id.textcrequire);
            dead=itemView.findViewById(R.id.textcdeadline);
            contact=itemView.findViewById(R.id.textcphone);
            location=itemView.findViewById(R.id.textclocation);
            applyby=itemView.findViewById(R.id.textcapplied);
            title=itemView.findViewById(R.id.textctitle);
            salary=itemView.findViewById(R.id.textcsalary);
            category=itemView.findViewById(R.id.textccategory);
            type=itemView.findViewById(R.id.texttype);
            apply=itemView.findViewById(R.id.apply);
            more=itemView.findViewById(R.id.more);
            empuid=itemView.findViewById(R.id.empUid);
            linear=itemView.findViewById(R.id.linear);
            relative=itemView.findViewById(R.id.expandable);



        }
    }
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    /*public availableadapter(@NonNull FirebaseRecyclerOptions<postjobhelper> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyviewHolder holder, int position, @NonNull postjobhelper model) {
        holder.cName.setText(model.getCompany());
        holder.jdescr.setText(model.getDescription());
        holder.jreq.setText(model.getRequirements());
        holder.dead.setText(model.getClosing());
        holder.contact.setText(model.getPhone());
        holder.location.setText(model.getLocation());
        holder.applyby.setText(model.getAppliedby());

       




        holder.apply.setOnClickListener(new View.OnClickListener() {
            FirebaseUser user;
            DatabaseReference reference,reference2;
            String userid;
            AutoCompleteTextView jobcategory,educationlevel;
            EditText graduate,skills;
            FirebaseDatabase rootNode,rootnode2;

            RadioGroup genderk;
            RadioButton male,female;
            Button update;

            DatePickerDialog.OnDateSetListener setListener;
            @Override
            public void onClick(View view) {

                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.contact.getContext())
                        .setContentHolder(new ViewHolder(R.layout.profileapply))
                        .setExpanded(true, 1000)
                        .setGravity(Gravity.TOP)
                        .create();
                View v = dialogPlus.getHolderView();
                dialogPlus.show();

                jobcategory=v.findViewById(R.id.job_categoryA);
                educationlevel=v.findViewById(R.id.educationA);
                graduate=v.findViewById(R.id.date_pickerA);
                skills=v.findViewById(R.id.skillsA);
                genderk=(RadioGroup) v.findViewById(R.id.gender);
                male=v.findViewById(R.id.radioButton);
                female=v.findViewById(R.id.radioButton2);
                update=v.findViewById(R.id.pupdateA);

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
                                v.getContext(),android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                                setListener,Year,month,Day);
                        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        datePickerDialog.show();
                    }
                });
                setListener=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month=month+1;
                        String date=Day+"/"+month+"/"+Year;
                        graduate.setText(date);
                    }
                };

                String[] jobcategpry= {"part-time","full-time"};

                ArrayAdapter<String> ss = new ArrayAdapter<String>(v.getContext(),android.R.layout.simple_spinner_dropdown_item,jobcategpry);
                ss.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                jobcategory.setAdapter(ss);

                jobcategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String location2=adapterView.getItemAtPosition(i).toString();

                    }
                });

                String[] education= {"DIPLOMA","DEGREE","MASTERS","PHD"};

                ArrayAdapter<String> pp = new ArrayAdapter<String>(v.getContext(),R.layout.dropdown,education);
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

                final TextView emailTextview = (EditText) v.findViewById(R.id.mEmailA);
                final TextView fullTextview = (EditText) v.findViewById(R.id.mFullNameA);
                final TextView phoneTextview = (EditText) v.findViewById(R.id.mphoneA);
                final TextView idTextview = (EditText) v.findViewById(R.id.id_noA);


                reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        userhelper applicantsprofile = snapshot.getValue(userhelper.class);
                        if (applicantsprofile != null) {
                            String email = applicantsprofile.Email;
                            String full = applicantsprofile.fullname;
                            String phone = applicantsprofile.phone;
                            String id = applicantsprofile.idnumber;

                            fullTextview.setText(full);
                            idTextview.setText(id);
                            emailTextview.setText(email);
                            phoneTextview.setText(phone);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(v.getContext(), "something wrong happened", Toast.LENGTH_SHORT).show();
                    }
                });
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        FirebaseDatabase.getInstance().getReference("applicants profile");
                        rootNode = FirebaseDatabase.getInstance();
                        reference = rootNode.getReference("applicants profile ");

                        String fullname=fullTextview.getText().toString().trim();
                        String email=emailTextview.getText().toString().trim();
                        String phone = phoneTextview.getText().toString().trim();
                        String grad=graduate.getText().toString().trim();
                        String jobcat=jobcategory.getText().toString().trim();
                        String educ=educationlevel.getText().toString().trim();
                        String skil=skills.getText().toString().trim();
                        String id=idTextview.getText().toString().trim();
                        String m1=male.getText().toString().trim();
                        String m2=female.getText().toString().trim();

                        final int min=100007;
                        final int max=200007;
                        final String random=new Random().nextInt(+(max-min)+1)+"COM"+min+1+"KENY";

                        if (male.isChecked()){
                            applicantsprofile applicantsprofile=new applicantsprofile(id,fullname, email, skil, phone,grad,jobcat,educ,m1);
                            reference.setValue(applicantsprofile);
                            Toast.makeText(v.getContext(), "updated successfully", Toast.LENGTH_SHORT).show();
                            return;
                        }else {
                            applicantsprofile applicantsprofile=new applicantsprofile(id,fullname, email, skil, phone,grad,jobcat,educ,m2);
                            reference.setValue(applicantsprofile);
                            Toast.makeText(v.getContext(), "updated successfully", Toast.LENGTH_SHORT).show();
                        }


                        Toast.makeText(v.getContext(), "updated successfully", Toast.LENGTH_SHORT).show();

                        Map<String,Object> map=new HashMap<>();
                        map.put("appliedby",phone);
                        map.put("status","pending");
                        map.put("batchno",random);
                        map.put("applicantsid",userid);

                        FirebaseDatabase.getInstance().getReference("job post")
                                .child(Objects.requireNonNull(getRef(holder.getAdapterPosition()).getKey())).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(v.getContext(), "job applied  successfully", Toast.LENGTH_SHORT).show();
                                        //dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(v.getContext(), "Error occurred while updating try again", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
            }
        });
    }

    /*@Override
    protected void onBindViewHolder(@NonNull MyviewHolder holder, int position, @NonNull postjobhelper model) {

        holder.cName.setText(model.getCompany());
        holder.jdescr.setText(model.getDescription());
        holder.jreq.setText(model.getRequirements());
        holder.dead.setText(model.getClosing());
        holder.contact.setText(model.getPhone());
        holder.location.setText(model.getLocation());

    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.jobsavailable,parent,false);
        return  new MyviewHolder(view);
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.jobsavailable,parent,false);
        return  new MyviewHolder(view);
    }

    class MyviewHolder extends RecyclerView.ViewHolder{
        TextView cName,jdescr,jreq,dead,contact,location,applyby;
        Button apply;
        LinearLayout linear;
        RelativeLayout relative;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            cName=itemView.findViewById(R.id.textcname);
            jdescr=itemView.findViewById(R.id.textcdescr);
            jreq=itemView.findViewById(R.id.textcrequire);
            dead=itemView.findViewById(R.id.textcdeadline);
            contact=itemView.findViewById(R.id.textcphone);
            location=itemView.findViewById(R.id.textclocation);
            applyby=itemView.findViewById(R.id.textcapplied);
            apply=itemView.findViewById(R.id.apply);

            linear=itemView.findViewById(R.id.linear);
            relative=itemView.findViewById(R.id.expandable);



        }
    }*/
}
