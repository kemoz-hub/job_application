package com.example.jobapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.jobapplication.databinding.ActivityAboutBinding;
import com.example.jobapplication.databinding.ActivityCompanyBinding;

public class About extends Drawerbase {
    ActivityAboutBinding activityAboutBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAboutBinding = ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(activityAboutBinding.getRoot());
        allocateActivityTitle("About");
    }
}