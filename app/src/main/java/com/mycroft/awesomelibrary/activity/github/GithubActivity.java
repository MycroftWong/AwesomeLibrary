package com.mycroft.awesomelibrary.activity.github;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.ToastUtils;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;
import com.mycroft.awesomelibrary.R;
import com.mycroft.awesomelibrary.activity.common.BaseCommonActivity;
import com.mycroft.awesomelibrary.model.ComponentModel;

/**
 * 显示资源的github
 *
 * @author mycroft
 */
public class GithubActivity extends BaseCommonActivity {

    private static final String EXTRA_GITHUB_URL = "github_url.extra";

    public static Intent getIntent(@NonNull Context context, @NonNull ComponentModel componentModel) {
        Intent intent = new Intent(context, GithubActivity.class);
        intent.putExtra(EXTRA_GITHUB_URL, componentModel);
        return intent;
    }

    @Override
    protected int getResId() {
        return R.layout.activity_github;
    }

    private ComponentModel mComponentModel;

    @Override
    protected void initFields(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mComponentModel = (ComponentModel) getIntent().getSerializableExtra(EXTRA_GITHUB_URL);
        } else {
            mComponentModel = (ComponentModel) savedInstanceState.getSerializable(EXTRA_GITHUB_URL);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(EXTRA_GITHUB_URL, mComponentModel);
    }

    private AgentWeb mAgentWeb;

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(findViewById(R.id.webContainer), new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .useDefaultIndicator(-1, 3)
                .setWebViewClient(mWebViewClient)
                .setWebChromeClient(mWebChromeClient)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)
                .interceptUnkownUrl()
                .createAgentWeb()
                .ready()
                .go(mComponentModel.getGithubUrl());
    }

    @Override
    protected void loadData(@Nullable Bundle savedInstanceState) {

    }

    private final WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //do you  work
        }
    };
    private final WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //do you work
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_github, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.copyGithub:
                clipGithubUrl();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void clipGithubUrl() {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData myClip;
        String text = mComponentModel.getGithubUrl();
        myClip = ClipData.newPlainText("github", text);
        clipboardManager.setPrimaryClip(myClip);
        ToastUtils.showShort(R.string.toast_clip_github_url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }
}
