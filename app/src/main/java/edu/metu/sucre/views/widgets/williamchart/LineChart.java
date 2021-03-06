package edu.metu.sucre.views.widgets.williamchart;

import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;

import com.db.chart.Tools;
import com.db.chart.animation.Animation;
import com.db.chart.model.LineSet;
import com.db.chart.renderer.AxisRenderer;
import com.db.chart.tooltip.Tooltip;
import com.db.chart.view.LineChartView;

import edu.metu.sucre.R;


public class LineChart extends CardController {


	private final LineChartView mChart;


	private final Context mContext;


	private String[] mLabels;

	private float[] mValues;

	private Tooltip mTip;

	private Runnable mBaseAction;


	public LineChart(Context context, LineChartView mChart, String[] mLabels, float[] mValues) {

		mContext = context;
		
		this.mChart = mChart;
		this.mLabels = mLabels;
		this.mValues = mValues;
	}


	@Override
	public void show(Runnable action) {

		super.show(action);

		// Tooltip
		mTip = new Tooltip(mContext, R.layout.linechart_three_tooltip, R.id.value);

		((TextView) mTip.findViewById(R.id.value)).setTypeface(
				  Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Thin.ttf"));

		mTip.setVerticalAlignment(Tooltip.Alignment.BOTTOM_TOP);
		mTip.setDimensions((int) Tools.fromDpToPx(58), (int) Tools.fromDpToPx(25));


			mTip.setEnterAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 1),
					  PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f),
					  PropertyValuesHolder.ofFloat(View.SCALE_X, 1f)).setDuration(200);

			mTip.setExitAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 0),
					  PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f),
					  PropertyValuesHolder.ofFloat(View.SCALE_X, 0f)).setDuration(200);

			mTip.setPivotX(Tools.fromDpToPx(65) / 2);
			mTip.setPivotY(Tools.fromDpToPx(25));

		mChart.setTooltips(mTip);

		// FCMData
		LineSet  dataset = new LineSet(mLabels, mValues);
		dataset.setColor(Color.parseColor("#b3b5bb"))
				  .setFill(Color.parseColor("#2d374c"))
				  .setDotsColor(Color.parseColor("#ffc755"))
				  .setThickness(4);
		mChart.addData(dataset);

		// Chart
		mChart.setBorderSpacing(Tools.fromDpToPx(15))
				  .setAxisBorderValues(0, 500)
				  .setYLabels(AxisRenderer.LabelPosition.OUTSIDE)
				   .setXLabels(AxisRenderer.LabelPosition.OUTSIDE)
				  .setLabelsColor(Color.parseColor("#6a84c3"))
				  .setXAxis(false)
				  .setYAxis(false);

		mBaseAction = action;
		Runnable chartAction = new Runnable() {
			@Override
			public void run() {

				mBaseAction.run();
				mTip.prepare(mChart.getEntriesArea(0).get(3), mValues[3]);
				mChart.showTooltip(mTip, true);
			}
		};

		Animation anim = new Animation().setEasing(new BounceInterpolator()).setEndAction(chartAction);

		mChart.show(anim);
	}

	public void update(float[] mValues) {
		
		this.mValues = mValues;
		
		mChart.dismissAllTooltips();
		mChart.updateValues(0, mValues);
		
		mChart.getChartAnimation().setEndAction(mBaseAction);
		mChart.notifyDataUpdate();
	}


	@Override
	public void dismiss(Runnable action) {

		super.dismiss(action);

		mChart.dismissAllTooltips();
		mChart.dismiss(new Animation().setEasing(new BounceInterpolator()).setEndAction(action));
	}

}
