package edu.metu.sucre.model.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilkayaktas on 9.04.2018 at 00:20.
 */
public class Channel {
    public String id; // channel id

    public String channelName;

    public String owner; // userId

    public String notificationKey; // notification key for FCM messages

    public List<String> guestUserId; // list of user ids

    public Channel() {
        guestUserId = new ArrayList<>();
    }
}
