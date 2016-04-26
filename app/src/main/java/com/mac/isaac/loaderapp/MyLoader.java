package com.mac.isaac.loaderapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Created by admin on 4/25/2016.
 */
public class MyLoader extends AsyncTaskLoader<SharedPreferences> implements SharedPreferences.OnSharedPreferenceChangeListener {

    private SharedPreferences prefs = null;

    public MyLoader(Context context) {
        super(context);
    }

    public static void persist(final SharedPreferences.Editor editor) {
        editor.apply();
    }

    @Override
    public SharedPreferences loadInBackground() {
        // Load the data asynchronously
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        prefs.registerOnSharedPreferenceChangeListener(this);
        return (prefs);
    }

    @Override
    protected void onStartLoading() {
        // starts the loading of the data
        super.onStartLoading();
        if (prefs != null) {
            deliverResult(prefs);
        }
        if (takeContentChanged() || prefs == null) {
            forceLoad();
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // notify loader that content has changed
        onContentChanged();
    }
}
