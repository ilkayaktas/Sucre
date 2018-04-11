package edu.metu.sucre.views.activities.messages;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;
import edu.metu.sucre.R;
import edu.metu.sucre.model.app.Message;
import edu.metu.sucre.model.app.MessagesFixtures;
import edu.metu.sucre.views.activities.base.BaseActivity;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class HealthChannelMessageActivity extends BaseActivity
		implements HealthChannelMessageMvpView,
					MessageInput.InputListener,
					MessageInput.AttachmentsListener,
					MessagesListAdapter.SelectionListener,
					MessagesListAdapter.OnLoadMoreListener {
	
	@Inject
	HealthChannelMessageMvpPresenter<HealthChannelMessageMvpView> mPresenter;

	@BindView(R.id.messagesList) MessagesList messagesList;
	@BindView(R.id.input) MessageInput input;

	private static final int TOTAL_MESSAGES_COUNT = 100;

	protected final String senderId = "0";
	protected ImageLoader imageLoader;
	protected MessagesListAdapter<Message> messagesAdapter;

	private Menu menu;
	private int selectionCount;
	private Date lastLoadedDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getActivityComponent().inject(this);
		
		setUnBinder(ButterKnife.bind(this));
		
		// Attach presenter
		mPresenter.onAttach(HealthChannelMessageActivity.this);

		initUI();
	}

	@Override
	protected int getActivityLayout() {
		return R.layout.activity_default_messages;
	}

	@Override
	protected void initUI() {

		input.setInputListener(this);

		imageLoader = (imageView, url) -> Picasso.with(HealthChannelMessageActivity.this).load(url).into(imageView);

		initAdapter();
	}

	@Override
	protected void onStart() {
		super.onStart();
		messagesAdapter.addToStart(MessagesFixtures.getTextMessage(), true);
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

	@Override
	public boolean onSubmit(CharSequence input) {
		messagesAdapter.addToStart(
				MessagesFixtures.getTextMessage(input.toString()), true);
		return true;
	}

	@Override
	public void onAddAttachments() {
		messagesAdapter.addToStart(
				MessagesFixtures.getImageMessage(), true);
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
		new Handler().postDelayed(new Runnable() { //imitation of internet connection
			@Override
			public void run() {
				ArrayList<Message> messages = MessagesFixtures.getMessages(lastLoadedDate);
				lastLoadedDate = messages.get(messages.size() - 1).getCreatedAt();
				messagesAdapter.addToEnd(messages, false);
			}
		}, 1000);
	}

	private MessagesListAdapter.Formatter<Message> getMessageStringFormatter() {
		return new MessagesListAdapter.Formatter<Message>() {
			@Override
			public String format(Message message) {
				String createdAt = new SimpleDateFormat("MMM d, EEE 'at' h:mm a", Locale.getDefault())
						.format(message.getCreatedAt());

				String text = message.getText();
				if (text == null) text = "[attachment]";

				return String.format(Locale.getDefault(), "%s: %s (%s)",
						message.getUser().getName(), text, createdAt);
			}
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
		messagesAdapter.registerViewClickListener(R.id.messageUserAvatar,
				new MessagesListAdapter.OnMessageViewClickListener<Message>() {
					@Override
					public void onMessageViewClick(View view, Message message) {
						Toast.makeText(HealthChannelMessageActivity.this, "Toast", Toast.LENGTH_SHORT).show();
					}
				});
		this.messagesList.setAdapter(messagesAdapter);
	}

}
