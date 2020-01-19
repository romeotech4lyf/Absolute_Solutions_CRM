package com.tech4lyf.absolutesolutionscrm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tech4lyf.absolutesolutionscrm.Models.Customer;
import com.tech4lyf.absolutesolutionscrm.Models.ServiceLogModel;

import java.util.List;
import java.util.ServiceLoader;

public class RVAdapterServices extends RecyclerView.Adapter<RVAdapterServices.ViewHolder> {
    private List<ServiceLogModel> adsList;
    private Context context;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public RVAdapterServices() {

    }

    public RVAdapterServices(List<ServiceLogModel> list, Context ctx) {
        adsList = list;
        context = ctx;
    }

    @Override
    public RVAdapterServices.ViewHolder
    onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custservicelog, parent, false);

        RVAdapterServices.ViewHolder viewHolder =
                new RVAdapterServices.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RVAdapterServices.ViewHolder holder, int position) {

        final int itemPos = position;
        String p= Integer.toString(position+1);
        final ServiceLogModel serviceLogModel = adsList.get(position);
        holder.cname.setText(serviceLogModel.getcName());
        holder.date.setText(serviceLogModel.getDate());
        holder.cid.setText(p);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ServiceLogModel serviceLogModel1= adsList.get(itemPos);
                Toast.makeText(context, ""+serviceLogModel.getcName(), Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                LayoutInflater inflater = ((AppCompatActivity)view.getContext()).getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.servdetails,null);

                TextView tvCname=(TextView) dialogView.findViewById(R.id.tvSCname);
                TextView tvDate=(TextView) dialogView.findViewById(R.id.tvSDate);
                TextView tvPhone=(TextView) dialogView.findViewById(R.id.tvSPhone);
                TextView tvTDS=(TextView) dialogView.findViewById(R.id.tvSTDS);
                TextView tvRej=(TextView) dialogView.findViewById(R.id.tvSRej);
                TextView tvParts=(TextView) dialogView.findViewById(R.id.tvSParts);
                TextView tvPrice=(TextView) dialogView.findViewById(R.id.tvsPrice);
                TextView tvHC=(TextView) dialogView.findViewById(R.id.tvsHC);
                TextView tvWD=(TextView) dialogView.findViewById(R.id.tvsWD);
                TextView tvDesc=(TextView) dialogView.findViewById(R.id.tvsDesc);

                ImageView imgSign=(ImageView)dialogView.findViewById(R.id.imgSign);


                tvCname.setText(serviceLogModel.getcName().toString());
                tvDate.setText(serviceLogModel.getDate().toString());
                tvPhone.setText(serviceLogModel.getPhone().toString());
                tvTDS.setText(serviceLogModel.getRaw()+"  A.RO: "+serviceLogModel.getAro().toString());
                tvRej.setText(serviceLogModel.getRejection().toString());
                tvParts.setText(serviceLogModel.getParts().toString());
                tvPrice.setText(serviceLogModel.getPrice().toString());
                tvWD.setText(serviceLogModel.getWorkdone().toString());
                tvHC.setText(serviceLogModel.getHandcash().toString());
                tvDesc.setText(serviceLogModel.getDesc().toString());

                byte[] decodedString = Base64.decode(serviceLogModel.getSign(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,decodedString.length);
                imgSign.setImageBitmap(decodedByte);


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
            date = (TextView) view.findViewById(R.id.SLCDate);
            cname = (TextView) view.findViewById(R.id.SLCName);
            cid = (TextView) view.findViewById(R.id.tvSLID);
        }
    }
}
