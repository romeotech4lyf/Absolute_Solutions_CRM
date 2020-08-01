package com.tech4lyf.absolutesolutionscrm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.tech4lyf.absolutesolutionscrm.Models.*;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    User user;
    boolean loginStatus;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private DatabaseReference mDatabase;
    EditText editUserName,editPassword;
    Button butnLogin;
    TextView forgotPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //testIpSW();

        editUserName = (EditText) findViewById(R.id.edtMail);
        editPassword = (EditText) findViewById(R.id.edtPassword);
        butnLogin = (Button) findViewById(R.id.btnFP);
        forgotPass=(TextView)findViewById(R.id.tvForgotPass);

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actFP=new Intent(Login.this,ForgotPassword.class);
                startActivity(actFP);
            }
        });


        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("Users");

        String un,pass;
        //un="testuser";
        //pass="Test@2019";
        //login(un,pass);

        butnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String un,pass;
                un="testuser";
                pass="Test@2019";

                un=editUserName.getText().toString();
                pass=editPassword.getText().toString();
                login(un,pass);



                //testIp();
                //login(editUserName.getText().toString(),editPassword.getText().toString());
            }
        });
    }

    boolean login(final String username, final String password) {



        databaseReference.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                loginStatus=false;
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0

                    for (DataSnapshot user : dataSnapshot.getChildren()) {
                        // do something with the individual "issues"
                        User usersBean = user.getValue(User.class);

                        if (usersBean.getPassword().toString().equals(password)){
                            //Intent intent = new Intent(context, MainActivity.class);
                            //startActivity(intent);
                            Toast.makeText(Login.this, "Login Success!", Toast.LENGTH_SHORT).show();
                            Intent actDashboard=new Intent(Login.this,Home.class);
                            startActivity(actDashboard);
                            Login.this.finish();



                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Password is wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Login.this, "Login failed!", Toast.LENGTH_SHORT).show();
            }
        });




        return false;
    }

    void testIp()
    {
        try {

            String un,pwd,key;
            un="testuser";
            pwd="Test@2019";



            if (TextUtils.isEmpty(un) ) {

                Toast.makeText(getApplicationContext(), "Name Empty!", Toast.LENGTH_SHORT).show();

            } else {


                user = new User(databaseReference.push().getKey(), un, pwd);

                databaseReference.child(user.getKey()).setValue(user);

                Toast.makeText(getApplicationContext(), "Submitted", Toast.LENGTH_SHORT).show();



            }
        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT).show();

        }
    }

    void testIpSW()
    {
        try {

            String un,pwd,key;
            un="testuser";
            pwd="Test@2019";



            if (TextUtils.isEmpty(un) ) {

                Toast.makeText(getApplicationContext(), "Name Empty!", Toast.LENGTH_SHORT).show();

            } else {



                firebaseDatabase=FirebaseDatabase.getInstance();
                databaseReference=firebaseDatabase.getReference().child("ScheduledWork");
                ScheduledWorkList user = new ScheduledWorkList(databaseReference.push().getKey(), "Akash", "10-09-1997","1","true");
                databaseReference.child(user.getKey()).setValue(user);

                Toast.makeText(getApplicationContext(), "Submitted", Toast.LENGTH_SHORT).show();



            }
        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT).show();

        }
    }



}
