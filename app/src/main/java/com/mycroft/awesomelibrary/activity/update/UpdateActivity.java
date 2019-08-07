package com.mycroft.awesomelibrary.activity.update;

import android.widget.Button;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.AppUtils;
import com.mycroft.awesomelibrary.R;
import com.mycroft.awesomelibrary.activity.common.BaseCommonComponentActivity;
import com.mycroft.awesomelibrary.service.FileServiceImpl;

import constacne.UiType;
import model.UiConfig;
import model.UpdateConfig;
import update.UpdateAppUtils;

/**
 * @author mycroft
 */
public class UpdateActivity extends BaseCommonComponentActivity {

    @Override
    protected int getResId() {
        return R.layout.activity_update;
    }

    @Override
    protected void initViews() {
        super.initViews();

        Button button = findViewById(R.id.updateButton);

        button.setOnClickListener(v -> showDownloadApkDialog("https://imtt.dd.qq.com/16891/apk/99B0E4180BE4E160FF78B4E22DADA91B.apk?fsname=com.tencent.mm_7.0.6_1460.apk&csr=1bbd",
                "发现新版本2.0.0",
                "1. 测试版本更新\n2. 保存apk位置"));
    }

    @Override
    protected void loadData() {

    }

    private void showDownloadApkDialog(String apkUrl, String updateTitle, String updateContent) {
        int textColor = ContextCompat.getColor(this, R.color.common_text_color);
        UiConfig uiConfig = new UiConfig();
        uiConfig.setUiType(UiType.SIMPLE);
        uiConfig.setUpdateLogoImgRes(R.mipmap.ic_launcher_round);
        uiConfig.setTitleTextColor(textColor);
        uiConfig.setContentTextColor(textColor);
        uiConfig.setTitleTextSize(18f);
        uiConfig.setContentTextSize(14f);
        uiConfig.setUpdateBtnTextColor(textColor);

        UpdateConfig updateConfig = new UpdateConfig();
        updateConfig.setForce(true);
        updateConfig.setCheckWifi(true);
        updateConfig.setNotifyImgRes(R.mipmap.ic_launcher);
        updateConfig.setApkSaveName(AppUtils.getAppName());
        updateConfig.setApkSavePath(FileServiceImpl.getInstance().getApkPath());

        UpdateAppUtils
                .getInstance()
                .uiConfig(uiConfig)
                .updateConfig(updateConfig)
                .apkUrl(apkUrl)
                .updateTitle(updateTitle)
                .updateContent(updateContent)
                .update();
    }

}
