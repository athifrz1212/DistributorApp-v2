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

import com.blitzco.distributorapp.adapters.AdapterProduct;
import com.blitzco.distributorapp.models.Product;
import com.blitzco.distributorapp.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewProduct extends AppCompatActivity {

    private RecyclerView list;
    private LinearLayout addBTN;
    private TextView brandname;
    private RelativeLayout go_back;
    private String currentUserRole;

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
        mAdapter = new AdapterProduct(proList, ViewProduct.this); //updateBTN brand_list data to adapter class

        //Values from view page on click
        String brandName = getIntent().getStringExtra("brand_Name").toString();

        brandname = findViewById(R.id.brandname);
        brandname.setText(brandName);


        addBTN = findViewById(R.id.addBTN);
//        addBTN.setVisibility(View.INVISIBLE);

        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseUser fUser = fAuth.getCurrentUser();

        DatabaseReference userDbRef = FirebaseDatabase.getInstance().getReference("User");
        userDbRef.child(fUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                currentUserRole = user.getRole();
                if(user.getRole().equals("ADMIN")) {
                    addBTN.setVisibility(View.VISIBLE);
                } else if(user.getRole().equals("AGENT")) {
                    addBTN.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        go_back = findViewById(R.id.go_back);

        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ViewProduct.this, ViewBrand.class);
                startActivity(intent);

            }
        });

        addBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ViewProduct.this, AddProduct.class);
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
