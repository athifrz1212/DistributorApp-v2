package com.blitzco.distributorapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.blitzco.distributorapp.models.Payment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminHome extends AppCompatActivity {

    private LinearLayout view_userBTN, inventoryBTN, add_shopBTN, view_shopsBTN,
            view_repairBTN, reportGeneratorBTN, view_mapBTN;

    private TextView balance;

    private long totalBalance = 0;

    private DatabaseReference paymentRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home);

        balance = findViewById(R.id.balance);

        paymentRef = FirebaseDatabase.getInstance().getReference("Payment");

        paymentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getChildrenCount() > 0 ) {
                    for(DataSnapshot snap: snapshot.getChildren()) {
                        Payment payment = snap.getValue(Payment.class);
                        totalBalance = totalBalance + payment.getBalance();
                    }
                    balance.setText(String.valueOf(totalBalance));
                } else {
                    balance.setText(String.valueOf(totalBalance));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
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