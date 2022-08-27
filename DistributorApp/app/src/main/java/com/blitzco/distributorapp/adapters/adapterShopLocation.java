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

import com.blitzco.distributorapp.MapActivity;
import com.blitzco.distributorapp.R;
import com.blitzco.distributorapp.EditShopRoute;
import com.blitzco.distributorapp.models.Shop;

import java.util.ArrayList;

public class AdapterShopLocation extends RecyclerView.Adapter<AdapterShopLocation.MyShopLocationHolder> {

    ArrayList<Shop> shopList;
    Context context;

    public AdapterShopLocation(ArrayList<Shop> shopList, Context context) {
        this.shopList = shopList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterShopLocation.MyShopLocationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_shop_location,parent,false);
        AdapterShopLocation.MyShopLocationHolder ShopHolder = new AdapterShopLocation.MyShopLocationHolder(view);

        return ShopHolder;
    }

    //bind values to the recycler view
    @Override
    public void onBindViewHolder(@NonNull AdapterShopLocation.MyShopLocationHolder ShopHolder, @SuppressLint("RecyclerView") int position) {
        ShopHolder.shop_Name.setText(shopList.get(position).getShop());
        ShopHolder.shop_area.setText(shopList.get(position).getArea());

        ShopHolder.shopEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Shop shop = shopList.get((position));

                Intent i =new Intent(context, EditShopRoute.class);
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

                Intent i =new Intent(context, MapActivity.class);
                i.putExtra("SName", shop.getShop());
                context.startActivity(i);
                MapActivity m = new MapActivity();
                m.lock = true;

            }
        });

    }

    //get no of items in the array brand_list
    @Override
    public int getItemCount() {
        return shopList.size();
    }

    //assign UI components(single_product.xml) to variables
    public class MyShopLocationHolder extends RecyclerView.ViewHolder{
        TextView shop_Name, shop_area, shopEdit;
        LinearLayout parentLayout;

        public MyShopLocationHolder(@NonNull View itemView) {
            super(itemView);
            shop_Name = itemView.findViewById(R.id.shop_name);
            shop_area = itemView.findViewById(R.id.shop_area);
            shopEdit = itemView.findViewById(R.id.shopEdit);
            parentLayout = itemView.findViewById(R.id.singleShopLocationLayout);
        }
    }
}