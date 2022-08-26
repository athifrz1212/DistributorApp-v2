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
import com.blitzco.distributorapp.edit_order;
import com.blitzco.distributorapp.models.Order;

import java.util.ArrayList;

public class adapterOrderList extends RecyclerView.Adapter<adapterOrderList.MyShopPageViewHolder> {

    ArrayList<Order> orderList;
    Context context;

    public adapterOrderList(ArrayList<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public adapterOrderList.MyShopPageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_order,parent,false);
        adapterOrderList.MyShopPageViewHolder ShopHolder = new adapterOrderList.MyShopPageViewHolder(view);

        return ShopHolder;
    }

    //bind values to the recycler view
    @Override
    public void onBindViewHolder(@NonNull adapterOrderList.MyShopPageViewHolder ShopHolder, @SuppressLint("RecyclerView") int position) {
        ShopHolder.model_name.setText(orderList.get(position).getModelName());
        ShopHolder.total.setText("Rs. "+orderList.get(position).getTotalPrice());
        ShopHolder.qty.setText(orderList.get(position).getQuantity());
        ShopHolder.DDate.setText(orderList.get(position).getdDate());

        ShopHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order orders = orderList.get((position));

                Intent i =new Intent(context, edit_order.class);

                i.putExtra("orderID", orders.getOrderID());
                i.putExtra("shopName", orders.getShopName());
                i.putExtra("brandName", orders.getBrandName());
                i.putExtra("modelName", orders.getModelName());
                i.putExtra("costPrice", orders.getCostPrice());
                i.putExtra("unitPrice", orders.getUnitPrice());
                i.putExtra("quantity", orders.getQuantity());
                i.putExtra("totalPrice", orders.getTotalPrice());
                i.putExtra("profit", orders.getProfit());
                i.putExtra("DDate", orders.getdDate());
                context.startActivity(i);
            }
        });

    }

    //get no of items in the array brand_list
    @Override
    public int getItemCount() {
        return orderList.size();
    }

    //assign UI components(single_product.xml) to variables
    public class MyShopPageViewHolder extends RecyclerView.ViewHolder{
        TextView model_name, total, qty, DDate;
        LinearLayout parentLayout;

        public MyShopPageViewHolder(@NonNull View itemView) {
            super(itemView);
            model_name = itemView.findViewById(R.id.model_name);
            total = itemView.findViewById(R.id.total);
            qty = itemView.findViewById(R.id.qty);
            DDate = itemView.findViewById(R.id.DDate);
            parentLayout = itemView.findViewById(R.id.singleOrderLayout);
        }
    }
}
