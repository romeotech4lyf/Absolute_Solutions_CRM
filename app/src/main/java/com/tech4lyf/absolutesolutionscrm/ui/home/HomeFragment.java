package com.tech4lyf.absolutesolutionscrm.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tech4lyf.absolutesolutionscrm.DailySpun;
import com.tech4lyf.absolutesolutionscrm.Models.ServiceEntryModel;
import com.tech4lyf.absolutesolutionscrm.R;
import com.tech4lyf.absolutesolutionscrm.ServiceLog;
import com.tech4lyf.absolutesolutionscrm.ui.RVAdapterInline;
import com.tech4lyf.absolutesolutionscrm.ui.schedulework.ScheduleWork;
import com.tech4lyf.absolutesolutionscrm.ui.serviceentry.ServiceEntry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class HomeFragment extends Fragment implements ServiceEntry.OnFragmentInteractionListener,ScheduleWork.OnFragmentInteractionListener,ServiceLog.OnFragmentInteractionListener,DailySpun.OnFragmentInteractionListener{

    private HomeViewModel homeViewModel;

    CardView cardServiceEntry,cardScheduleWork,cardServiceLog,cardDailySpun;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private RecyclerView recyclerViewSpun;
    private RecyclerView recyclerViewInline;
    private TextView emptySpun;
    private TextView emptyInline;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        /*final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }else{
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }

        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }else{
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }else{
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }

        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }else{
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }

        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.INTERNET)){
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.INTERNET}, 1);
            }else{
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.INTERNET}, 1);
            }
        }




        cardServiceEntry=(CardView)root.findViewById(R.id.cardServiceEntry);
        cardScheduleWork=(CardView)root.findViewById(R.id.cardScheduleWork);
        cardDailySpun=(CardView)root.findViewById(R.id.cardDailySpun);
        cardServiceLog=(CardView)root.findViewById(R.id.cardServiceLog);
        recyclerViewSpun = root.findViewById(R.id.rv_spun_today);
        recyclerViewInline = root.findViewById(R.id.rv_inline_today);
        emptySpun = root.findViewById(R.id.empty_spun);
        emptyInline = root.findViewById(R.id.empty_inline);
        cardServiceEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment serviceEntry = new ServiceEntry();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, serviceEntry); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });
        cardScheduleWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment scheduleWork = new ScheduleWork();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, scheduleWork); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });

        cardServiceLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment serviceLog = new ServiceLog();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, serviceLog); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });

        cardDailySpun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment dailySpun = new DailySpun();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, dailySpun); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });



        //
        DatabaseReference databaseReference;
        final ArrayList<ServiceEntryModel> serviceEntryModels = new ArrayList<>();
        final RVAdapterInline rvAdapterInline = new RVAdapterInline(this.getContext(),serviceEntryModels);
        recyclerViewSpun.setAdapter(rvAdapterInline);

        databaseReference = FirebaseDatabase.getInstance().getReference("ServiceEntry");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        ServiceEntryModel serviceEntryModel = dataSnapshot1.getValue(ServiceEntryModel.class);
                        try {
                            if(serviceEntryModel.getParts().contains("SPUN") ){
                                if(TimeUnit.DAYS.
                                        convert(Math.abs((Calendar.getInstance().getTime()).getTime())
                                                - (new SimpleDateFormat("dd-MM-yyyy").parse(serviceEntryModel.getDate())).getTime(),TimeUnit.MILLISECONDS)==90) {
                                    serviceEntryModels.add(serviceEntryModel);
                                    emptySpun.setVisibility(View.GONE);

                                }
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    rvAdapterInline.setServiceEntryModels(serviceEntryModels);
                    rvAdapterInline.notifyDataSetChanged();

                }
                Log.d("jhhh",serviceEntryModels.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //
        DatabaseReference databaseReference1;
        final ArrayList<ServiceEntryModel> serviceEntryModels1 = new ArrayList<>();
        final RVAdapterInline rvAdapterInline1 = new RVAdapterInline(this.getContext(),serviceEntryModels1);
        recyclerViewInline.setAdapter(rvAdapterInline);

        databaseReference1 = FirebaseDatabase.getInstance().getReference("ServiceEntry");
        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        ServiceEntryModel serviceEntryModel = dataSnapshot1.getValue(ServiceEntryModel.class);
                        try {
                            if(serviceEntryModel.getParts().contains("INLINE") || serviceEntryModel.getParts().contains("CARBON") || serviceEntryModel.getParts().contains("SEDIMENT")||serviceEntryModel.getParts().contains("inline")) {
                                if(TimeUnit.DAYS.
                                        convert(Math.abs((Calendar.getInstance().getTime()).getTime())
                                                - (new SimpleDateFormat("dd-MM-yyyy").parse(serviceEntryModel.getDate())).getTime(),TimeUnit.MILLISECONDS)==90) {
                                    serviceEntryModels1.add(serviceEntryModel);
                                    emptyInline.setVisibility(View.GONE);

                                }
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    rvAdapterInline1.setServiceEntryModels(serviceEntryModels1);
                    rvAdapterInline1.notifyDataSetChanged();

                }
                Log.d("jhhh",serviceEntryModels1.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return root;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}