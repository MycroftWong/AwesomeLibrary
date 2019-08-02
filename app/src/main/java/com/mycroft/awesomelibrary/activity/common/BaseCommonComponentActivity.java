package com.mycroft.awesomelibrary.activity.common;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.mycroft.awesomelibrary.R;
import com.mycroft.awesomelibrary.activity.github.GithubActivity;
import com.mycroft.awesomelibrary.model.ComponentModel;

/**
 * 同一处理toolbar, 可以跳转到github
 *
 * @author mycroft
 */
public abstract class BaseCommonComponentActivity extends BaseCommonActivity {

    public static final String EXTRA_COMPONENT_MODEL = "component_model.extra";

    private ComponentModel mComponentModel;

    @Override
    protected void initFields(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mComponentModel = (ComponentModel) getIntent().getSerializableExtra(EXTRA_COMPONENT_MODEL);
        } else {
            mComponentModel = (ComponentModel) savedInstanceState.getSerializable(EXTRA_COMPONENT_MODEL);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(EXTRA_COMPONENT_MODEL, mComponentModel);
    }

    @Override
    protected void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.github:
                startActivity(GithubActivity.getIntent(this, mComponentModel));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_common_component_model, menu);
        return true;
    }
}
