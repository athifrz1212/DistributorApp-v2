package com.blitzco.distributorapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blitzco.distributorapp.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddUser extends AppCompatActivity {

    private EditText txtFName, txtLName, txtContactNo, txtEmail, txtPassword;
    private Spinner txtRole;
    private Button createBtn, cancelBtn;
    private FirebaseAuth fAuth;
    private DatabaseReference dbRef;

    ArrayList<String> roles = new ArrayList<String>();
    ArrayAdapter rolesArrayAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user);
        fAuth = FirebaseAuth.getInstance();

        roles.add("ADMIN");
        roles.add("AGENT");

        txtFName = findViewById(R.id.FName);
        txtLName = findViewById(R.id.LName);
        txtContactNo = findViewById(R.id.contactNo);
        txtEmail = findViewById(R.id.email);
        txtRole = findViewById(R.id.role);
        txtPassword = findViewById(R.id.password);

        createBtn = findViewById(R.id.createBTN);
        cancelBtn = findViewById(R.id.cancelBTN);

        rolesArrayAdapter = new ArrayAdapter(this,R.layout.spinner_text, roles);
        rolesArrayAdapter.setDropDownViewResource(R.layout.spinner_text);
        txtRole.setAdapter(rolesArrayAdapter);
        rolesArrayAdapter.notifyDataSetChanged();


        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void signUp()
    {

        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        String firstName = txtFName.getText().toString();
        String lastName = txtLName.getText().toString();
        String role = txtRole.getSelectedItem().toString().toUpperCase();
        String contactNo = txtContactNo.getText().toString();

        dbRef = FirebaseDatabase.getInstance().getReference("User");

        fAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser fUser = fAuth.getCurrentUser();

                            User user = new User();
                            user.setUserID(fUser.getUid());
                            user.setEmail(fUser.getEmail());
                            user.setFirstName(firstName);
                            user.setLastName(lastName);
                            user.setRole(role);
                            user.setContactNo(contactNo);

                            dbRef.child(user.getUserID()).setValue(user);

                            Intent intent= new Intent(AddUser.this, ViewUsers.class);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(AddUser.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

