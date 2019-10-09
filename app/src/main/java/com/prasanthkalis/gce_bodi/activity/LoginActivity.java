package com.prasanthkalis.gce_bodi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.prasanthkalis.gce_bodi.R;
import com.prasanthkalis.gce_bodi.baseclass.BaseActivity;
import com.prasanthkalis.gce_bodi.databinding.ActivityLoginBinding;
import com.prasanthkalis.gce_bodi.fragment.StudentRegisterFragment;

public class LoginActivity extends BaseActivity {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        binding.btnPrincipalLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.edtName.getText().toString();
                if (!binding.edtName.getText().toString().equalsIgnoreCase("")) {
                    binding.edtPassword.getText().toString();
                    if (!binding.edtPassword.getText().toString().equalsIgnoreCase("")) {
                        Intent intent = new Intent(LoginActivity.this, Principal.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        binding.edtPassword.setError("Enter Password");
                    }
                } else {
                    binding.edtName.setError("Enter Name");
                }


            }
        });
        binding.txtStudentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent=new Intent(LoginActivity.this,Student.class);
                startActivity(intent);
           */
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.frame_Register, new StudentRegisterFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });


    }

}
