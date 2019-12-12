package com.tech4lyf.absolutesolutionscrm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tech4lyf.absolutesolutionscrm.Models.Machine;
import com.tech4lyf.absolutesolutionscrm.Models.ScheduledWorkList;

import java.util.List;

import javax.crypto.Mac;

public abstract class RVAdapterRO extends RecyclerView.Adapter<RVAdapterRO.ViewHolder> {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private List<Machine> macList;
    private Context context;

    public RVAdapterRO(List<Machine> list, Context ctx) {
        macList = list;
        context = ctx;
    }

    @Override
    public int getItemCount() {
        return macList.size();
    }

    @Override
    public RVAdapterRO.ViewHolder
    onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_sw, parent, false);

        RVAdapterRO.ViewHolder viewHolder =
                new RVAdapterRO.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RVAdapterRO.ViewHolder holder, int position) {
        final int itemPos = position;
        final Machine machine = macList.get(position);
        holder.macName.setText(machine.getMachName());

        /*if(machine.getMacStatus().equals("true"))
        {
            holder.status.setChecked(true);
        }
        else
        {
            holder.status.setChecked(false);
        }*/

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView macName;

        public CheckBox macStatus;
        /*public Button edit;
        public Button delete;*/

        public ViewHolder(View view) {
            super(view);

            macName = (TextView) view.findViewById(R.id.tvMachName);
            macStatus=(CheckBox)view.findViewById(R.id.cbMach);

            macStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    //Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show();

                    }

            });
            //phone = (TextView) view.findViewById(R.id.phone_i);
            /*edit = view.findViewById(R.id.edit_ad_b);
            delete = view.findViewById(R.id.delete_ad_b);*/
        }
    }

}