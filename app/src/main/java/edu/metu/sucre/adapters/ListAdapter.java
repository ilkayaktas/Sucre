package edu.metu.sucre.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.metu.sucre.R;
import edu.metu.sucre.events.ListItemClickedEvent;
import edu.metu.sucre.model.app.ListItem;
import edu.metu.sucre.views.activities.base.BaseActivity;


/**
 * Created by iaktas on 14.02.2016.
 */
public class ListAdapter extends BaseAdapter {
    private BaseActivity activity;
    private List<ListItem> list;

    public ListAdapter(BaseActivity activity, List<ListItem> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        
        if(convertView==null) {
            LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ListItem model = list.get(position);

        viewHolder.sugarLevel.setText(String.valueOf(model.sugarLevel));
        viewHolder.prePost.setText(model.prePost);
        viewHolder.date.setText(model.date);

        viewHolder.sugarLevel.setTypeface(activity.typeface);
        viewHolder.prePost.setTypeface(activity.typeface);
        viewHolder.date.setTypeface(activity.typeface);

        convertView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new ListItemClickedEvent());
            }
        });
        return convertView;
    }

    public class ViewHolder{
        @BindView(R.id.sugarLevel) TextView sugarLevel;
        @BindView(R.id.prePost)TextView prePost;
        @BindView(R.id.date)TextView date;

        ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}
