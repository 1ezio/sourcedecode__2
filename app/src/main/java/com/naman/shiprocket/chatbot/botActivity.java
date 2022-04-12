package com.naman.shiprocket.chatbot;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.naman.shiprocket.DashboardItems.orders.ordersAdapter;
import com.naman.shiprocket.DashboardItems.orders.ordersDAO;
import com.naman.shiprocket.ProgressDialogFragment;
import com.naman.shiprocket.R;
import com.naman.shiprocket.TrackingWithMap.Tracking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class botActivity extends AppCompatActivity {

    private RecyclerView chatsRV;
    private ImageButton sendMsgIB;
    private EditText userMsgEdt;
    private final String USER_KEY = "user";
    private final String BOT_KEY = "bot";
    private RequestQueue mRequestQueue;
    String[] finalResponse = new String[1];
    // creating a variable for array list and adapter class.
    private ArrayList<messageModel> messageModelArrayList;
    private MessageRVAdapter messageRVAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bot);
        chatsRV = findViewById(R.id.idRVChats);
        sendMsgIB = findViewById(R.id.idIBSend);
        userMsgEdt = findViewById(R.id.idEdtMessage);

        mRequestQueue = Volley.newRequestQueue(botActivity.this);
        mRequestQueue.getCache().clear();

        // creating a new array list
        messageModelArrayList = new ArrayList<>();
        messageModelArrayList.add(new messageModel("Hi, I am BOT! How can I assist you?", BOT_KEY));
        sendMsgIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userMsgEdt.getText().toString().isEmpty()) {
                    Toast.makeText(botActivity.this, "Please enter your message..", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendMessage(userMsgEdt.getText().toString());
                userMsgEdt.setText("");
            }
        });

        messageRVAdapter = new MessageRVAdapter(messageModelArrayList, this);

        // below line we are creating a variable for our linear layout manager.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(botActivity.this, RecyclerView.VERTICAL, false);

        chatsRV.setLayoutManager(linearLayoutManager);

        chatsRV.setAdapter(messageRVAdapter);
    }

    private void sendMessage(String userMsg) {

        messageModelArrayList.add(new messageModel(userMsg, USER_KEY));
        messageRVAdapter.notifyDataSetChanged();
        String url = "http://api.brainshop.ai/get?bid=165475&key=aZOTMrYWvhJ7PkHA&uid=[uid]&msg="+userMsg ;

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://api.brainshop.ai/").addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<chatmodel> call = retrofitAPI.getMessage(url);
        call.enqueue(new Callback<chatmodel>() {
            @Override
            public void onResponse(Call<chatmodel> call, Response<chatmodel> response) {
                if(response.isSuccessful()){
                    chatmodel model = response.body();
                    String cnt = model.getCnt();
                    char[] chars = cnt.toCharArray();
                    StringBuilder sb = new StringBuilder();
                    for(char c : chars){
                        if(Character.isDigit(c)){
                            sb.append(c);
                        }
                    }

                    Toast.makeText(botActivity.this, sb.toString(), Toast.LENGTH_SHORT).show();
                    Bundle bundle = getIntent().getExtras();
                    String token = bundle.getString("token");
                    if (sb.length()>0){


                        OkHttpClient client = new OkHttpClient().newBuilder()
                                .build();
                        Request request = new Request.Builder()
                                .url("https://apiv2.shiprocket.in/v1/external/orders/show/"+sb)
                                .method("GET", null)
                                .addHeader("Content-Type", "application/json")
                                .addHeader("Authorization", "Bearer "+token)
                                .build();

                        client.newCall(request).enqueue(new okhttp3.Callback() {
                            @Override
                            public void onFailure(okhttp3.Call call, IOException e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                                final String jsonResponse = response.body().string();
                                if(response.isSuccessful()){
                                    botActivity.this.runOnUiThread(new Runnable() {
                                        @RequiresApi(api = Build.VERSION_CODES.N)
                                        @Override
                                        public void run() {


                                            try {
                                                //Toast.makeText(botActivity.this, "Inside JSON", Toast.LENGTH_SHORT).show();
                                                JSONObject obj = new JSONObject(jsonResponse);
                                                JSONObject jsonObject = (obj.getJSONObject("data"));
                                                ArrayList<ordersDAO> arrayList = new ArrayList<>();
                                                Map<String ,String> productMap = new HashMap<>();
                                                String productDetails = jsonObject.getString("products");
                                                String name = (jsonObject.getString("customer_name"));

                                                String address = (jsonObject.getString("customer_address")+ " "+
                                                        jsonObject.getString("customer_city") +
                                                        " "+ jsonObject.getString("customer_state")); ;

                                                String cusPhn = jsonObject.getString("customer_phone").equals("")?"Not Available":jsonObject.getString("customer_phone");

                                                String status = (jsonObject.getString("status"));

                                                productDetails = productDetails.substring( 2,productDetails.length() - 2 );
                                                String[] list = productDetails.split(",");

                                                for(int j = 0;j< list.length;j++){
                                                    String str = list[j];
                                                    String[] arr1 = str.split(":");

                                                    try{
                                                        productMap.put(arr1[0].substring( 1,arr1[0].length() - 1 )
                                                                , arr1[1]);
                                                    }catch (Exception e ){
                                                        Toast.makeText(botActivity.this, "Error !!", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                                String productName = (productMap.get("name"));
                                                String price = (productMap.get("price"));
                                                String shipmentDetails = jsonObject.getString("shipments");
                                                shipmentDetails = shipmentDetails.substring( 2,shipmentDetails.length() - 2 );
                                                String[] lists = shipmentDetails.split(",");
                                                Map<String ,String> shipmentMap = new HashMap<>();
                                                for(int j = 0;j< lists.length;j++){
                                                    String str = lists[j];
                                                    String[] arr1 = str.split(":");

                                                    try{
                                                        shipmentMap.put(arr1[0].substring( 1,arr1[0].length() - 1 )
                                                                , arr1[1]);
                                                    }catch (Exception e ){
                                                        //Toast.makeText(botActivity.this, "Error !!", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                String finalResponse= "Name: "+name+"\n"+
                                                        "Phone: "+cusPhn+"\n"+"Address: "+address+"\n"
                                                        +"Product Name: "+productName+"\n"+"Price: "+price;
                                                messageModelArrayList.add(new messageModel(finalResponse, BOT_KEY));
                                                messageRVAdapter.notifyDataSetChanged();



                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }


                                        }
                                    });
                                }else{
                                    botActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            String finalResponse = "Order Does not Exist";
                                            messageModelArrayList.add(new messageModel(finalResponse, BOT_KEY));
//                            Toast.makeText(botActivity.this, "Ok: "+jsonResponse,Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        });



//                        String finalResponse ="Here are the Order Details of "+sb.toString()+ getOrderDetails(sb.toString());
                    }
                    else if (cnt.contains("Map") || cnt.contains("maps")){
                        messageModelArrayList.add(new messageModel(model.getCnt(), BOT_KEY));
                        messageRVAdapter.notifyDataSetChanged();
                        Intent intent =new Intent(botActivity.this, Tracking.class);
                        intent.putExtra("token", token);
                        startActivity(intent);


                    }
                    else{
                        messageModelArrayList.add(new messageModel(model.getCnt()+sb, BOT_KEY));
                    }
                    messageRVAdapter.notifyDataSetChanged();
                }else{
                    messageModelArrayList.add(new messageModel(response.toString()  , BOT_KEY));
                    messageRVAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<chatmodel> call, Throwable t) {
                messageModelArrayList.add(new messageModel( t.getMessage(), BOT_KEY));
                messageRVAdapter.notifyDataSetChanged();
            }
        });




//        RequestQueue queue = Volley.newRequestQueue(botActivity.this);
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//
//                    String botResponse = response.getString("cnt");
//                    messageModelArrayList.add(new messageModel(botResponse, BOT_KEY));
//
//                    messageRVAdapter.notifyDataSetChanged();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//
//                    messageModelArrayList.add(new messageModel("No response", BOT_KEY));
//                    messageRVAdapter.notifyDataSetChanged();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                // error handling.
//                messageModelArrayList.add(new messageModel(error.getMessage(), BOT_KEY));
//                Toast.makeText(botActivity.this, "No response from the bot..", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//        queue.add(jsonObjectRequest);
    }

    String getOrderDetails(String sb) {


        return finalResponse[0];
    }
}