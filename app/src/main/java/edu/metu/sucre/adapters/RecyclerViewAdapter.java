package edu.metu.sucre.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
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
    private BaseActivity activity;
    private List<CardItem> list;

    public RecyclerViewAdapter(BaseActivity activity, List<CardItem> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.card_item, parent, false);

        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        CardItem model = list.get(position);
        List<BloodSugar> bloodSugarListOfDay = model.bloodSugarListOfDay;

        ((ViewHolder)viewHolder).sugarLevel.setText(String.valueOf(bloodSugarListOfDay.get(0).value));
        ((ViewHolder)viewHolder).prePost.setText(bloodSugarListOfDay.get(0).sugarMeasurementType.toString());
        ((ViewHolder)viewHolder).date.setText(DateUtils.getFormattedDate(bloodSugarListOfDay.get(0).date));

        List<ListItem> sugarValues = new ArrayList<>();
        for ( BloodSugar bloodSugar: bloodSugarListOfDay) {
            sugarValues.add(new ListItem(bloodSugar.value, DateUtils.getFormattedDate(bloodSugar.date),
                    bloodSugar.sugarMeasurementType.toString()));
        }
        ListAdapter adapter = new ListAdapter(activity, sugarValues);
        ((ViewHolder)viewHolder).detailsOfDay.setAdapter(adapter);


        ((RecyclerViewAdapter.ViewHolder)viewHolder).sugarLevel.setTypeface(activity.typeface);
        ((RecyclerViewAdapter.ViewHolder)viewHolder).prePost.setTypeface(activity.typeface);
        ((RecyclerViewAdapter.ViewHolder)viewHolder).date.setTypeface(activity.typeface);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.sugarLevel) TextView sugarLevel;
        @BindView(R.id.prePost)TextView prePost;
        @BindView(R.id.date)TextView date;
        @BindView(R.id.details_of_day)ListView detailsOfDay;

        ViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
