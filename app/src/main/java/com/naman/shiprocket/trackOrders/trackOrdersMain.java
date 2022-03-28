package com.naman.shiprocket.trackOrders;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.naman.shiprocket.DashboardItems.orders.OrdersList;
import com.naman.shiprocket.DashboardItems.orders.ordersAdapter;
import com.naman.shiprocket.DashboardItems.orders.ordersDAO;
import com.naman.shiprocket.ProgressDialogFragment;
import com.naman.shiprocket.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class trackOrdersMain extends AppCompatActivity {
    TextView tName, tAddress, tphone, tProductname, tPickname, tPickContact,
            tDeliveryDate, tExecutiveName, tExecutiveContact, tStatus, tTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_orders_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        tName = findViewById(R.id.tname);
        tphone =findViewById(R.id.tphn);
        tProductname = findViewById(R.id.tpname);
        tAddress = findViewById(R.id.taddress);
        tPickname = findViewById(R.id.tpickname);
        tPickContact = findViewById(R.id.tpickcontact);
        tDeliveryDate = findViewById(R.id.tdDate);
        tExecutiveName = findViewById(R.id.texename);
        tExecutiveContact = findViewById(R.id.ytexecontact);
        tStatus = findViewById(R.id.tstatus);
        tTotal = findViewById(R.id.ttotal);


        EditText findId = findViewById(R.id.orderId);
        Button btn = findViewById(R.id.findbtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string = findId.getText().toString();
                if (!string.equals("")){
                    findOrder(string);
                }else{
                    Toast.makeText(trackOrdersMain.this, "Enter Valid ID", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    public void findOrder(String string){
        Bundle bundle = getIntent().getExtras();
        String token = bundle.getString("token");
        final ProgressDialogFragment progress=new ProgressDialogFragment(this);
        progress.show();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://apiv2.shiprocket.in/v1/external/orders/show/"+string)
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
                    trackOrdersMain.this.runOnUiThread(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void run() {


                            try {
                                LinearLayout linearLayout = findViewById(R.id.linearLayoutSee);
                                linearLayout.setVisibility(View.VISIBLE);
                                JSONObject obj = new JSONObject(jsonResponse);
                                JSONObject jsonObject = (obj.getJSONObject("data"));
                                ArrayList<ordersDAO> arrayList = new ArrayList<>();
                                Map<String ,String> productMap = new HashMap<>();
                                String productDetails = jsonObject.getString("products");
                                tName.setText(jsonObject.getString("customer_name"));

                                tAddress.setText(jsonObject.getString("customer_address")+ " "+
                                        jsonObject.getString("customer_city") +
                                        " "+ jsonObject.getString("customer_state")); ;

                                String cusPhn = jsonObject.getString("customer_phone").equals("")?"Not Available":jsonObject.getString("customer_phone");
                                tphone.setText(cusPhn);
                                tStatus.setText(jsonObject.getString("status"));
                                String tpick = jsonObject.has("pickup_boy_name")? jsonObject.getString("pickup_boy_name"):"Not Available";
                                tPickname.setText(tpick);
                                tPickContact.setText(jsonObject.has("pickup_boy_contact")? jsonObject.getString("pickup_boy_contact"):"Not Available");

                                String cusPayStatus = jsonObject.getString("payment_status");
                                String cusCreated = jsonObject.getString("created_at");
                                String cusUpdated = jsonObject.getString("updated_at");

                                productDetails = productDetails.substring( 2,productDetails.length() - 2 );
                                String[] list = productDetails.split(",");

                                for(int j = 0;j< list.length;j++){
                                    String str = list[j];
                                    String[] arr1 = str.split(":");

                                    try{
                                        productMap.put(arr1[0].substring( 1,arr1[0].length() - 1 )
                                                , arr1[1]);
                                    }catch (Exception e ){
                                        Toast.makeText(trackOrdersMain.this, "Error !!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                tProductname.setText(productMap.get("name"));
                                tTotal.setText(productMap.get("price"));
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
                                        Toast.makeText(trackOrdersMain.this, "Error !!", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                tExecutiveName.setText(shipmentMap.get("delivery_executive_name"));
                                tDeliveryDate.setText(shipmentMap.get("delivered_date"));
                                tExecutiveContact.setText(shipmentMap.get("delivery_executive_number"));

                                
                                progress.dismiss();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    });
                }else{
                    trackOrdersMain.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(trackOrdersMain.this, "Ok: "+jsonResponse,Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        progress.dismiss();

    }

}