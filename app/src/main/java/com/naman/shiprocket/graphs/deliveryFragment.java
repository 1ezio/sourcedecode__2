package com.naman.shiprocket.graphs;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.naman.shiprocket.ProgressDialogFragment;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link deliveryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class deliveryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public deliveryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment deliveryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static deliveryFragment newInstance(String param1, String param2) {
        deliveryFragment fragment = new deliveryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delivery, container, false);

        final ProgressDialogFragment progress=new ProgressDialogFragment(getActivity());
        progress.show();
        Bundle bundle = getActivity().getIntent().getExtras();
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

                    if (getActivity()!=null){
                        getActivity().runOnUiThread(new Runnable() {
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

                                PieChart pieChart = view.findViewById(R.id.piechart);
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
                                progress.dismiss();
                                dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                                pieChart.animateXY(5000, 5000);

                            }

                        });
                        progress.dismiss();
                    }


                }else{
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Ok: "+jsonResponse,Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        progress.dismiss();
        return view ;
    }
}