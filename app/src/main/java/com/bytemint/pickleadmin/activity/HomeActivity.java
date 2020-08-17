package com.bytemint.pickleadmin.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bytemint.pickleadmin.model.Orders;
import com.bytemint.pickleadmin.R;
import com.bytemint.pickleadmin.adapter.OrdersRecyclerViewAdapter;
import com.bytemint.pickleadmin.databinding.ActivityHomeBinding;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private ArrayList<String> ordersArrayList = new ArrayList<>();
    private ArrayList<Orders> list = new ArrayList<>();
    private OrdersRecyclerViewAdapter ordersRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHomeBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        RecyclerView recyclerView = binding.recyclerView;
        ordersRecyclerViewAdapter = new OrdersRecyclerViewAdapter(this, ordersArrayList);
        ordersRecyclerViewAdapter.setCardViewOnClickListener(new OrdersRecyclerViewAdapter.CardViewOnClickListener() {
            @Override
            public void onClick(int position) {
                startActivity(new Intent(HomeActivity.this, OrderDetailsActivity.class).putExtra("userId", list.get(position)));
            }
        });
        recyclerView.setAdapter(ordersRecyclerViewAdapter);

        getData();

        binding.refresh.setOnClickListener(view -> {
            getData();
        });
    }

    private void getData() {

        Query query = FirebaseDatabase.getInstance().getReference("Orders");
        query.orderByChild("date").limitToFirst(50).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Orders orders = snapshot.getValue(Orders.class);
                    if (list.contains(orders)) {
                        if (orders.getOrderStatus() == 398 || orders.getOrderStatus() == 324) {
                            int indexOf = list.indexOf(orders);
                            list.remove(indexOf);
                            ordersArrayList.remove(indexOf);
                            ordersRecyclerViewAdapter.notifyDataSetChanged();
                        }
                    } else {
                        if (orders.getOrderStatus() == 398 || orders.getOrderStatus() == 324)
                            return;
                        list.add(0,orders);
                        ordersArrayList.add(0, orders.toString());
                        ordersRecyclerViewAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}