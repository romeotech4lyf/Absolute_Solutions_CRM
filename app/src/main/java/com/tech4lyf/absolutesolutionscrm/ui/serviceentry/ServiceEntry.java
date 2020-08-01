package com.tech4lyf.absolutesolutionscrm.ui.serviceentry;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kyanogen.signatureview.SignatureView;
import com.tech4lyf.absolutesolutionscrm.Models.Customer;
import com.tech4lyf.absolutesolutionscrm.Models.ServiceEntryModel;
import com.tech4lyf.absolutesolutionscrm.R;
import com.tech4lyf.absolutesolutionscrm.ui.customers.CustomersFragment;
import com.tech4lyf.absolutesolutionscrm.ui.home.HomeFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ServiceEntry.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ServiceEntry#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServiceEntry extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Bitmap bitmap;
    Button clear,save;
    CardView signatureView;
    String path;

    TextInputEditText edtDate,edtPhone,edtRAW,edtARO,edtRej,edtParts,edtPrice,edtHandCash,edtDesc;
    AutoCompleteTextView edtCName;
    Switch swStatus;
    Button btnSave;

    String custName,date,phone,raw,aro,rejection,parts,price,handcash,workdone,sign,desc;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String testRAW,testARO;
    double RAW,ARO,rej;

    StringBuilder sb1,sb2;
    String[] macList,partList;
    ArrayList listMac,listPart;
    ArrayList checkedItemsMac,checkedItemsPart;
    boolean[] checkedMac,checkedPart;

    ServiceEntryModel serviceEntryModel;
    private static final String IMAGE_DIRECTORY = "/signdemo";

    private OnFragmentInteractionListener mListener;

    public ServiceEntry() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ServiceEntryModel.
     */
    // TODO: Rename and change types and number of parameters
    public static ServiceEntry newInstance(String param1, String param2) {
        ServiceEntry fragment = new ServiceEntry();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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

        testRAW="";
        testARO="";
        RAW=0;
        ARO=0;

        View root = inflater.inflate(R.layout.fragment_service_entry, container, false);

        signatureView =  (CardView)root.findViewById(R.id.signature_view);

        edtCName=(AutoCompleteTextView)root.findViewById(R.id.edtCname);
        edtDate=(TextInputEditText)root.findViewById(R.id.edtDate);
        edtPhone=(TextInputEditText)root.findViewById(R.id.edtPhone);
        edtRAW=(TextInputEditText)root.findViewById(R.id.edtRAW);
        edtARO=(TextInputEditText)root.findViewById(R.id.edtARO);
        edtRej=(TextInputEditText)root.findViewById(R.id.edtRej);
        edtParts=(TextInputEditText)root.findViewById(R.id.edtParts);
        edtPrice=(TextInputEditText)root.findViewById(R.id.edtPrice);
        edtHandCash=(TextInputEditText)root.findViewById(R.id.edtHandCash);
        edtDesc=(TextInputEditText)root.findViewById(R.id.edtDesc);

        swStatus=(Switch)root.findViewById(R.id.swStatus);

        btnSave=(Button)root.findViewById(R.id.btnSave);

        sb1=new StringBuilder();
        sb2=new StringBuilder();
        listPart = new ArrayList<>();
        checkedItemsPart = new ArrayList<>();

        serviceEntryModel=new ServiceEntryModel();


        //clear = (Button)root.findViewById(R.id.btnClear);
        //save = (Button)root.findViewById(R.id.save);


        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        //Create a new ArrayAdapter with your context and the simple layout for the dropdown menu provided by Android
        final ArrayAdapter<String> autoComplete = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1);
        //Child the root before all the push() keys are found and add a ValueEventListener()
        database.child("Customers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Basically, this says "For each DataSnapshot *Data* in dataSnapshot, do what's inside the method.
                for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()){
                    //Get the suggestion by childing the key of the string you want to get.
                    String suggestion = suggestionSnapshot.child("name").getValue(String.class);
                    //Add the retrieved string to the list
                    autoComplete.add(suggestion);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Parts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String Name = ds.child("partName").getValue().toString();
                    // Toast.makeText(getActivity(), Name, Toast.LENGTH_SHORT).show();
                    listPart.add(Name);
                    checkedItemsPart.add(false);
                    Log.d("List", listPart.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addtoDB();
            }
        });

        AutoCompleteTextView ACTV= (AutoCompleteTextView)root.findViewById(R.id.edtCname);
        ACTV.setAdapter(autoComplete);

        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentDate = Calendar.getInstance();
                final int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                mMonth=mMonth+1;
                final int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        /*      Your code   to get date and time    */

                        edtDate.setText(selectedday+"-"+selectedmonth+"-"+selectedyear);


                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });

        edtRej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(TextUtils.isEmpty(edtRAW.getText().toString())||TextUtils.isEmpty(edtARO.getText().toString()))
                {

                }
                else {
                    Double raw = Double.parseDouble(edtRAW.getText().toString());
                    Double aro = Double.parseDouble(edtARO.getText().toString());
                    DecimalFormat df2 = new DecimalFormat("#.###");
                    //rej = Double.parseDouble("3.5") + Double.parseDouble("4.5");
                    rej=(raw+aro)/raw;
                    edtRej.setText(""+df2.format(rej));
                }
            }
        });

        edtParts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "Hello", Toast.LENGTH_SHORT).show();
                sb2.setLength(0);

                partList=new String[listPart.size()];
                checkedPart = new boolean[listPart.size()];
                for (int i = 0; i < listPart.size(); i++) {
                    partList[i]=(String)listPart.get(i);
                    checkedPart[i]=false;
                }
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                builder.setTitle("Choose a Part");

// Add a checkbox list
                String[] animals = {"horse", "cow", "camel", "sheep", "goat"};
                boolean[] checkedItems = {true, false, false, true, false};
                builder.setMultiChoiceItems(partList, checkedPart, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        // The user checked or unchecked a box
                        //Toast.makeText(getActivity(), "" + which, Toast.LENGTH_SHORT).show();
                        if(sb1.length()!=0)
                        {
                            sb2.append(","+listPart.get(which).toString());
                        }
                        else {
                            sb2.append(listPart.get(which).toString());
                        }
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //Toast.makeText(getActivity(), ""+sb1, Toast.LENGTH_SHORT).show();
                        edtParts.setText(sb2);

                    }
                });
                builder.show();


            }
        });


        signatureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "Hi", Toast.LENGTH_SHORT).show();
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.activity_signature, null);

                final SignatureView signView = alertLayout.findViewById(R.id.signView);

                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Signature");
                // this is set the view from XML inside AlertDialog
                alert.setView(alertLayout);
                // disallow cancel of AlertDialog on click of back button and outside touch
                alert.setCancelable(false);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Signature cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

                alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(getActivity(), "OK", Toast.LENGTH_SHORT).show();
                        bitmap = signView.getSignatureBitmap();
                        path = saveImage(bitmap);}
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });



        /*clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        //        signatureView.clearCanvas();
            }
        });*/

        return root;
    }

    private void addtoDB() {

            custName=edtCName.getText().toString();
            date=edtDate.getText().toString();
            phone=edtPhone.getText().toString();
            raw=edtRAW.getText().toString();
            aro=edtARO.getText().toString();
            rejection=edtRej.getText().toString();
            parts=edtParts.getText().toString();
            price=edtPrice.getText().toString();
            handcash=edtHandCash.getText().toString();
            if(swStatus.isChecked())
            {
                workdone="true";
            }
            else
            {
                workdone="false";
            }

            sign=getImageData(bitmap);
            desc=edtDesc.getText().toString();

            if(TextUtils.isEmpty(custName))
            {
                Toast.makeText(getActivity(), "Enter Customer Name", Toast.LENGTH_SHORT).show();
            }

            else if(TextUtils.isEmpty(date))
            {
                Toast.makeText(getActivity(), "Enter Date", Toast.LENGTH_SHORT).show();
            }

            else if(TextUtils.isEmpty(phone))
            {
                Toast.makeText(getActivity(), "Enter Phone", Toast.LENGTH_SHORT).show();
            }

            else if(TextUtils.isEmpty(raw))
            {
                Toast.makeText(getActivity(), "Enter RAW Value", Toast.LENGTH_SHORT).show();
            }

            else if(TextUtils.isEmpty(aro))
            {
                Toast.makeText(getActivity(), "Enter A.RO Value", Toast.LENGTH_SHORT).show();
            }

            else if(TextUtils.isEmpty(rejection))
            {
                Toast.makeText(getActivity(), "Enter Rejection Value", Toast.LENGTH_SHORT).show();
            }

            else if(TextUtils.isEmpty(parts))
            {
                Toast.makeText(getActivity(), "Enter Parts", Toast.LENGTH_SHORT).show();
            }

            else if(TextUtils.isEmpty(price))
            {
                Toast.makeText(getActivity(), "Enter Price", Toast.LENGTH_SHORT).show();
            }

            else if(TextUtils.isEmpty(handcash))
            {
                Toast.makeText(getActivity(), "Enter Hand Cash", Toast.LENGTH_SHORT).show();
            }

            else if(TextUtils.isEmpty(workdone))
            {
                Toast.makeText(getActivity(), "Select Work done status", Toast.LENGTH_SHORT).show();
            }

            else if(TextUtils.isEmpty(sign))
            {
                Toast.makeText(getActivity(), "Enter Signature", Toast.LENGTH_SHORT).show();
            }

            else if(TextUtils.isEmpty(desc))
            {
                Toast.makeText(getActivity(), "Enter Description", Toast.LENGTH_SHORT).show();
            }

            else
            {
                try {
                    serviceEntryModel = new ServiceEntryModel(databaseReference.push().getKey(),custName,date,phone,raw,aro,rejection,parts,price,handcash,workdone,sign,desc);
                    databaseReference.child("ServiceEntry").child(serviceEntryModel.getKey()).setValue(serviceEntryModel);
                    Toast.makeText(getActivity(), "Service added successfully!", Toast.LENGTH_SHORT).show();

                    Fragment home = new HomeFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment, home); // give your fragment container id in first parameter
                    transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                    transaction.commit();

                }
                catch (Exception e) {

                    Toast.makeText(getActivity(), "Something went wrong!" + e, Toast.LENGTH_SHORT).show();

                }

            }



    }

    public String getImageData(Bitmap bmp) {

        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, bao); // bmp is bitmap from user image file
        bmp.recycle();
        byte[] byteArray = bao.toByteArray();
        String imageB64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
        //  store & retrieve this string to firebase

        return imageB64;
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY /*iDyme folder*/);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
            Log.d("hhhhh",wallpaperDirectory.toString());
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(getActivity(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";

    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
