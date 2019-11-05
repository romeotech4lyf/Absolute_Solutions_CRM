package com.tech4lyf.absolutesolutionscrm.ui.home;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.tech4lyf.absolutesolutionscrm.R;
import com.tech4lyf.absolutesolutionscrm.ui.schedulework.ScheduleWork;
import com.tech4lyf.absolutesolutionscrm.ui.serviceentry.ServiceEntry;

public class HomeFragment extends Fragment implements ServiceEntry.OnFragmentInteractionListener,ScheduleWork.OnFragmentInteractionListener{

    private HomeViewModel homeViewModel;

    CardView cardServiceEntry,cardScheduleWork;

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

        cardServiceEntry=(CardView)root.findViewById(R.id.cardServiceEntry);
        cardScheduleWork=(CardView)root.findViewById(R.id.cardScheduleWork);
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

        return root;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}