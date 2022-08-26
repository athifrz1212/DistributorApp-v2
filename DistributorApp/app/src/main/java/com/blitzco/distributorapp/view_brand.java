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

import com.blitzco.distributorapp.adapters.adapterBrand;
import com.blitzco.distributorapp.models.Brand;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class view_brand extends AppCompatActivity {

    RecyclerView brand_list;
    RelativeLayout go_back;
    LinearLayout addBTN;

    ArrayList<Brand> brandList = new ArrayList<Brand>();
    private RecyclerView.Adapter mAdapter;//view adapter
    private RecyclerView.LayoutManager layoutManager; //view layout manager


    @Override
    protected void
    onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_brand);

        brand_list = (RecyclerView) findViewById(R.id.brand_list);
        brand_list.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);//assign layout manager
        mAdapter = new adapterBrand(brandList,view_brand.this); //updateBTN brand_list data to adapter class

        go_back = findViewById(R.id.go_back);

        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(view_brand.this, home.class);
                startActivity(intent);
            }
        });

        addBTN = findViewById(R.id.addBTN);

        addBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(view_brand.this, add_brand.class);
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