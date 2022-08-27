package com.blitzco.distributorapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
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
import com.blitzco.distributorapp.models.Order;
import com.blitzco.distributorapp.models.Payment;
import com.blitzco.distributorapp.models.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddOrder extends AppCompatActivity {

    private EditText txtShopName, txtCost, txtQuantity, txtUnitPrice, txtTotal, txtOrderedDate;
    private Button addBTN, cancelBTN, calcBTN, searchBTN;
    private Spinner brandNameSpinner, modelNameSpinner;

    private ArrayList<String> brandsList = new ArrayList<String>();
    private ArrayList<String> modelsList = new ArrayList<String>();
    //load brands from the database
    private ArrayAdapter brandAdapter, modelAdapter;

    private ArrayList<String> titles = new ArrayList<String>();

    private DatabaseReference brandRef, productRef, userRef, orderRef, paymentRef;
    private long available, availableBalance ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_order);

        txtShopName = findViewById(R.id.SName);
        txtCost = findViewById(R.id.costPrice);
        brandNameSpinner = findViewById(R.id.BName);
        modelNameSpinner = findViewById(R.id.MName);

        txtQuantity = findViewById(R.id.Qty);
        txtUnitPrice = findViewById(R.id.Price);
        txtTotal = findViewById(R.id.Total);
        txtOrderedDate = findViewById(R.id.orderDate);

        addBTN = findViewById(R.id.addBTN);
        cancelBTN = findViewById(R.id.cancelBTN);
        calcBTN = findViewById(R.id.calcBTN);
        searchBTN = findViewById(R.id.searchBTN);
        calcBTN.setBackgroundColor(Color.rgb(24, 104, 101));
        searchBTN.setBackgroundColor(Color.rgb(24, 104, 101));

        ///----- DatabaseReference initialization-----
        brandRef = FirebaseDatabase.getInstance().getReference("Brand");
        productRef = FirebaseDatabase.getInstance().getReference("Product");
        userRef = FirebaseDatabase.getInstance().getReference("User");
        orderRef = FirebaseDatabase.getInstance().getReference("Order");
        paymentRef = FirebaseDatabase.getInstance().getReference("Payment");

        Intent i = getIntent();
        //Values from view page on click
        String ShopName = i.getStringExtra("ShopName");

        txtShopName.setText(ShopName);
///----------------------------
        searchBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(modelNameSpinner != null){
                    productRef.orderByChild("modelName").equalTo(modelNameSpinner.getSelectedItem().toString())
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot snap: snapshot.getChildren()) {
                                System.out.println(" >>>>>>>>>>>>>>>>>>> "+snap.getValue(Product.class).getModelName());
                                txtCost.setText(String.valueOf(snap.getValue(Product.class).getUnitPrice()));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}

                    });
                }
                else{
                    Toast.makeText(AddOrder.this, "Select a phone model.", Toast.LENGTH_SHORT);
                }

            }
        });
///----------------------------
        calcBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    long Quantity = Long.parseLong(txtQuantity.getText().toString());
                    long UnitPrice = Long.parseLong(txtUnitPrice.getText().toString());

                    long totalAmount = Math.multiplyExact(UnitPrice ,Quantity);
                    txtTotal.setText(String.valueOf(totalAmount));

                }catch (Exception ex){
                    Toast.makeText(AddOrder.this, "Please insert valid unit price and quantity", Toast.LENGTH_LONG).show();
                }
            }
        });
//--------------------------------------------------------------------------------------------------
        ///---Brand Names Spinner---

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


///-----Date picker setting-----------------

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        txtOrderedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddOrder.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month+1;
                        String date = year+"/"+month+"/"+day;
                        txtOrderedDate.setText(date);
                    }
                },year, month, day);
                datePickerDialog.show();
            }
        });

        ///-----------------------------------------------------------------------------------------

        addBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });


        cancelBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(AddOrder.this, ShopPage.class);
                i.putExtra("ShopName",ShopName );
                startActivity(i);
            }
        });

    }


    public void insert()
    {
        try{
            String ShopName = txtShopName.getText().toString().toUpperCase();
            String PhoneBrand = brandNameSpinner.getSelectedItem().toString().toUpperCase();
            String PhoneModel = modelNameSpinner.getSelectedItem().toString().toUpperCase();
            long cost_Price = Long.parseLong(txtCost.getText().toString());
            long quantity = Long.parseLong(txtQuantity.getText().toString());
            long UnitPrice = Long.parseLong(txtUnitPrice.getText().toString());
            long TotalPrice = Long.parseLong(txtTotal.getText().toString());
            long profit = TotalPrice - (cost_Price * quantity);
            String OrderDate  = txtOrderedDate.getText().toString();

            String[] dateSplit = OrderDate.split("/");
            String YYYY_MM = dateSplit[0]+"/"+dateSplit[1];

            productRef.orderByChild("modelName").equalTo(PhoneModel).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for(DataSnapshot snap: snapshot.getChildren()) {
                        Product product = snap.getValue(Product.class);
                        available = Long.valueOf(product.getQty());
                    }

                    if(quantity <= available) {

                        orderRef.orderByChild("shopName").equalTo(ShopName)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.getChildrenCount() != 0) {
                                    paymentRef.orderByChild("shopName").equalTo(ShopName).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for(DataSnapshot snap: snapshot.getChildren()) {
                                                Payment payment = snap.getValue(Payment.class);
                                                availableBalance = TotalPrice + payment.getBalance();
                                            }

                                            paymentRef.child(snapshot.getKey()).child("balance").setValue(availableBalance);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                } else {

                                    Payment payment = new Payment();

                                    payment.setShopName(ShopName);
                                    payment.setBalance(TotalPrice);
                                    payment.setLastPaydate(new Date().toString());

                                    paymentRef.push().setValue(payment);


                                    Toast.makeText(AddOrder.this, "New Shop Balance Added", Toast.LENGTH_LONG).show();
                                    Toast.makeText(AddOrder.this, "Order Added", Toast.LENGTH_LONG).show();

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });

/////////
                        Order order = new Order();

                        order.setShopName(ShopName); //bind the values to be in the given "?" place
                        order.setAgentId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        order.setBrandName(PhoneBrand); //bind the values to be in the given "?" place
                        order.setModelName(PhoneModel); //bind the values to be in the given "?" place
                        order.setCostPrice(cost_Price);
                        order.setQuantity(quantity); //bind the values to be in the given "?" place
                        order.setUnitPrice(UnitPrice); //bind the values to be in the given "?" place
                        order.setTotalPrice(TotalPrice);
                        order.setProfit(profit);
                        order.setdDate(OrderDate);
                        order.setYyyyMM(YYYY_MM);

                        orderRef.push().setValue(order);

                        long newQty = available - quantity;

                        productRef.orderByChild("modelName").equalTo(PhoneModel).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot snap: snapshot.getChildren()) {
                                    snap.getRef().child("quantity").setValue(String.valueOf(newQty));
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        modelNameSpinner.setSelection(0);
                        txtQuantity.setText("");
                        txtUnitPrice.setText("");
                        txtTotal.setText("");

                        Intent i =new Intent(AddOrder.this, ShopPage.class);
                        i.putExtra("ShopName",ShopName );
                        startActivity(i);
                    }
                    else {
                        txtQuantity.setText(String.valueOf(available));
                        Toast.makeText(AddOrder.this, "Available quantity "+available, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        catch (Exception ex)
        {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

}
