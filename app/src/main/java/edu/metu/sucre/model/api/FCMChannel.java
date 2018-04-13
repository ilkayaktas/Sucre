package edu.metu.sucre.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FCMChannel {

    @SerializedName("operation")
    @Expose
    public String operation;

    @SerializedName("notification_key_name")
    @Expose
    public String notificationKeyName;

    @SerializedName("notification_key")
    @Expose
    public String notificationKey;

    @SerializedName("registration_ids")
    @Expose
    public List<String> registrationIds = null;

    public FCMChannel() {
        registrationIds = new ArrayList<>();
    }
}
