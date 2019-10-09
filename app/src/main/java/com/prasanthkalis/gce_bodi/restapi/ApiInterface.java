package com.prasanthkalis.gce_bodi.restapi;

import com.google.gson.JsonObject;
import com.prasanthkalis.gce_bodi.model.NotificationModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface ApiInterface {

    @POST("fcm/send")
    Call<NotificationModel> sendNotification(@Header("Authorization") String val, @Body JsonObject sendnotification);

}
