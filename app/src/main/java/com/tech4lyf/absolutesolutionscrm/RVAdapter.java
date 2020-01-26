package com.tech4lyf.absolutesolutionscrm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tech4lyf.absolutesolutionscrm.Models.ScheduledWorkList;
import com.tech4lyf.absolutesolutionscrm.ui.serviceentry.ServiceEntry;


import java.util.List;

public class RVAdapter extends
        RecyclerView.Adapter<RVAdapter.ViewHolder> {

    private List<ScheduledWorkList> adsList;
    private Context context;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public RVAdapter()
    {

    }

    public RVAdapter(List<ScheduledWorkList> list, Context ctx) {
        adsList = list;
        context = ctx;
    }
    @Override
    public int getItemCount() {
        return adsList.size();
    }

    @Override
    public RVAdapter.ViewHolder
    onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_sw, parent, false);

        RVAdapter.ViewHolder viewHolder =
                new RVAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RVAdapter.ViewHolder holder, int position) {
        final int itemPos = position;
        final int p1=position;
        String p= Integer.toString(position+1);
        final ScheduledWorkList classifiedAd = adsList.get(position);
        holder.title.setText(classifiedAd.getTitle());
        holder.date.setText(classifiedAd.getDate());
        holder.sno.setText(p);

        if(classifiedAd.getStatus().equals("true"))
        {
            holder.status.setChecked(true);
        }
        else
        {
            holder.status.setChecked(false);
        }
        //holder.phone.setText(classifiedAd.getPhone());

        /*holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editClassifiedAd(classifiedAd.getAdId());
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteClassifiedAd(classifiedAd.getAdId(), itemPos);
            }
        });*/

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "Hi", Toast.LENGTH_SHORT).show();
                AlertDialog alertDialog=new AlertDialog.Builder(context).create();
                alertDialog.setTitle("Schedule Work");
                alertDialog.setMessage(classifiedAd.getTitle());
                alertDialog.setButton(Dialog.BUTTON_POSITIVE,"Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                        Query applesQuery = ref.child("ScheduledWork").orderByChild("key").equalTo(classifiedAd.getKey());

                        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                    appleSnapshot.getRef().removeValue();



                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.e("Hello", "onCancelled", databaseError.toException());

                            }
                        });

                                notifyItemRemoved(p1);
                    }
                });

                alertDialog.setButton(Dialog.BUTTON_NEGATIVE,"Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


                alertDialog.show();

                        /*
                        AlertDialog alertDialog = new AlertDialog.Builder(<YourActivityName>this).create(); //Read Update
        alertDialog.setTitle("hi");
        alertDialog.setMessage("this is my app");

        alertDialog.setButton("Continue..", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int which) {
              // here you can add functions
           }
        });

        alertDialog.show();  //<-- See This!
    }
                        * */
            }
        });
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView date;
        public TextView sno;
        public CheckBox status;
        /*public Button edit;
        public Button delete;*/

        public ViewHolder(View view) {
            super(view);
            date = (TextView) view.findViewById(R.id.tvDate);
            title = (TextView) view.findViewById(R.id.tvTitle);
            sno = (TextView) view.findViewById(R.id.tvSno);
            status=(CheckBox)view.findViewById(R.id.cbStatus);

            status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    //Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show();
                    if(b)
                    {
                        try {
                            String _key="";

                            firebaseDatabase=FirebaseDatabase.getInstance();
                            /*databaseReference=firebaseDatabase.getReference().child("ScheduledWork").child()
                            databaseReference.child("myDb").child("awais@gmailcom").child("leftSpace").setValue("YourDateHere");*/

                            int pos=getAdapterPosition();
                            ScheduledWorkList item=adsList.get(pos);

                            _key=item.getKey();
                            //Toast.makeText(context, "hello "+item.getTitle()+item.getKey(), Toast.LENGTH_SHORT).show();
                            /*databaseReference=firebaseDatabase.getReference().child("ScheduledWork").child(_key);
                            databaseReference.child(_key).child("status").setValue("true");*/
                            firebaseDatabase.getInstance().getReference().child("ScheduledWork").child(_key).child("status").setValue("true");


                            /*databaseReference.orderByChild("title").equalTo(item.getTitle()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        ///Here you can get all data

                                        String parent = dataSnapshot.getKey();

                                        databaseReference = firebaseDatabase.getReference();
                                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                                                    String parent = childSnapshot.getKey();
                                                    Toast.makeText(context, parent, Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {}
                                });*/

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    else
                    {
                        try {
                            String _key="";

                            firebaseDatabase=FirebaseDatabase.getInstance();
                            /*databaseReference=firebaseDatabase.getReference().child("ScheduledWork").child()
                            databaseReference.child("myDb").child("awais@gmailcom").child("leftSpace").setValue("YourDateHere");*/

                            int pos=getAdapterPosition();
                            ScheduledWorkList item=adsList.get(pos);

                            _key=item.getKey();
                            //Toast.makeText(context, "hello "+item.getTitle()+item.getKey(), Toast.LENGTH_SHORT).show();
                            /*databaseReference=firebaseDatabase.getReference().child("ScheduledWork").child(_key);
                            databaseReference.child(_key).child("status").setValue("true");*/
                            firebaseDatabase.getInstance().getReference().child("ScheduledWork").child(_key).child("status").setValue("false");


                            /*databaseReference.orderByChild("title").equalTo(item.getTitle()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        ///Here you can get all data

                                        String parent = dataSnapshot.getKey();

                                        databaseReference = firebaseDatabase.getReference();
                                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                                                    String parent = childSnapshot.getKey();
                                                    Toast.makeText(context, parent, Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {}
                                });*/

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            //phone = (TextView) view.findViewById(R.id.phone_i);
            /*edit = view.findViewById(R.id.edit_ad_b);
            delete = view.findViewById(R.id.delete_ad_b);*/
        }
    }
   /* private void editClassifiedAd(String adId){
        FragmentManager fm = ((MainActivity)context).getSupportFragmentManager();

        Bundle bundle=new Bundle();
        bundle.putString("adId", adId);

        AddClassifiedFragment addFragment = new AddClassifiedFragment();
        addFragment.setArguments(bundle);

        fm.beginTransaction().replace(R.id.adds_frame, addFragment).commit();
    }
    private void deleteClassifiedAd(String adId, final int position){
        FirebaseDatabase.getInstance().getReference()
                .child("classified").child(adId).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //remove item from list alos and refresh recyclerview
                            adsList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, adsList.size());

                            Log.d("Delete Ad", "Classified has been deleted");
                            Toast.makeText(context,
                                    "Classified has been deleted",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("Delete Ad", "Classified couldn't be deleted");
                            Toast.makeText(context,
                                    "Classified could not be deleted",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }*/
}

