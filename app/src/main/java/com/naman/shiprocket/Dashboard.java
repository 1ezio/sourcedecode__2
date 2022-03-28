package com.naman.shiprocket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
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
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Map jsonValueMap = new Gson().fromJson(jsonResponse, Map.class);
        TextView nameInDashboard = (TextView) findViewById(R.id.nameInDashboard);
        String name =jsonValueMap.get("first_name")+ " "+ jsonValueMap.get("last_name");
        nameInDashboard.setText(name);

        nameInDashboard.setTranslationY(1000);
        nameInDashboard.setAlpha(0);
        nameInDashboard.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(300).start();

        String token = Objects.requireNonNull(jsonValueMap.get("token")).toString();

        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);
        layout.setTranslationY(1000);
        layout.setAlpha(0);
        layout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(300).start();

        LinearLayout layout3 = (LinearLayout) findViewById(R.id.linearLayout3);
        layout3.setTranslationY(1000);
        layout3.setAlpha(0);
        layout3.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(300).start();


        CardView ordersCard = (CardView)  findViewById(R.id.ordersCard);
        CardView trackingCard = (CardView)  findViewById(R.id.trackingCardId);
        CardView graphCard = (CardView)  findViewById(R.id.graphID);
        CardView trackOrder = (CardView)  findViewById(R.id.trackingOrderId);
        CardView createOrder = (CardView)  findViewById(R.id.creatOrderCard);


        ordersCard.setTranslationY(1000);
        ordersCard.setAlpha(0);
        ordersCard.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(300).start();


        trackingCard.setTranslationY(1000);
        trackingCard.setAlpha(0);
        trackingCard.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(300).start();

        graphCard.setTranslationY(1000);
        graphCard.setAlpha(0);
        graphCard.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(300).start();

        trackOrder.setTranslationY(1000);
        trackOrder.setAlpha(0);
        trackOrder.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(300).start();

        createOrder.setTranslationY(1000);
        createOrder.setAlpha(0);
        createOrder.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(300).start();



        
        ordersCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, OrdersList.class);
                intent.putExtra("token", token);
                startActivity(intent);
            }
        });

        trackingCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Tracking.class);
                intent.putExtra("token", token);
                startActivity(intent);
            }
        });

        graphCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, graphActivity.class);
                intent.putExtra("token", token);
                startActivity(intent);
            }
        });

        trackOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, trackOrdersMain.class);
                intent.putExtra("token", token);
                startActivity(intent);
            }
        });

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