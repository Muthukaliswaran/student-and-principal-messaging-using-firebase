package com.prasanthkalis.gce_bodi.others;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.prasanthkalis.gce_bodi.baseclass.BaseActivity;

public class MessageSent extends BaseActivity {
    String title, body, year, email, name, mob, batch, dept;
    DatabaseReference rootRef, roorRef1;
    String sharedpref = "com.prasanthkalis.gce_bodi";

    public MessageSent(String title, String body, String year,String email,String name,String mob, String batch,String dept) {
        this.title = title;
        this.body = body;
        this.year = year;
        this.email=email;
        this.name=name;
        this.mob=mob;
        this.batch=batch;
        this.dept=dept;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public boolean adddata_ToDatabase(String check) {
        if (check.equalsIgnoreCase("0")) {

            rootRef = FirebaseDatabase.getInstance().getReference().child("students/"+mob);
            roorRef1 = FirebaseDatabase.getInstance().getReference().child("stud");
            DatabaseReference push=  rootRef.push();
            DatabaseReference push1=roorRef1.push();

            push.child("body").setValue(body);
            push1.child("body").setValue(body);

            push.child("title").setValue(title);
            push1.child("title").setValue(title);

            push.child("year").setValue(year);
            push1.child("year").setValue(year);

            push.child("email").setValue(email);
            push1.child("email").setValue(email);

            push.child("name").setValue(name);
            push1.child("name").setValue(name);

            push.child("mob").setValue(mob);
            push1.child("mob").setValue(mob);

            push.child("batch").setValue(batch);
            push1.child("batch").setValue(batch);

            push.child("dept").setValue(dept);
            push1.child("dept").setValue(dept);


        } else {
            rootRef = FirebaseDatabase.getInstance().getReference().child("principal");
            DatabaseReference push=  rootRef.push();
            push.child("body").setValue(body);
            push.child("title").setValue(title);
        }


        return true;
    }

}
