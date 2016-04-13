package com.gilang.icuwatch;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gilang.icuwatch.fragment.LoginFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, LoginFragment.newInstance(this)).commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getSupportFragmentManager();
        if(manager.getBackStackEntryCount() > 0){
            manager.popBackStack();
        }else{
            super.onBackPressed();
        }
    }
}
