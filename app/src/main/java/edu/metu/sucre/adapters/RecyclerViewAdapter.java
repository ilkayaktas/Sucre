package edu.metu.sucre.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.metu.sucre.R;
import edu.metu.sucre.events.ShareWithClickedEvent;
import edu.metu.sucre.model.app.BloodSugar;
import edu.metu.sucre.model.app.CardItem;
import edu.metu.sucre.model.app.ListItem;
import edu.metu.sucre.model.app.SugarMeasurementType;
import edu.metu.sucre.utils.DateUtils;
import edu.metu.sucre.views.activities.base.BaseActivity;

/**
 * Created by iaktas on 26.05.2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter {
    private RecyclerView recyclerView;
    private BaseActivity activity;
    private List<CardItem> list;
    private HashMap<Integer, Boolean> expandedViews;
    
    public RecyclerViewAdapter(BaseActivity activity, List<CardItem> list, RecyclerView recyclerView) {
        this.activity = activity;
        this.list = list;
        this.recyclerView = recyclerView;
        this.expandedViews = new HashMap<>();
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.card_item, parent, false);

        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        CardItem model = list.get(position);
        final List<BloodSugar> bloodSugarListOfDay = model.bloodSugarListOfDay;

        ((ViewHolder)viewHolder).sugarLevel.setText(String.valueOf(bloodSugarListOfDay.get(0).value));
        ((ViewHolder)viewHolder).date.setText(DateUtils.getFormattedDate(bloodSugarListOfDay.get(0).date));

        List<ListItem> sugarValues = new ArrayList<>();
        for ( BloodSugar bloodSugar: bloodSugarListOfDay) {
            sugarValues.add(new ListItem(bloodSugar.uuid, bloodSugar.value, DateUtils.getFormattedDateAsHour(bloodSugar.date),
                    bloodSugar.sugarMeasurementType.equals(SugarMeasurementType.PRE) ? activity.getString(R.string.pre) : activity.getString(R.string.post)));
        }
        ListAdapter adapter = new ListAdapter(activity, sugarValues);
        ((ViewHolder)viewHolder).detailsOfDay.setAdapter(adapter);

        ((ViewHolder)viewHolder).sugarLevel.setTypeface(activity.typeface);
        ((ViewHolder)viewHolder).date.setTypeface(activity.typeface);
        ((ViewHolder)viewHolder).lastMeasure.setTypeface(activity.typeface);
        ((ViewHolder)viewHolder).shareWithDoctor.setTypeface(activity.typeface);
	
	    ((ViewHolder)viewHolder).cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                if(expandedViews.containsKey(position)){
                    ((ViewHolder)viewHolder).detailsOfDayLayout.setVisibility(View.GONE);
                    expandedViews.remove(position);
                } else{
                    ((ViewHolder)viewHolder).detailsOfDayLayout.setVisibility(View.VISIBLE);
                    expandedViews.put(position, true);
                }
    
            }
        });
    
        ((ViewHolder)viewHolder).shareWith.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = generateShareString(bloodSugarListOfDay);
                EventBus.getDefault().post(new ShareWithClickedEvent(message));
            }
        });
    }
    
    /**
     * Generate a string from blood sugar list. this string is shared with other applications.
     * @return
     */
    private String generateShareString(List<BloodSugar> bloodSugarListOfDay){
        String str = activity.getString(R.string.my_blood_measurements) + "\n";
        
        for (BloodSugar bloodSugar : bloodSugarListOfDay) {
            str += DateUtils.getFormattedDate(bloodSugar.date)+
                    "  "+
                    DateUtils.getFormattedDateAsHour(bloodSugar.date)+
                    "  "+
                    (bloodSugar.sugarMeasurementType.equals(SugarMeasurementType.PRE) ? activity.getString(R.string.pre) : activity.getString(R.string.post))+
                    "  "+
                    bloodSugar.value+"\n";
        }
        return str;
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.card_view) CardView cardView;
        @BindView(R.id.sugarLevel) TextView sugarLevel;
        @BindView(R.id.date)TextView date;
        @BindView(R.id.details_of_day)ListView detailsOfDay;
        @BindView(R.id.last_measure)TextView lastMeasure;
        @BindView(R.id.share_with_doctor)TextView shareWithDoctor;
        @BindView(R.id.share_with)ImageButton shareWith;
        @BindView(R.id.details_of_day_layout)LinearLayout detailsOfDayLayout;
        
        

        ViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
