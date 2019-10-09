package com.prasanthkalis.gce_bodi.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.prasanthkalis.gce_bodi.R;
import com.prasanthkalis.gce_bodi.activity.Student;
import com.prasanthkalis.gce_bodi.databinding.FragmentOtpBinding;

import java.util.concurrent.TimeUnit;

public class OtpFragment extends Fragment {
    FragmentOtpBinding binding;
    FirebaseAuth auth;
    String phoneNumber, studName, studEmail, studBatch,stuDept;
    SharedPreferences.Editor preferences;
    String sharedpref = "com.prasanthkalis.gce_bodi";
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    private String verificationCode;

    public OtpFragment(String phoneNumber, String studEmail, String studName, String studBatch,String stuDept) {
        this.phoneNumber = phoneNumber;
        this.studName = studName;
        this.studEmail = studEmail;
        this.studBatch = studBatch;
        this.stuDept=stuDept;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_otp, container, false);
        preferences = getActivity().getSharedPreferences(sharedpref, Context.MODE_PRIVATE).edit();
        StartFirebaseLogin();
        phoneNumber = "+91".concat(phoneNumber);
        getOtp();

        textwatcher();    //call to text watcher function

        binding.btnCheckOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String otp = binding.edt1.getText().toString().concat(binding.edt2.getText().toString()).concat(binding.edt3.getText().toString()).concat(binding.edt4.getText().toString())
                            .concat(binding.edt5.getText().toString()).concat(binding.edt6.getText().toString());
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, otp);
                    SigninWithPhone(credential);
                } catch (Exception e) {
                }
            }

        });

        return binding.getRoot();
    }

    private void SigninWithPhone(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            preferences.putString("StudEmail", studEmail);
                            preferences.putString("StudName", studName);
                            preferences.putString("StudMob", phoneNumber);
                            preferences.putString("StudBatch", studBatch);
                            preferences.putString("StudDept",stuDept);
                            preferences.putString("LogIn_Status", "Student_Loggedin");
                            preferences.commit();
                            Intent intent = new Intent(getActivity(), Student.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            getActivity().finish();
                        } else {
                            Toast.makeText(getActivity(), "Incorrect OTP", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void StartFirebaseLogin() {
        FirebaseApp.initializeApp(getActivity());
        auth = FirebaseAuth.getInstance();
        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                String a = phoneAuthCredential.getSmsCode();
                if (a != null) {
                    char[] arr = a.toCharArray();
                    System.out.println(arr[0] + " " + arr[1] + " " + arr[2] + " " + arr[3] + " " + arr[4] + " " + arr[5]);
                    binding.edt1.setText(String.valueOf(arr[0]));
                    binding.edt2.setText(String.valueOf(arr[1]));
                    binding.edt3.setText(String.valueOf(arr[2]));
                    binding.edt4.setText(String.valueOf(arr[3]));
                    binding.edt5.setText(String.valueOf(arr[4]));
                    binding.edt6.setText(String.valueOf(arr[5]));

                }
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(getActivity(), "" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationCode = s;
                Toast.makeText(getActivity(), "Code sent", Toast.LENGTH_SHORT).show();
            }
        };


    }

    public void getOtp() {
        if (!phoneNumber.equalsIgnoreCase("")) {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phoneNumber,                     // Phone number to verify
                    60,                           // Timeout duration
                    TimeUnit.SECONDS,                // Unit of timeout
                    getActivity(),        // Activity (for callback binding)
                    mCallback);
        }
    }

    public void textwatcher() {
        binding.edt1.requestFocus();
        binding.edt1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.edt1.getText().toString().length() == 1) {
                    if (binding.edt2.getText().toString().length() == 0) {
                        binding.edt2.requestFocus();
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.edt2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.edt2.getText().toString().length() == 1) {
                    if (binding.edt3.getText().toString().length() == 0) {
                        binding.edt3.requestFocus();
                    }
                }
                if (binding.edt2.getText().toString().length() == 0) {
                    binding.edt1.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.edt3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.edt3.getText().toString().length() == 1) {
                    if (binding.edt4.getText().toString().length() == 0) {
                        binding.edt4.requestFocus();
                    }

                }
                if (binding.edt3.getText().toString().length() == 0) {
                    binding.edt2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.edt4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.edt4.getText().toString().length() == 1) {
                    if (binding.edt5.getText().toString().length() == 0) {
                        binding.edt5.requestFocus();
                    }

                }
                if (binding.edt4.getText().toString().length() == 0) {
                    binding.edt3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.edt5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.edt5.getText().toString().length() == 1) {
                    if (binding.edt6.getText().toString().length() == 0) {
                        binding.edt6.requestFocus();
                    }
                }
                if (binding.edt5.getText().toString().length() == 0) {
                    binding.edt4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.edt6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.edt6.getText().toString().length() == 0) {
                    binding.edt5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

}