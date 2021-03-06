package com.mycroft.awesomelibrary.model;

import android.app.Activity;

import com.mycroft.awesomelibrary.activity.addsub.AnimShopButtonActivity;
import com.mycroft.awesomelibrary.activity.addsub.ShoppingCartAddSubActivity;
import com.mycroft.awesomelibrary.activity.agent.AgentWebActivity;
import com.mycroft.awesomelibrary.activity.badge.BadgeViewActivity;
import com.mycroft.awesomelibrary.activity.city.CityPickerActivity;
import com.mycroft.awesomelibrary.activity.decoration.ItemDecorationActivity;
import com.mycroft.awesomelibrary.activity.edit.FormatEditTextActivity;
import com.mycroft.awesomelibrary.activity.edit.VerificationCodeViewActivity;
import com.mycroft.awesomelibrary.activity.expand.ExpandableTextViewActivity;
import com.mycroft.awesomelibrary.activity.immersion.ImmersionBarActivity;
import com.mycroft.awesomelibrary.activity.luban.LubanActivity;
import com.mycroft.awesomelibrary.activity.picker.AndroidPickerViewActivity;
import com.mycroft.awesomelibrary.activity.picker.PickerViewActivity;
import com.mycroft.awesomelibrary.activity.pinyin.PinyinActivity;
import com.mycroft.awesomelibrary.activity.room.RoomActivity;
import com.mycroft.awesomelibrary.activity.rximagepicker.RxImagePickerActivity;
import com.mycroft.awesomelibrary.activity.swipe.SwipePanelActivity;
import com.mycroft.awesomelibrary.activity.text.FadingTextViewActivity;
import com.mycroft.awesomelibrary.activity.text.HTextViewActivity;
import com.mycroft.awesomelibrary.activity.time.PrettyTimeActivity;
import com.mycroft.awesomelibrary.activity.title.TitleBarActivity;
import com.mycroft.awesomelibrary.activity.update.UpdateActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 组件库model
 *
 * @author mycroft
 */
public class ComponentModel implements Serializable {

    private static final long serialVersionUID = 4133073460077844477L;

    private static final List<ComponentModel> COMPONENT_MODEL_LIST = new ArrayList<>();

    static {
        COMPONENT_MODEL_LIST.add(new ComponentModel(1, "AgentWeb", "AgentWeb 是一个基于的 Android WebView ，极度容易使用以及功能强大的库，提供了 Android WebView 一系列的问题解决方案 ，并且轻量和极度灵活，体验请下载的 agentweb.apk， 或者你也可以到 Google Play 里面下载 AgentWeb ， 详细使用请参照上面的 Sample 。", "https://github.com/Justson/AgentWeb", AgentWebActivity.class, false, "虽然退出后释放了WebView占用内存，但是在使用时WebView占用内存仍然很高，建议在单独进程中使用"));
        COMPONENT_MODEL_LIST.add(new ComponentModel(6, "BadgeView", "支持自由定制外观、拖拽消除的MaterialDesign风格Android BadgeView", "https://github.com/qstumn/BadgeView", BadgeViewActivity.class));
        COMPONENT_MODEL_LIST.add(new ComponentModel(7, "CityPicker", "citypicker城市选择器，详细的省市区地址信息，支持仿iOS滚轮实现，仿京东样式，一级或者三级列表展示方式。", "https://github.com/crazyandcoder/citypicker", CityPickerActivity.class));
        COMPONENT_MODEL_LIST.add(new ComponentModel(8, "CustomTitleBar", "【通用标题栏】通用Android标题栏控件 A Common Titlebar For Android", "https://github.com/xiaohaibin/CustomTitleBar", TitleBarActivity.class));
        COMPONENT_MODEL_LIST.add(new ComponentModel(9, "SwipePanel", "Android 侧划，如斯优雅", "https://github.com/Blankj/SwipePanel", SwipePanelActivity.class));
        COMPONENT_MODEL_LIST.add(new ComponentModel(10, "ExpandableTextView", "Android's TextView that can expand/collapse like the Google Play's app description", "https://github.com/Manabu-GT/ExpandableTextView", ExpandableTextViewActivity.class));
        COMPONENT_MODEL_LIST.add(new ComponentModel(11, "RecyclerItemDecoration", "An Android ItemDecorations library which easily add ItemDecoration to RecyclerView items. Currently Contains PinnedHeaderItemDecoration, DividerItemDecoration, OffsetsItemDecoration, ShaderItemDecoration", "https://github.com/dinuscxj/RecyclerItemDecoration", ItemDecorationActivity.class));
        COMPONENT_MODEL_LIST.add(new ComponentModel(12, "FadingTextView", "A TextView that changes its content automatically every few seconds", "https://github.com/rosenpin/fading-text-view", FadingTextViewActivity.class));
        COMPONENT_MODEL_LIST.add(new ComponentModel(13, "HTextView", "Animation effects to text, not really textview", "https://github.com/hanks-zyh/HTextView", HTextViewActivity.class));
        COMPONENT_MODEL_LIST.add(new ComponentModel(15, "Android-PickerView", "This is a picker view for android , support linkage effect, timepicker and optionspicker.（时间选择器、省市区三级联动）", "https://github.com/Bigkoo/Android-PickerView", AndroidPickerViewActivity.class));
        COMPONENT_MODEL_LIST.add(new ComponentModel(16, "AddSubUtils", "对购物车加减按钮控件的简单封装，几行代码就搞定，采用链式调用，而且样式支持自定义，最重要的是在ListView中和RecyclerView中处理了复用item导致数据错乱的问题。", "https://github.com/Jmengfei/AddSubUtils", ShoppingCartAddSubActivity.class));
        COMPONENT_MODEL_LIST.add(new ComponentModel(16, "AnimShopButton", "A shopping cart button with a telescopic displacement rotation animation ...一个带伸缩位移旋转动画的购物车按钮", "https://github.com/mcxtzhang/AnimShopButton", AnimShopButtonActivity.class));
        COMPONENT_MODEL_LIST.add(new ComponentModel(17, "FormatEditText", "FormatEditText can be used as a number formatted text input box, which can be used to format phone numbers, format ID numbers, format bank card numbers, etc. 可配置自定义规则来格式化号码的输入框，可用来格式化电话号码、身份证号码、银行卡号码等。", "https://github.com/dkzwm/FormatEditText", FormatEditTextActivity.class));
        COMPONENT_MODEL_LIST.add(new ComponentModel(18, "VerificationCodeView", "正方形验证码输入框", "https://github.com/JackTuoTuo/VerificationCodeView", VerificationCodeViewActivity.class));
        COMPONENT_MODEL_LIST.add(new ComponentModel(20, "ImmersionBar", "android 4.4以上沉浸式状态栏和沉浸式导航栏管理，适配横竖屏切换，包括状态栏字体颜色和导航栏图标颜色，适用于Activity、Fragment、DialogFragment、Dialog，PopupWindow，并且适配刘海屏，适配软键盘弹出等问题，一句代码轻松实现，以及对bar的其他设置，详见README。", "https://github.com/gyf-dev/ImmersionBar", ImmersionBarActivity.class));
        COMPONENT_MODEL_LIST.add(new ComponentModel(22, "pinyin4j", "A copy of http://sourceforge.net/projects/pinyin4j, then deploy it to maven central repository.", "https://github.com/belerweb/pinyin4j", PinyinActivity.class));
        COMPONENT_MODEL_LIST.add(new ComponentModel(23, "PrettyTime", "Convert Java/Android Date() objects in just “a few minutes!”", "https://github.com/ocpsoft/prettytime", PrettyTimeActivity.class));
        COMPONENT_MODEL_LIST.add(new ComponentModel(24, "RxImagePicker", "RxJava2 and RxJava3 external support. Android flexible picture selector, provides the support for theme of Zhihu and WeChat (灵活的Android图片选择器，提供了知乎和微信主题的支持）. ", "https://github.com/qingmei2/RxImagePicker", RxImagePickerActivity.class));
        COMPONENT_MODEL_LIST.add(new ComponentModel(25, "pickerview", "One very very user-friendly Picker library（内部提供两种常用类型的Picker：时间选择器（支持聚合）和联动选择器（支持不联动）。支持扩展自定义Picker。） ", "https://github.com/jaaksi/pickerview", PickerViewActivity.class, false, "设置样式比较复杂"));
        COMPONENT_MODEL_LIST.add(new ComponentModel(26, "UpdateAppUtils", "一行代码快速实现app版本更新 ", "https://github.com/teprinciple/UpdateAppUtils", UpdateActivity.class, false, "测试中"));
    }

    private static final List<ComponentModel> HELPER_LIST = new ArrayList<>();

    static {
        HELPER_LIST.add(new ComponentModel(1002, "Room", "Room 数据库框架", "https://github.com/humazed/RoomAsset", RoomActivity.class));
        HELPER_LIST.add(new ComponentModel(1004, "Luban", "Luban(鲁班)—Image compression with efficiency very close to WeChat Moments/可能是最接近微信朋友圈的图片压缩算法", "https://github.com/Curzibn/Luban", LubanActivity.class));
    }

    public static List<ComponentModel> getComponents() {
        return COMPONENT_MODEL_LIST;
    }

    public static List<ComponentModel> getHelpers() {
        return HELPER_LIST;
    }

    private final int id;
    private final String name;
    private final String description;
    private final String githubUrl;
    private final Class<? extends Activity> klazz;
    private final boolean deprecated;

    private final String comment;

    private ComponentModel(int id, String name, String description, String githubUrl, Class<? extends Activity> klazz) {
        this(id, name, description, githubUrl, klazz, false, null);
    }

    private ComponentModel(int id, String name, String description, String githubUrl, Class<? extends Activity> klazz, boolean deprecated, String comment) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.githubUrl = githubUrl;
        this.klazz = klazz;
        this.deprecated = deprecated;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public Class<? extends Activity> getKlazz() {
        return klazz;
    }

    public boolean isDeprecated() {
        return deprecated;
    }

    public String getComment() {
        return comment;
    }
}
