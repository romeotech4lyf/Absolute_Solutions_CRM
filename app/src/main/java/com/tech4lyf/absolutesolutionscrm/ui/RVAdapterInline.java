package com.tech4lyf.absolutesolutionscrm.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tech4lyf.absolutesolutionscrm.Models.ServiceEntryModel;
import com.tech4lyf.absolutesolutionscrm.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RVAdapterInline  extends RecyclerView.Adapter<RVAdapterInline.ViewHolder>{
    public RVAdapterInline(Context context, ArrayList<ServiceEntryModel> serviceEntryModels) {
        this.context = context;
        this.serviceEntryModels = serviceEntryModels;
    }

    private Context context;
    private ArrayList<ServiceEntryModel> serviceEntryModels;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<ServiceEntryModel> getServiceEntryModels() {
        return serviceEntryModels;
    }

    public void setServiceEntryModels(ArrayList<ServiceEntryModel> serviceEntryModels) {
        this.serviceEntryModels = serviceEntryModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.custservicelog,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ServiceEntryModel serviceEntryModel = serviceEntryModels.get(position);
        holder.name.setText(serviceEntryModel.getcName());
        holder.id.setText(serviceEntryModel.getParts());
        holder.date.setText(serviceEntryModel.getDate());

        /*try {
            if (TimeUnit.DAYS.
                    convert(Math.abs((Calendar.getInstance().getTime()).getTime()) - (new SimpleDateFormat("dd-MM-yyyy").parse(serviceEntryModel.getDate())).getTime(),TimeUnit.MILLISECONDS)==68){
                holder.slc.setBackgroundColor(Color.GREEN);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
            holder.slc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


    }

    @Override
    public int getItemCount() {
        return serviceEntryModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView id;
        TextView name;
        TextView date;
        LinearLayout slc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            slc = itemView.findViewById(R.id.SLC);
            id = itemView.findViewById(R.id.tvSLID);
            name = itemView.findViewById(R.id.SLCName);
            date = itemView.findViewById(R.id.SLCDate);
        }
    }

}
