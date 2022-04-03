package com.example.lookclassy.home.fragments.home.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lookclassy.R;
import com.example.lookclassy.api.response.Product;

import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.WishListHolder> {

    List<Product> products;
    LayoutInflater layoutInflater;
    Context context;

    public WishListAdapter(List<Product> products,Context context) {
        this.products = products;
        this.context=context;
    }


    @NonNull
    @Override
    public WishListAdapter.WishListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WishListHolder(layoutInflater.inflate(R.layout.item_wishlist,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull WishListAdapter.WishListHolder holder, int position) {
        holder.wishlistproducrNameTV.setText(products.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    public class WishListHolder extends RecyclerView.ViewHolder {

        ImageView wproductIV;
        LinearLayout mainLL;
        TextView wishlistproducrNameTV, wishlistoldPriceTv, discountPrice, quantityTV;

        public WishListHolder(@NonNull View itemView) {
            super(itemView);
            wproductIV=itemView.findViewById(R.id.wproductIV);
            wishlistproducrNameTV=itemView.findViewById(R.id.wishlistproductNameTV);
        }
    }
}
