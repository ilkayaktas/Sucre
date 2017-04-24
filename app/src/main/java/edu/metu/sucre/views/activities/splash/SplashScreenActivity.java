package edu.metu.sucre.views.activities.splash;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.metu.sucre.R;
import edu.metu.sucre.views.activities.base.BaseActivity;
import edu.metu.sucre.views.activities.home.MainActivity;

/**
 * Created by ilkay on 11/03/2017.
 */

public class SplashScreenActivity extends BaseActivity implements SplashScreenMvpView{
	
	@Inject
	SplashScreenMvpPresenter<SplashScreenMvpView> mPresenter;

	@BindView(R.id.acilisKavramlarSozlugu) TextView acilisKavramlarSozlugu;
	@BindView(R.id.acilisKavramlarSozluguSlogan) TextView acilisKavramlarSozluguSlogan;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_splash_screen);
		
		getActivityComponent().inject(this);
		
		setUnBinder(ButterKnife.bind(this));
		
		mPresenter.onAttach(SplashScreenActivity.this);
		
		changeUIFonts();
		
		mPresenter.createDatabase();
	}
	
	private void changeUIFonts(){
		acilisKavramlarSozlugu.setTypeface(textFont);
		acilisKavramlarSozluguSlogan.setTypeface(textFont);
	}
	
	@Override
	protected void onDestroy() {
		mPresenter.onDetach();
		super.onDestroy();
	}
	
	@Override
	public void openMainActivity() {
		Intent intent = MainActivity.getStartIntent(SplashScreenActivity.this);
		startActivity(intent);
		finish();
		
	}
}
