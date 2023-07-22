package com.example.jobapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.example.jobapplication.databinding.ActivityAboutBinding;
import com.example.jobapplication.databinding.ActivityAnalysisBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Analysis extends Drawerbase {

    ArrayList barArraylist;
    ActivityAnalysisBinding analysisBinding;
    DatabaseReference reference;
    TextView economics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        analysisBinding = ActivityAnalysisBinding.inflate(getLayoutInflater());
        setContentView(analysisBinding.getRoot());
        allocateActivityTitle("Analysis");


        BarChart barChart=findViewById(R.id.barchat);
        economics=findViewById(R.id.economics);
        getData();
        BarDataSet barDataSet=new BarDataSet(barArraylist,"job types");
        BarData barData=new BarData(barDataSet);
        barChart.setData(barData);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
       barChart.getDescription().setEnabled(true);

    }
    public void getData(){
        barArraylist=new ArrayList();
        reference= FirebaseDatabase.getInstance().getReference("jobs applied");
        reference.orderByChild("jobcategory").equalTo("economics and statistics").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int counter=(int)snapshot.getChildrenCount();
                String usercount=String.valueOf(counter);
                economics.setText(usercount);

                barArraylist.add(new BarEntry(2f,12));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        barArraylist.add(new BarEntry(3f,18));
        barArraylist.add(new BarEntry(4f,10));
        barArraylist.add(new BarEntry(5f,13));
        barArraylist.add(new BarEntry(6f,16));

    }
}