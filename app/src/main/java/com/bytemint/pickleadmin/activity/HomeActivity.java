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
    private static final String TAG = "HomeActivity";
    private final ArrayList<Orders> ordersArrayList = new ArrayList<>();
    private OrdersRecyclerViewAdapter ordersRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHomeBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        RecyclerView recyclerView = binding.recyclerView;
        ordersRecyclerViewAdapter = new OrdersRecyclerViewAdapter(this, ordersArrayList);
        ordersRecyclerViewAdapter.setCardViewOnClickListener(position -> {
            try {
                startActivity(new Intent(HomeActivity.this, OrderDetailsActivity.class).putExtra("userId", ordersArrayList.get(position)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        recyclerView.setAdapter(ordersRecyclerViewAdapter);

        getData();

        binding.refresh.setOnClickListener(view -> {
            ordersArrayList.clear();
            ordersRecyclerViewAdapter.notifyDataSetChanged();
            getData();
        });
    }

    private void getData() {

        Query query = FirebaseDatabase.getInstance().getReference("Orders");
        query.keepSynced(true);
        query.orderByChild("date").limitToFirst(50).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Log.e(TAG, "onDataChange: " + snapshot.getKey());
                    Orders orders = snapshot.getValue(Orders.class);

                        if (orders.getOrderStatus() != 398 && orders.getOrderStatus() != 324) {
                            Log.e(TAG, "onDataChange: " + ordersArrayList.contains(orders) );
                            if (!ordersArrayList.contains(orders)) {
                                ordersArrayList.add(0, orders);
                                ordersRecyclerViewAdapter.notifyDataSetChanged();
                            }
                        }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}