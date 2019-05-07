package net.moyokoo.diooto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import net.moyokoo.diooto.config.DiootoConfig;
import net.moyokoo.diooto.config.ContentViewOriginModel;
import net.moyokoo.diooto.tools.NoScrollViewPager;
import net.moyokoo.diooto.interfaces.IIndicator;
import net.moyokoo.diooto.interfaces.IProgress;

import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends AppCompatActivity {
    private NoScrollViewPager mViewPager;
    List<ContentViewOriginModel> contentViewOriginModels;
    List<ImageFragment> fragmentList;
    DiootoConfig diootoConfig;
    FrameLayout indicatorLayout;
    static IIndicator iIndicator;
    static IProgress iProgress;
    boolean isNeedAnimationForClickPosition = true;

    public static void startImageActivity(Activity activity, DiootoConfig diootoConfig) {
        Intent intent = new Intent(activity, ImageActivity.class);
        intent.putExtra("config", diootoConfig);
        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_FULLSCREEN;
//        uiFlags |= 0x00001000;
//        getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image);
        mViewPager = findViewById(R.id.viewPager);
        indicatorLayout = findViewById(R.id.indicatorLayout);
        diootoConfig = getIntent().getParcelableExtra("config");
        indicatorLayout.setVisibility(diootoConfig.getIndicatorVisibility());
        int currentPosition = diootoConfig.getPosition();
        String[] imageUrls = diootoConfig.getImageUrls();
        contentViewOriginModels = diootoConfig.getContentViewOriginModels();
        fragmentList = new ArrayList<>();
        for (int i = 0; i < contentViewOriginModels.size(); i++) {
            ImageFragment imageFragment = ImageFragment.newInstance(
                    imageUrls[i], i, diootoConfig.getType(),
                    contentViewOriginModels.size() == 1 || diootoConfig.getPosition() == i, contentViewOriginModels.get(i)
            );
            fragmentList.add(imageFragment);
        }
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });
        mViewPager.setCurrentItem(currentPosition);
        if (iIndicator != null && contentViewOriginModels.size() != 1) {
            iIndicator.attach(indicatorLayout);
            iIndicator.onShow(mViewPager);
        }
    }

    //用来判断第一次点击的时候是否需要动画  第一次需要动画  后续viewpager滑动回到该页面的时候  不做动画
    public boolean isNeedAnimationForClickPosition(int position) {
        return isNeedAnimationForClickPosition && diootoConfig.getPosition() == position;
    }

    public void refreshNeedAnimationForClickPosition() {
        isNeedAnimationForClickPosition = false;
    }

    public void finishView() {
        if (Diooto.onFinishListener != null) {
            Diooto.onFinishListener.finish(fragmentList.get(mViewPager.getCurrentItem()).getDragDiootoView());
        }
        Diooto.onLoadPhotoBeforeShowBigImageListener = null;
        Diooto.onShowToMaxFinishListener = null;
        Diooto.onProvideViewListener = null;
        Diooto.onFinishListener = null;
        iIndicator = null;
        iProgress = null;
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            fragmentList.get(mViewPager.getCurrentItem()).backToMin();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
