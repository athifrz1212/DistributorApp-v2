package com.blitzco.distributorapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blitzco.distributorapp.adapters.adapterProduct;
import com.blitzco.distributorapp.models.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class view_product extends AppCompatActivity {

    RecyclerView list;
    LinearLayout addBTN;
    TextView brandname;
    RelativeLayout go_back;

    public ArrayList<Product> proList= new ArrayList<Product>();
    //to load data

    private RecyclerView.Adapter mAdapter;//view adapter
    private RecyclerView.LayoutManager layoutManager; //view layout manager

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_product);
        list = (RecyclerView) findViewById(R.id.pro_list);
        list.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);//assign layout manager
        mAdapter = new adapterProduct(proList, view_product.this); //updateBTN brand_list data to adapter class


        //Values from view page on click
        String brandName = getIntent().getStringExtra("brand_Name").toString();

        brandname = findViewById(R.id.brandname);
        brandname.setText(brandName);

        go_back = findViewById(R.id.go_back);

        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(view_product.this, view_brand.class);
                startActivity(intent);
            }
        });

        addBTN = findViewById(R.id.addBTN);

        addBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(view_product.this, add_product.class);
                intent.putExtra("brandName", brandName);
                startActivity(intent);
            }
        });

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Product");
        dbRef.orderByChild("brandName").equalTo(brandName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                proList.clear();
                for (DataSnapshot prod: snapshot.getChildren()) {
                    Product product = prod.getValue(Product.class);
                    product.setId(prod.getKey());
                    System.out.println(">>>>>> "+ prod.getKey());
                    System.out.println(">>>>>> "+ product.getModelName());
                    proList.add(product);
                }
                list.setLayoutManager(layoutManager);
                list.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

}
