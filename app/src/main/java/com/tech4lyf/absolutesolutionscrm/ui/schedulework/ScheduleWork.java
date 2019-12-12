package com.tech4lyf.absolutesolutionscrm.ui.schedulework;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tech4lyf.absolutesolutionscrm.Models.ScheduledWorkList;
import com.tech4lyf.absolutesolutionscrm.Models.User;
import com.tech4lyf.absolutesolutionscrm.R;
import com.tech4lyf.absolutesolutionscrm.RVAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScheduleWork.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScheduleWork#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleWork extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<ScheduledWorkList> scheduledWorklist;
    ListView lvscheduledWorkList;
    private FirebaseListAdapter<ScheduledWorkList> adapter;
    ListView listview;
    String count;

    FloatingActionButton fabnewSW;

    ScheduledWorkList scheduledWorkList;


    private RecyclerView SWRecyclerView;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ListView list;
    String[] maintitle ={
            "Title 1","Title 2",
            "Title 3","Title 4",
            "Title 5",
    };


    private OnFragmentInteractionListener mListener;

    public ScheduleWork() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScheduleWork.
     */
    // TODO: Rename and change types and number of parameters
    public static ScheduleWork newInstance(String param1, String param2) {
        ScheduleWork fragment = new ScheduleWork();
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




            /*ScheduledWorkListAdapter adapter=new ScheduledWorkListAdapter(getActivity(), maintitle);
            list=(ListView)getActivity().findViewById(R.id.scheduledWorkList);
            list.setAdapter(adapter);*/


            /*databaseReference.child("ScheduledWork").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {
                        // dataSnapshot is the "issue" node with all children with id 0

                        for (DataSnapshot scheduledwork : dataSnapshot.getChildren()) {
                            // do something with the individual "issues"
                            ScheduledWorkList scheduledWorkList = scheduledwork.getValue(ScheduledWorkList.class);
                            Log.d("Data",scheduledWorkList.getTask());


                        }
                    }else {
                        Toast.makeText(getView().getContext(), "No Data found", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getView().getContext(), "Login failed!", Toast.LENGTH_SHORT).show();
                }
            });*/



        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {





        View root = inflater.inflate(R.layout.fragment_schedule_work, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        getSWfromDB();

         SWRecyclerView = (RecyclerView) root.findViewById(R.id.rv1);

        LinearLayoutManager recyclerLayoutManager =
                new LinearLayoutManager(getActivity().getApplicationContext());
         SWRecyclerView.setLayoutManager(recyclerLayoutManager);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration( SWRecyclerView.getContext(),
                        recyclerLayoutManager.getOrientation());
         SWRecyclerView.addItemDecoration(dividerItemDecoration);


         fabnewSW=(FloatingActionButton)root.findViewById(R.id.fabNewSW);
         fabnewSW.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                newSW();
             }
         });


        // Inflate the layout for this fragment
        return root;
    }
    public void newSW()
    {
        //Toast.makeText(getActivity(), "Hello", Toast.LENGTH_SHORT).show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
//You must remember to remove the listener when you finish using it, also to keep track of changes you can use the ChildChange
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.e(dataSnapshot.getKey(),dataSnapshot.getChildrenCount() + "");
                if(dataSnapshot.getKey().equals("ScheduledWork"))
                {
                    count=dataSnapshot.getChildrenCount()+"";
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        LayoutInflater factory = LayoutInflater.from(getActivity());

//text_entry is an Layout XML file containing two text field to display in alert dialog
        final View textEntryView = factory.inflate(R.layout.newswdialog, null);


        final EditText edtdate = (EditText) textEntryView.findViewById(R.id.edtDate);
        final EditText edttitle = (EditText) textEntryView.findViewById(R.id.edtTitle);


//        final EditText input2 = (EditText) textEntryView.findViewById(R.id.EditText2);

        edtdate.setOnClickListener(new View.OnClickListener() {
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

                            edtdate.setText(selectedday+"-"+selectedmonth+"-"+selectedyear);


                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }


        });





        edtdate.setText("", TextView.BufferType.EDITABLE);
        edtdate.setHint("Date");
        edttitle.setText("", TextView.BufferType.EDITABLE);
        edttitle.setHint("Title");
  //      input2.setText("DefaultValue", TextView.BufferType.EDITABLE);

        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("New Schedule:").setView(textEntryView).setPositiveButton("Save",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {

                        //Log.i("AlertDialog","TextEntry 1 Entered "+input1.getText().toString());
                        Toast.makeText(getActivity(), edttitle.getText().toString(), Toast.LENGTH_SHORT).show();

                        String date,sno,status,title,key;

                        date=edtdate.getText().toString();
                        int snot= Integer.parseInt(count)+1;
                        sno=Integer.toString(snot);
                        status="false";
                        title=edttitle.getText().toString();

                        if(TextUtils.isEmpty(title))
                        {
                            Toast.makeText(getActivity(), "Enter Title", Toast.LENGTH_SHORT).show();
                        }
                        else if(TextUtils.isEmpty(date))
                        {
                            Toast.makeText(getActivity(), "Select Title", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            addSW(title, date, sno, status);
                        }

                        //Log.i("AlertDialog","TextEntry 2 Entered "+input2.getText().toString());
                        /* User clicked OK so do some stuff */
                    }
                }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                        /*
                         * User clicked cancel so do some stuff
                         */
                    }
                });
        alert.show();


}


    private void getSWfromDB() {
        databaseReference.child("ScheduledWork").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ScheduledWorkList> adsList = new ArrayList<ScheduledWorkList>();
                for (DataSnapshot adSnapshot: dataSnapshot.getChildren()) {
                    adsList.add(adSnapshot.getValue(ScheduledWorkList.class));
                }
                Log.d(TAG, "no of ads for search is "+adsList.size());

                RVAdapter recyclerViewAdapter = new
                        RVAdapter(adsList, getActivity());
                SWRecyclerView.setAdapter(recyclerViewAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
               /*Log.d(TAG, "Error trying to get classified ads for " +category+
                        " "+databaseError);
                Toast.makeText(getActivity(),
                        "Error trying to get classified ads for " +category,
                        Toast.LENGTH_SHORT).show();*/
            }
        });
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

    void addSW(String title,String date, String sno, String status)
    {

            try {
                scheduledWorkList = new ScheduledWorkList(databaseReference.push().getKey(), title,date,sno,status);
                databaseReference.child("ScheduledWork").child(scheduledWorkList.getKey()).setValue(scheduledWorkList);
                Toast.makeText(getActivity(), "Submitted", Toast.LENGTH_SHORT).show();
                getSWfromDB();


                }
             catch (Exception e) {

                Toast.makeText(getActivity(), "" + e, Toast.LENGTH_SHORT).show();

            }

    }
    

}
