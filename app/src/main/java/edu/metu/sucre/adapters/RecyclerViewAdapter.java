package edu.metu.sucre.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.metu.sucre.R;
import edu.metu.sucre.model.app.BloodSugar;
import edu.metu.sucre.model.app.CardItem;
import edu.metu.sucre.model.app.ListItem;
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
        List<BloodSugar> bloodSugarListOfDay = model.bloodSugarListOfDay;

        ((ViewHolder)viewHolder).sugarLevel.setText(String.valueOf(bloodSugarListOfDay.get(0).value));
        ((ViewHolder)viewHolder).date.setText(DateUtils.getFormattedDate(bloodSugarListOfDay.get(0).date));

        List<ListItem> sugarValues = new ArrayList<>();
        for ( BloodSugar bloodSugar: bloodSugarListOfDay) {
            sugarValues.add(new ListItem(bloodSugar.uuid, bloodSugar.value, DateUtils.getFormattedDateAsHour(bloodSugar.date),
                    bloodSugar.sugarMeasurementType.toString()));
        }
        ListAdapter adapter = new ListAdapter(activity, sugarValues);
        ((ViewHolder)viewHolder).detailsOfDay.setAdapter(adapter);

        ((ViewHolder)viewHolder).sugarLevel.setTypeface(activity.typeface);
        ((ViewHolder)viewHolder).date.setTypeface(activity.typeface);
        ((ViewHolder)viewHolder).lastMeasure.setTypeface(activity.typeface);
	
	    ((ViewHolder)viewHolder).cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   EventBus.getDefault().post(new ListItemClickedEvent(position));
                Log.d("____", "onClick: "+position);
                if(expandedViews.containsKey(position)){
                    ((ViewHolder)viewHolder).detailsOfDay.setVisibility(View.GONE);
                    expandedViews.remove(position);
                } else{
                    ((ViewHolder)viewHolder).detailsOfDay.setVisibility(View.VISIBLE);
                    expandedViews.put(position, true);
                }
    
            }
        });
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
        

        ViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
