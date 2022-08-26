package com.blitzco.distributorapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.blitzco.distributorapp.models.Brand;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class add_brand extends AppCompatActivity {
    DatabaseReference dbRef;
    EditText BName, SellerName, Address, CNo;
    Button add, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_brand);

        BName = findViewById(R.id.BName);
        SellerName = findViewById(R.id.SellerName);
        Address = findViewById(R.id.Addr);
        CNo = findViewById(R.id.CNo);

        add = findViewById(R.id.addBTN);
        cancel = findViewById(R.id.cancelBTN);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(add_brand.this,view_brand.class);
                startActivity(intent);
            }
        });
    }

    public void insert()
    {
        try {
            String brandName = BName.getText().toString().toUpperCase();
            String sellerName = SellerName.getText().toString().toUpperCase();
            String address = Address.getText().toString().toUpperCase();
            long contactNumber = Long.parseLong(CNo.getText().toString());

            Brand brand = new Brand();
            dbRef = FirebaseDatabase.getInstance().getReference("Brand");

            brand.setBrandName(brandName);
            brand.setSellerName(sellerName);
            brand.setAddress(address);
            brand.setContactNumber(String.valueOf(contactNumber));

            dbRef.push().setValue(brand);

//            SQLiteDatabase db = openOrCreateDatabase("asianDistributors", Context.MODE_PRIVATE, null); //create database if doesn't exist
            //db.execSQL("DROP TABLE 'brand'");
//            db.execSQL("CREATE TABLE IF NOT EXISTS 'brand' ('brandID' INTEGER PRIMARY KEY AUTOINCREMENT, 'brand_Name' TEXT,'seller_Name' TEXT,'address' TEXT,'contact_Number' Number)");
            //create table
//            String query= "INSERT INTO 'brand' ('brand_Name','seller_Name','address','contact_Number') VALUES (?,?,?,?)";
            //query to insert
//            SQLiteStatement statement = db.compileStatement(query);
            //enter query to the sqlite

//            statement.bindString(1,brandName); //bind the values to be in the given "?" place
//            statement.bindString(2,sellerName); //bind the values to be in the given "?" place
//            statement.bindString(3,address); //bind the values to be in the given "?" place
//            statement.bindLong(4,contactNumber); //bind the values to be in the given "?" place
//            statement.execute(); //execute the query
            Toast.makeText(this, "Brand Added", Toast.LENGTH_LONG).show();

            BName.setText("");
            SellerName.setText("");
            Address.setText("");
            CNo.setText("");
            BName.requestFocus();
        }
        catch (Exception ex)
        {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }


}