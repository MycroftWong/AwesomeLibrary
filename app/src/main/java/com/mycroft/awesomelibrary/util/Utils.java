package com.mycroft.awesomelibrary.util;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.fresco.helper.ImageLoader;
import com.mycroft.awesomelibrary.R;
import com.mycroft.awesomelibrary.activity.decoration.PinnedHeaderActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * 生成样例数据
 *
 * @author mycroft
 */
public class Utils {

    private static final String[] SAMPLE_IMAGE_URLS = new String[]{
            "http://img1.juimg.com/140908/330608-140ZP1531651.jpg",
            "http://img.esthetics.cn/Uploads/Picture/2019-05-06/5ccfa9a8c1f06.jpg",
            "http://img.esthetics.cn/Uploads/Picture/2019-05-06/5ccfa463997e3.png",
            "http://img.esthetics.cn/Uploads/Picture/2019-05-06/5ccfa1efad0f0.png",
            "http://img.esthetics.cn/Uploads/Picture/2019-05-06/5ccf9e907373c.png",
            "http://img.esthetics.cn/Uploads/Picture/2019-05-06/5ccf96aa9c275.png",
            "http://img1.juimg.com/140908/330608-140ZP1531651.jpg",
            "http://img.esthetics.cn/Uploads/Picture/2019-05-06/5ccfa9a8c1f06.jpg",
            "http://img.esthetics.cn/Uploads/Picture/2019-05-06/5ccfa463997e3.png",
            "http://img.esthetics.cn/Uploads/Picture/2019-05-06/5ccfa1efad0f0.png",
            "http://img.esthetics.cn/Uploads/Picture/2019-05-06/5ccf9e907373c.png",
            "http://img.esthetics.cn/Uploads/Picture/2019-05-06/5ccf96aa9c275.png",
            "http://img1.juimg.com/140908/330608-140ZP1531651.jpg",
            "http://img.esthetics.cn/Uploads/Picture/2019-05-06/5ccfa9a8c1f06.jpg",
            "http://img.esthetics.cn/Uploads/Picture/2019-05-06/5ccfa463997e3.png",
            "http://img.esthetics.cn/Uploads/Picture/2019-05-06/5ccfa1efad0f0.png",
            "http://img.esthetics.cn/Uploads/Picture/2019-05-06/5ccf9e907373c.png",
            "http://img.esthetics.cn/Uploads/Picture/2019-05-06/5ccf96aa9c275.png",
            "http://img1.juimg.com/140908/330608-140ZP1531651.jpg",
            "http://img.esthetics.cn/Uploads/Picture/2019-05-06/5ccfa9a8c1f06.jpg",
            "http://img.esthetics.cn/Uploads/Picture/2019-05-06/5ccfa463997e3.png",
            "http://img.esthetics.cn/Uploads/Picture/2019-05-06/5ccfa1efad0f0.png",
            "http://img.esthetics.cn/Uploads/Picture/2019-05-06/5ccf9e907373c.png",
            "http://img.esthetics.cn/Uploads/Picture/2019-05-06/5ccf96aa9c275.png",
    };

    public static ArrayList<String> getSampleImages() {
        return new ArrayList<>(Arrays.asList(SAMPLE_IMAGE_URLS));
    }

    public static ArrayList<PinnedHeaderActivity.SectionItem> getSampleSectionModel() {

        ArrayList<PinnedHeaderActivity.SectionItem> sectionModels = new ArrayList<>(SAMPLE_IMAGE_URLS.length);

        int section = -1;

        for (int i = 0; i < SAMPLE_IMAGE_URLS.length; i++) {
            if (i / 6 > section) {
                section++;

                sectionModels.add(new PinnedHeaderActivity.SectionItem(
                        String.format(Locale.CHINA, "section: %d", section)
                ));

                continue;
            }
            sectionModels.add(new PinnedHeaderActivity.SectionItem(
                    new PinnedHeaderActivity.SectionModel(
                            String.format(Locale.CHINA, "section: %d, item: %d", section, i % 6),
                            SAMPLE_IMAGE_URLS[i])
            ));
        }
        return sectionModels;
    }

    public static RecyclerView.Adapter getImageSampleAdapter() {
        return new ImageAdapter(getSampleImages());
    }

    static class ImageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        ImageAdapter(@Nullable List<String> data) {
            super(R.layout.item_section_drawee, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            SimpleDraweeView draweeView = helper.getView(R.id.draweeView);
            ImageLoader.loadImage(draweeView, item);
        }
    }
}
