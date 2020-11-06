package com.example.messengerapp.Fragments;

import com.example.messengerapp.Notifications.MyResponse;
import com.example.messengerapp.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAA9IUwg2U:APA91bHhfJThEIYw_50lrnols8g02FcDczwrkqC-yeNvn3iq3SiK4FwgWxzFpDnBjsfXCRllrmcXQrFvNoObtw3L6MCFd8ZelgrKsvIX2BSHZSBzNswGb0XVf5J2L3dTFuW4Txq4_niM"
    })

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
