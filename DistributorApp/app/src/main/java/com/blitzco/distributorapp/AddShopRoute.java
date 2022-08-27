package com.blitzco.distributorapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.blitzco.distributorapp.models.Shop;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddShopRoute extends AppCompatActivity {

    EditText ShopName, Area,Address, ContactNo;
    Button addBTN, cancelBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_shop_route);

        ShopName = findViewById(R.id.SName);
        Area = findViewById(R.id.AreaName);
        Address = findViewById(R.id.Address);
        ContactNo = findViewById(R.id.CNo);

        addBTN = findViewById(R.id.addBTN);
        cancelBTN = findViewById(R.id.cancelBTN);

        addBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });

        cancelBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AddShopRoute.this, AdminHome.class);
                startActivity(intent);
            }
        });

    }

    public void insert() {
        if (ShopName != null && Area != null && Address != null && ContactNo != null)
        {
            try {

                String shopName = ShopName.getText().toString().toUpperCase();
                String area = Area.getText().toString().toUpperCase();
                String address = Address.getText().toString().toUpperCase();
                String contact = ContactNo.getText().toString();

                if (shopName.length() != 0 && address.length() != 0 && contact.length() != 0 && area.length() != 0) {
                    try {

                        Shop shop = new Shop();
                        shop.setShop(shopName);
                        shop.setContact(contact);
                        shop.setAddress(address);
                        shop.setArea(area);

                        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Shop");
                        dbRef.push().setValue(shop);

                        Toast.makeText(this, "Shop details saved", Toast.LENGTH_LONG).show();

                        ShopName.setText("");
                        Area.setText("");
                        Address.setText("");
                        ContactNo.setText("");
                        ShopName.requestFocus();
                    } catch (Exception e) {
                        Toast.makeText(this, "Make sure the phone number is valid", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "Make sure no fields are empty", Toast.LENGTH_LONG).show();
                }
            } catch (Exception ex) {
                Toast.makeText(this, "Saving failed", Toast.LENGTH_LONG).show();
            }
        }
    }
}