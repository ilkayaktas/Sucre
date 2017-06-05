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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.metu.sucre.R;
import edu.metu.sucre.adapters.RecyclerViewAdapter;
import edu.metu.sucre.events.ListItemClickedEvent;
import edu.metu.sucre.model.app.BloodSugar;
import edu.metu.sucre.model.app.CardItem;
import edu.metu.sucre.utils.DateUtils;
import edu.metu.sucre.views.activities.base.BaseFragment;

import static edu.metu.sucre.utils.AppConstants.REPORT_RECORD_HISTORY_COUNT;

/**
 * Created by iaktas on 14.03.2017.
 */

public class ListFragment extends BaseFragment implements ListMvpView{

    @Inject
    ListMvpPresenter<ListMvpView> mPresenter;

    @BindView(R.id.recycler_view) RecyclerView recyclerView;

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
        this.bloodSugarList = bloodSugarList;
    
        LinkedHashMap<String, List<BloodSugar>> bloodSugarMap = new LinkedHashMap<>();
        for (Iterator<BloodSugar> itr = bloodSugarList.iterator(); itr.hasNext();) {
            String key = DateUtils.getFormattedDate(itr.next().date);

            if (bloodSugarMap.containsKey(key)){
                itr.remove();
            } else{
                List<BloodSugar> list = getListAsGroup(key, bloodSugarList);
                bloodSugarMap.put(key, list);
            }
        }


        List<CardItem> listOfBloodSugarValuesAsGrouped = new ArrayList<>();

        for (Iterator<List<BloodSugar>> itr = bloodSugarMap.values().iterator(); itr.hasNext();){
            List<BloodSugar> dailyList = itr.next();
            listOfBloodSugarValuesAsGrouped.add(new CardItem(dailyList));
        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getBaseActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getBaseActivity(), listOfBloodSugarValuesAsGrouped);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private List<BloodSugar> getListAsGroup(String key, List<BloodSugar> list){
        List<BloodSugar> newList = new ArrayList<>();

        for (BloodSugar bloodSugar : list) {
            if(key.equals(DateUtils.getFormattedDate(bloodSugar.date))){
                newList.add(bloodSugar);
            }
        }
        return newList;
    }

}
