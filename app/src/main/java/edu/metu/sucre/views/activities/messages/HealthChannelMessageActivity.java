package edu.metu.sucre.views.activities.messages;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;
import com.stfalcon.chatkit.utils.DateFormatter;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;
import edu.metu.sucre.R;
import edu.metu.sucre.events.MessageEvent;
import edu.metu.sucre.model.api.Channel;
import edu.metu.sucre.model.api.Message;
import edu.metu.sucre.model.api.User;
import edu.metu.sucre.model.app.DialogMessage;
import edu.metu.sucre.model.app.DialogUser;
import edu.metu.sucre.model.app.MessagesFixtures;
import edu.metu.sucre.utils.AppConstants;
import edu.metu.sucre.utils.DateUtils;
import edu.metu.sucre.views.activities.base.BaseActivity;
import edu.metu.sucre.views.activities.healthdatalist.HealthDataListActivity;
import edu.metu.sucre.views.activities.sugarlevel.SugarLevelActivity;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;
import java.util.*;

public class HealthChannelMessageActivity extends BaseActivity
		implements HealthChannelMessageMvpView,
					MessageInput.InputListener,
					MessageInput.AttachmentsListener,
					MessagesListAdapter.SelectionListener,
					MessagesListAdapter.OnLoadMoreListener,
					MessagesListAdapter.OnMessageViewClickListener<DialogMessage>,
					DateFormatter.Formatter  {
	
	@Inject
	HealthChannelMessageMvpPresenter<HealthChannelMessageMvpView> presenter;

	@BindView(R.id.toolbar_title) TextView toolbarTitle;
	@BindView(R.id.messagesList) MessagesList messagesList;
	@BindView(R.id.input) MessageInput input;

	private static final int TOTAL_MESSAGES_COUNT = 100;

	protected String senderId = "0";
	protected ImageLoader imageLoader;
	protected MessagesListAdapter<DialogMessage> messagesAdapter;

	private Menu menu;
	private int selectionCount;
	private Date lastLoadedDate;
	private String dialogId = null;
	private Map<String, User> userMapOfChannel = new HashMap<>();
	private Channel thisChannel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Get arguments
		getArguments();

		// Dependency Injection
		getActivityComponent().inject(this);

		// Butterknife binding
		setUnBinder(ButterKnife.bind(this));
		
		// Attach presenter
		presenter.onAttach(HealthChannelMessageActivity.this);

		// Init UI
		initUI();
	}

	private void getArguments() {
		Bundle b = getIntent().getExtras();
		if(b != null){
			dialogId = b.getString(AppConstants.DIALOG_ID);
			senderId = b.getString(AppConstants.SENDER_ID);
		}
	}

	@Override
	protected int getActivityLayout() {
		return R.layout.activity_default_messages;
	}

	@Override
	protected void initUI() {
		toolbarTitle.setTypeface(sketchFont);

		input.setInputListener(this);
		input.setAttachmentsListener(this);

		imageLoader = (imageView, url) -> Picasso.with(HealthChannelMessageActivity.this).load(url).into(imageView);

		initAdapter();

		presenter.getUsersOfChannels(dialogId);
		presenter.getChannel(dialogId);
	}

	@Override
	protected void onStart() {
		super.onStart();
		// TODO Get all previous messages
		// messagesAdapter.addToStart(MessagesFixtures.getTextMessage(), true);
		EventBus.getDefault().register(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		EventBus.getDefault().unregister(this);
	}

	@Override
	protected void onDestroy() {
		presenter.onDetach();
		super.onDestroy();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onSubmit(CharSequence input) {
		presenter.sendMessage(createMessage(input.toString()));
		return true;
	}

	@Override
	public void onNewMessage(DialogMessage message){
			messagesAdapter.addToStart(message,true);
	}

	@Override
	public void loadMessagesToEnd(List<Message> messages) {
		List<DialogMessage> msgList = new ArrayList<>();
		for (Message m : messages) {

			User senderUser = userMapOfChannel.get(m.senderUserId);

			if(senderUser == null){
				senderUser = new User();
				senderUser.userId = UUID.randomUUID().toString();
				senderUser.name = "Sucre";
				senderUser.picture = "https://image.freepik.com/free-vector/doctor-character-background_1270-84.jpg";
			}

			DialogMessage msg = new DialogMessage(m.id,
					new DialogUser(senderUser.userId,
							senderUser.name,
							senderUser.picture, true),
					m.messageText,
					m.createdAt);

			msgList.add(msg);
		}
		messagesAdapter.addToEnd(msgList, true);
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessageEvent(MessageEvent event) {
		if (event.message.toChannelId != null && event.message.toChannelId.equals(thisChannel.id)) {

			User msgSentBy = userMapOfChannel.get(event.message.senderUserId);

			if(msgSentBy == null)return;

			DialogMessage msg = new DialogMessage(event.message.id,
					new DialogUser(msgSentBy.userId,
							msgSentBy.name,
							msgSentBy.picture, true),
					event.message.messageText);

			onNewMessage(msg);

		}

	}

	@Override
	public void onAddAttachments() {
		messagesAdapter.addToStart(MessagesFixtures.getImageMessage(), true);
	}

	@Override
	public void onBackPressed() {
		if (selectionCount == 0) {
			super.onBackPressed();
		} else {
			messagesAdapter.unselectAllItems();
		}
	}

	@Override
	public void onLoadMore(int page, int totalItemsCount) {
		if (totalItemsCount < TOTAL_MESSAGES_COUNT) {
//			presenter.getMessages(thisChannel.id);
		}
	}

	@Override
	public void onSelectionChanged(int count) {
		this.selectionCount = count;
		menu.findItem(R.id.action_delete).setVisible(count > 0);
		menu.findItem(R.id.action_copy).setVisible(count > 0);
	}

	private MessagesListAdapter.Formatter<DialogMessage> getMessageStringFormatter() {
		return message -> {
            String createdAt = DateUtils.formatDateForMessaging(message.getCreatedAt());

            String text = message.getText();
            if (text == null) text = "[attachment]";

            return String.format(Locale.getDefault(), "%s: %s (%s)",
                    message.getUser().getName(), text, createdAt);
        };
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.menu = menu;
		getMenuInflater().inflate(R.menu.chat_actions_menu, menu);
		onSelectionChanged(0);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_delete:
				messagesAdapter.deleteSelectedMessages();
				break;
			case R.id.action_copy:
				messagesAdapter.copySelectedMessagesText(this, getMessageStringFormatter(), true);
				Toast.makeText(this, "Copied!", Toast.LENGTH_SHORT).show();
				break;
		}
		return true;
	}

	private void initAdapter() {
		messagesAdapter = new MessagesListAdapter<>(senderId, imageLoader);
		messagesAdapter.enableSelectionMode(this);
		messagesAdapter.setLoadMoreListener(this);
		messagesAdapter.setDateHeadersFormatter(this);
		this.messagesList.setAdapter(messagesAdapter);
	}

	@Override
	public String format(Date date) {
		if (DateFormatter.isToday(date)) {
			return getString(R.string.date_header_today);
		} else if (DateFormatter.isYesterday(date)) {
			return getString(R.string.date_header_yesterday);
		} else {
			return DateFormatter.format(date, DateFormatter.Template.STRING_DAY_MONTH_YEAR);
		}
	}

	@OnClick(R.id.iv_addUser)
	public void onAddUserClicked(){
		new LovelyTextInputDialog(this, R.style.EditTextTintTheme)
				.setTopColorRes(R.color.md_deep_orange_800)
				.setIcon(R.drawable.ic_settings)
				.setTitle(R.string.text_input_title_messagescreen)
				.setMessage(R.string.text_input_message_messagescreen)
				//.setInputFilter(R.string.text_input_error_message, text -> text.matches("\\w+"))
				.setConfirmButton(android.R.string.ok, email -> presenter.addUserToChannel(dialogId, email))
				.show();
	}

	@OnClick(R.id.iv_showOwner)
	public void onShowOwnerClicked(){
		Intent intent = new Intent(this, HealthDataListActivity.class);
		intent.putExtra(AppConstants.SENDER_ID, thisChannel.owner);
		startActivity(intent);
	}

	@OnClick(R.id.iv_blood)
	public void onShowOwnerBloodSugar(){
        Intent intent = new Intent(this, SugarLevelActivity.class);
        intent.putExtra(AppConstants.SENDER_ID, thisChannel.owner);
        startActivity(intent);
	}

	@Override
	public void addUser(User user) {
		userMapOfChannel.put(user.userId, user);
	}

	@Override
	public void updateChannelInfo(Channel channel) {
		thisChannel = channel;
		toolbarTitle.setText(channel.channelName);
		presenter.getMessages(thisChannel.id);
	}

	private Message createMessage(String text){
		Message message = new Message();
		message.messageText = text;
		if(thisChannel != null){
			message.toChannelId = thisChannel.id;
		}
		message.senderUserId = senderId;
		message.createdAt = Calendar.getInstance().getTime();
		return message;
	}

	@Override
	public void onMessageViewClick(View view, DialogMessage message) {
		Toast.makeText(this, "assadada", Toast.LENGTH_SHORT).show();
	}
}
