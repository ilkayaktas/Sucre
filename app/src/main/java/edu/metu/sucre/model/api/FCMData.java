package edu.metu.sucre.model.api;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class FCMData {

    @SerializedName("messageText")
    @Expose
    public String messageText;

    @SerializedName("createdAt")
    @Expose
    public Date createdAt;

    @SerializedName("senderUserId")
    @Expose
    public String senderUserId;

    @SerializedName("toChannelId")
    @Expose
    public String toChannelId;

}
