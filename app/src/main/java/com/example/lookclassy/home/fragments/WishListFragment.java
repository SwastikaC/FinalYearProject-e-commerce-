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
import android.widget.Toast;

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
    RecyclerView allwishlistProductRV;
    List<Product> products;
    SwipeRefreshLayout swipeRefresh;
    ImageView emptyWishlistIV;
    AllProductResponse allProductResponse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wish_list, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        allwishlistProductRV = view.findViewById(R.id.allwishlistProductRV);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        emptyWishlistIV = view.findViewById(R.id.emptyWishlistIV);
        swipeRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        swipeRefresh.setRefreshing(true);
                        getWishlistItems();
                    }


                });
                getWishlistItems();
            }
        });
        getWishlistItems();

    }

    private void getWishlistItems() {
        //load
        String key = SharedPrefUtils.getString(getActivity(), "apk");
        Call<AllProductResponse> wishItemsCall = ApiClient.getClient().getMyWishlist(key);
        wishItemsCall.enqueue(new Callback<AllProductResponse>() {
            @Override
            public void onResponse(Call<AllProductResponse> call, Response<AllProductResponse> response) {
                swipeRefresh.setRefreshing(false);
                if (response.isSuccessful()) {
                    if (!response.body().getError()) {
                        if (response.body().getProducts().size() == 0) {
                            showEmptyLayout();
                        } else {
                            emptyWishlistIV.setVisibility(View.GONE);
                            allProductResponse = response.body();
                            products = response.body().getProducts();
                            loadWishList();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AllProductResponse> call, Throwable t) {
                swipeRefresh.setRefreshing(false);
            }
        });

    }


    private void showEmptyLayout() {

        emptyWishlistIV.setVisibility(View.VISIBLE);
    }


    private void loadWishList() {
        allwishlistProductRV.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        allwishlistProductRV.setLayoutManager(layoutManager);
        WishListAdapter wishlistAdapter = new WishListAdapter(products, getContext());
        wishlistAdapter.setWishCartItemClick(new WishListAdapter.WishlistCartItemClick() {
            @Override
            public void onRemoveCart(int position) {
                String key = SharedPrefUtils.getString(getActivity(), "apk");
                Call<RegisterResponse> removeCartCall = ApiClient.getClient().deleteFromCart(key, products.get(position).getCartID());
                removeCartCall.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        if (response.isSuccessful()) {
                            if (!response.body().getError()) {
                                products.remove(products.get(position));
                                wishlistAdapter.notifyItemChanged(position);
                                Toast.makeText(getContext(), "Wishlist Item successfully deleted", Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {

                    }
                });
            }
        });
        allwishlistProductRV.setAdapter(wishlistAdapter);
    }





//    // delete wishlist item
//    private void loadWishListList() {
//        allWishlistProductRV.setHasFixedSize(true);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        allWishlistProductRV.setLayoutManager(layoutManager);
//        WishlistAdapter wishlistAdapter = new WishlistAdapter(products, getContext(), true);
//        //wishlistAdapter.setWishlistCartItemClick(new WishlistAdapter.WishlistCartItemClick() {
//            @Override
//            public void AddCart(int position) {
//
//            }
//        });
//    }
}
