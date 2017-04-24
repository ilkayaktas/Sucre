package edu.metu.sucre.controller.pref;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Inject;
import javax.inject.Singleton;

import edu.metu.sucre.di.annotations.ApplicationContext;

/**
 * Created by iaktas on 10.03.2017.
 */

@Singleton
public class PreferenceHelperImpl implements PreferenceHelper {
    public static final String SHARED_PREF_DBDREATED = "DatabaseCreated";
    
    private final SharedPreferences mPrefs;
    
    @Inject
    public PreferenceHelperImpl(@ApplicationContext Context context) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }
    
    @Override
    public boolean getDatabaseCreatedStatus() {
        return mPrefs.getBoolean(SHARED_PREF_DBDREATED, false);
    }

    @Override
    public void setDatabaseCreatedStatus() {
        mPrefs.edit().putBoolean(SHARED_PREF_DBDREATED, true).apply();
    }
}
