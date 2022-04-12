package com.naman.shiprocket.TrackingWithMap;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.naman.shiprocket.DashboardItems.orders.OrdersList;
import com.naman.shiprocket.DashboardItems.orders.ordersAdapter;
import com.naman.shiprocket.DashboardItems.orders.ordersDAO;
import com.naman.shiprocket.ProgressDialogFragment;
import com.naman.shiprocket.R;
import com.naman.shiprocket.databinding.ActivityTrackingBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Tracking extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityTrackingBinding binding;
    private LatLng latLng;
    private String location;
    String token ;

    private List<LatLng> latlongList = new ArrayList<>() ;

    private String cusName,cusPhn,cuspayMethod,cusAddress,cusAdd2Show ;
    ArrayList<mapMarkerDAO> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Bundle bundle = getIntent().getExtras();
        token = bundle.getString("token");
        binding = ActivityTrackingBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://apiv2.shiprocket.in/v1/external/orders")
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer "+token)
                .build();
        List<String> mapLists =new ArrayList<>() ;
        final ProgressDialogFragment progress=new ProgressDialogFragment(this);
        progress.show();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String jsonResponse = response.body().string();

                if(response.isSuccessful()){


                    Tracking.this.runOnUiThread(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void run() {


                            try {
                                JSONObject obj = new JSONObject(jsonResponse);
                                JSONArray arr = (obj.getJSONArray("data"));

                                for (int i = 0; i < arr.length(); i++)
                                {
                                    Map<String ,String> productMap = new HashMap<>();
                                    String productDetails = arr.getJSONObject(i).getString("products");
                                    cusName = arr.getJSONObject(i).getString("customer_name");
                                    cusPhn = arr.getJSONObject(i).getString("customer_phone");
                                    cuspayMethod = arr.getJSONObject(i).getString("payment_method");
                                        cusAddress = arr.getJSONObject(i).getString("customer_city") +
                                                " "+ arr.getJSONObject(i).getString("customer_state") ;
                                    cusAdd2Show =arr.getJSONObject(i).getString("customer_address")+" " + arr.getJSONObject(i).getString("customer_address_2")+" "+
                                            arr.getJSONObject(i).getString("customer_city") +
                                            " "+ arr.getJSONObject(i).getString("customer_state") ;
                                        mapLists.add(cusAddress);
                                    String total = arr.getJSONObject(i).getString("total");
                                    productDetails = productDetails.substring( 2,productDetails.length() - 2 );
                                    String[] list = productDetails.split(",");

                                    for(int j = 0;j< list.length;j++){
                                        String str = list[j];
                                        String[] arr1 = str.split(":");
                                        try{
                                            productMap.put(arr1[0].substring( 1,arr1[0].length() - 1 )
                                                    , arr1[1]);
                                        }catch (Exception e ){
                                            Toast.makeText(Tracking.this, "Error!!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    arrayList.add(new mapMarkerDAO(cusName, cusPhn,cuspayMethod,cusAddress,
                                            productMap.get("name"),total,cusAdd2Show));

                                }
                                List<Address> addressList = null;
                                for(int i=0; i<mapLists.size();i++){
                                    String locationItems = mapLists.get(i);
                                    if(!locationItems.equals("")){
                                        Geocoder geocoder = new Geocoder(Tracking.this);
                                        try{
                                            addressList = geocoder.getFromLocationName(locationItems,1);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        Address address = (addressList.size()>0)? addressList.get(0): null;
                                        if (address==null){
                                            continue;
                                        }

                                        latLng = new LatLng(address.getLatitude(), address.getLongitude());
                                        latlongList.add(latLng);

                                        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                                .findFragmentById(R.id.map);
                                        mapFragment.getMapAsync(Tracking.this);
                                    }
                                }



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            progress.dismiss();
                        }
                    });
                }else{
                    Tracking.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Tracking.this, "Ok: "+jsonResponse,Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        LatLng latLng = null;

        Marker marker = null;


        for(int i=0;i<latlongList.size();i++){
            latLng = latlongList.get(i);

            String name = arrayList.get(i).getCusName();
            String phn = arrayList.get(i).getCusPhn();
            String method = arrayList.get(i).getCuspayMethod();
            String total = arrayList.get(i).getCusTotal();
            String address = arrayList.get(i).getCusAdd2Show();
            String cusProductName = arrayList.get(i).getCusProductName();
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));

            mMap.setInfoWindowAdapter(new customMarkerAdapter(Tracking.this));
            mMap.addMarker(new MarkerOptions().position(latLng).title("Buyer Name : "+name).snippet("Phone : " + phn+

                    '\n' + "Pay Method :"+method
                    +'\n' + "Price :"+total+
                    '\n' + "Address : " +address+
                    '\n' + "Product : " + cusProductName
            ));


        }

    }
}