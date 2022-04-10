package com.naman.shiprocket.graphs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.naman.shiprocket.R;

public class GraphNewActivity extends AppCompatActivity {

    MeowBottomNavigation meowBottomNavigation ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_new);
        meowBottomNavigation= findViewById(R.id.bottomNavigation);
        meowBottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.deliverygraph));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.increase));

        meowBottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment = null ;

                switch (item.getId()){
                    case 1 :
                        fragment = new deliveryFragment(    );
                        break;
                    case 2:
                        fragment = new mapFragment();
                        break;
                }
                loadfragment(fragment);
            }
        });
        meowBottomNavigation.show(1,true);

        meowBottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

            }
        });

        meowBottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {

            }
        });

    }


    private void loadfragment(Fragment fragment) {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout,fragment).commit();

    }
}