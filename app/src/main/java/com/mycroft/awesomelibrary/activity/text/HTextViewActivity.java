package com.mycroft.awesomelibrary.activity.text;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.hanks.htextview.evaporate.EvaporateTextView;
import com.hanks.htextview.fade.FadeTextView;
import com.hanks.htextview.fall.FallTextView;
import com.hanks.htextview.line.LineTextView;
import com.hanks.htextview.rainbow.RainbowTextView;
import com.hanks.htextview.scale.ScaleTextView;
import com.hanks.htextview.typer.TyperTextView;
import com.mycroft.awesomelibrary.R;
import com.mycroft.awesomelibrary.activity.common.BaseCommonComponentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * TextView动画
 *
 * @author wangqiang
 */
public class HTextViewActivity extends BaseCommonComponentActivity {

    @BindView(R.id.lineText)
    LineTextView lineText;
    @BindView(R.id.fadeText)
    FadeTextView fadeText;
    @BindView(R.id.typerText)
    TyperTextView typerText;
    @BindView(R.id.rainbowText)
    RainbowTextView rainbowText;
    @BindView(R.id.scaleText)
    ScaleTextView scaleText;
    @BindView(R.id.evaporateText)
    EvaporateTextView evaporateText;
    @BindView(R.id.fallText)
    FallTextView fallText;

    @Override
    protected int getResId() {
        return R.layout.activity_htext_view;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        ButterKnife.bind(this);

    }

    @Override
    protected void loadData(@Nullable Bundle savedInstanceState) {

    }

    @OnClick({R.id.lineText, R.id.fadeText, R.id.typerText, R.id.rainbowText, R.id.scaleText, R.id.evaporateText, R.id.fallText})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lineText:
                lineText.animateText("Today is Monday");
                break;
            case R.id.fadeText:
                fadeText.animateText("Today is Monday");
                break;
            case R.id.typerText:
                typerText.animateText("This is FadeTextView");
                break;
            case R.id.rainbowText:
                rainbowText.animateText("This is FadeTextView");
                break;
            case R.id.scaleText:
                scaleText.animateText("This is FadeTextView");
                break;
            case R.id.evaporateText:
                evaporateText.animateText("This is FadeTextView");
                break;
            case R.id.fallText:
                fallText.animateText("This is FadeTextView");
                break;
            default:
                break;
        }
    }


}
