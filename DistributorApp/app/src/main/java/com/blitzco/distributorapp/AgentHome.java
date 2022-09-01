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

public class AgentHome extends AppCompatActivity {

    LinearLayout view_Repair, inventoryBTN,
            view_route, view_map, shops;
    TextView balance;

    long totalBalance = 0;

    DatabaseReference paymentRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agent_home);

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

        view_Repair = findViewById(R.id.view_repairsBTN);

        view_Repair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AgentHome.this, ViewRepairs.class);
                startActivity(intent);
            }
        });


        inventoryBTN = findViewById(R.id.inventoryBTN);

        inventoryBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AgentHome.this, ViewBrand.class);
                startActivity(intent);
            }
        });

        shops = findViewById(R.id.shopsBTN);

        shops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AgentHome.this, ViewShops.class);
                startActivity(intent);
            }
        });

        view_route = findViewById(R.id.view_routeBTN);

        view_route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AgentHome.this, ViewShopRoute.class);
                startActivity(intent);
            }
        });

        view_map = findViewById(R.id.mapBTN);

        view_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AgentHome.this, MapActivity.class);
                startActivity(intent);
            }
        });

    }
}