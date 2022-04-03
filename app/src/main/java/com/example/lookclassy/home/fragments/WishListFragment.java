package com.example.lookclassy.home.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.lookclassy.R;
import com.example.lookclassy.api.ApiClient;
import com.example.lookclassy.api.response.AllProductResponse;
import com.example.lookclassy.api.response.Product;
import com.example.lookclassy.api.response.RegisterResponse;
import com.example.lookclassy.checkout.CheckOutActivity;
import com.example.lookclassy.home.fragments.home.adapters.ShopAdapter;
import com.example.lookclassy.home.fragments.home.adapters.WishListAdapter;
import com.example.lookclassy.utils.SharedPrefUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WishListFragment extends Fragment {


    List<Product> products;
    RecyclerView allwishlistProductRV;
    SwipeRefreshLayout swipeRefresh;
    RelativeLayout addedToCart, wishlisttrashIV;
    ImageView wishlistbackIV;
    AllProductResponse allProductResponse;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wish_list, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        allwishlistProductRV = view.findViewById(R.id.allwishlistProductRV);
        addedToCart = view.findViewById(R.id.addedToCart);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);




    }

    private void getWishList(){
        String key = SharedPrefUtils.getString(getActivity(), getString(R.string.api_key));
        Call<AllProductResponse> wishListCall = ApiClient.getClient().wishlist(key);
        wishListCall.enqueue(new Callback<AllProductResponse>() {
            @Override
            public void onResponse(Call<AllProductResponse> call, Response<AllProductResponse> response) {
                swipeRefresh.setRefreshing(false);
                if (response.isSuccessful()) {
                    if (!response.body().getError()) {
                        allProductResponse = response.body();
                        products = response.body().getProducts();
                        loadWishList();

                    }
                }

            }

            @Override
            public void onFailure(Call<AllProductResponse> call, Throwable t) {

            }
        });

    }

    private void loadWishList(){

        //allProductRV.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        allwishlistProductRV.setLayoutManager(layoutManager);
        WishListAdapter wishListAdapter = new WishListAdapter(products,getContext());
    }


    }

