package com.naman.shiprocket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.naman.shiprocket.DashboardItems.orders.OrdersList;
import com.naman.shiprocket.TrackingWithMap.Tracking;
import com.naman.shiprocket.createOrder.createOrderActivity;
import com.naman.shiprocket.graphs.graphActivity;
import com.naman.shiprocket.trackOrders.trackOrdersMain;

import java.util.Map;
import java.util.Objects;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Bundle bundle = getIntent().getExtras();
        String jsonResponse = bundle.getString("jsonResponse");

        Map jsonValueMap = new Gson().fromJson(jsonResponse, Map.class);
        TextView nameInDashboard = (TextView) findViewById(R.id.nameInDashboard);
        String name =jsonValueMap.get("first_name")+ " "+ jsonValueMap.get("last_name");
        nameInDashboard.setText(name);
        String token = Objects.requireNonNull(jsonValueMap.get("token")).toString();

        CardView ordersCard = (CardView)  findViewById(R.id.ordersCard);
        ordersCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, OrdersList.class);
                intent.putExtra("token", token);
                startActivity(intent);
            }
        });

        CardView trackingCard = (CardView)  findViewById(R.id.trackingCardId);
        trackingCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Tracking.class);
                intent.putExtra("token", token);
                startActivity(intent);
            }
        });

        CardView graphCard = (CardView)  findViewById(R.id.graphID);
        graphCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, graphActivity.class);
                intent.putExtra("token", token);
                startActivity(intent);
            }
        });

        CardView trackOrder = (CardView)  findViewById(R.id.trackingOrderId);
        trackOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, trackOrdersMain.class);
                intent.putExtra("token", token);
                startActivity(intent);
            }
        });

        CardView createOrder = (CardView)  findViewById(R.id.creatOrderCard);
        createOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, createOrderActivity.class);
                intent.putExtra("token", token);
                startActivity(intent);
            }
        });


    }
}