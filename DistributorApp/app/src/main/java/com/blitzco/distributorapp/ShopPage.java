package com.blitzco.distributorapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blitzco.distributorapp.adapters.AdapterOrderList;
import com.blitzco.distributorapp.models.Order;

import java.util.ArrayList;

public class ShopPage extends AppCompatActivity {

    TextView shopname, balance;
    LinearLayout addBTN, paymentBTN;
    RecyclerView order_list;
    RelativeLayout go_back;

    ArrayList<Order> Order_List = new ArrayList<Order>();
    private RecyclerView.Adapter mAdapter;//view adapter
    private RecyclerView.LayoutManager layoutManager; //view layout manager

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_page);

        balance = findViewById(R.id.balance);
        shopname = findViewById(R.id.ShopName);
        order_list = (RecyclerView) findViewById(R.id.orders_list);
        paymentBTN = findViewById(R.id.paymentBTN);
        addBTN = findViewById(R.id.addBTN);

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
        String SName = i.getStringExtra("txtShopName").toString();
        shopname.setText(SName);

        order_list.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);//assign layout manager
        mAdapter = new AdapterOrderList(Order_List, ShopPage.this); //updateBTN Order_list data to adapter class

        SQLiteDatabase db = openOrCreateDatabase("asianDistributors", Context.MODE_PRIVATE, null);

        final Cursor cBalance = db.rawQuery("SELECT SUM(balance) AS totalBalance FROM payments WHERE Shop_Name='"+SName+"'", null);
        cBalance.moveToFirst();
        int balanceIndex = cBalance.getColumnIndex ( "totalBalance");
        String finalBalanace = cBalance.getString(balanceIndex);
        balance.setText(finalBalanace);

        paymentBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(ShopPage.this, AddPayment.class);
                i.putExtra("txtShopName", SName);
                startActivity(i);
            }
        });

        addBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i= new Intent(ShopPage.this, AddOrder.class);
                i.putExtra("txtShopName", SName);
                startActivity(i);
            }
        });

        final Cursor c = db.rawQuery("SELECT * FROM orders WHERE Shop_Name='"+shopname.getText().toString()+"' ORDER BY orderDate DESC", null);
        int orderID = c.getColumnIndex("orderID");
        int Shop_Name = c.getColumnIndex("Shop_Name");
        int Brand_Name = c.getColumnIndex("Brand_Name");
        int Model_Name = c.getColumnIndex("Model_Name");
        int costPrice = c.getColumnIndex("costPrice");
        int quantity = c.getColumnIndex("quantity");
        int unitPrice = c.getColumnIndex("unitPrice");
        int total = c.getColumnIndex("total");
        int profitIndex = c.getColumnIndex("profit");
        int orderDate = c.getColumnIndex("orderDate");
        int YYYY_MM = c.getColumnIndex("YYYY_MM");

        Order_List.clear();

        if (c.moveToFirst())
        {
            do {
                Order order = new Order();
                order.setOrderID(c.getString(orderID));
                order.setShopName(c.getString(Shop_Name));
                order.setBrandName(c.getString(Brand_Name));
                order.setModelName(c.getString(Model_Name));
                order.setCostPrice(costPrice);
                order.setUnitPrice(unitPrice);
                order.setQuantity(quantity);
                order.setTotalPrice(total);
                order.setProfit(profitIndex);
                order.setdDate(c.getString(orderDate));
                order.setYyyyMM(c.getString(YYYY_MM));

                Order_List.add(order);

            }while (c.moveToNext());
        }
        order_list.setLayoutManager(layoutManager);
        order_list.setAdapter(mAdapter);

    }
}
