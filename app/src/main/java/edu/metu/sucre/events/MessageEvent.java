package edu.metu.sucre.events;

import edu.metu.sucre.model.api.Message;

/**
 * Created by ilkayaktas on 19.04.2018 at 23:33.
 */
public class MessageEvent {
    public Message message;

    public MessageEvent(Message message) {
        this.message = message;
    }
}
