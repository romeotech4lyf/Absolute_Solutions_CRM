package com.tech4lyf.absolutesolutionscrm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tech4lyf.absolutesolutionscrm.Models.ForgotPass;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ForgotPassword extends AppCompatActivity {

    Button btnFP;
    EditText editTextMail;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String email,fmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        btnFP=(Button)findViewById(R.id.btnFP);
        editTextMail=(EditText)findViewById(R.id.edtMail);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ForgotPass forgotPass=dataSnapshot.getValue(ForgotPass.class);
                email=forgotPass.getMail();
                Log.e("Emailf",forgotPass.getMail());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btnFP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mail=editTextMail.getText().toString();

                Log.w("Email"+mail,"Mail"+mail);
                if(TextUtils.isEmpty(mail))
                {
                    Toast.makeText(ForgotPassword.this, "Please enter the Mail!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(mail.equalsIgnoreCase(email))
                    {
                        fmail=mail;
                        Toast.makeText(ForgotPassword.this, "Mail sent", Toast.LENGTH_SHORT).show();
                        SendPass sendPass=new SendPass();
                        sendPass.execute();
                    }
                    else
                    {
                        Toast.makeText(ForgotPassword.this, "Please check your e-mail address!", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

    }

    class SendPass extends AsyncTask<String,Void,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            StringBuilder sb=null;
            BufferedReader reader=null;
            String serverResponse=null;
            try {

                URL url = new URL("https://www.tech4lyf.com/dev/thirdparty/absolutesolutions/admins/forgot_password.php?mail="+fmail+"&pass=1234");
                Log.w("URL:",url.toString());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("GET");
                connection.connect();
                int statusCode = connection.getResponseCode();
                //Log.e("statusCode", "" + statusCode);
                if (statusCode == 200) {
                    sb = new StringBuilder();
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                }

                connection.disconnect();
                if (sb!=null)
                    serverResponse=sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return serverResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //All your UI operation can be performed here
            System.out.println(s);
        }
    }

        }
