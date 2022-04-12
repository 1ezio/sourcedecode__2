package com.naman.shiprocket.createOrder;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.naman.shiprocket.Dashboard;
import com.naman.shiprocket.ProgressDialogFragment;
import com.naman.shiprocket.R;
import com.naman.shiprocket.loginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class createOrderActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        EditText cName, cProductName, cPhone,
                cAddr1, cAddr2, cCity, cState, cCountry, cPin ,cId;
        TextInputLayout tl1 = findViewById(R.id.cnamei);
        ConstraintLayout cl1 = findViewById(R.id.cl1);
        cName = findViewById(R.id.cname);
        cProductName = findViewById(R.id.cpname);
        cPhone = findViewById(R.id.cphn);
        cAddr1 = findViewById(R.id.caddr1);
        cAddr2 = findViewById(R.id.caddr2);
        cCity = findViewById(R.id.ccity);
        cState = findViewById(R.id.cstate);
        cCountry = findViewById(R.id.ccountry);
        cPin = findViewById(R.id.cpin);
        cId = findViewById(R.id.ccid);
        Button updateButton = findViewById(R.id.updateId);
        Button btn = findViewById(R.id.btnId);
        //cId.setTranslationY(800);
        cl1.setTranslationY(800);

        cl1.setAlpha(0);
        cl1.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(300).start();

        TextView textView = findViewById(R.id.cancelOrderID);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cId.getText().toString().equals(null)){
                    Toast.makeText(createOrderActivity.this, "Enter Order ID", Toast.LENGTH_SHORT).show();
                }else{
                    deleteOrder(cId.getText().toString());
                }
            }
        });


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sName = cName.getText().toString();
                String spname = cProductName.getText().toString();
                String sphone = cPhone.getText().toString();
                String saddr1 = cAddr1.getText().toString();
                String saddr2 = cAddr2.getText().toString();
                String scity = cCity.getText().toString();
                String sstate = cState.getText().toString();
                String scountry = cCountry.getText().toString();
                String spin = cPin.getText().toString();
                String sid = cId.getText().toString();

                updateOrder(sid,sName,saddr1,saddr2,scity,spin,scountry,sphone,spname,sstate);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{

                    Bundle bundle = getIntent().getExtras();
                    String token = bundle.getString("token");
                    String sName = cName.getText().toString();
                    String spname = cProductName.getText().toString();
                    String sphone = cPhone.getText().toString();
                    String saddr1 = cAddr1.getText().toString();
                    String saddr2 = cAddr2.getText().toString();
                    String scity = cCity.getText().toString();
                    String sstate = cState.getText().toString();
                    String scountry = cCountry.getText().toString();
                    String spin = cPin.getText().toString();
                    String sid = cId.getText().toString();


                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();

                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType, "{\n  \"order_id\": \""+sid+"\"" +
                        ",\n  \"order_date\": \"2022-07-24 11:11\",\n  \"pickup_location\": \"Jammu\"," +
                        "\n  \"channel_id\": \"\",\n  \"comment\": \"Reseller: M/s Goku\"," +
                        "\n  \"billing_customer_name\": \"" + sName+ "\",\n  \"billing_last_name\": \"Uzumaki\"" +
                        ",\n  \"billing_address\": \" "+ saddr1 +" \",\n  \"billing_address_2\": \" "+saddr2+" \"," +
                        "\n  \"billing_city\": \""+scity+"\",\n  \"billing_pincode\": \""+spin+"\",\n  \"billing_state\": \""+sstate+"\"" +
                        ",\n  \"billing_country\": \""+scountry+"\",\n  \"billing_email\": \"naruto@uzumaki.com\",\n  \"billing_phone\": \""+sphone+"\"," +
                        "\n  \"shipping_is_billing\": true,\n  \"shipping_customer_name\": \"\",\n  \"shipping_last_name\": \"\"," +
                        "\n  \"shipping_address\": \"\",\n  \"shipping_address_2\": \"\",\n  \"shipping_city\": \"\"," +
                        "\n  \"shipping_pincode\": \"\",\n  \"shipping_country\": \"\",\n  \"shipping_state\": \"\",\n  \"shipping_email\": \"\"" +
                        ",\n  \"shipping_phone\": \"\",\n  \"order_items\": [\n    {\n      \"name\": \""+spname+"\",\n      \"sku\": \"chakra123\"," +
                        "\n      \"units\": 10,\n      \"selling_price\": \"900\",\n      \"discount\": \"\",\n      \"tax\": \"\"," +
                        "\n      \"hsn\": 441122\n    }\n  ],\n  \"payment_method\": \"Prepaid\"," +
                        "\n  \"shipping_charges\": 0,\n  \"giftwrap_charges\": 0,\n  \"transaction_charges\": 0," +
                        "\n  \"total_discount\": 0,\n  \"sub_total\": 9000,\n  \"length\": 10,\n  \"breadth\": 15,\n  \"height\": 20," +
                        "\n  \"weight\": 2.5\n}");
                    Request request = new Request.Builder()
                            .url("https://apiv2.shiprocket.in/v1/external/orders/create/adhoc")
                            .method("POST", body)
                            .addHeader("Content-Type", "application/json")
                            .addHeader("Authorization", "Bearer "+token)
                            .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String jsonResponse = response.body().string();
                        if(response.isSuccessful()){

                            createOrderActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //Toast.makeText(createOrderActivity.this, String.valueOf(jsonResponse), Toast.LENGTH_SHORT).show();
                                    Toast.makeText(createOrderActivity.this, "Order Created / Updated", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }else{
                            createOrderActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(createOrderActivity.this, "Ok: "+jsonResponse,Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });


                }catch(ArithmeticException e){
                    Toast.makeText(createOrderActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(createOrderActivity.this, createOrderActivity.class));
                }



            }
        });



    }
    public void deleteOrder(String id){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Bundle bundle = getIntent().getExtras();
        String token = bundle.getString("token");
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n  \"ids\": ["+id+"]\n}");
        Request request = new Request.Builder()
                .url("https://apiv2.shiprocket.in/v1/external/orders/cancel")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer "+token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String jsonResponse = response.body().string();
                if(response.isSuccessful()){
                    createOrderActivity.this.runOnUiThread(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void run() {
                            Toast.makeText(createOrderActivity.this, "Order Cancelled", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(createOrderActivity.this,createOrderActivity.class));
                        }
                    });
                }else{
                    createOrderActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(createOrderActivity.this, "Ok: "+jsonResponse,Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    public void updateOrder(String sid, String sName,String saddr1,String saddr2,String scity, String spin,String scountry,String sphone,String spname,String sstate ){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Bundle bundle = getIntent().getExtras();
        String token = bundle.getString("token");
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n  \"order_id\": \""+sid+"\"" +
                ",\n  \"order_date\": \"2022-07-24 11:11\",\n  \"pickup_location\": \"Jammu\"," +
                "\n  \"channel_id\": \"\",\n  \"comment\": \"Reseller: M/s Goku\"," +
                "\n  \"billing_customer_name\": \"" + sName+ "\",\n  \"billing_last_name\": \"Uzumaki\"" +
                ",\n  \"billing_address\": \" "+ saddr1 +" \",\n  \"billing_address_2\": \" "+saddr2+" \"," +
                "\n  \"billing_city\": \""+scity+"\",\n  \"billing_pincode\": \""+spin+"\",\n  \"billing_state\": \""+sstate+"\"" +
                ",\n  \"billing_country\": \""+scountry+"\",\n  \"billing_email\": \"naruto@uzumaki.com\",\n  \"billing_phone\": \""+sphone+"\"," +
                "\n  \"shipping_is_billing\": true,\n  \"shipping_customer_name\": \"\",\n  \"shipping_last_name\": \"\"," +
                "\n  \"shipping_address\": \"\",\n  \"shipping_address_2\": \"\",\n  \"shipping_city\": \"\"," +
                "\n  \"shipping_pincode\": \"\",\n  \"shipping_country\": \"\",\n  \"shipping_state\": \"\",\n  \"shipping_email\": \"\"" +
                ",\n  \"shipping_phone\": \"\",\n  \"order_items\": [\n    {\n      \"name\": \""+spname+"\",\n      \"sku\": \"chakra123\"," +
                "\n      \"units\": 10,\n      \"selling_price\": \"900\",\n      \"discount\": \"\",\n      \"tax\": \"\"," +
                "\n      \"hsn\": 441122\n    }\n  ],\n  \"payment_method\": \"Prepaid\"," +
                "\n  \"shipping_charges\": 0,\n  \"giftwrap_charges\": 0,\n  \"transaction_charges\": 0," +
                "\n  \"total_discount\": 0,\n  \"sub_total\": 9000,\n  \"length\": 10,\n  \"breadth\": 15,\n  \"height\": 20," +
                "\n  \"weight\": 2.5\n}");
        Request request = new Request.Builder()
                .url("https://apiv2.shiprocket.in/v1/external/orders/update/adhoc")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer "+token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String jsonResponse = response.body().string();
                if(response.isSuccessful()){

                    createOrderActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(createOrderActivity.this, String.valueOf(jsonResponse), Toast.LENGTH_SHORT).show();
                            Toast.makeText(createOrderActivity.this, "Order Updated", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(createOrderActivity.this,createOrderActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    });
                }else{
                    createOrderActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(createOrderActivity.this, "Ok: "+jsonResponse,Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

}

