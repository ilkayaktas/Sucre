package edu.metu.sucre.model.app;

import com.stfalcon.chatkit.commons.models.IDialog;

import java.util.ArrayList;

/*
 * Created by troy379 on 04.04.17.
 */
public class Dialog implements IDialog<DialogMessage> {

    private String id;
    private String dialogPhoto;
    private String dialogName;
    private ArrayList<DialogUser> users;
    private DialogMessage lastMessage;
    private String notificationKey;

    private int unreadCount;

    public Dialog(String id, String name, String photo,
                  ArrayList<DialogUser> users, DialogMessage lastMessage, int unreadCount, String notificationKey) {

        this.id = id;
        this.dialogName = name;
        this.dialogPhoto = photo;
        this.users = users;
        this.lastMessage = lastMessage;
        this.notificationKey = notificationKey;
        this.unreadCount = unreadCount;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getDialogPhoto() {
        return dialogPhoto;
    }

    @Override
    public String getDialogName() {
        return dialogName;
    }

    @Override
    public ArrayList<DialogUser> getUsers() {
        return users;
    }

    @Override
    public DialogMessage getLastMessage() {
        return lastMessage;
    }

    @Override
    public void setLastMessage(DialogMessage lastMessage) {
        this.lastMessage = lastMessage;
    }

    @Override
    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public String getNotificationKey() {
        return notificationKey;
    }

    public void setNotificationKey(String notificationKey) {
        this.notificationKey = notificationKey;
    }
}
