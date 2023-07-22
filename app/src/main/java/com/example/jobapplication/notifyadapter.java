package com.example.jobapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class notifyadapter extends ArrayAdapter {
    private Activity context;
    List<notifyfirebase> notifyfirebases;

    public notifyadapter(Activity context, List<notifyfirebase> notifyfirebases) {
        super(context, R.layout.notifyme, notifyfirebases);
        this.context = context;
        this.notifyfirebases = notifyfirebases;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listItem = inflater.inflate(R.layout.notifyme, null, true);

        TextView idno = listItem.findViewById(R.id.messageN);


        notifyfirebase cmm = notifyfirebases.get(position);

        idno.setText(cmm.getMessage());


        return listItem;

    }
}