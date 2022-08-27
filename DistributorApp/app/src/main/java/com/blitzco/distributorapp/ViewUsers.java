package com.blitzco.distributorapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blitzco.distributorapp.adapters.AdapterUsers;
import com.blitzco.distributorapp.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewUsers extends AppCompatActivity {

    private RecyclerView users_list;
    private RelativeLayout go_back;
    private String currentUserRole;
    private ImageView addBTN;
    private FirebaseAuth fAuth;
    private DatabaseReference dbRef;

    private ArrayList<User> userList = new ArrayList<User>();
    private RecyclerView.Adapter mAdapter;//view adapter
    private RecyclerView.LayoutManager layoutManager; //view layout manager

    @Override
    protected void
    onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_user);

        users_list = (RecyclerView) findViewById(R.id.user_list);
        users_list.setHasFixedSize(true);

        addBTN = findViewById(R.id.addBTN);
//        addBTN.setVisibility(View.INVISIBLE);

        layoutManager = new LinearLayoutManager(this);//assign layout manager
        mAdapter = new AdapterUsers(userList, ViewUsers.this);

        go_back = findViewById(R.id.go_back);

        fAuth = FirebaseAuth.getInstance();
        FirebaseUser fUser = fAuth.getCurrentUser();

        dbRef = FirebaseDatabase.getInstance().getReference("User");
        dbRef.child(fUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {

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

        addBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ViewUsers.this, AddUser.class);
                startActivity(intent);
            }
        });

        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentUserRole.equals("ADMIN")) {
                    Intent intent= new Intent(ViewUsers.this, AdminHome.class);
                    startActivity(intent);
                } else if(currentUserRole.equals("AGENT")) {
                    Intent intent= new Intent(ViewUsers.this, AgentHome.class);
                    startActivity(intent);
                }
            }
        });

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("User");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    userList.clear();
                for (DataSnapshot usr: snapshot.getChildren()) {
                    User user = usr.getValue(User.class);
                    user.setUserID(usr.getKey());
                    userList.add(user);
                }
                users_list.setLayoutManager(layoutManager);
                users_list.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}