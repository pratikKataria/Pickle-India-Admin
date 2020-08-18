package com.bytemint.pickleadmin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bytemint.pickleadmin.adapter.OrderDetailRecyclerViewAdapter;
import com.bytemint.pickleadmin.databinding.ActivityOrderDetailsBinding;
import com.bytemint.pickleadmin.model.OfferCombo;
import com.bytemint.pickleadmin.model.Orders;
import com.bytemint.pickleadmin.R;
import com.bytemint.pickleadmin.model.OrdersDetails;
import com.bytemint.pickleadmin.model.ProductModel;
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
    private ArrayList<OrdersDetails> comboList = new ArrayList<>();

    private ActivityOrderDetailsBinding orderDetailsBinding;
    private OrderDetailRecyclerViewAdapter adapter;
    private OrderDetailRecyclerViewAdapter comboAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_details);
        setUserNameAndUserPhoneNumber();
        initRecyclerView();
        initRecyclerViewCombo();
        userOrderedProducts();
        getComboList();

        orderDetailsBinding.ordercompleted.setOnClickListener(n -> {
            updateOrder();
        });


    }

    private Orders getArgumentExtra() {
        Orders orders = new Orders();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("userId")) {
            orders = (Orders) bundle.getSerializable("userId");
        }
        return orders;
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

    private void initRecyclerViewCombo() {
        RecyclerView comboRecyclerView = orderDetailsBinding.comboRecyclerView;
        comboAdapter = new OrderDetailRecyclerViewAdapter(comboList, this);
        comboRecyclerView.setAdapter(comboAdapter);
    }

    private void getComboList() {
        Orders orders = getArgumentExtra();

        if (orders != null && orders.getComboId() != null) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Offers").child(orders.getComboId());
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.e("OrderDetailsActivity ", dataSnapshot + " ");

                    OfferCombo offerCombo = dataSnapshot.getValue(OfferCombo.class);

                    if (offerCombo != null) {
                        String[] ids_cat = offerCombo.getProductIds_cat().split(" ");
                        String[] id = new String[ids_cat.length];
                        String[] cat = new String[ids_cat.length];
                        for (int i = 0; i < ids_cat.length; i++) {
                            if (ids_cat[i].split("_").length > 1) {
                                id[i] = ids_cat[i].split("_")[0];
                                cat[i] = ids_cat[i].split("_")[1];
                            }
                        }

                        for (int i = 0; i < ids_cat.length; i++) {
                            DatabaseReference newRef = FirebaseDatabase.getInstance().getReference("Products").child(cat[i]).child(id[i]);
                            newRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    ProductModel productModel = dataSnapshot.getValue(ProductModel.class);

                                    if (productModel != null) {
                                        OrdersDetails ordersDetails = new OrdersDetails();
                                        ordersDetails.setItemName(productModel.getItemName());
                                        ordersDetails.setItemThumbImage(productModel.getItemThumbImage());

                                        comboList.add(ordersDetails);
                                        comboAdapter.notifyDataSetChanged();
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    private void setUserNameAndUserPhoneNumber() {
        String userId = getArgumentExtra().getUserId();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Customers").child(userId).child("personalInformation");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, String> user = (Map<String, String>) dataSnapshot.getValue();
                if (user != null && user.containsKey("username") && user.containsKey("userPhoneNo")) {
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
                    if (ordersDetails != null) {
                        ordersDetailsArrayList.add(ordersDetails);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}