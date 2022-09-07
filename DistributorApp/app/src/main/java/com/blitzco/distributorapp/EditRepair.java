package com.blitzco.distributorapp;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class EditRepair extends AppCompatActivity {
    private EditText txtAgentID, txtRepairID, txtShopName, txtModelName,txtIssue,txtReceivedType,txtReceivedDate, txtBrandName;
    private Button update, delete, cancel;

    private DatabaseReference repairRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_repair);

        repairRef = FirebaseDatabase.getInstance().getReference("Repair");

        Intent i = getIntent();

        //Values from view page on click
        String id = i.getStringExtra("repairID").toString();
        String agentID = i.getStringExtra("agentID").toString();
        String shopName = i.getStringExtra("ShopName").toString();
        String brandName = i.getStringExtra("BName").toString();
        String modelName = i.getStringExtra("MName").toString();
        String issue = i.getStringExtra("issue").toString();
        String receivedType = i.getStringExtra("re_Type").toString();
        String receivedDate = i.getStringExtra("re_Date").toString();

        //text fields
        txtAgentID = findViewById(R.id.agentID);
        txtRepairID = findViewById(R.id.repairID);
        txtShopName = findViewById(R.id.SName);
        txtBrandName = findViewById(R.id.BName);
        txtModelName = findViewById(R.id.MName);
        txtIssue = findViewById(R.id.Issue);
        txtReceivedType = findViewById(R.id.RType);
        txtReceivedDate = findViewById(R.id.RDate);


        update = findViewById(R.id.updateBTN);
        cancel = findViewById(R.id.cancelBTN);
        delete = findViewById(R.id.deleteBTN);

        txtAgentID.setText(agentID);
        txtRepairID.setText(id);
        txtShopName.setText(shopName);
        txtBrandName.setText(brandName);
        txtModelName.setText(modelName);
        txtIssue.setText(issue);
        txtReceivedType.setText(receivedType);
        txtReceivedDate.setText(receivedDate);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
                Intent intent= new Intent(EditRepair.this, ViewRepairs.class);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
                Intent intent= new Intent(EditRepair.this, ViewRepairs.class);
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(EditRepair.this, ViewRepairs.class);
                startActivity(intent);
            }
        });

    }

    public void update()
    {
        String repairID = txtRepairID.getText().toString();
        String agentid = txtAgentID.getText().toString().toUpperCase();
        String shopname = txtShopName.getText().toString().toUpperCase();
        String brandname = txtBrandName.getText().toString().toUpperCase();
        String modelname = txtModelName.getText().toString().toUpperCase();
        String issueType = txtIssue.getText().toString().toUpperCase();
        String receivedtype = txtReceivedType.getText().toString().toUpperCase();
        String receiveddate = txtReceivedDate.getText().toString().toUpperCase();
        String[] splitDate = receiveddate.split("/");
        String yearMonth = splitDate[0]+"/"+splitDate[1];

        if((modelname!=null)&& (brandname!=null) && (issueType!=null)) {

            HashMap repair = new HashMap();
            repair.put("repairID", repairID);
            repair.put("agentId", agentid);
            repair.put("shopName", shopname);
            repair.put("brandName", brandname);
            repair.put("modelName", modelname);
            repair.put("issue", issueType);
            repair.put("reType", receivedtype);
            repair.put("reDate", receiveddate);
            repair.put("yearMonth", yearMonth);

            repairRef.child(repairID).setValue(repair);

            Toast.makeText(this, "Repair Updated", Toast.LENGTH_LONG).show();

            Intent intent= new Intent(EditRepair.this, ViewRepairs.class);
            startActivity(intent);

        }else
        {
            Toast.makeText(this, "Please insert valid details", Toast.LENGTH_LONG).show();
        }
    }


    public void delete()
    {
        String repairID = txtRepairID.getText().toString();

        SQLiteDatabase db = openOrCreateDatabase("asianDistributors", Context.MODE_PRIVATE, null); //create database if doesn't exist
        //db.execSQL("DROP TABLE 'repairs'");
        if(repairID!= null) {
            db.execSQL("CREATE TABLE IF NOT EXISTS repairs ('repairID' INTEGER PRIMARY KEY AUTOINCREMENT,'shop_Name' TEXT, 'brand_Name' TEXT, 'model_Name' TEXT, 'issue' TEXT, 'ReType' TEXT, 'ReDate' TEXT)");
            //create table
            String query = "DELETE FROM repairs  WHERE repairID = " + repairID;
            //query to insert
            db.execSQL(query);//execute query

            Toast.makeText(this, "Repair Deleted", Toast.LENGTH_LONG).show();

            txtRepairID.setText("");
            txtShopName.setText("");
            txtBrandName.setText("");
            txtModelName.setText("");
            txtIssue.setText("");
            txtReceivedType.setText("");
            txtReceivedDate.setText("");
            
            Intent intent= new Intent(EditRepair.this, ViewRepairs.class);
            startActivity(intent);
        }else
        {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

}
