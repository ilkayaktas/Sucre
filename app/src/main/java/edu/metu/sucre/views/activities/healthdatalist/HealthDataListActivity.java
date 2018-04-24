package edu.metu.sucre.views.activities.healthdatalist;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import edu.metu.sucre.R;
import edu.metu.sucre.model.api.HealthData;
import edu.metu.sucre.views.activities.base.BaseActivity;
import edu.metu.sucre.views.adapters.HealthDataListAdapter;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ilkay on 02/08/2017.
 */

public class HealthDataListActivity extends BaseActivity implements HealthDataListMvpView {

	@Inject
	HealthDataListMvpPresenter<HealthDataListMvpView> mPresenter;

	@BindView(R.id.toolbar_title) TextView toolbarTitle;
	@BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
	@BindView(R.id.rv_recycler) RecyclerView recyclerView;
	private HealthDataListAdapter recyclerViewAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getActivityComponent().inject(this);

		setUnBinder(ButterKnife.bind(this));

		// Attach presenter
		mPresenter.onAttach(HealthDataListActivity.this);

		initUI();
	}

	@Override
	protected void initUI() {
		setFont();

		initRecylerView();

		swipeRefreshLayout.setOnRefreshListener(() -> {
			mPresenter.provideContent();
		});
	}

	private void initRecylerView(){

		recyclerViewAdapter = new HealthDataListAdapter(this, Arrays.asList("One", "Two", "Three"), recyclerView);

		RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(mLayoutManager);
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		recyclerView.setAdapter(recyclerViewAdapter);
	}

	@Override
	protected int getActivityLayout() {
		return R.layout.activity_recycler;
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
		toolbarTitle.setTypeface(fontGothic);
	}

	@Override
	public void updateUI(String text) {
		((HealthDataListAdapter)recyclerView.getAdapter()).addNewItem(text);
		swipeRefreshLayout.setRefreshing(false);
	}

	@Override
	public void showHealthData(List<HealthData> healthDataList) {

	}
}
