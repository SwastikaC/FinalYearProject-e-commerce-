package com.example.lookclassy.More;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lookclassy.R;
import com.example.lookclassy.api.ApiClient;
import com.example.lookclassy.api.response.OrderHistory;
import com.example.lookclassy.api.response.OrderHistoryResponse;
import com.example.lookclassy.home.fragments.home.adapters.OrderAdapter;
import com.example.lookclassy.utils.SharedPrefUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHistoryActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R .layout.activity_order_history);
//    }
//}

    RecyclerView orderHistoryRV;
    List<OrderHistory> orders;
    TextView totalPriceTv,backTV;
    OrderHistoryResponse orderHistory;
    SwipeRefreshLayout swipeRefresh;
    LinearLayout addToCartLL;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        orderHistoryRV = findViewById(R.id.OrderHistoryRV);
        //addTOCart = findViewById(R.id.addToCartB);
        backTV=findViewById(R.id.backTV);
        getOrderHistory();

        backTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }

    private void getOrderHistory(){
        String key = SharedPrefUtils.getString(this, getString(R.string.api_key));
        Call<OrderHistoryResponse> orderHistory = ApiClient.getClient().orderHistory(key);

        orderHistory.enqueue(new Callback<OrderHistoryResponse>() {
            @Override
            public void onResponse(Call<OrderHistoryResponse> call, Response<OrderHistoryResponse> response) {
                if (response.isSuccessful()) {
                    if (!response.body().getError()) {
                        //orderHistory = response.body();
                        orders = response.body().getOrderHistory();
                        loadWishList();

                    }
                }
            }

            @Override
            public void onFailure(Call<OrderHistoryResponse> call, Throwable t) {

            }
        });
    }
    private void loadWishList() {
        //wishListRV.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        orderHistoryRV.setLayoutManager(layoutManager);
        OrderAdapter orderAdapter = new OrderAdapter(orders, this);

        orderHistoryRV.setAdapter(orderAdapter);
    }
}