package com.mycroft.awesomelibrary.model;

import android.app.Activity;

import com.mycroft.awesomelibrary.activity.agent.AgentWebActivity;
import com.mycroft.awesomelibrary.activity.badge.BadgeViewActivity;
import com.mycroft.awesomelibrary.activity.city.CityPickerActivity;
import com.mycroft.awesomelibrary.activity.diooto.DiootoActivity;
import com.mycroft.awesomelibrary.activity.drag.DragDraweeActivity;
import com.mycroft.awesomelibrary.activity.drag.DragImageActivity;
import com.mycroft.awesomelibrary.activity.selector.PictureSelectorActivity;
import com.mycroft.awesomelibrary.activity.title.TitleBarActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 组件库model
 *
 * @author mycroft
 */
public class ComponentModel {

    public static List<ComponentModel> get() {
        ArrayList<ComponentModel> list = new ArrayList<>();
        list.add(new ComponentModel(1, "AgentWeb", "AgentWeb 是一个基于的 Android WebView ，极度容易使用以及功能强大的库，提供了 Android WebView 一系列的问题解决方案 ，并且轻量和极度灵活，体验请下载的 agentweb.apk， 或者你也可以到 Google Play 里面下载 AgentWeb ， 详细使用请参照上面的 Sample 。", "https://github.com/Justson/AgentWeb", AgentWebActivity.class));
        list.add(new ComponentModel(2, "Diooto", "微博,微信图库效果,微信视频拖放效果,适配状态栏 、屏幕旋转 、全屏 、长图、GIF、视频", "https://github.com/moyokoo/Diooto", DiootoActivity.class));
        list.add(new ComponentModel(3, "PictureSelector", "Android图片选择器，仿微信的图片选择器的样式和效果。可横竖屏切换显示, 自定义配置，单选，多选，是否显示拍照，material design风格，单选裁剪，拍照裁剪，滑动翻页预览，双击放大，缩放", "https://github.com/wj576038874/PhotoSelector", PictureSelectorActivity.class));
        list.add(new ComponentModel(4, "DragCloseHelper", "仿微信朋友圈图片拖动关闭，Fresco实现", "https://github.com/bauer-bao/DragCloseHelper", DragDraweeActivity.class));
        list.add(new ComponentModel(5, "DragCloseHelper", "仿微信朋友圈图片拖动关闭，Glide实现", "https://github.com/bauer-bao/DragCloseHelper", DragImageActivity.class));
        list.add(new ComponentModel(6, "BadgeView", "支持自由定制外观、拖拽消除的MaterialDesign风格Android BadgeView", "https://github.com/qstumn/BadgeView", BadgeViewActivity.class));
        list.add(new ComponentModel(7, "CityPicker", "citypicker城市选择器，详细的省市区地址信息，支持仿iOS滚轮实现，仿京东样式，一级或者三级列表展示方式。", "https://github.com/crazyandcoder/citypicker", CityPickerActivity.class));
        list.add(new ComponentModel(8, "TitleBarView", "【通用标题栏】通用Android标题栏控件 A Common Titlebar For Android", "https://github.com/xiaohaibin/CustomTitleBar", TitleBarActivity.class));
        return list;
    }

    private int id;
    private String name;
    private String description;
    private String githubUrl;
    private Class<? extends Activity> klazz;

    public ComponentModel() {
    }

    public ComponentModel(int id, String name, String description, String githubUrl, Class<? extends Activity> klazz) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.githubUrl = githubUrl;
        this.klazz = klazz;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    public Class<? extends Activity> getKlazz() {
        return klazz;
    }

    public void setKlazz(Class<? extends Activity> klazz) {
        this.klazz = klazz;
    }
}
