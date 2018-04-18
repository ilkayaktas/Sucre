package edu.metu.sucre.views.activities.messages;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
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
import edu.metu.sucre.model.api.User;
import edu.metu.sucre.model.app.Message;
import edu.metu.sucre.model.app.MessagesFixtures;
import edu.metu.sucre.utils.AppConstants;
import edu.metu.sucre.views.activities.base.BaseActivity;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HealthChannelMessageActivity extends BaseActivity
		implements HealthChannelMessageMvpView,
					MessageInput.InputListener,
					MessageInput.AttachmentsListener,
					MessagesListAdapter.SelectionListener,
					MessagesListAdapter.OnLoadMoreListener,
					DateFormatter.Formatter  {
	
	@Inject
	HealthChannelMessageMvpPresenter<HealthChannelMessageMvpView> presenter;

	@BindView(R.id.toolbar_title) TextView toolbarTitle;
	@BindView(R.id.messagesList) MessagesList messagesList;
	@BindView(R.id.input) MessageInput input;

	private static final int TOTAL_MESSAGES_COUNT = 100;

	protected final String senderId = "0";
	protected ImageLoader imageLoader;
	protected MessagesListAdapter<Message> messagesAdapter;

	private Menu menu;
	private int selectionCount;
	private Date lastLoadedDate;
	private String notificationKey = null;
	private String dialogName = null;
	private String dialogId = null;
	private List<User> userList = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getArguments();

		getActivityComponent().inject(this);
		
		setUnBinder(ButterKnife.bind(this));
		
		// Attach presenter
		presenter.onAttach(HealthChannelMessageActivity.this);

		initUI();
	}

	private void getArguments() {
		Bundle b = getIntent().getExtras();
		if(b != null){
			notificationKey = b.getString(AppConstants.NOTIFICATION_KEY);
			dialogName = b.getString(AppConstants.DIALOG_NAME);
			dialogId = b.getString(AppConstants.DIALOG_ID);
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
	}

	@Override
	protected void onStart() {
		super.onStart();
		messagesAdapter.addToStart(MessagesFixtures.getTextMessage(), true);
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
		messagesAdapter.addToStart(
				MessagesFixtures.getTextMessage(input.toString()), true);
		return true;
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
			loadMessages();
		}
	}

	@Override
	public void onSelectionChanged(int count) {
		this.selectionCount = count;
		menu.findItem(R.id.action_delete).setVisible(count > 0);
		menu.findItem(R.id.action_copy).setVisible(count > 0);
	}

	protected void loadMessages() {
		//imitation of internet connection
		new Handler().postDelayed(() -> {
            ArrayList<Message> messages = MessagesFixtures.getMessages(lastLoadedDate);
            lastLoadedDate = messages.get(messages.size() - 1).getCreatedAt();
            messagesAdapter.addToEnd(messages, false);
        }, 1000);
	}

	private MessagesListAdapter.Formatter<Message> getMessageStringFormatter() {
		return message -> {
            String createdAt = new SimpleDateFormat("MMM d, EEE 'at' h:mm a", Locale.getDefault())
                    .format(message.getCreatedAt());

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

	@Override
	public void addUser(User user) {
		userList.add(user);
	}
}
