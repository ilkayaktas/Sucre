package edu.metu.sucre.model.db.realm;

import edu.metu.sucre.model.app.DialogMessage;
import io.realm.annotations.PrimaryKey;

import java.util.Date;

/**
 * Created by ilkayaktas on 21.04.2018 at 14:21.
 */
public class RealmDialogMessage {
    @PrimaryKey
    public String uuid;
    public Date date;
    public String channelId;
    public String userId;
    public String message;

    public RealmDialogMessage() {
    }

    public RealmDialogMessage(DialogMessage dialogMessage, String channelId){
        this.uuid = dialogMessage.getId();
        this.date = dialogMessage.getCreatedAt();
        this.channelId = channelId;
        this.userId = dialogMessage.getUser().getId();
        this.message = dialogMessage.getText();
    }
}
