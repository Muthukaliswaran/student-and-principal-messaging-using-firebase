package com.prasanthkalis.gce_bodi.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.databinding.DataBindingUtil;

import com.prasanthkalis.gce_bodi.R;
import com.prasanthkalis.gce_bodi.baseclass.BaseActivity;
import com.prasanthkalis.gce_bodi.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {
    ActivityMainBinding binding;
    String TAG = this.getClass().getSimpleName(), Check_LoginStatus,sharedPref="com.prasanthkalis.gce_bodi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        SharedPreferences preferences = getSharedPreferences(sharedPref, MODE_PRIVATE);
        Check_LoginStatus=preferences.getString("LogIn_Status","");
        Log.d(TAG, "onCreate: "+Check_LoginStatus);

        if(Check_LoginStatus.equalsIgnoreCase("principal_Loggedin")){
            Intent intent=new Intent(this,Principal.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        }else if (Check_LoginStatus.equalsIgnoreCase("Student_Loggedin")){
            Intent intent=new Intent(this,Student.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        }

    }

}
