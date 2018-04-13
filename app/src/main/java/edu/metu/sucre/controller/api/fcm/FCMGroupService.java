package edu.metu.sucre.controller.api.fcm;

import edu.metu.sucre.model.api.FCMChannel;
import edu.metu.sucre.model.api.FCMChannelResponse;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface FCMGroupService {
    @POST("notification")
    Observable<FCMChannelResponse> createGroup(@Body FCMChannel channel);
}
