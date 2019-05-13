package com.mycroft.awesomelibrary.activity.common;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.mycroft.awesomelibrary.R;
import com.mycroft.awesomelibrary.activity.github.GithubActivity;

/**
 * 同一处理toolbar, 可以跳转到github
 *
 * @author mycroft
 */
public abstract class BaseCommonComponentActivity extends BaseCommonActivity {

    public static final String EXTRA_GITHUB_URL = "github_url.extra";

    private String mGithubUrl;

    @Override
    protected void initFields(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mGithubUrl = getIntent().getStringExtra(EXTRA_GITHUB_URL);
        } else {
            mGithubUrl = savedInstanceState.getString(EXTRA_GITHUB_URL);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_GITHUB_URL, mGithubUrl);
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
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
                startActivity(GithubActivity.getIntent(this, mGithubUrl));
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
