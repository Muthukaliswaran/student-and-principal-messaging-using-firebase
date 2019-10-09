package com.prasanthkalis.gce_bodi.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.prasanthkalis.gce_bodi.R;
import com.prasanthkalis.gce_bodi.databinding.FragmentStudentregisterBinding;

public class StudentRegisterFragment extends Fragment {
    FragmentStudentregisterBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_studentregister, null, false);


        binding.btnStudentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
        return binding.getRoot();
    }

    void validation() {


        if (!binding.edtStudentEmail.getText().toString().equalsIgnoreCase("")) {
            if (!binding.edtStudentName.getText().toString().equalsIgnoreCase("")) {
                if (!binding.edtBatch.getText().toString().equalsIgnoreCase("")) {
                    if (!binding.edtDept.getText().toString().equalsIgnoreCase("")) {
                        if (!binding.edtMobileNum.getText().toString().equalsIgnoreCase("")) {
                            if (binding.chBoxStudent.isChecked()) {
                                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                                FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.add(R.id.frame_Register, new OtpFragment(binding.edtMobileNum.getText().toString(), binding.edtStudentEmail.getText().toString(), binding.edtStudentName.getText().toString(), binding.edtBatch.getText().toString(),binding.edtDept.getText().toString()));
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                            } else {
                                binding.chBoxStudent.setError("Please Accept the Agreement");
                            }
                        } else {
                            binding.edtMobileNum.setError("Enter Mobile Number");
                        }
                    } else {
                        binding.edtDept.setError("Select Department");
                    }
                } else {
                    binding.edtBatch.setError("Enter Batch Details");
                }
            } else {
                binding.edtStudentName.setError("Please Enter Name");
            }
        } else {
            binding.edtStudentEmail.setError("Please Enter Email");
        }
    }


}