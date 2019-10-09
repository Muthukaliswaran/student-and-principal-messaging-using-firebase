package com.prasanthkalis.gce_bodi.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.prasanthkalis.gce_bodi.R;
import com.prasanthkalis.gce_bodi.baseclass.BaseActivity;
import com.prasanthkalis.gce_bodi.databinding.ActivityPrincipalBinding;
import com.prasanthkalis.gce_bodi.fragment.MessageCompose;
import com.prasanthkalis.gce_bodi.fragment.Message_PrincipalReceivedFragment;
import com.prasanthkalis.gce_bodi.fragment.Message_PrincipalSentFragment;
import com.prasanthkalis.gce_bodi.notification_service.MyFirebaseMessagingService;
import com.prasanthkalis.gce_bodi.others.MessageSent;
import com.prasanthkalis.gce_bodi.presenter.SendNotificationPresenter;
import com.prasanthkalis.gce_bodi.viewpager_adapter.ViewPagerAdapter;

import static android.content.ContentValues.TAG;

public class Principal extends BaseActivity implements SendNotificationPresenter.send, MessageCompose.compose {
    String sharedPref = "com.prasanthkalis.gce_bodi";
    Context context;
    ActivityPrincipalBinding binding;
    String title,body,year,email,name,mob,batch,dept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_principal);
        MyFirebaseMessagingService myFirebaseMessagingService = new MyFirebaseMessagingService();
        MyFirebaseMessagingService.count = 0;
        binding.ToolbarPrinci.setTitle("Principal");
        setSupportActionBar(binding.ToolbarPrinci);
        SharedPreferences.Editor sharedpref = getSharedPreferences(sharedPref, MODE_PRIVATE).edit();
        sharedpref.putString("LogIn_Status", "principal_Loggedin");
        sharedpref.commit();

        setupViewPager(binding.pagerOrder);
        FirebaseMessaging.getInstance().unsubscribeFromTopic("Student");


        FirebaseMessaging.getInstance().subscribeToTopic("Principal")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(Principal.this, "Failed to subscribe you dont receive any messages, reinstalling the app can fix this issue", Toast.LENGTH_LONG).show();
                        }
                    }
                });
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toas
                        Log.d(TAG, "token123: " + token);
                    }
                });

    }


    public void sendNotification(String title, String body, String year) {
        SharedPreferences sharedPreferences = getSharedPreferences(sharedPref, MODE_PRIVATE);
        email = sharedPreferences.getString("StudEmail", null);
        name = sharedPreferences.getString("StudName", null);
        mob = sharedPreferences.getString("StudMob", null);
        batch = sharedPreferences.getString("StudBatch", null);
        dept = sharedPreferences.getString("StudDept", null);

        MessageSent messageSent=new MessageSent(title,body,year,email,name,mob,batch,dept);
        messageSent.adddata_ToDatabase("1");


        SendNotificationPresenter sendNotificationPresenter = new SendNotificationPresenter(context, this, 1, title, body);
        sendNotificationPresenter.NotificationSend();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_pricipal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.SendMessageToStudent:
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.FragmentPrincipal, new MessageCompose(this,"principal"));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.PrincipalLogout:
                SharedPreferences.Editor preferences = getSharedPreferences(sharedPref, MODE_PRIVATE).edit();
                preferences.putString("LogIn_Status", "Loggedout");
                preferences.commit();
                FirebaseMessaging.getInstance().unsubscribeFromTopic("Principal");
                Intent intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void success() {
        Toast.makeText(this, "Message Sent to Students", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failure() {
        Toast.makeText(this, "Failed To Send Notification Please Check Your Internet Connection!", Toast.LENGTH_SHORT).show();
    }

    public void setupViewPager(ViewPager viewpager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Message_PrincipalReceivedFragment(), "Received");
        adapter.addFragment(new Message_PrincipalSentFragment(), "Sent");
        viewpager.setAdapter(adapter);
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tablayout));
        viewpager.getAdapter().notifyDataSetChanged();
        binding.tablayout.setupWithViewPager(viewpager);
    }

    @Override
    public void send(String Title, String Body, String year) {
        this.title = Title;
        this.body = Body;
        this.year = year;
        sendNotification(title, body, year);
    }
}
