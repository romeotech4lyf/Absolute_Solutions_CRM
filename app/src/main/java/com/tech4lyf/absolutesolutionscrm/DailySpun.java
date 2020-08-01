package com.tech4lyf.absolutesolutionscrm;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tech4lyf.absolutesolutionscrm.Models.ServiceEntryModel;
import com.tech4lyf.absolutesolutionscrm.ui.RVAdapterInline;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;


public class DailySpun extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private InlineChange.OnFragmentInteractionListener mListener;

    public DailySpun() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inline_change, container, false);
    }

    private RecyclerView recyclerView;

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InlineChange.OnFragmentInteractionListener) {
            mListener = (InlineChange.OnFragmentInteractionListener) context;
        } else {
            //throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private ArrayList<ServiceEntryModel> serviceEntryModels = new ArrayList<>();
    DatabaseReference databaseReference;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.inline_change_list);
        final RVAdapterInline rvAdapterInline = new RVAdapterInline(this.getContext(),serviceEntryModels);
        recyclerView.setAdapter(rvAdapterInline);

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


    }
}
