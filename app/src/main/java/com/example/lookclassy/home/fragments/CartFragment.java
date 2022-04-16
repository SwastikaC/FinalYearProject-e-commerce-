package com.example.lookclassy.home.fragments;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.lookclassy.utils.SharedPrefUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment {
    RecyclerView allProductRV;
    List<Product> products;
    TextView totalPriceTv;
    SwipeRefreshLayout swipeRefresh;
    LinearLayout addToCartLL;
    AllProductResponse allProductResponse;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        allProductRV = view.findViewById(R.id.allProductRV);
        totalPriceTv = view.findViewById(R.id.totalPriceTv);
        addToCartLL = view.findViewById(R.id.addToCartLL);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        addToCartLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allProductResponse != null && allProductResponse.getProducts().size() > 0) {
                    Intent intent = new Intent(getContext(), CheckOutActivity.class);
                    intent.putExtra(CheckOutActivity.CHECK_OUT_PRODUCTS, allProductResponse);
                    getContext().startActivity(intent);
                }
            }
        });
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(true);
                getCartItems();
            }
        });

        getCartItems();
    }

    private void getCartItems() {

        String key = SharedPrefUtils.getString(getActivity(), getString(R.string.api_key));
        Call<AllProductResponse> cartItemsCall = ApiClient.getClient().getMyCart(key);
        cartItemsCall.enqueue(new Callback<AllProductResponse>() {
            @Override
            public void onResponse(Call<AllProductResponse> call, Response<AllProductResponse> response) {
                swipeRefresh.setRefreshing(false);
                if (response.isSuccessful()) {
                    if (!response.body().getError()) {
                        allProductResponse = response.body();
                        products = response.body().getProducts();
                        loadCartList();
                        setPrice(products);
                    }
                }
            }

            @Override
            public void onFailure(Call<AllProductResponse> call, Throwable t) {
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    private void loadCartList() {
        allProductRV.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        allProductRV.setLayoutManager(layoutManager);
        ShopAdapter shopAdapter = new ShopAdapter(products, getContext(), true);
        shopAdapter.setCartItemClick(new ShopAdapter.CartItemClick() {
            @Override
            public void onRemoveCart(int position) {
                String key = SharedPrefUtils.getString(getActivity(), getString(R.string.api_key));
                Call<RegisterResponse> removeCartCall = ApiClient.getClient().deleteFromCart(key, products.get(position).getCartID());
                removeCartCall.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        if (response.isSuccessful()) {
                            products.remove(products.get(position));
                            shopAdapter.notifyItemRemoved(position);
                            setPrice(products);
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {

                    }
                });
            }
        });
        allProductRV.setAdapter(shopAdapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        getCartItems();
    }

    private void setPrice(List<Product> data) {
        List<Product> newData = data;

        double totalPrice = 0;
        for (int i = 0; i < newData.size(); i++) {
            Product product = newData.get(i);
            int price = product.getDiscountPrice();
            int q = product.getCartQuantity();
            if (products.get(i).getDiscountPrice() != 0 || products.get(i).getDiscountPrice() != null)
                totalPrice = totalPrice + price * q;
            else
                totalPrice = totalPrice + price * q;
        }
        totalPriceTv.setText("Rs. " + totalPrice);
    }

}