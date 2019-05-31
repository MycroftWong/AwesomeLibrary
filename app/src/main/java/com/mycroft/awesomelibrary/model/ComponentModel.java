package com.mycroft.awesomelibrary.model;

import android.app.Activity;

import com.mycroft.awesomelibrary.activity.addsub.AnimShopButtonActivity;
import com.mycroft.awesomelibrary.activity.addsub.ShoppingCartAddSubActivity;
import com.mycroft.awesomelibrary.activity.agent.AgentWebActivity;
import com.mycroft.awesomelibrary.activity.badge.BadgeViewActivity;
import com.mycroft.awesomelibrary.activity.check.CheckHelperActivity;
import com.mycroft.awesomelibrary.activity.city.CityPickerActivity;
import com.mycroft.awesomelibrary.activity.decoration.ItemDecorationActivity;
import com.mycroft.awesomelibrary.activity.diooto.DiootoActivity;
import com.mycroft.awesomelibrary.activity.drag.DragDraweeActivity;
import com.mycroft.awesomelibrary.activity.drag.DragImageActivity;
import com.mycroft.awesomelibrary.activity.edit.FormatEditTextActivity;
import com.mycroft.awesomelibrary.activity.edit.VerificationCodeViewActivity;
import com.mycroft.awesomelibrary.activity.expand.ExpandableTextViewActivity;
import com.mycroft.awesomelibrary.activity.picker.PickerViewActivity;
import com.mycroft.awesomelibrary.activity.room.RoomActivity;
import com.mycroft.awesomelibrary.activity.selector.PictureSelectorActivity;
import com.mycroft.awesomelibrary.activity.selector.RxGalleyActivity;
import com.mycroft.awesomelibrary.activity.swipe.SwipePanelActivity;
import com.mycroft.awesomelibrary.activity.text.FadingTextViewActivity;
import com.mycroft.awesomelibrary.activity.text.HTextViewActivity;
import com.mycroft.awesomelibrary.activity.title.TitleBarActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 组件库model
 *
 * @author mycroft
 */
public class ComponentModel {

    private static final List<ComponentModel> COMPONENT_MODEL_LIST = new ArrayList<>();

    static {
        COMPONENT_MODEL_LIST.add(new ComponentModel(1, "AgentWeb", "AgentWeb 是一个基于的 Android WebView ，极度容易使用以及功能强大的库，提供了 Android WebView 一系列的问题解决方案 ，并且轻量和极度灵活，体验请下载的 agentweb.apk， 或者你也可以到 Google Play 里面下载 AgentWeb ， 详细使用请参照上面的 Sample 。", "https://github.com/Justson/AgentWeb", AgentWebActivity.class));
        COMPONENT_MODEL_LIST.add(new ComponentModel(2, "Diooto", "微博,微信图库效果,微信视频拖放效果,适配状态栏 、屏幕旋转 、全屏 、长图、GIF、视频", "https://github.com/moyokoo/Diooto", DiootoActivity.class));
        COMPONENT_MODEL_LIST.add(new ComponentModel(3, "PictureSelector", "Android图片选择器，仿微信的图片选择器的样式和效果。可横竖屏切换显示, 自定义配置，单选，多选，是否显示拍照，material design风格，单选裁剪，拍照裁剪，滑动翻页预览，双击放大，缩放", "https://github.com/wj576038874/PhotoSelector", PictureSelectorActivity.class));
        COMPONENT_MODEL_LIST.add(new ComponentModel(4, "DragCloseHelper", "仿微信朋友圈图片拖动关闭，Fresco实现", "https://github.com/bauer-bao/DragCloseHelper", DragDraweeActivity.class));
        COMPONENT_MODEL_LIST.add(new ComponentModel(5, "DragCloseHelper", "仿微信朋友圈图片拖动关闭，Glide实现", "https://github.com/bauer-bao/DragCloseHelper", DragImageActivity.class));
        COMPONENT_MODEL_LIST.add(new ComponentModel(6, "BadgeView", "支持自由定制外观、拖拽消除的MaterialDesign风格Android BadgeView", "https://github.com/qstumn/BadgeView", BadgeViewActivity.class));
        COMPONENT_MODEL_LIST.add(new ComponentModel(7, "CityPicker", "citypicker城市选择器，详细的省市区地址信息，支持仿iOS滚轮实现，仿京东样式，一级或者三级列表展示方式。", "https://github.com/crazyandcoder/citypicker", CityPickerActivity.class));
        COMPONENT_MODEL_LIST.add(new ComponentModel(8, "CustomTitleBar", "【通用标题栏】通用Android标题栏控件 A Common Titlebar For Android", "https://github.com/xiaohaibin/CustomTitleBar", TitleBarActivity.class));
        COMPONENT_MODEL_LIST.add(new ComponentModel(9, "SwipePanel", "Android 侧划，如斯优雅", "https://github.com/Blankj/SwipePanel", SwipePanelActivity.class));
        COMPONENT_MODEL_LIST.add(new ComponentModel(10, "ExpandableTextView", "Android's TextView that can expand/collapse like the Google Play's app description", "https://github.com/Manabu-GT/ExpandableTextView", ExpandableTextViewActivity.class));
        COMPONENT_MODEL_LIST.add(new ComponentModel(11, "RecyclerItemDecoration", "An Android ItemDecorations library which easily add ItemDecoration to RecyclerView items. Currently Contains PinnedHeaderItemDecoration, DividerItemDecoration, OffsetsItemDecoration, ShaderItemDecoration", "https://github.com/dinuscxj/RecyclerItemDecoration", ItemDecorationActivity.class));
        COMPONENT_MODEL_LIST.add(new ComponentModel(12, "FadingTextView", "A TextView that changes its content automatically every few seconds", "https://github.com/rosenpin/fading-text-view", FadingTextViewActivity.class));
        COMPONENT_MODEL_LIST.add(new ComponentModel(13, "HTextView", "Animation effects to text, not really textview", "https://github.com/hanks-zyh/HTextView", HTextViewActivity.class));
        COMPONENT_MODEL_LIST.add(new ComponentModel(14, "RxGalleryFinal", "图片选择库，单选/多选、拍照、裁剪、压缩，自定义。包括视频选择和录制。", "https://github.com/FinalTeam/RxGalleryFinal", RxGalleyActivity.class, true));
        COMPONENT_MODEL_LIST.add(new ComponentModel(15, "Android-PickerView", "This is a picker view for android , support linkage effect, timepicker and optionspicker.（时间选择器、省市区三级联动）", "https://github.com/Bigkoo/Android-PickerView", PickerViewActivity.class));
        COMPONENT_MODEL_LIST.add(new ComponentModel(16, "AddSubUtils", "对购物车加减按钮控件的简单封装，几行代码就搞定，采用链式调用，而且样式支持自定义，最重要的是在ListView中和RecyclerView中处理了复用item导致数据错乱的问题。", "https://github.com/Jmengfei/AddSubUtils", ShoppingCartAddSubActivity.class));
        COMPONENT_MODEL_LIST.add(new ComponentModel(16, "AnimShopButton", "A shopping cart button with a telescopic displacement rotation animation ...一个带伸缩位移旋转动画的购物车按钮", "https://github.com/mcxtzhang/AnimShopButton", AnimShopButtonActivity.class));
        COMPONENT_MODEL_LIST.add(new ComponentModel(17, "FormatEditText", "FormatEditText can be used as a number formatted text input box, which can be used to format phone numbers, format ID numbers, format bank card numbers, etc. 可配置自定义规则来格式化号码的输入框，可用来格式化电话号码、身份证号码、银行卡号码等。", "https://github.com/dkzwm/FormatEditText", FormatEditTextActivity.class));
        COMPONENT_MODEL_LIST.add(new ComponentModel(18, "VerificationCodeView", "正方形验证码输入框", "https://github.com/JackTuoTuo/VerificationCodeView", VerificationCodeViewActivity.class));
    }

    private static final List<ComponentModel> HELPER_LIST = new ArrayList<>();

    static {
        HELPER_LIST.add(new ComponentModel(1001, "CheckHelper", "RecylerView 列表选择工具，提供单选、多选等解耦 Api", "https://github.com/gminibird/CheckHelper", CheckHelperActivity.class));
        HELPER_LIST.add(new ComponentModel(1002, "Room", "Room 数据库框架", "https://github.com/humazed/RoomAsset", RoomActivity.class));
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

    public ComponentModel(int id, String name, String description, String githubUrl, Class<? extends Activity> klazz) {
        this(id, name, description, githubUrl, klazz, false);
    }

    public ComponentModel(int id, String name, String description, String githubUrl, Class<? extends Activity> klazz, boolean deprecated) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.githubUrl = githubUrl;
        this.klazz = klazz;
        this.deprecated = deprecated;
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
}
