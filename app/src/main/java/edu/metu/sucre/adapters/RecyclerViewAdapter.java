package edu.metu.sucre.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.metu.sucre.R;
import edu.metu.sucre.model.app.ListItem;
import edu.metu.sucre.views.activities.base.BaseActivity;

/**
 * Created by iaktas on 26.05.2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter {
    private BaseActivity activity;
    private List<ListItem> list;

    public RecyclerViewAdapter(BaseActivity activity, List<ListItem> list) {
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
        ListItem model = list.get(position);

        ((RecyclerViewAdapter.ViewHolder)viewHolder).sugarLevel.setText(String.valueOf(model.sugarLevel));
        ((RecyclerViewAdapter.ViewHolder)viewHolder).prePost.setText(model.prePost);
        ((RecyclerViewAdapter.ViewHolder)viewHolder).date.setText(model.date);

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

        ViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
