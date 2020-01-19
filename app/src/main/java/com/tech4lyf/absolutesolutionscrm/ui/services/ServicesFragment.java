package com.tech4lyf.absolutesolutionscrm.ui.services;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.tech4lyf.absolutesolutionscrm.InlineChange;
import com.tech4lyf.absolutesolutionscrm.R;
import com.tech4lyf.absolutesolutionscrm.ServiceLog;
import com.tech4lyf.absolutesolutionscrm.ui.serviceentry.ServiceEntry;

public class ServicesFragment extends Fragment implements InlineChange.OnFragmentInteractionListener{

    private ServicesViewModel servicesViewModel;

    CardView serEnt,serLog,inlineChange;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        servicesViewModel =
                ViewModelProviders.of(this).get(ServicesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_services, container, false);


        serEnt=(CardView)root.findViewById(R.id.cardNewSvc);
        serLog=(CardView)root.findViewById(R.id.cardLog);
        inlineChange=(CardView)root.findViewById(R.id.cardInline);


        serEnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment serviceEntry = new ServiceEntry();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, serviceEntry); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });

        serLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment serviceLog = new ServiceLog();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, serviceLog); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });

        inlineChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment inlineChange = new InlineChange();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, inlineChange); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });

        /*final TextView textView = root.findViewById(R.id.text_slideshow);
        servicesViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}