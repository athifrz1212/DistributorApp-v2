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
import com.blitzco.distributorapp.EditShopRoute;
import com.blitzco.distributorapp.models.Shop;
import com.blitzco.distributorapp.ShopPage;

import java.util.ArrayList;

public class AdapterAreaShop extends RecyclerView.Adapter<AdapterAreaShop.MyAreaShopViewHolder> {

    ArrayList<Shop> shopList;
    Context context;

    public AdapterAreaShop(ArrayList<Shop> shopList, Context context) {
        this.shopList = shopList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterAreaShop.MyAreaShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_shop,parent,false);
        AdapterAreaShop.MyAreaShopViewHolder ShopHolder = new AdapterAreaShop.MyAreaShopViewHolder(view);

        return ShopHolder;
    }

    //bind values to the recycler view
    @Override
    public void onBindViewHolder(@NonNull AdapterAreaShop.MyAreaShopViewHolder ShopHolder, @SuppressLint("RecyclerView") int position) {
        ShopHolder.shop_Name.setText(shopList.get(position).getShop());
        ShopHolder.Cno.setText(shopList.get(position).getContact());

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

                Intent i = new Intent(context, ShopPage.class);
                i.putExtra("SName", shop.getShop());
            }
        });

    }

    //get no of items in the array brand_list
    @Override
    public int getItemCount() {
        return shopList.size();
    }

    //assign UI components(single_product.xml) to variables
    public class MyAreaShopViewHolder extends RecyclerView.ViewHolder{
        TextView shop_Name, Cno, shopEdit;
        LinearLayout parentLayout;

        public MyAreaShopViewHolder(@NonNull View itemView) {
            super(itemView);
            shop_Name = itemView.findViewById(R.id.shop_name);
            Cno = itemView.findViewById(R.id.Shop_Cno);
            shopEdit = itemView.findViewById(R.id.shopEdit);
            parentLayout = itemView.findViewById(R.id.singleShopLayout);
        }
    }
}
