package com.blitzco.distributorapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.blitzco.distributorapp.models.Brand;
import com.blitzco.distributorapp.models.Product;
import com.blitzco.distributorapp.models.Repair;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class AddRepair extends AppCompatActivity {

    private EditText txtShopName, txtIssue, txtReceivedType, txtReceivedDate;
    private Button addBTN, cancelBTN;
    private Spinner brandNameSpinner, modelNameSpinner;

    private ArrayList<String> brandsList = new ArrayList<String>();
    private ArrayList<String> modelsList = new ArrayList<String>();
    //load brands from the database
    private ArrayAdapter brandAdapter, modelAdapter;
    private DatabaseReference brandRef, productRef, repairRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_repair);

        txtShopName = findViewById(R.id.SName);
        brandNameSpinner = findViewById(R.id.BName);
        modelNameSpinner = findViewById(R.id.MName);
        txtIssue = findViewById(R.id.Issue);
        txtReceivedType = findViewById(R.id.RType);
        txtReceivedDate = findViewById(R.id.RDate);

        addBTN = findViewById(R.id.addBTN);
        cancelBTN = findViewById(R.id.cancelBTN);

        ///----- DatabaseReference initialization-----
        brandRef = FirebaseDatabase.getInstance().getReference("Brand");
        productRef = FirebaseDatabase.getInstance().getReference("Product");
        repairRef = FirebaseDatabase.getInstance().getReference("Repair");

        brandRef.orderByChild("brandName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap: snapshot.getChildren()) {
                    Brand brand = snap.getValue(Brand.class);
                    brandsList.add(brand.getBrandName());
                }
                brandAdapter.notifyDataSetChanged();
                modelNameSpinner.setActivated(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        brandAdapter = new ArrayAdapter(this,R.layout.spinner_text, brandsList);
        brandAdapter.setDropDownViewResource(R.layout.spinner_text);
        brandNameSpinner.setAdapter(brandAdapter);


        modelAdapter = new ArrayAdapter(this, R.layout.spinner_text, modelsList);
        modelAdapter.setDropDownViewResource(R.layout.spinner_text);
        modelNameSpinner.setAdapter(modelAdapter);

//        productRef.orderByChild("brandName").equalTo(brandNameSpinner.getSelectedItem().toString())
        productRef.orderByChild("brandName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap: snapshot.getChildren()) {
                    Product product = snap.getValue(Product.class);
                    modelsList.add(product.getModelName());
                }
                modelAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        //Date picker setting
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        txtReceivedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddRepair.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month+1;
                        String date = year+"/"+month+"/"+day;
                        txtReceivedDate.setText(date);
                    }
                },year, month, day);
                datePickerDialog.show();
            }
        });

        addBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert();
            }
        });

        cancelBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AddRepair.this, ViewRepairs.class);
                startActivity(intent);
            }
        });

    }

    public void insert()
    {
        try{
            String shopName = txtShopName.getText().toString().toUpperCase();
            String brandName = brandNameSpinner.getSelectedItem().toString().toUpperCase();
            String modelName = modelNameSpinner.getSelectedItem().toString().toUpperCase();
            String issue = txtIssue.getText().toString().toUpperCase();
            String ReceivedType= txtReceivedType.getText().toString().toUpperCase();
            String ReceivedDate= txtReceivedDate.getText().toString().toUpperCase();
            String[] splitDate = ReceivedDate.split("/");
            String yearMonth =splitDate[0]+"/"+splitDate[1];


            Repair repair = new Repair();
            repair.setAgentId(FirebaseAuth.getInstance().getCurrentUser().getUid());
            repair.setBrandName(brandName);
            repair.setShopName(shopName);
            repair.setModelName(modelName);
            repair.setIssue(issue);
            repair.setReType(ReceivedType);
            repair.setReDate(ReceivedDate);
            repair.setYearMonth(yearMonth);

            repairRef.push().setValue(repair);

            Toast.makeText(this, "Repair Added", Toast.LENGTH_LONG).show();

            txtIssue.setText("");
            txtReceivedType.setText("");
            txtReceivedDate.setText("");

            txtShopName.requestFocus();
        }
        catch (Exception ex)
        {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }


}
