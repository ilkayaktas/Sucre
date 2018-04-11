package edu.metu.sucre.views.activities.channels;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;
import edu.metu.sucre.R;
import edu.metu.sucre.model.app.Dialog;
import edu.metu.sucre.model.app.DialogsFixtures;
import edu.metu.sucre.model.app.Message;
import edu.metu.sucre.views.activities.base.BaseActivity;
import edu.metu.sucre.views.activities.messages.HealthChannelMessageActivity;

import javax.inject.Inject;


public class HealthChannelsActivity extends BaseActivity implements HealthChannelsMvpView,
        DialogsListAdapter.OnDialogClickListener<Dialog>,
        DialogsListAdapter.OnDialogLongClickListener<Dialog>{

    @Inject
    HealthChannelsMvpPresenter<HealthChannelsMvpView> mPresenter;

    protected ImageLoader imageLoader;
    protected DialogsListAdapter<Dialog> dialogsAdapter;
    @BindView(R.id.dialogsList)  DialogsList dialogsList;
    @BindView(R.id.healthview)  RelativeLayout relativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        // Attach presenter
        mPresenter.onAttach(HealthChannelsActivity.this);

        initUI();

    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_default_dialogs;
    }

    @Override
    protected void initUI() {
        imageLoader = (imageView, url) -> Picasso.with(HealthChannelsActivity.this).load(url).into(imageView);
        initAdapter();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            finish();

            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private void initAdapter() {
        dialogsAdapter = new DialogsListAdapter<>(imageLoader); // image loader ne istersen onu yapabilirsin
        dialogsAdapter.setItems(DialogsFixtures.getDialogs()); // dialog listesi ekleniyor.

        dialogsAdapter.setOnDialogClickListener(this);
        dialogsAdapter.setOnDialogLongClickListener(this);

        dialogsList.setAdapter(dialogsAdapter);
    }

    @Override
    public void onDialogClick(Dialog dialog) {
        startActivity(HealthChannelMessageActivity.class);
    }

    @Override
    public void onDialogLongClick(Dialog dialog) {
        Snackbar.make(relativeLayout, "Long pressed!", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onNewMessage(String dialogId, Message message) {
        boolean isUpdated = dialogsAdapter.updateDialogWithMessage(dialogId, message);
        if (!isUpdated) {
            //Dialog with this ID doesn't exist, so you can create new Dialog or update all dialogs list
        }
    }

    @Override
    public void onNewDialog(Dialog dialog) {
        dialogsAdapter.addItem(dialog);
    }

}
