package com.prasanthkalis.gce_bodi.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.prasanthkalis.gce_bodi.R;
import com.prasanthkalis.gce_bodi.databinding.MessageComposeBinding;

public class MessageCompose extends Fragment {
    MessageComposeBinding binding;
    String title, body, year;
    compose compose1;
    String check;

    public MessageCompose(compose compose1, String check) {
        this.compose1 = compose1;
        this.check = check;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.message_compose, container, false);
        if (check.equalsIgnoreCase("principal")) {
            binding.edtCurrentYear.setVisibility(View.GONE);
            binding.txtYear.setVisibility(View.GONE);
        }
        binding.btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!check.equalsIgnoreCase("principal")) {
                    title = binding.edtTitle.getText().toString();
                    body = binding.edtBody.getText().toString();
                    year = binding.edtCurrentYear.getText().toString();

                    if (title.length() != 0) {
                        if (body.length() != 0) {
                            if (year.length() != 0) {
                                compose1.send(title, body, year);
                            } else {
                                binding.edtCurrentYear.setError("Select Year");
                            }
                        } else {
                            binding.edtBody.setError("Enter Meesage Body");
                        }
                    } else {
                        binding.edtTitle.setError("Enter Title");
                    }
                } else {
                    title = binding.edtTitle.getText().toString();
                    body = binding.edtBody.getText().toString();
                    if (title.length() != 0) {
                        if (body.length() != 0) {
                            compose1.send(title, body, year);

                        } else {
                            binding.edtBody.setError("Enter Meesage Body");
                        }
                    } else {
                        binding.edtTitle.setError("Enter Title");
                    }
                }
            }
        });
        return binding.getRoot();
    }

    public interface compose {
        void send(String Title, String Body, String year);
    }
}
