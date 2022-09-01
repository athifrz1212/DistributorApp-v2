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

import com.blitzco.distributorapp.adapters.AdapterShopLocation;
import com.blitzco.distributorapp.models.Shop;
import com.blitzco.distributorapp.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewShopRoute extends AppCompatActivity {

    static String searchShopName;
    RecyclerView shop_List;
    Button mapSearchBTN;
    EditText search_shop;
    RelativeLayout go_back;

    ArrayList<Shop> shopList= new ArrayList<Shop>();

    private RecyclerView.Adapter sAdapter;//view adapter
    private RecyclerView.LayoutManager layoutManager; //view layout manager

    private FirebaseAuth fAuth;

    private String currentUserRole;
    private String agent = "AGENT";
    private String admin = "ADMIN";
    private DatabaseReference userRef, shopRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_route);

        search_shop = findViewById(R.id.serch_shop);
        mapSearchBTN = findViewById(R.id.btnMap);

        shop_List = (RecyclerView) findViewById(R.id.shopList);
        shop_List.setHasFixedSize(true);

        shopRef = FirebaseDatabase.getInstance().getReference("Shop");

        layoutManager = new LinearLayoutManager(this);//assign layout manager
        sAdapter = new AdapterShopLocation(shopList, ViewShopRoute.this); //updateBTN brand_list data to adapter class

        fAuth = FirebaseAuth.getInstance();
        FirebaseUser fUser = fAuth.getCurrentUser();

        userRef = FirebaseDatabase.getInstance().getReference("User");
        userRef.child(fUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                currentUserRole = user.getRole();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        go_back = findViewById(R.id.go_back);

        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentUserRole.equals(admin)) {
                    System.out.println(" >>>>>>>>>>>>>> ========== User Model: "+currentUserRole);
                    Intent intent= new Intent(ViewShopRoute.this, AdminHome.class);
                    startActivity(intent);
                } else if(currentUserRole.equals(agent)) {
                    System.out.println(" >>>>>>>>>>>>>> ========== User Model: "+currentUserRole);
                    Intent intent= new Intent(ViewShopRoute.this, AgentHome.class);
                    startActivity(intent);
                }
            }
        });


        mapSearchBTN.setOnClickListener(new View.OnClickListener() {
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
                                Toast.makeText(ViewShopRoute.this, searchShopName+" shop unavailable", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
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
                if(snapshot.getChildrenCount() != 0) {
                    for(DataSnapshot snap: snapshot.getChildren()) {
                        Shop sh = snap.getValue(Shop.class);

                        shopList.add(sh);
                    }

                    shop_List.setLayoutManager(layoutManager);
                    shop_List.setAdapter(sAdapter);
                } else {
                    Toast.makeText(ViewShopRoute.this, "No locations saved", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

    }



}