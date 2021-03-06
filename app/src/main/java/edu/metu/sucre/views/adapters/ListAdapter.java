package edu.metu.sucre.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.metu.sucre.R;
import edu.metu.sucre.events.ListItemDeletedEvent;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        
        if(convertView==null) {
            LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final ListItem model = list.get(position);

        viewHolder.sugarLevel.setText(String.valueOf(model.sugarLevel));
        viewHolder.prePost.setText(model.prePost);
        viewHolder.date.setText(model.date);

        viewHolder.sugarLevel.setTypeface(activity.fontGothic );
        viewHolder.prePost.setTypeface(activity.fontGothic );
        viewHolder.date.setTypeface(activity.fontGothic );
    
        viewHolder.delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            new LovelyStandardDialog(activity)
                    .setTopColorRes(R.color.mobss_color_red)
                    .setButtonsColorRes(R.color.mobss_color_light_gray)
                    .setIcon(R.drawable.ic_question)
                    .setMessage(R.string.kayit_silme_islemi)
                    .setPositiveButton(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            EventBus.getDefault().post(new ListItemDeletedEvent(model.uuid));

                            list.remove(position);

                            notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();

            }
        });
        return convertView;
    }

    public class ViewHolder{
        @BindView(R.id.sugarLevel) TextView sugarLevel;
        @BindView(R.id.prePost)TextView prePost;
        @BindView(R.id.date)TextView date;
        @BindView(R.id.delete_record)ImageButton delete;
        

        ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}
