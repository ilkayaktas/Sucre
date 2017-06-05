package edu.metu.sucre.views.activities.sugarlevel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.metu.sucre.R;
import edu.metu.sucre.adapters.ViewPagerAdapter;
import edu.metu.sucre.model.app.BloodSugar;
import edu.metu.sucre.views.activities.base.BaseActivity;
import edu.metu.sucre.views.activities.base.BaseFragment;
import edu.metu.sucre.views.fragments.listfragment.ListFragment;
import edu.metu.sucre.views.fragments.listfragment.OnBloodSugarSelectedListener;
import edu.metu.sucre.views.fragments.statisticsfragment.OnShareButtonClickedListener;
import edu.metu.sucre.views.fragments.statisticsfragment.StatisticsFragment;
import edu.metu.sucre.views.fragments.statisticsfragment.StatisticsMvpView;
import edu.metu.sucre.views.widgets.viewpagers.NonScrollableViewPager;

/**
 * Created by ilkay on 27/04/2017.
 */

public class SugarLevelActivity extends BaseActivity implements SugarLevelMvpView, OnBloodSugarSelectedListener, OnShareButtonClickedListener{
	
	@Inject
	SugarLevelMvpPresenter<SugarLevelMvpView> mPresenter;
	
	@BindView(R.id.view_pager_for_fragment) NonScrollableViewPager view_pager_for_fragment;
	private ViewPagerAdapter mPagerAdapter;

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
	}
	
	@Override
	protected void onStop() {
		super.onStop();
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
		View v = inflator.inflate(R.layout.layout_sugarlevel_activity_actionbar, null);
		((TextView)v.findViewById(R.id.actionbar_title)).setTypeface(textFont);
		
		mActionBar.setCustomView(v);
		mActionBar.setDisplayShowCustomEnabled(true);
	}
	
	@Override
	public void onBloodSugarSelected(List<BloodSugar> bloodSugarList) {
		StatisticsMvpView fragment = (StatisticsMvpView) mPagerAdapter.getItem(1);
		fragment.updateData(bloodSugarList);
		view_pager_for_fragment.setCurrentItem(1);
	}
	
	@Override
	public void onShareButonClicked(String str) {
		Intent share = new Intent(Intent.ACTION_SEND);
		share.setType("text/plain");
		share.putExtra(Intent.EXTRA_TEXT, str);
		startActivity(Intent.createChooser(share, getString(R.string.share_using)));
	}
}
