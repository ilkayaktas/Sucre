package edu.metu.sucre.views.fragments.statisticsfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.db.chart.view.LineChartView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    private OnShareButtonClickedListener mCallback;
    private LineChart lineChart;
    private List<BloodSugar> bloodSugarList;
    
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
    public void onAttach(Context context) {
        super.onAttach(context);
        
        try {
            mCallback = (OnShareButtonClickedListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException((context.toString()) + " must implement OnShareButtonClickedListener");
        }
    }
    
    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }
    
    @Override
    public void updateData(List<BloodSugar> bloodSugarList) {
        this.bloodSugarList = bloodSugarList;
        
        // get values from blood sugar list
        float[] values = getValuesFromRecords();

        // update chart
        lineChart.update(values);
    }
    
    @OnClick(R.id.share_with)
    public void onShareButtonClicked(View view){
        String sharedString = generateShareString();
        mCallback.onShareButonClicked(sharedString);
    }
    
    /**
     * Set text view fonts.
     */
    private void setFonts(){
        maxValue.setTypeface(((BaseActivity)getActivity()).typeface);
    }
    
    /**
     * Generate chart labels.
     * @return
     */
    private String [] generateLabels(){
        String [] labels = new String[AppConstants.REPORT_RECORD_HISTORY_COUNT];

        for (int i = 0; i < AppConstants.REPORT_RECORD_HISTORY_COUNT; i++) {
            labels[i] = ""+(i+1);
        }

        return labels;
    }
    
    /**
     * Generate initial values of chart.
     * @return
     */
    private float [] generateInitialValues(){
        float [] values = new float[AppConstants.REPORT_RECORD_HISTORY_COUNT];
        for (int i = 0; i < AppConstants.REPORT_RECORD_HISTORY_COUNT; i++) {
            values[i] = 0;
        }

        return values;
    }
    
    /**
     * Get values from blood sugar list.
     * @return
     */
    private float [] getValuesFromRecords(){
        float [] values = new float[AppConstants.REPORT_RECORD_HISTORY_COUNT];

        for (int i = 0, j = AppConstants.REPORT_RECORD_HISTORY_COUNT-1; i < AppConstants.REPORT_RECORD_HISTORY_COUNT; j--, i++) {
            if(i < this.bloodSugarList.size()){
                values[j] = this.bloodSugarList.get(i).value;
            } else{
                values[j] = 0;
            }

        }

        return values;
    }
    
    /**
     * Generate a string from blood sugar list. this string is shared with other applications.
     * @return
     */
    private String generateShareString(){
        String str = getActivity().getString(R.string.my_blood_measurements) + "\n";
        DateFormat df = SimpleDateFormat.getDateTimeInstance();
        
        for (BloodSugar bloodSugar : bloodSugarList) {
            str += df.format(bloodSugar.date)+"  "+bloodSugar.sugarMeasurementType.toString()+"  "+bloodSugar.value+"\n";
        }
        return str;
    }
}
