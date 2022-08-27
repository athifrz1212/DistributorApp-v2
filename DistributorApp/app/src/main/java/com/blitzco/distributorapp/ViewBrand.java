package com.blitzco.distributorapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blitzco.distributorapp.adapters.AdapterBrand;
import com.blitzco.distributorapp.models.Brand;
import com.blitzco.distributorapp.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewBrand extends AppCompatActivity {

    private RecyclerView brand_list;
    private RelativeLayout go_back;
    private LinearLayout addBTN;
    private FirebaseAuth fAuth;
    private DatabaseReference dbRef;

    private ArrayList<Brand> brandList = new ArrayList<Brand>();
    private RecyclerView.Adapter mAdapter;//view adapter
    private RecyclerView.LayoutManager layoutManager; //view layout manager

    @Override
    protected void
    onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_brand);

        brand_list = (RecyclerView) findViewById(R.id.brand_list);
        brand_list.setHasFixedSize(true);

        addBTN = findViewById(R.id.addBTN);
//        addBTN.setVisibility(View.INVISIBLE);

        layoutManager = new LinearLayoutManager(this);//assign layout manager
        mAdapter = new AdapterBrand(brandList, ViewBrand.this); //updateBTN brand_list data to adapter class

        go_back = findViewById(R.id.go_back);

        fAuth = FirebaseAuth.getInstance();
        FirebaseUser fUser = fAuth.getCurrentUser();

        dbRef = FirebaseDatabase.getInstance().getReference("User");
        dbRef.child(fUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
//                if(user.getRole().equals("ADMIN")) {
//                    addBTN.setVisibility(View.VISIBLE);
//                } else if(user.getRole().equals("AGENT")) {
//                    addBTN.setVisibility(View.INVISIBLE);
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ViewBrand.this, AgentHome.class);
                startActivity(intent);
            }
        });

        addBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ViewBrand.this, AddBrand.class);
                startActivity(intent);
            }
        });

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Brand");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    brandList.clear();
                for (DataSnapshot prod: snapshot.getChildren()) {
                    Brand brand = prod.getValue(Brand.class);
                    brand.setBrandID(prod.getKey());
                    brandList.add(brand);
                }
                brand_list.setLayoutManager(layoutManager);
                brand_list.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}