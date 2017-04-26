package edu.metu.sucre.views.activities.sugarlevel;

import java.util.List;

import edu.metu.sucre.model.app.ListItem;
import edu.metu.sucre.views.activities.base.MvpView;

/**
 * Created by ilkay on 27/04/2017.
 */

public interface SugarLevelMvpView extends MvpView {
	void updateListView(List<ListItem> sugarValues);
}
