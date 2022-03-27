package com.naman.shiprocket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

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

public class loginActivity extends AppCompatActivity {

    TabLayout tabLayout ;
    ViewPager viewPager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText userName = (EditText) findViewById(R.id.userName);
        EditText password = (EditText) findViewById(R.id.password);
        Button btn = (Button) findViewById(R.id.loginbtn);


        userName.setTranslationX(800);
        password.setTranslationX(800);
        btn.setTranslationX(800);

        userName.setAlpha(0);
        password.setAlpha(0);
        btn.setAlpha(0);

        userName.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        btn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userNameString = userName.getText().toString();
                String passwordString = password.getText().toString();
                getLogin(userNameString,passwordString);
            }
        });
    }

    private void getLogin(String username, String password ){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", username);
            jsonObject.put("password", password);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        MediaType JSON =MediaType.parse("application/json; charset=utf-8");
        RequestBody body =  RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder()
                .url("https://apiv2.shiprocket.in/v1/external/auth/login")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
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

                    loginActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(loginActivity.this, "Ok: "+jsonResponse,Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(loginActivity.this, Dashboard.class);
                            intent.putExtra("jsonResponse", jsonResponse);
                            startActivity(intent);
                        }
                    });
                }else{
                    loginActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(loginActivity.this, "Ok: "+jsonResponse,Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }


}