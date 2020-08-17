package com.bytemint.pickleadmin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bytemint.pickleadmin.adapter.OrderDetailRecyclerViewAdapter;
import com.bytemint.pickleadmin.databinding.ActivityOrderDetailsBinding;
import com.bytemint.pickleadmin.model.Orders;
import com.bytemint.pickleadmin.R;
import com.bytemint.pickleadmin.model.OrdersDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderDetailsActivity extends AppCompatActivity {

    private ArrayList<OrdersDetails> ordersDetailsArrayList = new ArrayList<>();
    private ActivityOrderDetailsBinding orderDetailsBinding;
    private OrderDetailRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_details);
        setUserNameAndUserPhoneNumber();
        initRecyclerView();
        userOrderedProducts();

        orderDetailsBinding.ordercompleted.setOnClickListener(n -> {
            updateOrder();
        });
    }

    public void updateOrder() {
        if (!orderDetailsBinding.updateCode.getText().toString().isEmpty() && getArgumentExtra() != null) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Orders").child(getArgumentExtra().getOrderId()).child("orderStatus");
            reference.setValue(Integer.parseInt(orderDetailsBinding.updateCode.getText().toString())).addOnCompleteListener(task -> {
                orderDetailsBinding.updateCode.setText("");
                Toast.makeText(OrderDetailsActivity.this, "updated ", Toast.LENGTH_LONG).show();
            });
        }
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = orderDetailsBinding.orderDetailRecyclerView;
        adapter = new OrderDetailRecyclerViewAdapter(ordersDetailsArrayList, this);
        recyclerView.setAdapter(adapter);
    }

    private Orders getArgumentExtra() {
        Orders orders = new Orders();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("userId")) {
            orders = (Orders) bundle.getSerializable("userId");
        }
        return orders;
    }

    private void setUserNameAndUserPhoneNumber() {
        String userId = getArgumentExtra().getUserId();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Customers").child(userId).child("personalInformation");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, String> user = (Map<String, String>) dataSnapshot.getValue();
                if (user != null) {
                    String name = user.get("username");
                    String phoneNo = user.get("userPhoneNo");
                    orderDetailsBinding.username.setText(name);
                    orderDetailsBinding.userphoneNumber.setText(phoneNo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void userOrderedProducts() {
        Orders orders = getArgumentExtra();
        if (orders == null) return;

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("OrdersDetails").child(orders.getOrderId());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("OrderDetailsActivity", dataSnapshot + " ");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    OrdersDetails ordersDetails = snapshot.getValue(OrdersDetails.class);
                    ordersDetailsArrayList.add(ordersDetails);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}