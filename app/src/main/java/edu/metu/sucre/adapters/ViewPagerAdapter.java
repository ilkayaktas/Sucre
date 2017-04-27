package edu.metu.sucre.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import edu.metu.sucre.views.activities.base.BaseFragment;

/**
 * Created by iaktas on 15.03.2017.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private final List<BaseFragment> fragments;

    public ViewPagerAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
