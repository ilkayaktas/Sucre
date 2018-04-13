package edu.metu.sucre.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FCMChannelResponse {

    @SerializedName("notification_key")
    @Expose
    public String notificationKey;

    @SerializedName("error")
    @Expose
    public String error;

}
