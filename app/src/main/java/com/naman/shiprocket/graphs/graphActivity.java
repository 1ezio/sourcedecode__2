package com.naman.shiprocket.graphs;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.naman.shiprocket.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class graphActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        Bundle bundle = getIntent().getExtras();
        String token = bundle.getString("token");
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://apiv2.shiprocket.in/v1/external/shipments")
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer "+token)
                .build();
        HashMap<String, Integer> map = new HashMap<>();
                client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String jsonResponse = response.body().string();
                if(response.isSuccessful()){


                    graphActivity.this.runOnUiThread(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void run() {


                            try {
                                JSONObject obj = new JSONObject(jsonResponse);
                                JSONArray arr = (obj.getJSONArray("data"));

                                for (int i = 0; i < arr.length(); i++){
                                    String status = arr.getJSONObject(i).getString("status");
                                     map.put(status,  Integer.valueOf(map.getOrDefault(status,0))+1) ;
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Toast.makeText(graphActivity.this, String.valueOf(map), Toast.LENGTH_SHORT).show();

                            PieChart pieChart = findViewById(R.id.piechart);
                            ArrayList<Entry> noOfValues = new ArrayList();

                            int i = 0;
                            ArrayList<String> keys = new ArrayList();
                            for (Map.Entry<String,Integer> entry : map.entrySet()){
                                noOfValues.add(new Entry(entry.getValue(), i));
                                keys.add(entry.getKey());
                                i+=1;
                            }

                            PieDataSet dataSet = new PieDataSet( noOfValues, "Status");
                            PieData data = new PieData(keys, dataSet);
                            data.setValueTextSize(10);
                            //pieChart.setCenterTextSize(30);
                            pieChart.setData(data);
                            Legend l = pieChart.getLegend();

                            dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                            pieChart.animateXY(5000, 5000);

                        }
                    });
                }else{
                    graphActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(graphActivity.this, "Ok: "+jsonResponse,Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });










    }
}