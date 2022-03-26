package com.naman.shiprocket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class loginFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup)  inflater.inflate(R.layout.loginfragment, container,false);

        EditText email  = root.findViewById(R.id.userName);
        EditText password = root.findViewById(R.id.password);
        Button btn = root.findViewById(R.id.loginbtn);

        email.setTranslationX(800);
        password.setTranslationX(800);
        btn.setTranslationX(800);

        email.setAlpha(0);
        password.setAlpha(0);
        btn.setAlpha(0);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        btn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        return root;
    }
}
