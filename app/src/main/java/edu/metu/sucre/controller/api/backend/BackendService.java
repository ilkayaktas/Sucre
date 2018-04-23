package edu.metu.sucre.controller.api.backend;

import edu.metu.sucre.model.api.*;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

/**
 * Created by ilkayaktas on 8.04.2018 at 19:33.
 */
public interface BackendService {

    @POST("login")
    Observable<?> login(@Body User user,
                        @Query(value="userId") String userId,
                        @Query(value="token") String token,
                        @Query(value="expiredate") String expireDate);

    @POST("logout")
    Observable<?> logout(@Query(value="userId") String userId,
                         @Query(value="token") String token);

    @GET("user/get")
    Observable<User> getUser(@Query("userId") String userId);

    @POST("user/save")
    Observable<User> saveUser(@Body User user);

    @GET("user/channel")
    Observable<List<Channel>> getUserChannels(@Query("userId") String userId);

    @POST("channel/create")
    Observable<Channel> createChannel(@Body Channel channel);

    @POST("channel/update")
    Observable<Channel> updateChannel(@Query("id") String id, @Query("email") String email);

    @GET("channel/get")
    Observable<Channel> getChannel(@Query("id") String channelId);

    @POST("message/send")
    Observable<Void> sendMessage(@Body Message message);

    @GET("message/get")
    Observable<List<Message>> getMessages(@Query("channelId") String channelId);

    @POST("healthdata/save")
    Observable<HealthData> saveHealthData(@Body HealthData healthData);

    @GET("healthdata/get")
    Observable<List<HealthData>> getHealthData(@Query("userId") String userId, @Query("healthDataType") String healthDataType);

    @POST("bloodsugar/save")
    Observable<BloodSugarData> saveBloodSugar(@Body BloodSugarData bloodSugar);

    @GET("bloodsugar/get")
    Observable<List<BloodSugarData>> getBloodSugar(@Query("userId") String userId, @Query("sugarMeasurementType") String sugarMeasurementType);

}
