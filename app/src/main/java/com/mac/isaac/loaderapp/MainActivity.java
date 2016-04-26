package com.mac.isaac.loaderapp;

import android.content.SharedPreferences;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<SharedPreferences> {

    private static final String KEY = "prefs";
    @BindView(R.id.textView)
    private TextView textView;
    @BindView(R.id.button)
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPrefs(v);
            }
        });
    }

    @OnClick(R.id.button)
    public void setPrefs(View view) {
        /*sp=this.getSharedPreferences("service_validation", MODE_WORLD_READABLE);
        val=sp.getInt("VALIDATION", val);*/
        Log.i("MYTAG", "Starting Loader");
        // start loader
        getSupportLoaderManager()
                .initLoader(0, null, this)
                .forceLoad();
    }

    @Override
    public Loader<SharedPreferences> onCreateLoader(int id, Bundle args) {
        return (new MyLoader(this));
    }

    @Override
    public void onLoadFinished(Loader<SharedPreferences> loader, SharedPreferences data) {
        int value = data.getInt(KEY, 0);
        value += 1;
        textView.setText(String.valueOf(value));
        // update value
        SharedPreferences.Editor editor = data.edit();
        editor.putInt(KEY, value);
        MyLoader.persist(editor);
    }

    @Override
    public void onLoaderReset(Loader<SharedPreferences> loader) {

    }
}
