package com.blitzco.distributorapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private EditText txtUsername, txtPassword;
    private TextView txtErrorMsg;
    private Button loginBtn, cancelBtn, newUserBtn;
    private FirebaseAuth fAuth;
    private DatabaseReference dbRef;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        fAuth = FirebaseAuth.getInstance();

        txtUsername = findViewById(R.id.username);
        txtPassword = findViewById(R.id.password);

        txtErrorMsg = findViewById(R.id.errorMsg);
        txtErrorMsg.setVisibility(View.INVISIBLE);

        txtUsername.setText("blitzco.1212@gmail.com");
        txtPassword.setText(("blitz@1212"));

        loginBtn = findViewById(R.id.loginBTN);
        cancelBtn = findViewById(R.id.cancelBTN);
        newUserBtn = findViewById(R.id.newUserBTN);
        newUserBtn.setVisibility(View.INVISIBLE);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        newUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Login.this, "Create new user",Toast.LENGTH_LONG).show();
                Intent intent= new Intent(Login.this, AddUser.class);
                startActivity(intent);
            }
        });

    }

    public void login()
    {
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();

        if(!username.equals("") && !password.equals("")) {
            txtErrorMsg.setVisibility(View.INVISIBLE);

            fAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = fAuth.getCurrentUser();
                            dbRef = FirebaseDatabase.getInstance().getReference("User");
                            dbRef.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    User user = snapshot.getValue(User.class);
                                    if(user.getRole().equals("ADMIN")) {
                                        Toast.makeText(Login.this, "Logged as Admin",Toast.LENGTH_LONG).show();
                                        Intent intent= new Intent(Login.this, AdminHome.class);
                                        startActivity(intent);

                                    } else if(user.getRole().equals("AGENT")) {
                                        Toast.makeText(Login.this, "Logged as Agent",Toast.LENGTH_LONG).show();
                                        Intent intent= new Intent(Login.this, Home.class);
                                        startActivity(intent);
                                    }

                                    txtUsername.setText("");
                                    txtPassword.setText((""));
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        } else {
                            // If sign in fails, display a message to the user.
                            txtErrorMsg.setVisibility(View.VISIBLE);
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Login failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }else
        {
            txtErrorMsg.setText("Username or Password is empty");
            txtErrorMsg.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Username or Password is empty",Toast.LENGTH_LONG).show();
        }

    }
}
