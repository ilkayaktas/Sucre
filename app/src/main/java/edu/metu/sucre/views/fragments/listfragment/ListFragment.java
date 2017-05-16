package edu.metu.sucre.views.fragments.listfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.metu.sucre.R;
import edu.metu.sucre.adapters.ListAdapter;
import edu.metu.sucre.model.app.BloodSugar;
import edu.metu.sucre.model.app.ListItem;
import edu.metu.sucre.views.activities.base.BaseFragment;

/**
 * Created by iaktas on 14.03.2017.
 */

public class ListFragment extends BaseFragment implements ListMvpView{

    @Inject
    ListMvpPresenter<ListMvpView> mPresenter;

    @BindView(R.id.list_of_sugarlevel_big) ListView fragment_list;

    public static ListFragment newInstance(){
        Bundle args = new Bundle();
        ListFragment fragment = new ListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listview, container, false);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this, view));

        mPresenter.onAttach(this);

        mPresenter.getAllBloodSugarMeasurements();

        return view;
    }

    @Override
    protected void setUp(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("fragment'de tıklandı");
            }
        });
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }

    @Override
    public void updateBloodSugarList(List<BloodSugar> bloodSugarList) {
        List<ListItem> sugarValues = new ArrayList<>();
        for ( BloodSugar bloodSugar: bloodSugarList) {
            sugarValues.add(new ListItem(bloodSugar.value, bloodSugar.date.toString(), bloodSugar.sugarMeasurementType.toString() ));
        }
        ListAdapter adapter = new ListAdapter(getBaseActivity(), sugarValues);
        fragment_list.setAdapter(adapter);

    }
}
