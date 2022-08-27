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
import com.blitzco.distributorapp.EditBrand;
import com.blitzco.distributorapp.models.Brand;
import com.blitzco.distributorapp.ViewProduct;

import java.util.List;

public class AdapterBrand extends RecyclerView.Adapter<AdapterBrand.MyBrandViewHolder>{

    List<Brand> brandList;
    Context context;

    public AdapterBrand(List<Brand> brandList, Context context) {
        this.brandList = brandList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyBrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_brand,parent,false);
        MyBrandViewHolder brandHolder = new MyBrandViewHolder(view);

        return brandHolder;
    }

    //bind values to the recycler view
    @Override
    public void onBindViewHolder(@NonNull AdapterBrand.MyBrandViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        holder.brand_No.setText(brandList.get(position).getBrandID());
        holder.brand_Name.setText(brandList.get(position).getBrandName());

        holder.brandEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Brand brands = brandList.get((position));

                Intent i =new Intent(context, EditBrand.class);
                i.putExtra("brandID", brands.getBrandID());
                i.putExtra("brand_Name", brands.getBrandName());
                i.putExtra("seller_Name", brands.getSellerName());
                i.putExtra("address", brands.getAddress());
                i.putExtra("contact", brands.getContactNumber());
                context.startActivity(i);
            }
        });

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Brand brands = brandList.get((position));
                Intent i =new Intent(context, ViewProduct.class);
                i.putExtra("brand_Name", brands.getBrandName());
                context.startActivity(i);
            }
        });

    }

    //get no of items in the array brand_list
    @Override
    public int getItemCount() {
        return brandList.size();
    }

    //assign UI components(single_product.xml) to variables
    public class MyBrandViewHolder extends RecyclerView.ViewHolder{
        TextView brand_No, brand_Name,brandEdit;
        LinearLayout parentLayout;

        public MyBrandViewHolder(@NonNull View itemView) {
            super(itemView);
//            brand_No = itemView.findViewById(R.id.brand_);
            brand_Name = itemView.findViewById(R.id.brand_name);
            parentLayout = itemView.findViewById(R.id.singleBrandLayout);
            brandEdit = itemView.findViewById(R.id.brandEdit);
        }
    }
}
