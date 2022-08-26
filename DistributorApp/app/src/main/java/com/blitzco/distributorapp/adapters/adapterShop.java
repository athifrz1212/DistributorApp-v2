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
import com.blitzco.distributorapp.edit_shopRoute;
import com.blitzco.distributorapp.models.Shop;
import com.blitzco.distributorapp.shop_page;

import java.util.ArrayList;

public class adapterShop extends RecyclerView.Adapter<adapterShop.MyShopViewHolder> {

    ArrayList<Shop> shopList;
    Context context;

    public adapterShop(ArrayList<Shop> shopList, Context context) {
        this.shopList = shopList;
        this.context = context;
    }

    @NonNull
    @Override
    public adapterShop.MyShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_shop,parent,false);
        adapterShop.MyShopViewHolder ShopHolder = new adapterShop.MyShopViewHolder(view);

        return ShopHolder;
    }

    //bind values to the recycler view
    @Override
    public void onBindViewHolder(@NonNull adapterShop.MyShopViewHolder ShopHolder, @SuppressLint("RecyclerView") int position) {
        ShopHolder.shop_Name.setText(shopList.get(position).getShop());
        ShopHolder.Cno.setText(shopList.get(position).getContact());

        ShopHolder.shopEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Shop shop = shopList.get((position));

                Intent i =new Intent(context, edit_shopRoute.class);
                i.putExtra("shopID", shop.getId());
                i.putExtra("SName", shop.getShop());
                i.putExtra("Area", shop.getArea());
                i.putExtra("Address", shop.getAddress());
                i.putExtra("CNo", shop.getContact());

                context.startActivity(i);
            }
        });

        ShopHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Shop shop = shopList.get((position));

                Intent i =new Intent(context, shop_page.class);
                i.putExtra("SName", shop.getShop());
                context.startActivity(i);
            }
        });

    }

    //get no of items in the array brand_list
    @Override
    public int getItemCount() {
        return shopList.size();
    }

    //assign UI components(single_product.xml) to variables
    public class MyShopViewHolder extends RecyclerView.ViewHolder{
        TextView shop_Name, Cno, shopEdit;
        LinearLayout parentLayout;

        public MyShopViewHolder(@NonNull View itemView) {
            super(itemView);
            shop_Name = itemView.findViewById(R.id.shop_name);
            Cno = itemView.findViewById(R.id.Shop_Cno);
            shopEdit = itemView.findViewById(R.id.shopEdit);
            parentLayout = itemView.findViewById(R.id.singleShopLayout);
        }
    }
}
