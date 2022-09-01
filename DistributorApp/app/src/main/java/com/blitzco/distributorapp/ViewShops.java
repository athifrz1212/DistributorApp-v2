package com.blitzco.distributorapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blitzco.distributorapp.adapters.AdapterShop;
import com.blitzco.distributorapp.models.Shop;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewShops extends AppCompatActivity {

    private static String searchShopName;
    private RecyclerView shop_List;
    private Button searchBTN, deleteBTN;
    private EditText search_shop;
    private RelativeLayout go_back;

    private ArrayList<Shop> shopList= new ArrayList<Shop>();

    private RecyclerView.Adapter sAdapter;//view adapter
    private RecyclerView.LayoutManager layoutManager; //view layout manager

    private DatabaseReference shopRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_shops);

        search_shop = findViewById(R.id.serch_shop);
        searchBTN = findViewById(R.id.searchBTN);
        deleteBTN = findViewById(R.id.btnDelete);

        shop_List = (RecyclerView) findViewById(R.id.shopList);
        shop_List.setHasFixedSize(true);

        shopRef = FirebaseDatabase.getInstance().getReference("Shop");


        layoutManager = new LinearLayoutManager(this);//assign layout manager
        sAdapter = new AdapterShop(shopList, ViewShops.this); //updateBTN brand_list data to adapter class

        go_back = findViewById(R.id.go_back);

        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ViewShops.this, AgentHome.class);
                startActivity(intent);
            }
        });


        searchBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                searchShopName = search_shop.getText().toString().toUpperCase();

                shopRef.orderByChild("shop").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        shopList.clear();
                        for(DataSnapshot snap: snapshot.getChildren()) {
                            Shop shop = snap.getValue(Shop.class);

                            if(shop.getShop().contains(searchShopName)) {
                                shopList.add(shop);
                                shop_List.setLayoutManager(layoutManager);
                                shop_List.setAdapter(sAdapter);
                            } else {
                                Toast.makeText(ViewShops.this, searchShopName+" shop unavailable", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }
        });

        deleteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                searchShopName = search_shop.getText().toString().toUpperCase();

                shopRef.orderByChild("shop").equalTo(searchShopName).removeEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()) {
                            snapshot.getRef().removeValue();
                            Toast.makeText(ViewShops.this, searchShopName+" shop details deleted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ViewShops.this, searchShopName+" shop details unavailable", Toast.LENGTH_SHORT).show();
                        }
                        Intent intent = new Intent(ViewShops.this, ViewShops.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        if(search_shop.getText().toString().length() == 0) {
            page();
        }

    }

    private void page() {

        shopRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                shopList.clear();
                for(DataSnapshot snap: snapshot.getChildren()) {
                    Shop sh = snap.getValue(Shop.class);

                    shopList.add(sh);
                }

                shop_List.setLayoutManager(layoutManager);
                shop_List.setAdapter(sAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }


}