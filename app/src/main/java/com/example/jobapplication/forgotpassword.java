package com.example.jobapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotpassword extends AppCompatActivity {

    FirebaseAuth fAuth;
    ProgressBar progressBar;
    EditText email;
    Button reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        email=findViewById(R.id.REmail);
        reset=findViewById(R.id.Rbutn);
        progressBar=findViewById(R.id.progressBarR);

        fAuth= FirebaseAuth.getInstance();
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetpassword();
            }
        });

    }

    private void resetpassword() {
        String Email=email.getText().toString().trim();

        if(Email.isEmpty()){
            email.setError("email is required");
            email.requestFocus();
            return;
        }

       /* if(Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            email.setError("please provide valid email");
            email.requestFocus();
            return;
        }*/
        progressBar.setVisibility(View.VISIBLE);
        fAuth.sendPasswordResetEmail(Email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    AlertDialog.Builder builder=new AlertDialog.Builder(forgotpassword.this);
                    builder.setTitle("check a LINK has been sent to your email");
                    builder.setMessage("click and change password");
                    builder.show();
                    Toast.makeText(forgotpassword.this, "check your email to reset your password", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }else{
                    Toast.makeText(forgotpassword.this, "You are not registered in this application", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}