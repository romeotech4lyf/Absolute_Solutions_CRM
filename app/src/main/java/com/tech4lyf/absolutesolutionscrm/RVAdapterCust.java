package com.tech4lyf.absolutesolutionscrm;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tech4lyf.absolutesolutionscrm.Models.Customer;

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


                TextInputEditText tvName=(TextInputEditText) dialogView.findViewById(R.id.tvName);
                TextInputEditText tvDate=(TextInputEditText) dialogView.findViewById(R.id.tvDate);
                TextInputEditText tvPhone=(TextInputEditText) dialogView.findViewById(R.id.tvPhone);
                TextInputEditText tvRef=(TextInputEditText) dialogView.findViewById(R.id.tvRef);
                TextInputEditText tvLoc=(TextInputEditText) dialogView.findViewById(R.id.tvLoc);
                TextInputEditText tvAddr=(TextInputEditText) dialogView.findViewById(R.id.tvAddr);
                TextInputEditText tvMac=(TextInputEditText) dialogView.findViewById(R.id.tvMach);
                TextInputEditText tvParts=(TextInputEditText) dialogView.findViewById(R.id.tvParts);
                TextInputEditText tvPrice=(TextInputEditText) dialogView.findViewById(R.id.tvPrice);
                TextInputEditText tvHC=(TextInputEditText) dialogView.findViewById(R.id.tvHC);

                ImageView imgMac=(ImageView)dialogView.findViewById(R.id.imgMac);
                ImageView imgCust=(ImageView)dialogView.findViewById(R.id.imgCust);




                tvName.setText(""+customer.getName().toString());
                tvDate.setText(""+customer.getDate().toString());
                tvPhone.setText(""+customer.getPhone().toString());
                tvRef.setText(""+customer.getRef().toString());
                tvLoc.setText(""+customer.getLoc().toString());

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

                tvAddr.setText(""+customer.getAddr().toString());
                tvMac.setText(""+customer.getRomac().toString());
                tvParts.setText(""+customer.getParts().toString());
                tvPrice.setText(""+customer.getPrice().toString());
                tvHC.setText(""+customer.getHandcash().toString());

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
