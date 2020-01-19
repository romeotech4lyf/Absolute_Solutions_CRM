package com.tech4lyf.absolutesolutionscrm;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tech4lyf.absolutesolutionscrm.Models.Customer;
import com.tech4lyf.absolutesolutionscrm.Models.ScheduledWorkList;
import com.tech4lyf.absolutesolutionscrm.ui.newcustomer.NewCustomer;

import java.util.List;
import java.util.logging.Logger;

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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final int itemPos = position;

        String p= Integer.toString(position+1);
        final Customer cust = adsList.get(position);
        holder.cname.setText(cust.getName());
        holder.date.setText(cust.getDate());
        holder.cid.setText(p);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Customer customer= adsList.get(position);
                Toast.makeText(context, ""+customer.getName(), Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                LayoutInflater inflater = ((AppCompatActivity)view.getContext()).getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.custdetails,null);


                TextView tvName=(TextView) dialogView.findViewById(R.id.tvName);
                TextView tvDate=(TextView) dialogView.findViewById(R.id.tvDate);
                TextView tvPhone=(TextView) dialogView.findViewById(R.id.tvPhone);
                TextView tvRef=(TextView) dialogView.findViewById(R.id.tvRef);
                TextView tvLoc=(TextView) dialogView.findViewById(R.id.tvLoc);
                TextView tvAddr=(TextView) dialogView.findViewById(R.id.tvAddr);
                TextView tvMac=(TextView) dialogView.findViewById(R.id.tvMach);
                TextView tvParts=(TextView) dialogView.findViewById(R.id.tvParts);
                TextView tvPrice=(TextView) dialogView.findViewById(R.id.tvPrice);
                TextView tvHC=(TextView) dialogView.findViewById(R.id.tvHC);

                ImageView imgMac=(ImageView)dialogView.findViewById(R.id.imgMac);
                ImageView imgCust=(ImageView)dialogView.findViewById(R.id.imgCust);

                tvName.setText("Name          "+customer.getName().toString());
                tvDate.setText("Date            "+customer.getDate().toString());
                tvPhone.setText("Phone         "+customer.getPhone().toString());
                tvRef.setText("Reference  "+customer.getRef().toString());
                tvLoc.setText("Location    "+customer.getLoc().toString());

                tvLoc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(dialogView.getContext(), "Hello", Toast.LENGTH_SHORT).show();
                        String strUri = "http://maps.google.com/maps?q=loc:" + customer.getLoc()+" (" + "#Tech4LYF" + ")";
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));

                        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

                        dialogView.getContext().startActivity(intent);
                    }
                });

                tvAddr.setText("Address     "+customer.getAddr().toString());
                tvMac.setText("Machine     "+customer.getRomac().toString());
                tvParts.setText("Parts          "+customer.getParts().toString());
                tvPrice.setText("Price          "+customer.getPrice().toString());
                tvHC.setText("Hand Cash "+customer.getHandcash().toString());

                byte[] decodedString = Base64.decode(customer.getMacpic(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,decodedString.length);
                imgMac.setImageBitmap(decodedByte);

                byte[] decodedString1 = Base64.decode(customer.getCustomerpic(), Base64.DEFAULT);
                Bitmap decodedByte1 = BitmapFactory.decodeByteArray(decodedString1, 0,decodedString1.length);
                imgCust.setImageBitmap(decodedByte1);



                // Set the custom layout as alert dialog view
                builder.setView(dialogView);
                // Get the custom alert dialog view widgets reference

                // Create the alert dialog
                final AlertDialog dialog = builder.create();
                // Set positive/yes button click listener

                // Display the custom alert dialog on interface
                dialog.show();


            }
        });




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
