package com.example.jobapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class manageusersadapter extends FirebaseRecyclerAdapter<userhelper,manageusersadapter.MyviewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public manageusersadapter(@NonNull FirebaseRecyclerOptions<userhelper> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyviewHolder holder, int position, @NonNull userhelper model) {

        holder.Name.setText(model.getFullname());
        holder.idno.setText(model.getIdnumber());
        holder.Email.setText(model.getEmail());
        holder.Password.setText(model.getPassword());
        holder.phoneno.setText(model.getPhone());
        holder.usertype.setText(String.valueOf(model.getUsertype()));


        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.Name.getContext())
                        .setContentHolder(new ViewHolder(R.layout.adminchangepass))
                        .setExpanded(true, 1000)
                        .setGravity(Gravity.TOP)
                        .create();



                View v = dialogPlus.getHolderView();

                EditText fullname = v.findViewById(R.id.txtfullnameA);
                EditText idno=v.findViewById(R.id.txtidnoA);
                EditText pass = v.findViewById(R.id.txtspassA);
                EditText phone = v.findViewById(R.id.txtphoneA);
                EditText user = v.findViewById(R.id.txtuserA);

                Button update=v.findViewById(R.id.btnupdateA);

                fullname.setText(model.getFullname());
                idno.setText(model.getIdnumber());
                pass.setText(model.getPassword());
                phone.setText(model.getPhone());
                user.setText(String.valueOf(model.getUsertype()));

                dialogPlus.show();

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String pass1=pass.getText().toString();
                        FirebaseUser user1= FirebaseAuth.getInstance().getCurrentUser();
                        user1.updatePassword(pass1).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                Toast.makeText(v.getContext(), "user updated successfully", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(v.getContext(), "failed to activate users profile", Toast.LENGTH_SHORT).show();
                            }
                        });

                        Map<String,Object> map=new HashMap<>();
                        map.put("phone",phone.getText().toString());
                        map.put("fullname",fullname.getText().toString());
                        map.put("password",pass.getText().toString());




                        FirebaseDatabase.getInstance().getReference("users")
                                .child(Objects.requireNonNull(getRef(holder.getAdapterPosition()).getKey())).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.usertype.getContext(), "profile  updated successfully", Toast.LENGTH_SHORT).show();
                                        //dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.usertype.getContext(), "Error occurred while updating try again", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
            }
        });
        //holder.confirm.setText(model.getConfirm());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.Name.getContext());
                builder.setTitle("Are you sure ?");
                builder.setMessage("Deleted data can't be undo");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference("users").child(getRef(holder.getAdapterPosition()).getKey()).removeValue();
                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.Name.getContext(), "cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.manageusers,parent,false);
        return  new MyviewHolder(view);
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView Name,idno,Email,Password,phoneno,usertype,confirm;
        Button delete,edit;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            Name=itemView.findViewById(R.id.textname);
            idno=itemView.findViewById(R.id.textidno);
            Email=itemView.findViewById(R.id.textemail);
            Password=itemView.findViewById(R.id.textpassword);
            phoneno=itemView.findViewById(R.id.textphone);
            usertype=itemView.findViewById(R.id.textusertype);

            delete=itemView.findViewById(R.id.delete);
            edit=itemView.findViewById(R.id.edit);
        }
    }
}
