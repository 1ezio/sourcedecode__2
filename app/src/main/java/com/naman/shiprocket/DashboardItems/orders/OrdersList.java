package com.naman.shiprocket.DashboardItems;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.naman.shiprocket.Dashboard;
import com.naman.shiprocket.R;
import com.naman.shiprocket.loginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OrdersList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_list);
        Bundle bundle = getIntent().getExtras();
        String token = bundle.getString("token");
        TextView text = (TextView) findViewById(R.id.dummytext);

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://apiv2.shiprocket.in/v1/external/orders")
                .method("GET", null)
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

                    OrdersList.this.runOnUiThread(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void run() {


                            try {
                                JSONObject obj = new JSONObject(jsonResponse);
                                JSONArray arr = (obj.getJSONArray("data"));
                                for (int i = 0; i < arr.length(); i++)
                                {
                                    String productDetails = arr.getJSONObject(i).getString("products");
                                    productDetails = productDetails.substring( 2,productDetails.length() - 2 );
                                    String[] list = productDetails.split(",");
                                    Map<String ,String> productMap = new HashMap<String, String>();
                                    for(int j = 0;j< list.length;j++){
                                        String str = list[j];
                                        String[] arr1 = str.split(":");
                                        Log.d("TAG", String.valueOf(arr1.length));
                                        try{
                                            productMap.put(arr1[0], arr1[1]);
                                        }catch (Exception e ){
                                            //map.put(arr1[0], "NA");
                                        }

                                    }
                                    text.setText(String.valueOf(productMap));

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    });
                }else{
                    OrdersList.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(OrdersList.this, "Ok: "+jsonResponse,Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


    }
}