package com.example.lookclassy.checkout.orderComplete;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class OrderActivity extends AppCompatActivity implements OrderAdapter.OnOrderDetailsClick{

    RecyclerView orderRV;
    List<OrderHistory> data;
    OrderAdapter orderAdapter;
    ImageView backIVO;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.WHITE);
        setContentView(R.layout.activity_order);
        orderRV=findViewById(R.id.orderRV);
        backIVO=findViewById(R.id.backIVO);

        backIVO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        historyDataCall();
    }

    private void historyDataCall() {
        String key = SharedPrefUtils.getString(this, getString(R.string.api_key));
        Call<OrderHistoryResponse> orderHistoryResponseCall = ApiClient.getClient().orderHistory(key);


        orderHistoryResponseCall.enqueue(new Callback<OrderHistoryResponse>() {
            @Override
            public void onResponse(Call<OrderHistoryResponse> call, Response<OrderHistoryResponse> response) {
                if (response.isSuccessful()){
                    setOrderRV(response.body().getOrderHistory());
                }
            }

            @Override
            public void onFailure(Call<OrderHistoryResponse> call, Throwable t) {

            }
        });
    }

    private void setOrderRV(List<OrderHistory> orderHistoryList){
        data=orderHistoryList;
        orderRV.setHasFixedSize(true);
        orderRV.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter=new OrderAdapter(orderHistoryList,this);
        orderRV.setAdapter(orderAdapter);
        orderAdapter.setEachHistory(this);
    }

    @Override
    public void onOrderClick(int position) {
        OrderHistory orderHistory= data.get(position);

        Intent intent=new Intent(getApplicationContext(),OrderHistoryDetailsActivity.class);
        intent.putExtra(OrderHistoryDetailsActivity.ORDER_DETAILS_KEY,  orderHistory.getBag().get(0));
        startActivity(intent);
    }
}