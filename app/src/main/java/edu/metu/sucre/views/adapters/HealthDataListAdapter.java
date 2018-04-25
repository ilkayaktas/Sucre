package edu.metu.sucre.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import edu.metu.sucre.R;
import edu.metu.sucre.model.api.HealthData;
import edu.metu.sucre.views.activities.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iaktas on 26.05.2017.
 */
public class HealthDataListAdapter extends RecyclerView.Adapter<HealthDataListAdapter.ViewHolder> {
    private RecyclerView rv;
    private BaseActivity activity;
    private List<HealthData> cardContentList;

    public HealthDataListAdapter(BaseActivity activity) {
        this.activity = activity;
        this.cardContentList = new ArrayList<>();
    }

    public HealthDataListAdapter(BaseActivity activity, List<HealthData> cardContentList, RecyclerView rv) {
        this.activity = activity;
        this.cardContentList = cardContentList;
        this.rv = rv;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(parent.getContext());
        View convertView = inflater.inflate(R.layout.cardview_recycler, parent, false);

        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        if(cardContentList.size() == 0 ) return;

        final HealthData healthData = cardContentList.get(position);

        viewHolder.date.setText(edu.metu.sucre.utils.DateUtils.getFormattedDate(healthData.date));
        viewHolder.date.setTypeface(activity.fontGothic);

        viewHolder.type.setText(healthData.healthDataType.name());
        viewHolder.type.setTypeface(activity.fontGothic);

        viewHolder.detail.setText(healthData.dataTextDetail);
        viewHolder.detail.setTypeface(activity.fontGothic);

    }

    @Override
    public int getItemCount() {
        return cardContentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_cardview_date) TextView date;
        @BindView(R.id.tv_cardview_type) TextView type;
        @BindView(R.id.tv_cardview_detail) Button detail;

        public ViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void addNewItem(HealthData ticker){
        cardContentList.add(ticker);
        notifyItemInserted(cardContentList.size()-1);
    }

    public void clearAll() {
        int size = cardContentList.size();
        cardContentList.clear();
        notifyItemRangeRemoved(0, size);
    }


}
