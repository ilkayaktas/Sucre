package edu.metu.sucre.views.fragments.statisticsfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.db.chart.view.LineChartView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.metu.sucre.R;
import edu.metu.sucre.model.app.BloodSugar;
import edu.metu.sucre.views.activities.base.BaseFragment;
import edu.metu.sucre.views.widgets.williamchart.LineChart;

/**
 * Created by iaktas on 14.03.2017.
 */

public class StatisticsFragment extends BaseFragment implements StatisticsMvpView{

    @Inject
    StatisticsMvpPresenter<StatisticsMvpView> mPresenter;

    @BindView(R.id.linear_chart) LineChartView lineChartView;
    private LineChart lineChart;
    
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
    
        setUnBinder(ButterKnife.bind(this, layout));
        
        mPresenter.onAttach(this);
    
        String[]str={"a","a","a","a","a","a","a","a","a","a"};
        float[]f={1.2f,1.2f,1.2f,1.2f,1.2f,1.2f,1.2f,1.2f,1.2f,1.2f};
        lineChart = new LineChart(getContext(),lineChartView, str, f);
        lineChart.init();
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
    
    
    @Override
    public void updateData(List<BloodSugar> bloodSugarList) {
        float[]f={1.8f,1.2f,1.2f,1.8f,1.2f,1.2f,1.8f,1.2f,1.2f,1.2f};
        lineChart.update(f);
    }
}
