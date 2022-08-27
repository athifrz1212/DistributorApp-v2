package com.blitzco.distributorapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.blitzco.distributorapp.models.Product;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class add_product extends AppCompatActivity {

    EditText BName,MName, CostPrice, Qty;
    Button add, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);

        //Setting relevant input fields to the variables
        BName = findViewById(R.id.BName);
        MName = findViewById(R.id.MName);
        Qty = findViewById(R.id.Qty);
        CostPrice = findViewById(R.id.CostPrice);

        add = findViewById(R.id.addBTN);
        cancel = findViewById(R.id.cancelBTN);

        String brand_name = getIntent().getStringExtra("brandName").toString();
        BName.setText(brand_name);
        //brand.clear(); //clear the ArrayList


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(add_product.this, view_product.class);
                intent.putExtra("brand_Name",brand_name );
                startActivity(intent);
            }
        });

    }

    public void insert()
    {
        try{
            String brandName = BName.getText().toString().toUpperCase();
            String modelName = MName.getText().toString().toUpperCase();
            String costPrice = CostPrice.getText().toString();
            String qty = Qty.getText().toString();

            if((modelName!=null)&& (Long.valueOf(costPrice)!=0) && (Long.valueOf(qty)>0)) {
                Product product = new Product();
                product.setBrandName(brandName);
                product.setModelName(modelName);
                product.setUnitPrice(costPrice);
                product.setQty(qty);

                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Product");
                dbRef.push().setValue(product);

                Toast.makeText(this, "Product Added", Toast.LENGTH_LONG).show();

                MName.setText("");
                CostPrice.setText("");
                Qty.setText("");
                MName.requestFocus();
            }else{
                Toast.makeText(this, "Please insert valid details", Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }


}
