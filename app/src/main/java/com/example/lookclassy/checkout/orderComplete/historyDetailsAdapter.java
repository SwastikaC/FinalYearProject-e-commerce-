package com.example.lookclassy.checkout.orderComplete;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lookclassy.R;
import com.example.lookclassy.api.response.Bag;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;


public class historyDetailsAdapter extends RecyclerView.Adapter<historyDetailsAdapter.ViewHolder> implements Serializable {

    LayoutInflater layoutInflater;
    List<Bag> data;
    Context context;


    public historyDetailsAdapter(List<Bag> data,Context context){
        this.data=data;
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public historyDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.historydetails,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull historyDetailsAdapter.ViewHolder holder, int position) {
        Bag bag=data.get(position);
        holder.unitPrice.setText(bag.getUnitPrice() + "");
        holder.orderQuantity.setText(bag.getQuantity()+"");
        holder.orderProduct.setText(bag.getProduct().getName());
        Picasso.get().load(bag.getProduct ().getImages().get(0)).into(holder.productImages);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView unitPrice, orderProduct, orderQuantity;
        ImageView productImages;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            unitPrice=itemView.findViewById(R.id.unitPrice);
            orderProduct=itemView.findViewById(R.id.orderProduct);
            orderQuantity=itemView.findViewById(R.id.orderQuantity);
            productImages=itemView.findViewById(R.id.productImages);
        }
    }

}
