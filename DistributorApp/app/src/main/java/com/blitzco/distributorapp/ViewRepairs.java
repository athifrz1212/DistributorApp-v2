package com.blitzco.distributorapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blitzco.distributorapp.adapters.AdapterRepair;
import com.blitzco.distributorapp.models.Repair;
import com.blitzco.distributorapp.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private String agent = "AGENT";
    private String admin = "ADMIN";

    private ArrayList<Repair> repairList= new ArrayList<Repair>();

    private RecyclerView.Adapter mAdapter;//view adapter
    private RecyclerView.LayoutManager layoutManager; //view layout manager

    private FirebaseAuth fAuth;
    private DatabaseReference repairRef, userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_repairs);
        repair_list = (RecyclerView) findViewById(R.id.repair_list);
        repair_list.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);//assign layout manager
        mAdapter = new AdapterRepair(repairList, ViewRepairs.this); //updateBTN brand_list data to adapter class

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                repairList.clear();
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
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        };

        fAuth = FirebaseAuth.getInstance();
        FirebaseUser fUser = fAuth.getCurrentUser();

        repairRef = FirebaseDatabase.getInstance().getReference("Repair");

        userRef = FirebaseDatabase.getInstance().getReference("User");
        userRef.child(fUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                currentUserRole = user.getRole();
                if(user.getRole().equals(admin)) {
                    repairRef.orderByChild("modelName").addListenerForSingleValueEvent(valueEventListener);

                } else if(user.getRole().equals(agent)) {
                    repairRef.orderByChild("agentId").equalTo(fUser.getUid())
                    .addListenerForSingleValueEvent(valueEventListener);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        go_back = findViewById(R.id.go_back);

        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentUserRole.equals(admin)) {
                    Intent intent= new Intent(ViewRepairs.this, AdminHome.class);
                    startActivity(intent);
                } else if(currentUserRole.equals(agent)) {
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

    }

}
