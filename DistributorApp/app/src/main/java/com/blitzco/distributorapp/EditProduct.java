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

public class EditProduct extends AppCompatActivity {

    EditText proID, MName, CostPrice, Qty, BName;
    Button add, delete, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_product);

        Intent i =getIntent();

        //Values from view page on click
        String id = i.getStringExtra("productID").toString();
        String BrandName = i.getStringExtra("brand_Name").toString();
        String ModelName = i.getStringExtra("model_Name").toString();
        String CPrice = i.getStringExtra("cost_price").toString();
        String qty = i.getStringExtra("quantity").toString();

        //text fields
        proID = findViewById(R.id.proID);
        proID.setEnabled(false);
        BName = findViewById(R.id.BName);
        MName = findViewById(R.id.MName);
        Qty = findViewById(R.id.Qty);
        Qty.setFocusable(true);
        CostPrice = findViewById(R.id.CostPrice);

        add = findViewById(R.id.updateBTN);
        cancel = findViewById(R.id.cancelBTN);
        delete = findViewById(R.id.deleteBTN);

        proID.setText(id);
        BName.setText(BrandName);
        MName.setText(ModelName);
        CostPrice.setText(CPrice);
        Qty.setText(qty);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { update(); }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { delete(); }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String brandName = BName.getText().toString().toUpperCase();
                Intent i =new Intent(EditProduct.this, ViewProduct.class);
                i.putExtra("brand_Name",brandName );
                startActivity(i);
            }
        });

    }

    public void update()
    {
        String productID = proID.getText().toString();
        String brandName = BName.getText().toString().toUpperCase();
        String modelName = MName.getText().toString().toUpperCase();
        String costPrice = CostPrice.getText().toString();
        String qty = Qty.getText().toString();
//        int costPrice = Integer.parseInt(CostPrice.getText().toString());
//        int qty = Integer.parseInt(Qty.getText().toString());

        if((modelName!=null)&& (Integer.parseInt(costPrice)!=0) && (Integer.parseInt(qty)>0)) {

            HashMap product = new HashMap();
            product.put("brandName",brandName);
            product.put("modelName",modelName);
            product.put("unitPrice",costPrice);
            product.put("qty",qty);

            DatabaseReference pdbRef = FirebaseDatabase.getInstance().getReference("Product");
            pdbRef.child(productID).updateChildren(product).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isComplete()) {
                        Toast.makeText(EditProduct.this, "Product Updated", Toast.LENGTH_LONG).show();
                        String brandname = BName.getText().toString().toUpperCase();
                        Intent i =new Intent(EditProduct.this, ViewProduct.class);
                        i.putExtra("brand_Name",brandname );
                        startActivity(i);
                    } else{
                        Toast.makeText(EditProduct.this, "Update canceled", Toast.LENGTH_LONG).show();
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
//        int productID = Integer.parseInt(proID.getText().toString());

        String productID = proID.getText().toString();

        DatabaseReference pdbRef = FirebaseDatabase.getInstance().getReference("Product");
        pdbRef.child(productID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(EditProduct.this, "Product Deleted", Toast.LENGTH_LONG).show();

                    Intent i =new Intent(EditProduct.this, ViewBrand.class);
                    startActivity(i);
                } else {
                    Toast.makeText(EditProduct.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
