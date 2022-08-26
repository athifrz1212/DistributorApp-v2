package com.blitzco.distributorapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    private EditText username, password;
    private Button login, cancel;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        fAuth = FirebaseAuth.getInstance();

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        username.setText("athifrahman2000@gmail.com");
        password.setText(("arz@1212"));

        login = findViewById(R.id.loginBTN);
        cancel = findViewById(R.id.cancelBTN);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void login()
    {
        String uname = username.getText().toString();
        String pswd = password.getText().toString();

        if(!uname.equals("") && !pswd.equals("")) {

        fAuth.signInWithEmailAndPassword(uname, pswd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = fAuth.getCurrentUser();

                            Toast.makeText(login.this, "Login success",Toast.LENGTH_LONG).show();
                            Intent intent= new Intent(login.this, home.class);
                            startActivity(intent);
                            username.setText("");
                            password.setText((""));

//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }
                    }
                });
        }else
        {
            Toast.makeText(this, "Username or Password is empty",Toast.LENGTH_LONG).show();
        }

    }
}
