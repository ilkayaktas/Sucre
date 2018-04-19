package edu.metu.sucre.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FCMMessage {

    @SerializedName("to")
    @Expose
    public String to;
    @SerializedName("data")
    @Expose
    public FCMData data;
    @SerializedName("notification")
    @Expose
    public FCMNotification notification;

    public FCMMessage() {
        data = new FCMData();
        notification = new FCMNotification();
    }
}
