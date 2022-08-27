package com.blitzco.distributorapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class admin_home extends AppCompatActivity {

    LinearLayout add_userBTN, view_userBTN, reportGeneratorBTN;
    TextView balance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home);

        add_userBTN = findViewById(R.id.add_userBTN);

        add_userBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(admin_home.this, add_user.class);
                startActivity(intent);
            }
        });

        view_userBTN = findViewById(R.id.view_userBTN);

        view_userBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(admin_home.this, view_users.class);
                startActivity(intent);
            }
        });

        reportGeneratorBTN = findViewById(R.id.reportGeneratorBTN);

        reportGeneratorBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(admin_home.this, reportGenerator.class);
                startActivity(intent);
            }
        });

    }
}