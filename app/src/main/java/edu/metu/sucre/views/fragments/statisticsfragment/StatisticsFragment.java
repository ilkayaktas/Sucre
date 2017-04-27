package edu.metu.sucre.views.fragments.statisticsfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import edu.metu.sucre.R;
import edu.metu.sucre.views.activities.base.BaseFragment;
import edu.metu.sucre.views.widgets.williamchart.LineCardOne;
import edu.metu.sucre.views.widgets.williamchart.LineCardTwo;

/**
 * Created by iaktas on 14.03.2017.
 */

public class StatisticsFragment extends BaseFragment implements StatisticsMvpView{

    @Inject
    StatisticsMvpPresenter<StatisticsMvpView> mPresenter;

    public static StatisticsFragment newInstance(){
        Bundle args = new Bundle();
        StatisticsFragment fragment = new StatisticsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_statistics, container, false);

        getActivityComponent().inject(this);

        mPresenter.onAttach(this);

        (new LineCardOne((CardView) layout.findViewById(R.id.card1), getContext())).init();
        (new LineCardTwo((CardView) layout.findViewById(R.id.card9))).init();
        return layout;
    }

    @Override
    protected void setUp(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("fragment'de tıklandı");
            }
        });
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }


}
