package com.tech4lyf.absolutesolutionscrm.ui.newcustomer;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tech4lyf.absolutesolutionscrm.MainActivity;
import com.tech4lyf.absolutesolutionscrm.Models.Customer;
import com.tech4lyf.absolutesolutionscrm.Models.Machine;
import com.tech4lyf.absolutesolutionscrm.Models.ScheduledWorkList;
import com.tech4lyf.absolutesolutionscrm.Models.User;
import com.tech4lyf.absolutesolutionscrm.R;
import com.tech4lyf.absolutesolutionscrm.RVAdapter;
import com.tech4lyf.absolutesolutionscrm.RVAdapterRO;
import com.tech4lyf.absolutesolutionscrm.ui.customers.CustomersFragment;
import com.tech4lyf.absolutesolutionscrm.ui.serviceentry.ServiceEntry;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.crypto.Mac;

import static android.app.Activity.RESULT_OK;
import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewCustomer.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewCustomer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewCustomer extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    Customer customer;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Intent intent ;
    Bitmap bitmap,bitmapmac;
    int cam=0;
    public  static final int RequestPermissionCode  = 1 ;
    CardView cvCustPic,cvMacPic;
    ImageView imgcust,imgMac;
    String id,name,date,phone,ref,loc,addr,romac,parts,price,handcash,customerpic,macpic;


    String[] macArrName;
    String[] macArrKey;

    ArrayList listMac,listPart;
    ArrayList checkedItemsMac,checkedItemsPart;
    int fetchCount;
    String[] macList,partList;
    boolean[] checkedMac,checkedPart;

    int itemCount;
    StringBuilder sb1,sb2;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText edtName,edtDate,edtPhone,edtRef,edtLoc,edtAddr,edtMac,edtPart,edtPrice,edtHC;
    Button btnSave;

    private OnFragmentInteractionListener mListener;

    public NewCustomer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewCustomer.
     */
    // TODO: Rename and change types and number of parameters
    public static NewCustomer newInstance(String param1, String param2) {
        NewCustomer fragment = new NewCustomer();
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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_new_customer, container, false);

        edtName=(EditText)root.findViewById(R.id.edtnewcustName);
        edtPhone=(EditText)root.findViewById(R.id.edtnewcustPhone);
        edtRef=(EditText)root.findViewById(R.id.edtnewcustRef);
        edtLoc=(EditText)root.findViewById(R.id.edtnewcustLoc);
        edtAddr=(EditText)root.findViewById(R.id.edtnewcustAddr);
        edtMac=(EditText)root.findViewById(R.id.edtnewcustRO);
        edtPart=(EditText)root.findViewById(R.id.edtnewcustPart);
        edtPrice=(EditText)root.findViewById(R.id.edtnewcustPrice);
        edtHC=(EditText)root.findViewById(R.id.edtnewcustHC);
        edtDate=root.findViewById(R.id.edtnewcustDate);
        btnSave=(Button)root.findViewById(R.id.btnnewcustSave);

        macArrName=new String[150];

        listMac = new ArrayList<>();
        checkedItemsMac = new ArrayList<>();

        listPart = new ArrayList<>();
        checkedItemsPart = new ArrayList<>();

        fetchCount = 0;

        itemCount = 0;

        sb1=new StringBuilder();
        sb2=new StringBuilder();


        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Machines").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String Name = ds.child("machName").getValue().toString();
                    //Toast.makeText(getActivity(), Name, Toast.LENGTH_SHORT).show();
                    listMac.add(Name);
                    checkedItemsMac.add(false);
                    Log.d("List", listMac.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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


        edtMac.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          //Toast.makeText(getActivity(), "Hello", Toast.LENGTH_SHORT).show();
                                          sb1.setLength(0);

                                          macList=new String[listMac.size()];
                                          checkedMac = new boolean[listMac.size()];
                                          for (int i = 0; i < listMac.size(); i++) {
                                              macList[i]=(String)listMac.get(i);
                                              checkedMac[i]=false;
                                          }
                                          AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                          builder.setTitle("Choose a Machine");

// Add a checkbox list
                                          String[] animals = {"horse", "cow", "camel", "sheep", "goat"};
                                          boolean[] checkedItems = {true, false, false, true, false};
                                          builder.setMultiChoiceItems(macList, checkedMac, new DialogInterface.OnMultiChoiceClickListener() {
                                              @Override
                                              public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                                  // The user checked or unchecked a box
                                                  //Toast.makeText(getActivity(), "" + which, Toast.LENGTH_SHORT).show();
                                                  if(sb1.length()!=0)
                                                  {
                                                      sb1.append(","+listMac.get(which).toString());
                                                  }
                                                  else {
                                                      sb1.append(listMac.get(which).toString());
                                                  }
                                              }
                                          });
                                          builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                              @Override
                                              public void onClick(DialogInterface dialogInterface, int i) {

                                                  //Toast.makeText(getActivity(), ""+sb1, Toast.LENGTH_SHORT).show();
                                                  edtMac.setText(sb1);

                                              }
                                          });
                                          builder.show();


                                      }
                                  });

        edtPart.setOnClickListener(new View.OnClickListener() {
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
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                        edtPart.setText(sb2);

                    }
                });
                builder.show();


            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref="";
                name=edtName.getText().toString();
                phone=edtPhone.getText().toString();
                ref=edtRef.getText().toString();
                loc=edtLoc.getText().toString();
                addr=edtAddr.getText().toString();
                romac=edtMac.getText().toString();
                parts=edtPart.getText().toString();
                price=edtPrice.getText().toString();
                handcash=edtHC.getText().toString();
                date=edtDate.getText().toString();

                /*name="Akash";
                phone="9876543210";
                ref="Raju";
                loc="Chennai";
                addr="Ayapakkam";
                romac="Machine1";
                parts="HelloPart";
                price="25000";
                handcash="10000";
                date="03-11-2019";*/


                if(bitmap!=null)
                {
                    customerpic=getImageData(bitmap);
                }
                if(bitmapmac!=null)
                {
                    macpic=getImageData(bitmapmac);
                }

                if(TextUtils.isEmpty(name))
                {
                    Toast.makeText(getActivity(), "Enter Name", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(date))
                {
                    Toast.makeText(getActivity(), "Enter Date", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(phone))
                {
                    Toast.makeText(getActivity(), "Enter Phone", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(loc))
                {
                    Toast.makeText(getActivity(), "Enter Location", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(addr))
                {
                    Toast.makeText(getActivity(), "Enter Address", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(romac))
                {
                    Toast.makeText(getActivity(), "Select Machine", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(parts))
                {
                    Toast.makeText(getActivity(), "Select Parts", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(price))
                {
                    Toast.makeText(getActivity(), "Select Price", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(handcash))
                {
                    Toast.makeText(getActivity(), "Enter Hand Cash", Toast.LENGTH_SHORT).show();
                }

                else if(TextUtils.isEmpty(date))
                {
                    Toast.makeText(getActivity(), "Enter Date", Toast.LENGTH_SHORT).show();
                }

                else if(TextUtils.isEmpty(customerpic))
                {
                    Toast.makeText(getActivity(), "Customer Photo needed", Toast.LENGTH_SHORT).show();
                }

                else if(TextUtils.isEmpty(macpic))
                {
                    Toast.makeText(getActivity(), "Machine Photo needed", Toast.LENGTH_SHORT).show();
                }
                else {


                    addCust(id,name,date,phone,ref,loc,addr,romac,parts,price,handcash,customerpic,macpic);
                    Toast.makeText(getActivity(), "Customer Added Successfully!", Toast.LENGTH_SHORT).show();
                    Fragment cust = new CustomersFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment, cust); // give your fragment container id in first parameter
                    transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                    transaction.commit();
                }
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference();
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "Date", Toast.LENGTH_SHORT).show();
                //edtdate.setText("");
                Calendar mcurrentDate = Calendar.getInstance();
                final int mYear = mcurrentDate.get(Calendar.YEAR);
                final int mMonth = mcurrentDate.get(Calendar.MONTH);
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




        cvCustPic=(CardView)root.findViewById(R.id.cardnewcustPhoto);
        imgcust=(ImageView)root.findViewById(R.id.imgcustPic);
        cvCustPic.setOnClickListener(new View.OnClickListener() {
            String btnTake;

            @Override
            public void onClick(View view) {
                Context context;
                cam=1;
                AlertDialog.Builder alertBuilder=new AlertDialog.Builder(getActivity());
                alertBuilder.setTitle("Choose an Option");

                /*LayoutInflater factory = LayoutInflater.from(getActivity());
                final View view1 = factory.inflate(R.layout.imagealert, null);*/

                if(bitmap!=null)
                {
                    btnTake="Recapture";
                }
                else
                {
                    btnTake="Capture";
                }


                alertBuilder.setMessage("")
                        .setCancelable(false)
                        .setPositiveButton(btnTake,new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity
                                intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                                startActivityForResult(intent, 7);
                                //getActivity().finish();
                            }
                        })
                        .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // if this button is clicked, just close
                                        // the dialog box and do nothing
                                        dialog.cancel();
                                    }

                                });


                // create alert dialog
                AlertDialog alertDialog = alertBuilder.create();

                // show it
                alertDialog.show();

            }
        });

        cvMacPic=(CardView)root.findViewById(R.id.cardnewcustMacPhoto);
        imgMac=(ImageView)root.findViewById(R.id.imgMacPic);
        cvMacPic.setOnClickListener(new View.OnClickListener() {
            String btnTake;
            @Override
            public void onClick(View view) {
                Context context;
                cam=2;
                AlertDialog.Builder alertBuilder=new AlertDialog.Builder(getActivity());
                alertBuilder.setTitle("Choose an Option");
                /*LayoutInflater factory = LayoutInflater.from(getActivity());
                final View view1 = factory.inflate(R.layout.imagealert, null);*/

                if(bitmapmac!=null)
                {
                    btnTake="Recapture";
                }
                else
                {
                    btnTake="Capture";
                }


                alertBuilder.setMessage("")
                        .setCancelable(false)
                        .setPositiveButton(btnTake,new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity
                                intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                                startActivityForResult(intent, 7);
                                //getActivity().finish();
                            }
                        })
                        .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }

                        });


                // create alert dialog
                AlertDialog alertDialog = alertBuilder.create();

                // show it
                alertDialog.show();

            }
        });


        return root;

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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 7 && resultCode == RESULT_OK) {

            if(cam==1) {
                bitmap = (Bitmap) data.getExtras().get("data");

                //cv.setImageBitmap(bitmap);
                //cvCustPic.setCardBackgroundColor(bitmap);
                imgcust.setImageBitmap(bitmap);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
                imgcust.setLayoutParams(layoutParams);
                Toast.makeText(getActivity(), "Photo captured", Toast.LENGTH_SHORT).show();
            }
            if(cam==2)
            {
                bitmapmac = (Bitmap) data.getExtras().get("data");

                //cv.setImageBitmap(bitmap);
                //cvCustPic.setCardBackgroundColor(bitmap);
                imgMac.setImageBitmap(bitmapmac);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
                imgMac.setLayoutParams(layoutParams);
                Toast.makeText(getActivity(), "Photo captured", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void EnableRuntimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.CAMERA))
        {

            Toast.makeText(getActivity(),"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(getActivity(),new String[]{
                    Manifest.permission.CAMERA}, RequestPermissionCode);

        }
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
/*            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");*/
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

    void addCust(String id,String name,String date,String phone,String ref,String loc,String addr,String romac,String parts,String price,String handcash,String customerpic,String macpic)
    {

        try {
            customer = new Customer(databaseReference.push().getKey(), id,name,date,phone,ref,loc,addr,romac,parts,price,handcash,customerpic,macpic);
            databaseReference.child("Customers").child(customer.getKey()).setValue(customer);
            Toast.makeText(getActivity(), "Customer added successfully!", Toast.LENGTH_SHORT).show();

        }
        catch (Exception e) {

            Toast.makeText(getActivity(), "" + e, Toast.LENGTH_SHORT).show();

        }

    }

}
