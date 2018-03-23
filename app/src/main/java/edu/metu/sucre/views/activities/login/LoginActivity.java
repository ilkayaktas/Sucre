package edu.metu.sucre.views.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import edu.metu.sucre.R;
import edu.metu.sucre.utils.AppConstants;
import edu.metu.sucre.views.activities.base.BaseActivity;

import javax.inject.Inject;
import java.util.Arrays;

/**
 * Created by ilkay on 02/08/2017.
 */

public class LoginActivity extends BaseActivity implements LoginMvpView {
	
	@Inject
	LoginMvpPresenter<LoginMvpView> mPresenter;

	private CallbackManager mCallbackManager;

	@BindView(R.id.login_button) LoginButton loginButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView( R.layout.activity_login );

		getActivityComponent().inject(this);
		
		setUnBinder(ButterKnife.bind(this));

		setFacebookLogin();

		// Attach presenter
		mPresenter.onAttach(LoginActivity.this);

		initUI();
	}

	private void setFacebookLogin() {
		mCallbackManager = CallbackManager.Factory.create();

		// Set the initial permissions to request from the user while logging in
		loginButton.setReadPermissions( Arrays.asList( AppConstants.EMAIL, AppConstants.USER_POSTS));

		// Register a callback to respond to the user
		loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
			@Override
			public void onSuccess(LoginResult loginResult) {
				setResult(RESULT_OK);
				finish();
			}

			@Override
			public void onCancel() {
				setResult(RESULT_CANCELED);
				finish();
			}

			@Override
			public void onError(FacebookException e) {
				// Handle exception
			}
		});
	}

	protected void initUI() {
		setFont();
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

	private void setFont(){
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		mCallbackManager.onActivityResult(requestCode, resultCode, data);
	}
}
