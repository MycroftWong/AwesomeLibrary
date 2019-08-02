package com.mycroft.awesomelibrary.activity.picker;

import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.constant.TimeConstants;
import com.mycroft.awesomelibrary.R;
import com.mycroft.awesomelibrary.activity.common.BaseCommonComponentActivity;

import org.jaaksi.pickerview.picker.TimePicker;
import org.jaaksi.pickerview.widget.PickerView;

import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PickerViewActivity extends BaseCommonComponentActivity {

    @BindView(R.id.chooseDateButton)
    Button chooseDateButton;
    @BindView(R.id.chooseTimeButton)
    Button chooseTimeButton;

    @Override
    protected int getResId() {
        return R.layout.activity_picker_view;
    }

    @Override
    protected void initViews() {
        super.initViews();
        ButterKnife.bind(this);
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.chooseDateButton, R.id.chooseTimeButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.chooseDateButton:
                chooseDate();
                break;
            case R.id.chooseTimeButton:
                chooseTime();
                break;
            default:
                break;
        }
    }

    private void chooseDate() {
        new TimePicker.Builder(this, TimePicker.TYPE_DATE,
                (picker, date) -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    chooseDateButton.setText(String.format(Locale.CHINA, "%d年%d月%d日",
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH) + 1,
                            calendar.get(Calendar.DAY_OF_MONTH)));
                })
                // 设置选中时间
                //.setSelectedDate()
                .setSelectedDate(System.currentTimeMillis() - TimeConstants.DAY * 360L)
                // 设置pickerview样式
                .setInterceptor((pickerView, params) -> {
                    pickerView.setVisibleItemCount(5);
                    // 将年月设置为循环的
                    int type = (int) pickerView.getTag();
                    if (type == TimePicker.TYPE_YEAR || type == TimePicker.TYPE_MONTH) {
                        pickerView.setIsCirculation(true);
                    }
                })
                .create()
                .show();
    }

    private void chooseTime() {
        new TimePicker.Builder(this, TimePicker.TYPE_TIME,
                (picker, date) -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    chooseDateButton.setText(String.format(Locale.CHINA, "%d时%d分",
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE)));
                })
                // 设置时间区间
                // 设置选中时间
                .setSelectedDate(System.currentTimeMillis() - TimeConstants.DAY * 360L)
                // 设置pickerview样式
                .setInterceptor((pickerView, params) -> {
                    pickerView.setVisibleItemCount(5);
                    // 将年月设置为循环的
                    int type = (int) pickerView.getTag();
                    if (type == TimePicker.TYPE_YEAR || type == TimePicker.TYPE_MONTH) {
                        pickerView.setIsCirculation(true);
                    }
                })
                .create()
                .show();
    }
}
