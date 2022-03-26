package com.naman.shiprocket.DashboardItems.orders;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.naman.shiprocket.R;

public class seeMoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_more);

        TextView bpaymentStatus, bpaymentmethod, bcreatedAt, bUpdatedAt, bproductId;
        Button btn ;

        Intent intent = getIntent();
        String[]  values= intent.getStringArrayExtra("values");
        String paymentStatus = values[0];
        String payementMethod = values[1];
        String createdAt = values[2];;
        String productId =values[3];;
        String updatedAt = values[4];;

        bpaymentmethod=(TextView) findViewById(R.id.bPaymentMethod);
        bpaymentmethod.setText(payementMethod);

        bpaymentStatus=(TextView) findViewById(R.id.bPaymentStatus);
        bpaymentStatus.setText(paymentStatus);

        bcreatedAt=(TextView) findViewById(R.id.bCreatedat);
        bcreatedAt.setText(createdAt);

        bUpdatedAt=(TextView) findViewById(R.id.bUpdatedat);
        bUpdatedAt.setText(updatedAt);

        bproductId=(TextView) findViewById(R.id.bProductId);
        bproductId.setText(productId);

        btn = findViewById(R.id.okBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



    }
}