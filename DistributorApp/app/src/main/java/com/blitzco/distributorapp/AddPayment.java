package com.blitzco.distributorapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.blitzco.distributorapp.models.Payment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;

public class AddPayment extends AppCompatActivity {

    private EditText txtShopName, txtAmount, txtPaymentDate;
    private Button payBTN, cancelBTN;

    private DatabaseReference paymentRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_payment);

        txtShopName = findViewById(R.id.ShopName);
        txtAmount = findViewById(R.id.amount);
        txtPaymentDate = findViewById(R.id.paymentDate);

        payBTN = findViewById(R.id.payBTN);
        cancelBTN = findViewById(R.id.cancelBTN);

        Intent i = getIntent();
        //Values from view page on click
        String SName = i.getStringExtra("ShopName").toString();

        paymentRef = FirebaseDatabase.getInstance().getReference("Payment");

        ///---Date picker setting
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        txtPaymentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddPayment.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month+1;
                        String date = year+"/"+month+"/"+day;
                        txtPaymentDate.setText(date);
                    }
                },year, month, day);
                datePickerDialog.show();
            }
        });

        ///--------------------------------------------------
        txtShopName.setText(SName);

        payBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pay();
            }
        });

        cancelBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(AddPayment.this, ShopPage.class);
                i.putExtra("ShopName",SName );
                startActivity(i);
            }
        });

    }

    void pay()
    {
        String shopName = txtShopName.getText().toString().toUpperCase();
        long amount = Integer.parseInt(txtAmount.getText().toString().toUpperCase());
        String date = txtPaymentDate.getText().toString();

        paymentRef.orderByChild("shopName").equalTo(shopName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Payment payment1 = snapshot.getValue(Payment.class);
                long availableBalance = payment1.getBalance() - amount;

                HashMap payment = new HashMap();

                payment.put("paymentId", payment1.getPaymentId());
                payment.put("shopName", payment1.getShopName());
                payment.put("balance", availableBalance);
                payment.put("lastPaydate", date);

                paymentRef.child(snapshot.getKey()).updateChildren(payment).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        Toast.makeText(AddPayment.this, amount+" Deducted. "+availableBalance+" is available", Toast.LENGTH_LONG).show();

                        Intent i =new Intent(AddPayment.this, ShopPage.class);
                        i.putExtra("ShopName",shopName );
                        startActivity(i);
                    }
                });

                txtAmount.setText("");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

    }
}
