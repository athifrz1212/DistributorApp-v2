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

import com.blitzco.distributorapp.adapters.AdapterOrderList;
import com.blitzco.distributorapp.models.Order;
import com.blitzco.distributorapp.models.Payment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShopPage extends AppCompatActivity {

    private TextView txtShopName, txtBalance;
    private LinearLayout addBTN, paymentBTN;
    private RecyclerView order_list;
    private RelativeLayout go_back;

    private ArrayList<Order> Order_List = new ArrayList<Order>();
    private RecyclerView.Adapter mAdapter;//view adapter
    private RecyclerView.LayoutManager layoutManager; //view layout manager

    private DatabaseReference orderRef, paymentRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_page);

        txtBalance = findViewById(R.id.balance);
        txtShopName = findViewById(R.id.ShopName);
        order_list = (RecyclerView) findViewById(R.id.orders_list);
        paymentBTN = findViewById(R.id.paymentBTN);
        addBTN = findViewById(R.id.addBTN);

        orderRef = FirebaseDatabase.getInstance().getReference("Order");
        paymentRef = FirebaseDatabase.getInstance().getReference("Payment");

        go_back = findViewById(R.id.go_back);

        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ShopPage.this, ViewShops.class);
                startActivity(intent);
            }
        });

        Intent i = getIntent();
        //Values from view page on click
        String shopName = i.getStringExtra("ShopName").toString();
        txtShopName.setText(shopName);

        order_list.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);//assign layout manager
        mAdapter = new AdapterOrderList(Order_List, ShopPage.this); //updateBTN Order_list data to adapter class

        paymentRef.orderByChild("shopName").equalTo(shopName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getChildrenCount() != 0) {
                    txtBalance.setText(String.valueOf(snapshot.getValue(Payment.class).getBalance()));
                } else {
                    txtBalance.setText("0");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        paymentBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(ShopPage.this, AddPayment.class);
                i.putExtra("ShopName", shopName);
                startActivity(i);
            }
        });

        addBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i= new Intent(ShopPage.this, AddOrder.class);
                i.putExtra("ShopName", shopName);
                startActivity(i);
            }
        });

        orderRef.orderByChild("shopName").equalTo(shopName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Order_List.clear();
                for(DataSnapshot snap: snapshot.getChildren()) {
                    Order order = snap.getValue(Order.class);
                    Order_List.add(order);
                }
                order_list.setLayoutManager(layoutManager);
                order_list.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

    }
}
