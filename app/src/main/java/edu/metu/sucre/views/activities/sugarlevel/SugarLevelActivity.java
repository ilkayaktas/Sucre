package edu.metu.sucre.views.activities.sugarlevel;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.metu.sucre.R;
import edu.metu.sucre.adapters.ListAdapter;
import edu.metu.sucre.model.app.ListItem;
import edu.metu.sucre.views.activities.base.BaseActivity;
import edu.metu.sucre.views.widgets.dialogs.rateme.Config;
import edu.metu.sucre.views.widgets.dialogs.rateme.RateMe;

/**
 * Created by ilkay on 27/04/2017.
 */

public class SugarLevelActivity extends BaseActivity implements SugarLevelMvpView {
	
	@Inject
	SugarLevelMvpPresenter<SugarLevelMvpView> mPresenter;
	
	@BindView(R.id.list_of_sugarlevel_big) ListView listOfSugarLevel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sugarlevel);
		
		getActivityComponent().inject(this);
		
		setUnBinder(ButterKnife.bind(this));
		
		RateMe.init(new Config(5, 10)); // 5 gün ya da 10 defa uygulama başlattıktan sonra
		
		// Attach presenter
		mPresenter.onAttach(SugarLevelActivity.this);
		
		addCustomActionBar();
		
		temporaryInitializeList();
	}
	
	private void temporaryInitializeList() {
		List<ListItem> list = new ArrayList<>();
		for (int i = 0; i < 15; i++) {
			ListItem listItem = new ListItem(120+5*i, i%2==0?"pre":"post", "24.04.2017");
			list.add(listItem);
		}
		
		updateListView(list);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		RateMe.onStart(this);
		RateMe.showRateDialogIfNeeded(this);
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
			
			finish();
			
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
	
	@Override
	public void updateListView(List<ListItem> sugarValues) {
		ListAdapter adapter = new ListAdapter(this, sugarValues);
		listOfSugarLevel.setAdapter(adapter);
	}
}
