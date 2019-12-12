package com.tech4lyf.absolutesolutionscrm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tech4lyf.absolutesolutionscrm.Models.Customer;
import com.tech4lyf.absolutesolutionscrm.Models.ScheduledWorkList;

import java.util.List;

public class RVAdapterCust extends RecyclerView.Adapter<RVAdapterCust.ViewHolder> {
    private List<Customer> adsList;
    private Context context;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public RVAdapterCust() {

    }

    public RVAdapterCust(List<Customer> list, Context ctx) {
        adsList = list;
        context = ctx;
    }

    @Override
    public RVAdapterCust.ViewHolder
    onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_oldcust, parent, false);

        RVAdapterCust.ViewHolder viewHolder =
                new RVAdapterCust.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final int itemPos = position;
        final Customer cust = adsList.get(position);
        holder.cname.setText(cust.getName());
        holder.date.setText(cust.getDate());
        holder.cid.setText(cust.getId());


    }

    @Override
    public int getItemCount() {
        return adsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView cname;
        public TextView date;
        public TextView cid;
        /*public Button edit;
        public Button delete;*/

        public ViewHolder(View view) {
            super(view);
            date = (TextView) view.findViewById(R.id.tvCDate);
            cname = (TextView) view.findViewById(R.id.tvCName);
            cid = (TextView) view.findViewById(R.id.tvCID);
        }
    }
}
