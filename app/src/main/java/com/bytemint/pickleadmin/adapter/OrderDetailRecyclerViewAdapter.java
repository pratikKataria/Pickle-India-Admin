package com.bytemint.pickleadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bytemint.pickleadmin.R;
import com.bytemint.pickleadmin.databinding.CardviewOrderedProductDetailBinding;
import com.bytemint.pickleadmin.model.OrdersDetails;

import java.util.ArrayList;

public class OrderDetailRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<OrdersDetails> list;
    Context context;

    public OrderDetailRecyclerViewAdapter(ArrayList<OrdersDetails> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CardviewOrderedProductDetailBinding cardviewOrderedProductDetailBinding = DataBindingUtil.inflate(layoutInflater, R.layout.cardview_ordered_product_detail, parent, false);
        return new OrderDetailViewHolder(cardviewOrderedProductDetailBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OrderDetailViewHolder orderDetailViewHolder = (OrderDetailViewHolder) holder;
        orderDetailViewHolder.setOrderDetailsBinding(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class OrderDetailViewHolder extends RecyclerView.ViewHolder {
        CardviewOrderedProductDetailBinding ordersBinding;
        public OrderDetailViewHolder(CardviewOrderedProductDetailBinding ordersBinding) {
            super(ordersBinding.getRoot());
            this.ordersBinding = ordersBinding;
        }

        public void setOrderDetailsBinding(OrdersDetails orderDetails) {
            ordersBinding.setOrderDetails(orderDetails);
            ordersBinding.executePendingBindings();
        }
    }
}
