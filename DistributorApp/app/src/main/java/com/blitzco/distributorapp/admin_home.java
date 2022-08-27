package com.blitzco.distributorapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class admin_home extends AppCompatActivity {

    LinearLayout view_userBTN, inventoryBTN, reportGeneratorBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home);

        view_userBTN = findViewById(R.id.view_userBTN);

        view_userBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(admin_home.this, add_user.class);
                startActivity(intent);
            }
        });

        inventoryBTN = findViewById(R.id.inventoryBTN);

        inventoryBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(admin_home.this, view_brand.class);
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