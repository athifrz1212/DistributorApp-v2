package com.blitzco.distributorapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AgentHome extends AppCompatActivity {

    LinearLayout view_Repair, inventoryBTN,
            view_route, view_map, shops;
    TextView balance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        balance = findViewById(R.id.balance);

        SQLiteDatabase db = openOrCreateDatabase("asianDistributors", Context.MODE_PRIVATE, null);

        //--repairs Table
        //db.execSQL("DROP TABLE repairs");
        db.execSQL("CREATE TABLE IF NOT EXISTS 'repairs' ('repairID' INTEGER PRIMARY KEY AUTOINCREMENT,'shop_Name' TEXT, 'brand_Name' TEXT, 'model_Name' TEXT, 'issue' TEXT, 'ReType' TEXT, 'ReDate' TEXT, 'YYYY_MM' TEXT)");

    ///---End of table creation---

        final Cursor cBalance = db.rawQuery("SELECT SUM(balance) AS 'totalBalance' FROM payments ", null);
        cBalance.moveToFirst();

        if(cBalance.getCount() > 0)
        {
            int balanceIndex = cBalance.getColumnIndex ( "totalBalance");
            balance.setText(cBalance.getString(balanceIndex));
        }
        else{
            balance.setText("0");
        }



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