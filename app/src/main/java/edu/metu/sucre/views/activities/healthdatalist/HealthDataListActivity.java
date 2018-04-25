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
import edu.metu.sucre.utils.AppConstants;
import edu.metu.sucre.views.activities.base.BaseActivity;
import edu.metu.sucre.views.adapters.HealthDataListAdapter;

import javax.inject.Inject;
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
	private String senderId = "0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getArguments();

		getActivityComponent().inject(this);

		setUnBinder(ButterKnife.bind(this));

		// Attach presenter
		mPresenter.onAttach(HealthDataListActivity.this);

		initUI();
	}

	private void getArguments() {
		Bundle b = getIntent().getExtras();
		if(b != null){
			senderId = b.getString(AppConstants.SENDER_ID);
		}
	}

	@Override
	protected void initUI() {
		setFont();

		mPresenter.getHealthData(senderId);
		swipeRefreshLayout.setOnRefreshListener(() -> {
			mPresenter.getHealthData(senderId);
		});
	}

	private void initRecylerView(List<HealthData> healthDataList){

		recyclerViewAdapter = new HealthDataListAdapter(this, healthDataList, recyclerView);

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
	public void showHealthData(List<HealthData> healthDataList) {
		initRecylerView(healthDataList);
		setLoading(false);
	}

	@Override
	public void setLoading(boolean isLoading) {
		swipeRefreshLayout.setRefreshing(isLoading);
	}
}
