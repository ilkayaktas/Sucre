package edu.metu.sucre.views.activities.channels;


import edu.metu.sucre.model.api.Channel;
import edu.metu.sucre.model.app.Dialog;
import edu.metu.sucre.model.app.DialogMessage;
import edu.metu.sucre.views.activities.base.MvpView;

/**
 * Created by ilkay on 02/08/2017.
 */

public interface HealthChannelsMvpView extends MvpView {
    void onNewMessage(String dialogId, DialogMessage message);
    void onNewDialog(Channel channel, Dialog dialog);
    void showErrorToast(String message);
}
