package com.naman.shiprocket;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class loginAdapter extends FragmentPagerAdapter {

    private Context context ;
    int tabsCount ;

    public loginAdapter(FragmentManager fm, Context context,int tabsCount){
        super(fm);
        this.context = context;
        this.tabsCount = tabsCount;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        loginFragment loginFragment = new loginFragment();
        return loginFragment;
    }

    @Override
    public int getCount() {
        return 1 ;
    }
}
