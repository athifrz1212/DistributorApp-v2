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

import com.blitzco.distributorapp.EditProduct;
import com.blitzco.distributorapp.R;
import com.blitzco.distributorapp.models.Product;

import java.util.List;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.MyProductViewHolder> {

    List<Product> proList ;
    Context context;

    public AdapterProduct(List<Product> proList, Context context) {
        this.proList = proList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_product,parent,false);
        MyProductViewHolder ProductHolder = new MyProductViewHolder(view);

        return ProductHolder;
    }

    //bind values to the recycler view
    @Override
    public void onBindViewHolder(@NonNull MyProductViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.pro_Name.setText(proList.get(position).getModelName());
        holder.pro_qty.setText(proList.get(position).getQty());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Product pro = proList.get((position));

                //Intent i =new Intent(getApplicationContext(),EditProduct.class);
                Intent i =new Intent(context, EditProduct.class);
                i.putExtra("productID", pro.getId());
                i.putExtra("brand_Name", pro.getBrandName());
                i.putExtra("model_Name", pro.getModelName());
                i.putExtra("cost_price", pro.getUnitPrice());
                i.putExtra("quantity", pro.getQty());
                context.startActivity(i);
            }
        });

    }

    //get no of items in the array brand_list
    @Override
    public int getItemCount() {
        return proList.size();
    }

    //assign UI components(single_product.xml) to variables
    public class MyProductViewHolder extends RecyclerView.ViewHolder{
        TextView pro_Name, pro_qty;
        LinearLayout parentLayout;

        public MyProductViewHolder(@NonNull View itemView) {
            super(itemView);
            pro_Name = itemView.findViewById(R.id.pro_name);
            pro_qty = itemView.findViewById(R.id.pro_qty);
            parentLayout = itemView.findViewById(R.id.singleProductLayout);
        }
    }
}
