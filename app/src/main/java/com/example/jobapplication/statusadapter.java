package com.example.jobapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class statusadapter extends FirebaseRecyclerAdapter<jobsapplied,statusadapter.MyviewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public statusadapter(@NonNull FirebaseRecyclerOptions<jobsapplied> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyviewHolder holder, int position, @NonNull jobsapplied model) {

        holder.cname.setText(model.getCompanyname());
        holder.jdescr.setText(model.getJobcategory());
        holder.phone.setText(model.getPhoneno());
        holder.status.setText(model.getStatus());
        holder.batch.setText(model.getObnumber());

    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.applicantstatus,parent,false);
        return  new MyviewHolder(view);
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView cname,jdescr,phone,status,batch;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            cname=itemView.findViewById(R.id.textcnames);
            jdescr=itemView.findViewById(R.id.textcdescrs);
            phone=itemView.findViewById(R.id.textphones);
            status=itemView.findViewById(R.id.textstatus);
            batch=itemView.findViewById(R.id.textbatchs);

        }
    }
}
