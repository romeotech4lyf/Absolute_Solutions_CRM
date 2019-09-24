package com.tech4lyf.absolutesolutionscrm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tech4lyf.absolutesolutionscrm.Models.LoginUser;

public class Login extends AppCompatActivity {

    private DatabaseReference mDatabase;
    EditText editUserName,editPassword;
    Button butnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editUserName = (EditText) findViewById(R.id.edtUsername);
        editPassword = (EditText) findViewById(R.id.edtPassword);
        butnLogin = (Button) findViewById(R.id.btnLogin);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        butnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            /*Query query = mDatabase.child("users").orderByChild("username").equalTo(editUserName.getText().toString().trim());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // dataSnapshot is the "issue" node with all children with id 0

                        for (DataSnapshot user : dataSnapshot.getChildren()) {
                            // do something with the individual "issues"
                            LoginUser loginUser = user.getValue(LoginUser.class);
                            Log.e("Check",loginUser.getUserName());

                            if (loginUser.getPassword().equals(editPassword.getText().toString().trim())) {
                                //Intent intent = new Intent(context, MainActivity.class);
                                //startActivity(intent);
                                Toast.makeText(Login.this, "Login Success!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Password is wrong", Toast.LENGTH_LONG).show();
                            }
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            }
        });*/

                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //LoginUser user = dataSnapshot.getValue(LoginUser.class);

                        LoginUser loginUser=new LoginUser("akash","Akash@1997");
                        databaseReference.child("users").setValue(loginUser);
                        //Log.d("Done", "User name: " + user.getUserName() + ", id: " + user.getPassword());
                        //textView.setText(user.getName());
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("FB-Error", "Failed to read value.", error.toException());
                    }
                });
            }

        });
    }



}
