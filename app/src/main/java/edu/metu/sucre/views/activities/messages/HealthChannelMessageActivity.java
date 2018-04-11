package edu.metu.sucre.views.activities.messages;

import android.os.Bundle;
import android.view.KeyEvent;
import butterknife.ButterKnife;
import edu.metu.sucre.R;
import edu.metu.sucre.views.activities.base.BaseActivity;

import javax.inject.Inject;

public class HealthChannelMessageActivity extends BaseActivity implements HealthChannelMessageMvpView {
	
	@Inject
	HealthChannelMessageMvpPresenter<HealthChannelMessageMvpView> mPresenter;

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
		return R.layout.activity_default_dialogs;
	}

	@Override
	protected void initUI() {
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

}
