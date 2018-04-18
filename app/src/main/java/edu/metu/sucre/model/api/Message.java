package edu.metu.sucre.model.api;

import java.util.Date;

/**
 * Created by aselsan on 18.04.2018 at 11:08.
 */

public class Message {
    public String id; // message id

    public String senderUserId;

    public String messageText;

    public String toChannelId;

    public String toUserId;

    public Date createdAt;
}
