package com.prasanthkalis.gce_bodi.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import com.prasanthkalis.gce_bodi.databinding.ActivityStudentBinding;
import com.prasanthkalis.gce_bodi.fragment.MessageCompose;
import com.prasanthkalis.gce_bodi.fragment.Message_StudentSentFragment;
import com.prasanthkalis.gce_bodi.fragment.Message_StudentReceivedFragment;
import com.prasanthkalis.gce_bodi.notification_service.MyFirebaseMessagingService;
import com.prasanthkalis.gce_bodi.others.MessageSent;
import com.prasanthkalis.gce_bodi.presenter.SendNotificationPresenter;
import com.prasanthkalis.gce_bodi.viewpager_adapter.ViewPagerAdapter;

import static android.content.ContentValues.TAG;

public class Student extends BaseActivity implements SendNotificationPresenter.send, MessageCompose.compose  {
    public static boolean active;
    String sharedPref = "com.prasanthkalis.gce_bodi";
    ActivityStudentBinding binding;
    Context context;
    String title,body,year,email,name,mob,batch,dept;
    @Override
    protected void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        active = false;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_student);
        MyFirebaseMessagingService.count = 0;
        binding.ToolbarStud.setTitle("Student");

        SharedPreferences sharedPreferences = getSharedPreferences(sharedPref, MODE_PRIVATE);
        email = sharedPreferences.getString("StudEmail", null);
        name = sharedPreferences.getString("StudName", null);
        mob = sharedPreferences.getString("StudMob", null);
        batch = sharedPreferences.getString("StudBatch", null);
        dept = sharedPreferences.getString("StudDept", null);

        setSupportActionBar(binding.ToolbarStud);

        setupViewPager(binding.pagerOrder);

        FirebaseMessaging.getInstance().unsubscribeFromTopic("Principal");


        FirebaseMessaging.getInstance().subscribeToTopic("Student")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(Student.this, "Failed to subscribe you dont receive any messages, reinstalling the app can fix this issue", Toast.LENGTH_LONG).show();
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
                    }
                });
    }
    public void sendNotification(String title,String body) {

        MessageSent messageSent=new MessageSent(title,body,year,email,name,mob,batch,dept);
        messageSent.adddata_ToDatabase("0");

        SendNotificationPresenter sendNotificationPresenter = new SendNotificationPresenter(context, this, 0,title,body);
        sendNotificationPresenter.NotificationSend();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_student, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.SendMessageToPrincipal:
                FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.FragmentStudent,new MessageCompose(this,"student"));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();     //send notification to student
                break;

            case R.id.StudLogout:
                SharedPreferences.Editor preferences = getSharedPreferences(sharedPref, MODE_PRIVATE).edit();
                preferences.putString("LogIn_Status", "Loggedout");
                preferences.commit();
                FirebaseMessaging.getInstance().unsubscribeFromTopic("Student");
                Intent intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void success() {
        Toast.makeText(this, "Message Sent to Principal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failure() {
        Toast.makeText(this, "Failed To Send Notification Please Check Your Internet Connection!", Toast.LENGTH_SHORT).show();
    }

    public void setupViewPager(ViewPager viewpager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Message_StudentReceivedFragment(), "Received");
        adapter.addFragment(new Message_StudentSentFragment(mob), "Sent");
        viewpager.setAdapter(adapter);
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tablayout));
        viewpager.getAdapter().notifyDataSetChanged();
        binding.tablayout.setupWithViewPager(viewpager);
    }
    @Override
    public void send(String Title, String Body, String year) {
        this.title=Title;
        this.body=Body;
        this.year=year;
        sendNotification(title,body);
    }

}
