package edu.metu.sucre.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FCMNotification {

    @SerializedName("body")
    @Expose
    public String body;
    @SerializedName("title")
    @Expose
    public String title;

}
