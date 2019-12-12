package com.tech4lyf.absolutesolutionscrm.ui.customers;

import android.content.Intent;
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

import com.tech4lyf.absolutesolutionscrm.R;
import com.tech4lyf.absolutesolutionscrm.ui.newcustomer.NewCustomer;
import com.tech4lyf.absolutesolutionscrm.ui.oldcustomers.OldCustomers;
import com.tech4lyf.absolutesolutionscrm.ui.serviceentry.ServiceEntry;

public class CustomersFragment extends Fragment implements NewCustomer.OnFragmentInteractionListener,OldCustomers.OnFragmentInteractionListener{

    private CustomersViewModel customersViewModel;

    CardView cardNew,cardOld;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        customersViewModel =
                ViewModelProviders.of(this).get(CustomersViewModel.class);

        View root = inflater.inflate(R.layout.fragment_customers, container, false);
/*        final TextView textView = root.findViewById(R.id.text_tools);
        customersViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        cardNew=(CardView)root.findViewById(R.id.cardnewcust);
        cardNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newCust = new NewCustomer();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, newCust); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });

        cardOld=(CardView)root.findViewById(R.id.cardoldcust);
        cardOld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment oldCust = new OldCustomers();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, oldCust); // give your fragment container id in first parameter
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