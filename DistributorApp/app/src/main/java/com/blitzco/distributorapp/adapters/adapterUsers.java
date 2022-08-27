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
import com.blitzco.distributorapp.edit_brand;
import com.blitzco.distributorapp.models.User;

import java.util.List;

public class adapterUsers extends RecyclerView.Adapter<adapterUsers.MyUserViewHolder>{

    List<User> userList;
    Context context;

    public adapterUsers(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_user,parent,false);
        MyUserViewHolder userHolder = new MyUserViewHolder(view);

        return userHolder;
    }

    //bind values to the recycler view
    @Override
    public void onBindViewHolder(@NonNull adapterUsers.MyUserViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.firstName.setText(userList.get(position).getFirstName());
        holder.lastName.setText(userList.get(position).getLastName());

        holder.userEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                User users = userList.get((position));

                Intent i =new Intent(context, edit_brand.class);
                i.putExtra("userID", users.getUserID());
                i.putExtra("firstName", users.getFirstName());
                i.putExtra("lastName", users.getLastName());
                context.startActivity(i);
            }
        });

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                User users = userList.get((position));
//                Intent i =new Intent(context, edit_user.class);
//                i.putExtra("firstName", users.getFirstName());
//                i.putExtra("lastName", users.getLastName());
//                context.startActivity(i);
            }
        });

    }

    //get no of items in the array brand_list
    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class MyUserViewHolder extends RecyclerView.ViewHolder{
        TextView firstName, lastName, userEdit;
        LinearLayout parentLayout;

        public MyUserViewHolder(@NonNull View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.first_name);
            lastName = itemView.findViewById(R.id.last_name);
            parentLayout = itemView.findViewById(R.id.singleUserLayout);
            userEdit = itemView.findViewById(R.id.userEdit);
        }
    }
}
