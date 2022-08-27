package com.blitzco.distributorapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class AdminHome extends AppCompatActivity {

    LinearLayout view_userBTN, inventoryBTN, add_shopBTN, view_shopsBTN,
            view_repairBTN, reportGeneratorBTN, view_mapBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home);

        view_userBTN = findViewById(R.id.view_userBTN);

        view_userBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AdminHome.this, AddUser.class);
                startActivity(intent);
            }
        });

        inventoryBTN = findViewById(R.id.inventoryBTN);

        inventoryBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AdminHome.this, ViewBrand.class);
                startActivity(intent);
            }
        });

        view_userBTN = findViewById(R.id.view_userBTN);

        view_userBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AdminHome.this, ViewUsers.class);
                startActivity(intent);
            }
        });

        add_shopBTN = findViewById(R.id.add_shopBTN);

        add_shopBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AdminHome.this, AddShopRoute.class);
                startActivity(intent);
            }
        });

        view_repairBTN = findViewById(R.id.view_repairBTN);

        view_repairBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AdminHome.this, ViewRepairs.class);
                startActivity(intent);
            }
        });

        view_shopsBTN = findViewById(R.id.view_shopBTN);

        view_shopsBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AdminHome.this, ViewShops.class);
                startActivity(intent);
            }
        });

        reportGeneratorBTN = findViewById(R.id.reportGeneratorBTN);

        reportGeneratorBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AdminHome.this, ReportGenerator.class);
                startActivity(intent);
            }
        });

        view_mapBTN = findViewById(R.id.mapBTN);

        view_mapBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AdminHome.this, MapActivity.class);
                startActivity(intent);
            }
        });

    }
}