package com.blitzco.distributorapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class EditBrand extends AppCompatActivity {
    EditText txtBrandID,txtBrandName, txtSellerName, txtAddress, txtContactNo;
    Button add, cancel, delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_brand);

        Intent i = getIntent();

        //Values from view page on click
        String id = i.getStringExtra("brandID").toString();
        String BrandName = i.getStringExtra("brand_Name").toString();
        String SellerName = i.getStringExtra("seller_Name").toString();
        String Address = i.getStringExtra("address").toString();
        String CNo = i.getStringExtra("contact").toString();

        //text fields
        txtBrandID = findViewById(R.id.brandID);
        txtBrandID.setEnabled(false);
        txtBrandName = findViewById(R.id.BName);
        txtSellerName = findViewById(R.id.SellerName);
        txtAddress = findViewById(R.id.Address);
        txtContactNo = findViewById(R.id.cno);

        add = findViewById(R.id.updateBTN);
        cancel = findViewById(R.id.cancelBTN);
        delete = findViewById(R.id.deleteBTN);

        txtBrandID.setText(id);
        txtBrandName.setText(BrandName);
        txtSellerName.setText(SellerName);
        txtAddress.setText(Address);
        txtContactNo.setText(CNo);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditBrand.this, ViewBrand.class);
                startActivity(intent);
            }
        });
    }

    public void update()
    {
        String brandID = txtBrandID.getText().toString();
        String brandName = txtBrandName.getText().toString().toUpperCase();
        String seName = txtSellerName.getText().toString().toUpperCase();
        String addr = txtAddress.getText().toString();
        String contactNo = txtContactNo.getText().toString();


        if((seName!=null)&& (addr!=null) && (addr!=null)) {

            HashMap brand = new HashMap();
            brand.put("brandID", brandID);
            brand.put("address", addr);
            brand.put("brandName", brandName);
            brand.put("contactNumber", contactNo);
            brand.put("sellerName", seName);

            DatabaseReference pdbRef = FirebaseDatabase.getInstance().getReference("Brand");
            pdbRef.child(brandID).updateChildren(brand).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isComplete()) {
                        Toast.makeText(EditBrand.this, "Brand Details Updated", Toast.LENGTH_LONG).show();

                        Intent intent= new Intent(EditBrand.this, ViewBrand.class);
                        startActivity(intent);
                    } else{
                        Toast.makeText(EditBrand.this, "Update canceled", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }else
        {
            Toast.makeText(this, "Please insert valid details", Toast.LENGTH_LONG).show();
        }
    }


    public void delete()
    {
        String BrandID = txtBrandID.getText().toString();

        DatabaseReference pdbRef = FirebaseDatabase.getInstance().getReference("Brand");
        pdbRef.child(BrandID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(EditBrand.this, "Brand Deleted", Toast.LENGTH_LONG).show();

                    Intent i =new Intent(EditBrand.this, ViewBrand.class);
                    startActivity(i);
                } else {
                    Toast.makeText(EditBrand.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
