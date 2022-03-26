package com.naman.shiprocket.DashboardItems.orders;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.naman.shiprocket.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        //TextView text = (TextView) findViewById(R.id.dummytext);

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
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                if(response.isSuccessful()){


                    OrdersList.this.runOnUiThread(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void run() {


                            try {
                                JSONObject obj = new JSONObject(jsonResponse);
                                JSONArray arr = (obj.getJSONArray("data"));
                                ArrayList<ordersDAO> arrayList = new ArrayList<>();
                                for (int i = 0; i < arr.length(); i++)
                                {
                                    Map<String ,String> productMap = new HashMap<>();
                                    String productDetails = arr.getJSONObject(i).getString("products");
                                    String cusName = arr.getJSONObject(i).getString("customer_name");
                                    String cusID = arr.getJSONObject(i).getString("id");
                                    String cusPhn = arr.getJSONObject(i).getString("customer_phone");
                                    String cusEmail = arr.getJSONObject(i).getString("customer_email");
                                    String cuspayMethod = arr.getJSONObject(i).getString("payment_method");
                                    String cusPayStatus = arr.getJSONObject(i).getString("payment_status");
                                    String cusCreated = arr.getJSONObject(i).getString("created_at");
                                    String cusUpdated = arr.getJSONObject(i).getString("updated_at");



                                    Toast.makeText(OrdersList.this, cusName, Toast.LENGTH_SHORT).show();
                                    productDetails = productDetails.substring( 2,productDetails.length() - 2 );
                                    String[] list = productDetails.split(",");

                                    for(int j = 0;j< list.length;j++){
                                        String str = list[j];
                                        String[] arr1 = str.split(":");
                                        //Log.d("TAG", String.valueOf(arr1.length));
                                        //Toast.makeText(OrdersList.this, str, Toast.LENGTH_SHORT).show();
                                        try{
                                            productMap.put(arr1[0].substring( 1,arr1[0].length() - 1 )
                                                    , arr1[1]);
                                        }catch (Exception e ){
                                            //map.put(arr1[0], "NA");
                                        }


                                    }
                                    //text.setText(String.valueOf(productMap));
                                    arrayList.add(new ordersDAO(cusName, cusID,cusEmail, cusPhn,cusPayStatus
                                            ,productMap.get("price"),cuspayMethod,cusCreated,cusUpdated,
                                            productMap.get("id"),productMap.get("name"),"1"));
                                    //Toast.makeText(OrdersList.this, String.valueOf(productMap.get("price")), Toast.LENGTH_LONG).show();



                                }
                                ordersAdapter adapter = new ordersAdapter( getApplicationContext(),arrayList);
                                recyclerView.setLayoutManager(new LinearLayoutManager(OrdersList.this));
                                recyclerView.setAdapter(adapter);


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