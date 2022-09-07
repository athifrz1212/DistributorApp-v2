package com.blitzco.distributorapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blitzco.distributorapp.R;
import com.blitzco.distributorapp.EditRepair;
import com.blitzco.distributorapp.models.Repair;

import java.util.ArrayList;

public class AdapterRepair extends RecyclerView.Adapter<AdapterRepair.MyRepairViewHolder> {

    ArrayList<Repair> repairList ;
    Context context;

    public AdapterRepair(ArrayList<Repair> repairList, Context context) {
        this.repairList = repairList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterRepair.MyRepairViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_repair,parent,false);
        AdapterRepair.MyRepairViewHolder RepairHolder = new AdapterRepair.MyRepairViewHolder(view);

        return RepairHolder;
    }

    //bind values to the recycler view
    @Override
    public void onBindViewHolder(@NonNull AdapterRepair.MyRepairViewHolder RepairHolder, @SuppressLint("RecyclerView") int position) {
        RepairHolder.MName.setText(repairList.get(position).getModelName());
        RepairHolder.shop_Name.setText(repairList.get(position).getShopName());
        RepairHolder.RDate.setText(repairList.get(position).getReDate());

        RepairHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Repair repa = repairList.get((position));

                Intent i =new Intent(context, EditRepair.class);
                i.putExtra("repairID", repa.getRepairID());
                i.putExtra("agentID", repa.getAgentId());
                i.putExtra("SName", repa.getShopName());
                i.putExtra("BName", repa.getBrandName());
                i.putExtra("MName", repa.getModelName());
                i.putExtra("issue", repa.getIssue());
                i.putExtra("re_Type", repa.getReType());
                i.putExtra("re_Date", repa.getReDate());
                context.startActivity(i);
            }
        });

    }

    //get no of items in the array brand_list
    @Override
    public int getItemCount() {
        return repairList.size();
    }

    //assign UI components(single_product.xml) to variables
    public class MyRepairViewHolder extends RecyclerView.ViewHolder{
        TextView shop_Name, MName, RDate;
        LinearLayout parentLayout;

        public MyRepairViewHolder(@NonNull View itemView) {
            super(itemView);
            MName = itemView.findViewById(R.id.phone_name);
            shop_Name = itemView.findViewById(R.id.shop_name);
            RDate = itemView.findViewById(R.id.RDate);
            parentLayout = itemView.findViewById(R.id.singleRepairLayout);
        }
    }
}
