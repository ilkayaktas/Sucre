package edu.metu.sucre.views.activities.sugarlevel;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.metu.sucre.R;
import edu.metu.sucre.adapters.ViewPagerAdapter;
import edu.metu.sucre.events.ListItemClickedEvent;
import edu.metu.sucre.views.activities.base.BaseActivity;
import edu.metu.sucre.views.activities.base.BaseFragment;
import edu.metu.sucre.views.fragments.listfragment.ListFragment;
import edu.metu.sucre.views.fragments.statisticsfragment.StatisticsFragment;
import edu.metu.sucre.views.widgets.dialogs.rateme.RateMe;
import edu.metu.sucre.views.widgets.viewpagers.NonScrollableViewPager;

/**
 * Created by ilkay on 27/04/2017.
 */

public class SugarLevelActivity extends BaseActivity implements SugarLevelMvpView {
	
	@Inject
	SugarLevelMvpPresenter<SugarLevelMvpView> mPresenter;
	
	@BindView(R.id.view_pager_for_fragment) NonScrollableViewPager view_pager_for_fragment;
	private PagerAdapter mPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sugarlevel);
		
		getActivityComponent().inject(this);
		
		setUnBinder(ButterKnife.bind(this));
		
		// Attach presenter
		mPresenter.onAttach(SugarLevelActivity.this);
		
		addCustomActionBar();

		setViewPager();
	}
	
	private void setViewPager(){

		List<BaseFragment> fragmentList = new ArrayList<>();
		fragmentList.add(ListFragment.newInstance());
		fragmentList.add(StatisticsFragment.newInstance());
		mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
		view_pager_for_fragment.setAdapter(mPagerAdapter);
	}

	@Override
	protected void onStart() {
		super.onStart();
		RateMe.onStart(this);
		RateMe.showRateDialogIfNeeded(this);
		EventBus.getDefault().register(this);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		EventBus.getDefault().unregister(this);
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessageEvent(ListItemClickedEvent event) {
		view_pager_for_fragment.setCurrentItem(1);
	}

	@Override
	protected void onDestroy() {
		mPresenter.onDetach();
		super.onDestroy();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			if(view_pager_for_fragment.getCurrentItem() != 0) {
				view_pager_for_fragment.setCurrentItem(0);
			} else{
				finish();
			}

			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}
	
	private void addCustomActionBar(){
		ActionBar mActionBar = getSupportActionBar();
		mActionBar.setDisplayShowHomeEnabled(false);
		mActionBar.setDisplayShowTitleEnabled(false);
		
		LayoutInflater inflator = LayoutInflater.from(this);
		View v = inflator.inflate(R.layout.layout_actionbar, null);
		((TextView)v.findViewById(R.id.actionbar_title)).setTypeface(textFont);
		
		mActionBar.setCustomView(v);
		mActionBar.setDisplayShowCustomEnabled(true);
	}
	
}
