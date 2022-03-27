package com.naman.shiprocket.createOrder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.naman.shiprocket.Dashboard;
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


        EditText cName, cProductName, cPhone,
                cAddr1, cAddr2, cCity, cState, cCountry, cPin ,cId;

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

        Button btn = findViewById(R.id.btnId);
        //cId.setTranslationY(800);
        cName.setTranslationX(800);
        cProductName.setTranslationX(-800);
        cPhone.setTranslationX(800);
        cAddr1.setTranslationX(-800);
        cAddr2.setTranslationX(800);
        cCity.setTranslationX(-800);
        cState.setTranslationX(800);
        cCountry.setTranslationX(-800);
        cPin.setTranslationX(800);

        btn.setTranslationX(-800);


        //cId.setAlpha(0);
        cName.setAlpha(0);
        cProductName.setAlpha(0);
        cPhone.setAlpha(0);
        cAddr1.setAlpha(0);
        cAddr2.setAlpha(0);
        cCity.setAlpha(0);
        cState.setAlpha(0);
        cCountry.setAlpha(0);
        cPin.setAlpha(0);

        btn.setAlpha(0);

        //cId.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(300).start();
        cName.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(300).start();
        cProductName.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(300).start();
        cPhone.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(300).start();
        cAddr1.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(300).start();
        cAddr2.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(300).start();
        cCity.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(300).start();
        cState.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(300).start();
        cCountry.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(300).start();
        cPin.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(300).start();
        btn.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(300).start();

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
                                    Toast.makeText(createOrderActivity.this, String.valueOf(jsonResponse), Toast.LENGTH_SHORT).show();
                                    Toast.makeText(createOrderActivity.this, "Order Created", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(createOrderActivity.this, createOrderActivity.class));
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


                }catch(Exception e){
                    Toast.makeText(createOrderActivity.this, "Enter Valid Details", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(createOrderActivity.this, createOrderActivity.class));
                }



            }
        });



    }
}