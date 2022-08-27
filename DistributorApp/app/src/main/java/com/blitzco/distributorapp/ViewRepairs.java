package com.blitzco.distributorapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blitzco.distributorapp.adapters.AdapterRepair;
import com.blitzco.distributorapp.models.Repair;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewRepairs extends AppCompatActivity {
    private RecyclerView repair_list;
    private ImageView addBTN;
    private RelativeLayout go_back;
    private String currentUserRole;

    private ArrayList<Repair> repairList= new ArrayList<Repair>();

    private RecyclerView.Adapter mAdapter;//view adapter
    private RecyclerView.LayoutManager layoutManager; //view layout manager

    private DatabaseReference repairRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_repairs);
        repair_list = (RecyclerView) findViewById(R.id.repair_list);
        repair_list.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);//assign layout manager
        mAdapter = new AdapterRepair(repairList, ViewRepairs.this); //updateBTN brand_list data to adapter class

        go_back = findViewById(R.id.go_back);

        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentUserRole.equals("ADMIN")) {
                    Intent intent= new Intent(ViewRepairs.this, AdminHome.class);
                    startActivity(intent);
                } else if(currentUserRole.equals("AGENT")) {
                    Intent intent= new Intent(ViewRepairs.this, AgentHome.class);
                    startActivity(intent);
                }
            }
        });

        addBTN = findViewById(R.id.addBTN);

        addBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ViewRepairs.this, AddRepair.class);
                startActivity(intent);
            }
        });

        repairRef = FirebaseDatabase.getInstance().getReference("Repair");
        repairRef.orderByChild("agentId").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                proList.clear();
                for (DataSnapshot snap: snapshot.getChildren()) {
                    Repair repair = snap.getValue(Repair.class);
                    repair.setRepairID(snap.getKey());
                    repairList.add(repair);
                }
                repair_list.setLayoutManager(layoutManager);
                repair_list.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        SQLiteDatabase db = openOrCreateDatabase("asianDistributors", Context.MODE_PRIVATE, null);
        //create database if doesn't exist

        final Cursor c = db.rawQuery("SELECT * FROM repairs ORDER BY ReDate DESC", null);
        //cursor is used to fetch data from the database
        int id=c.getColumnIndex("repairID");//getting the column id from the database
        int SName = c.getColumnIndex("shop_Name");
        int BName = c.getColumnIndex("brand_Name");
        int MName=c.getColumnIndex("model_Name");
        int issue =c.getColumnIndex("issue");
        int re_type =c.getColumnIndex("ReType");
        int re_date =c.getColumnIndex("ReDate");


        if(c.moveToFirst())
        {
            do{

                Repair re = new Repair();
                re.setRepairID(c.getString(id));
                re.setShopName(c.getString(SName));
                re.setBrandName(c.getString(BName));
                re.setModelName(c.getString(MName));
                re.setIssue(c.getString(issue));
                re.setReType(c.getString(re_type));
                re.setReDate(c.getString(re_date));

                repairList.add(re);

            }while(c.moveToNext());
        }
        repair_list.setLayoutManager(layoutManager);
        repair_list.setAdapter(mAdapter);

    }

}
