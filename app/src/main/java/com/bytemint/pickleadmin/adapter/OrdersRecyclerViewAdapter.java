package com.bytemint.pickleadmin.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bytemint.pickleadmin.R;
import com.bytemint.pickleadmin.databinding.CardviewOrdersBinding;

import java.util.ArrayList;

public class OrdersRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<String> ordersList;

    CardViewOnClickListener cardViewOnClickListener;

    public OrdersRecyclerViewAdapter(Context context, ArrayList<String> ordersList) {
        this.ordersList = ordersList;
        this.context = context;
    }

    public void setCardViewOnClickListener(CardViewOnClickListener cardViewOnClickListener) {
        this.cardViewOnClickListener = cardViewOnClickListener;
    }

    public interface  CardViewOnClickListener {
        void onClick(int position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        CardviewOrdersBinding ordersBinding = DataBindingUtil.inflate(inflater, R.layout.cardview_orders, parent, false);

        return new OrderViewHolder(ordersBinding, cardViewOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OrderViewHolder orderViewHolder = (OrderViewHolder) holder;
        orderViewHolder.setText(ordersList.get(position));
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        CardviewOrdersBinding ordersBinding;
        public OrderViewHolder(CardviewOrdersBinding ordersBinding, CardViewOnClickListener cardViewOnClickListener) {
            super(ordersBinding.getRoot());
            this.ordersBinding = ordersBinding;

            ordersBinding.cardViewOrders.setOnClickListener(n -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION && cardViewOnClickListener!=null) {
                    cardViewOnClickListener.onClick(getAdapterPosition());
                }
            });

        }

        public void setText(String text) {
            ordersBinding.textview.setText(text);
            ordersBinding.executePendingBindings();
        }
    }
}
