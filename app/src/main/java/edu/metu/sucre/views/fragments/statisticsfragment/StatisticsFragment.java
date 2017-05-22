package edu.metu.sucre.views.fragments.statisticsfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.db.chart.view.LineChartView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.metu.sucre.R;
import edu.metu.sucre.model.app.BloodSugar;
import edu.metu.sucre.utils.AppConstants;
import edu.metu.sucre.views.activities.base.BaseActivity;
import edu.metu.sucre.views.activities.base.BaseFragment;
import edu.metu.sucre.views.widgets.williamchart.LineChart;

/**
 * Created by iaktas on 14.03.2017.
 */

public class StatisticsFragment extends BaseFragment implements StatisticsMvpView{

    @Inject
    StatisticsMvpPresenter<StatisticsMvpView> mPresenter;

    @BindView(R.id.linear_chart) LineChartView lineChartView;
    @BindView(R.id.share_with_label) TextView maxValue;


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

        setFonts();

        String[] labels = generateLabels();
        float[] values = generateInitialValues();

        lineChart = new LineChart(getContext(),lineChartView, labels, values);
        lineChart.init();
        return layout;
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }
    
    @Override
    public void updateData(List<BloodSugar> bloodSugarList) {
        float[] values = getValuesFromRecords(bloodSugarList);

        lineChart.update(values);
    }

    private void setFonts(){
        maxValue.setTypeface(((BaseActivity)getActivity()).typeface);
    }

    private String [] generateLabels(){
        String [] labels = new String[AppConstants.REPORT_RECORD_HISTORY_COUNT];

        for (int i = 0; i < AppConstants.REPORT_RECORD_HISTORY_COUNT; i++) {
            labels[i] = ""+(i+1);
        }

        return labels;
    }

    private float [] generateInitialValues(){
        float [] values = new float[AppConstants.REPORT_RECORD_HISTORY_COUNT];
        for (int i = 0; i < AppConstants.REPORT_RECORD_HISTORY_COUNT; i++) {
            values[i] = 0;
        }

        return values;
    }

    private float [] getValuesFromRecords(List<BloodSugar> bloodSugarList){
        float [] values = new float[AppConstants.REPORT_RECORD_HISTORY_COUNT];

        for (int i = 0, j = AppConstants.REPORT_RECORD_HISTORY_COUNT-1; i < AppConstants.REPORT_RECORD_HISTORY_COUNT; j--, i++) {
            if(i < bloodSugarList.size()){
                values[j] = bloodSugarList.get(i).value;
            } else{
                values[j] = 0;
            }

        }

        return values;
    }
}
