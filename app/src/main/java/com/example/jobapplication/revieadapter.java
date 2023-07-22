package com.example.jobapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class revieadapter extends  FirebaseRecyclerAdapter<jobsapplied,revieadapter.MyviewHolder> {
PDFView pdfView;

    public revieadapter(@NonNull FirebaseRecyclerOptions<jobsapplied> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyviewHolder holder, int i, @NonNull jobsapplied model) {
        holder.cname.setText(model.getCompanyname());
        holder.jdescr.setText(model.getJobcategory());
        holder.phone.setText(model.getPhoneno());
        holder.status.setText(model.getStatus());
        holder.batch.setText(model.getObnumber());
        holder.fulln.setText(model.getFullname());
        holder.skills.setText(model.getSkills());
        holder.idnu.setText(model.getIdnumber());
        holder.grad.setText(model.getDategraduated());
        holder.cover.setText(model.getCoverletter());
        holder.educat.setText(model.getEducation());
        holder.gender.setText(model.getGender());

        holder.viewpk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.fulln.getContext())
                        .setContentHolder(new ViewHolder(R.layout.viewprofile))
                        .setExpanded(false, 1000)
                        .setGravity(Gravity.CENTER)
                        .create();

                View v = dialogPlus.getHolderView();


                TextView skills=v.findViewById(R.id.Rtextskills);

                pdfView=v.findViewById(R.id.pdf_view);

                skills.setText(model.getCoverletter());



                String url=skills.getText().toString();

                new Retrievepdfstream().execute(url);



                dialogPlus.show();
            }
        });

        holder.review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.status.getContext())
                        .setContentHolder(new ViewHolder(R.layout.updatestatus))
                        .setExpanded(false, 1000)
                        .setGravity(Gravity.CENTER)
                        .create();



                View v = dialogPlus.getHolderView();
                EditText phone = v.findViewById(R.id.txtphoneU);
                EditText name1=v.findViewById(R.id.txtcnameU);
                EditText batch = v.findViewById(R.id.txtbatchU);
                TextView status2=v.findViewById(R.id.txtstatusU2);
                Spinner status = v.findViewById(R.id.txtstatusU);
                Button btn=v.findViewById(R.id.btnupdate);

                phone.setText(model.getPhoneno());
                batch.setText(model.getObnumber());
                status2.setText(model.getStatus());
                name1.setText(model.getCompanyname());

                String[] statusk={"Received","Reviewed","shortlisted"};

                ArrayAdapter<String> aa=new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_item,statusk);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                status.setAdapter(aa);
                status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(v.getContext(), statusk[i],Toast.LENGTH_LONG).show();
                        String text=status.getSelectedItem().toString();
                        status2.setText(text);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                dialogPlus.show();


                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("status",status2.getText().toString());

                        String phonek = phone.getText().toString();
                        String mess = status2.getText().toString();
                        String batchno=batch.getText().toString();
                        String name=name1.getText().toString();

                        if(mess=="Received"||mess=="Reviewed") {

                            try {
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(phonek, null, "Dear applicant,  the position applied for, in  " + name + "company  of batch no  " + batchno + ", has  been " + mess + "", null, null);
                                Toast.makeText(v.getContext(), "SMS sent successfully", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {

                                Toast.makeText(v.getContext(), "please enter phone and message", Toast.LENGTH_SHORT).show();
                            }

                        }else

                            try {
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(phonek, null, "Dear applicant,congratulations  the position applied for, in  " + name + "company  of batch no  " + batchno + " is successful.  You have been " + mess + "", null, null);
                                Toast.makeText(v.getContext(), "SMS sent successfully", Toast.LENGTH_SHORT).show();
                            } catch (Exception e)
                            {

                                Toast.makeText(v.getContext(), "please enter phone and message", Toast.LENGTH_SHORT).show();
                            }



                        FirebaseDatabase.getInstance().getReference("jobs applied")
                                .child(Objects.requireNonNull(getRef(holder.getAdapterPosition()).getKey())).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.status.getContext(), "status  updated successfully", Toast.LENGTH_SHORT).show();
                                        //dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.status.getContext(), "Error occurred while updating try again", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });


            }
        });
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.reviewjob,parent,false);
        return  new MyviewHolder(view);
    }

    public class MyviewHolder extends RecyclerView.ViewHolder  {
        TextView cname,jdescr,phone,status,batch,cover,grad,skills,fulln,idnu,educat,gender;
        Button review,viewpk;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            cname=itemView.findViewById(R.id.textcnameR);
            jdescr=itemView.findViewById(R.id.textcdescrR);
            phone=itemView.findViewById(R.id.textphoneR);
            status=itemView.findViewById(R.id.textstatusR);
            batch=itemView.findViewById(R.id.textbatchR);
            cover=itemView.findViewById(R.id.textcoverR);
            grad=itemView.findViewById(R.id.textgradR);
            skills=itemView.findViewById(R.id.textskillsR);
            fulln=itemView.findViewById(R.id.textfullnameR);
            idnu=itemView.findViewById(R.id.textidnoR);
            educat=itemView.findViewById(R.id.texteduclevelR);
            gender=itemView.findViewById(R.id.textgenderR);




            review=itemView.findViewById(R.id.Review);
            viewpk=itemView.findViewById(R.id.viewP);

        }
    }

    private class Retrievepdfstream extends AsyncTask<String,Void, InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream=null;
            try {
                URL uRl=new URL(strings[0]);
                HttpURLConnection urlconnection=(HttpURLConnection) uRl.openConnection();
                        if(urlconnection.getResponseCode()==200)
                        {
                            inputStream=new BufferedInputStream(urlconnection.getInputStream());

                        }
            }catch(IOException e){
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream).load();
        }
    }

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
   /* public revieadapter(@NonNull FirebaseRecyclerOptions<postjobhelper> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull revieadapter.MyviewHolder holder, int position, @NonNull postjobhelper model) {

        holder.cname.setText(model.getCompany());
        holder.jdescr.setText(model.getDescription());
        holder.phone.setText(model.getAppliedby());
        holder.status.setText(model.getStatus());
        holder.batch.setText(model.getBatchno());

        holder.review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.status.getContext())
                        .setContentHolder(new ViewHolder(R.layout.updatestatus))
                        .setExpanded(true, 1000)
                        .setGravity(Gravity.TOP)
                        .create();



                View v = dialogPlus.getHolderView();

                EditText phone = v.findViewById(R.id.txtphoneU);
                EditText name1=v.findViewById(R.id.txtcnameU);
                EditText batch = v.findViewById(R.id.txtbatchU);
                TextView status2=v.findViewById(R.id.txtstatusU2);
                Spinner status = v.findViewById(R.id.txtstatusU);
                Button btn=v.findViewById(R.id.btnupdate);

                phone.setText(model.getAppliedby());
                batch.setText(String.valueOf(model.getBatchno()));
                status2.setText(model.getStatus());
                name1.setText(model.getCompany());

                String[] statusk={"Received","Reviewed","shortlisted"};

                ArrayAdapter<String> aa=new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_item,statusk);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                status.setAdapter(aa);
                status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(v.getContext(), statusk[i],Toast.LENGTH_LONG).show();
                        String text=status.getSelectedItem().toString();
                        status2.setText(text);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                dialogPlus.show();


                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("status",status2.getText().toString());



                        String phonek = phone.getText().toString();
                        String mess = status2.getText().toString();
                        String batchno=batch.getText().toString();
                        String name=name1.getText().toString();

                        if(mess=="Received"||mess=="Reviewed") {


                            try {
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(phonek, null, "Dear applicant,  the position applied for, in  " + name + "company  of batch no  " + batchno + ", has  been " + mess + "", null, null);
                                Toast.makeText(v.getContext(), "SMS sent successfully", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {

                                Toast.makeText(v.getContext(), "please enter phone and message", Toast.LENGTH_SHORT).show();
                            }

                        }else

                            try {
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(phonek, null, "Dear applicant,congratulations  the position applied for, in  " + name + "company  of batch no  " + batchno + " is successful.  You have been " + mess + "", null, null);
                                Toast.makeText(v.getContext(), "SMS sent successfully", Toast.LENGTH_SHORT).show();
                            } catch (Exception e)
                            {

                                Toast.makeText(v.getContext(), "please enter phone and message", Toast.LENGTH_SHORT).show();
                            }



                        FirebaseDatabase.getInstance().getReference("job post")
                                .child(Objects.requireNonNull(getRef(holder.getAdapterPosition()).getKey())).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.status.getContext(), "status  updated successfully", Toast.LENGTH_SHORT).show();
                                        //dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.status.getContext(), "Error occurred while updating try again", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });


            }
        });
        holder.viewp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @NonNull
    @Override
    public revieadapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.reviewjob,parent,false);
        return  new MyviewHolder(view);
    }

    public class MyviewHolder extends RecyclerView.ViewHolder  {
        TextView cname,jdescr,phone,status,batch;
        Button review,viewp;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            cname=itemView.findViewById(R.id.textcnameR);
            jdescr=itemView.findViewById(R.id.textcdescrR);
            phone=itemView.findViewById(R.id.textphoneR);
            status=itemView.findViewById(R.id.textstatusR);
            batch=itemView.findViewById(R.id.textbatchR);

            review=itemView.findViewById(R.id.Review);
            viewp=itemView.findViewById(R.id.viewP);
        }
    }*/
}
