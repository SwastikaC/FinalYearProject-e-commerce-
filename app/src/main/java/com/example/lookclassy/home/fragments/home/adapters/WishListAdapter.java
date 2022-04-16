package com.example.lookclassy.home.fragments.home.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.example.lookclassy.singleProductPage.SingleProductActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.WishListViewHolder> {
    List<Product> productDataList;
    LayoutInflater inflater;
//    Boolean isWishlist = false;
    Context context;
    //  WishLisItemClick wishlistItemClick;
    WishlistCartItemClick  wishlistCartItemClick ;
    Boolean removeEnabled = true;

    public WishListAdapter(List<Product> productDataList, Context context){
        this.productDataList = productDataList;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
//        this.isWishlist = isWishlist;
    }

    public void setWishCartItemClick(WishlistCartItemClick wishlistCartItemClick) {
        this.wishlistCartItemClick = wishlistCartItemClick;
    }

    public void setRemoveEnabled(Boolean removeEnabled) {

        this.removeEnabled = removeEnabled;
    }


    @NonNull
    @Override
    public WishListAdapter.WishListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WishListViewHolder(inflater.inflate(R.layout.item_wishlist, parent, false));
     }
    @Override
    public void onBindViewHolder(@NonNull WishListAdapter.WishListViewHolder holder, int position) {
        holder.wishlistproductNameTV.setText(productDataList.get(position).getName());
        if (productDataList.get(position).getDiscountPrice() == null || productDataList.get(position).getDiscountPrice() == 0){
            holder.wishlistoldPriceTV.setVisibility(View.GONE);
            holder.wishlistdiscountPriceTV.setText("Rs." + productDataList.get(position).getPrice() + "");
        } else
            holder.wishlistdiscountPriceTV.setText("Rs." + productDataList.get(position).getDiscountPrice());
        holder.wishlistoldPriceTV.setText(productDataList.get(position).getPrice() + "");



        Picasso.get().load(productDataList.get(position).getImages().get(0)).into(holder.wishlistproductIV);
        holder.wishlistLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent productPage = new Intent(context, SingleProductActivity.class);
                System.out.println(productDataList.get(holder.getAdapterPosition()).getImages());
                productPage.putExtra(SingleProductActivity.SINGLE_DATA_KEY, productDataList.get(holder.getAdapterPosition()));
                context.startActivity(productPage);

            }
        });



    }

    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    @Override
    public int getItemCount() {

        return productDataList.size();
    }

    public class WishListViewHolder extends RecyclerView.ViewHolder {

        ImageView wishlistproductIV, wishlisttrashIV, addedToCart;
        TextView wishlistproductNameTV, wishlistoldPriceTV, wishlistdiscountPriceTV;
        LinearLayout wishlistLL;

        public WishListViewHolder(@NonNull View itemView) {
            super(itemView);
            wishlistproductIV = itemView.findViewById(R.id.wishlistproductIV);
            wishlisttrashIV = itemView.findViewById(R.id.wishlisttrashIV);
            wishlistproductNameTV = itemView.findViewById(R.id.wishlistproductNameTV);
            wishlistoldPriceTV = itemView.findViewById(R.id.wishlistoldPriceTV);
            wishlistdiscountPriceTV = itemView.findViewById(R.id.wishlistdiscountPriceTV);
            addedToCart = itemView.findViewById(R.id.addedToCart);
            wishlistLL = itemView.findViewById(R.id.wishlistLL);
        }
    }
    public interface WishlistCartItemClick {
        public void onRemoveCart(int position);
    }
}
