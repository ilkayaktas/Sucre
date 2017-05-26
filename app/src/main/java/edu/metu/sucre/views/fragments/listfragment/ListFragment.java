package edu.metu.sucre.views.fragments.listfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.metu.sucre.R;
import edu.metu.sucre.adapters.RecyclerViewAdapter;
import edu.metu.sucre.events.ListItemClickedEvent;
import edu.metu.sucre.model.app.BloodSugar;
import edu.metu.sucre.model.app.ListItem;
import edu.metu.sucre.views.activities.base.BaseFragment;

import static edu.metu.sucre.utils.AppConstants.REPORT_RECORD_HISTORY_COUNT;

/**
 * Created by iaktas on 14.03.2017.
 */

public class ListFragment extends BaseFragment implements ListMvpView{

    @Inject
    ListMvpPresenter<ListMvpView> mPresenter;

    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private List<ListItem> sugarValues;
    private List<BloodSugar> bloodSugarList = null;
    private OnBloodSugarSelectedListener mCallback;
    
    public static ListFragment newInstance(){
        Bundle args = new Bundle();
        ListFragment fragment = new ListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recylerview, container, false);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this, view));

        mPresenter.onAttach(this);

        mPresenter.getAllBloodSugarMeasurements();

        return view;
    }
    
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    
        try {
            mCallback = (OnBloodSugarSelectedListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException((context.toString()) + " must implement OnBloodSugarSelectedListener");
        }
    }
    
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }
    
    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ListItemClickedEvent event) {
        List<BloodSugar> bloodSugarList;
        if((event.position + REPORT_RECORD_HISTORY_COUNT) > this.bloodSugarList.size()) {
            bloodSugarList = this.bloodSugarList.subList(event.position, this.bloodSugarList.size());
        } else{
            bloodSugarList = this.bloodSugarList.subList(event.position, (event.position + REPORT_RECORD_HISTORY_COUNT));
        }
        mCallback.onBloodSugarSelected(bloodSugarList);
    }
    
    @Override
    public void updateBloodSugarList(List<BloodSugar> bloodSugarList) {
        sugarValues = new ArrayList<>();
        this.bloodSugarList = bloodSugarList;
        
        DateFormat df = SimpleDateFormat.getDateTimeInstance();

        for ( BloodSugar bloodSugar: bloodSugarList) {
            sugarValues.add(new ListItem(bloodSugar.value, df.format(bloodSugar.date),
                    bloodSugar.sugarMeasurementType.toString() ));
        }
//        ListAdapter adapter = new ListAdapter(getBaseActivity(), sugarValues);
//        fragment_list.setAdapter(adapter);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getBaseActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getBaseActivity(), sugarValues);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerViewAdapter);
    }
}
