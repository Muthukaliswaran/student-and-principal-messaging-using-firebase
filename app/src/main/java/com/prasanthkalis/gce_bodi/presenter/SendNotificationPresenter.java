package com.prasanthkalis.gce_bodi.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.prasanthkalis.gce_bodi.model.NotificationModel;
import com.prasanthkalis.gce_bodi.restapi.ApiClient;
import com.prasanthkalis.gce_bodi.restapi.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class SendNotificationPresenter {
    public send send1;

    String Notification_Body,title,body;
    Context context;
    int checkcall_from_student_or_pricipal;

    public SendNotificationPresenter(Context context, send send1, int checkcall_from_student_or_pricipal,String title,String body) {
        this.context = context;
        this.send1 = send1;
        this.checkcall_from_student_or_pricipal = checkcall_from_student_or_pricipal;
        this.body=body;
        this.title=title;
    }

    public void NotificationSend() {
        JsonObject sendTo = new JsonObject();
        JsonObject notification = new JsonObject();
        JsonObject data = new JsonObject();
        try {
            if (checkcall_from_student_or_pricipal == 0) {   //send to principal block
                notification.addProperty("body", body);
                notification.addProperty("title", title);

                data.addProperty("message", "notificationMessage");
                data.addProperty("key1", "we will meet tommorow");
                data.addProperty("key2", "StudentToPrincipal");


                sendTo.addProperty("to", "/topics/Principal");
                sendTo.add("notification", notification);
                sendTo.add("data", data);


            } else {                                                    //send to student block
                notification.addProperty("body", body);
                notification.addProperty("title", title);

                data.addProperty("message", "notificationMessage");
                data.addProperty("key1", "we will meet tommorow");
                data.addProperty("key2", "PrincipalToStudent");
                sendTo.addProperty("to", "/topics/Student");

                sendTo.add("notification", notification);
                sendTo.add("data", data);

            }


        } catch (Exception e) {

        }


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<NotificationModel> usercall = apiInterface.sendNotification("key=AAAAs5S5G9E:APA91bHvqsBY9UwWNGE3lCWZAf_xXyPiIuBb6VZkf2Gd7yRlPrdVd6l6PDguSg0ktJqa082iTqW81iNdW2_hmWzk152URUS_LmruA06wH6puUg_-A_K5aAPOvwewaFTSOGBv2CBwUM0M", sendTo);
        usercall.enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "onResponse: " + response.message());
                    send1.success();

                } else {
                    Toast.makeText(context, "Something Went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NotificationModel> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t);
                send1.failure();
            }
        });
    }


    public interface send {
        void success();

        void failure();
    }
}
