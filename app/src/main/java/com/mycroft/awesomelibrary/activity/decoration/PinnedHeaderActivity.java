package com.mycroft.awesomelibrary.activity.decoration;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.SectionEntity;
import com.dinuscxj.itemdecoration.PinnedHeaderDecoration;
import com.mycroft.awesomelibrary.R;
import com.mycroft.awesomelibrary.activity.common.BaseCommonActivity;
import com.mycroft.awesomelibrary.util.Utils;
import com.mycroft.lib.net.GlideApp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 黏贴头部
 *
 * @author mycroft
 */
public class PinnedHeaderActivity extends BaseCommonActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected int getResId() {
        return R.layout.activity_pinned_header;
    }

    @Override
    protected void initFields(@Nullable Bundle savedInstanceState) {
        mSectionModels.addAll(Utils.getSampleSectionModel());
    }

    private final List<SectionItem> mSectionModels = new ArrayList<>();

    @Override
    protected void initViews() {
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SectionAdapter adapter = new SectionAdapter(mSectionModels);
        recyclerView.setAdapter(adapter);
        PinnedHeaderDecoration pinnedHeaderDecoration = new PinnedHeaderDecoration();

        pinnedHeaderDecoration.registerTypePinnedHeader(SECTION_HEADER_VIEW, (recyclerView, i) -> true);

        recyclerView.addItemDecoration(pinnedHeaderDecoration);
    }

    @Override
    protected void loadData() {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private static final int SECTION_HEADER_VIEW = 0x00000444;

    static class SectionAdapter extends BaseSectionQuickAdapter<SectionItem, BaseViewHolder> {

        SectionAdapter(List<SectionItem> data) {
            super(R.layout.item_section_image, android.R.layout.simple_list_item_1, data);
        }

        @Override
        protected void convertHead(BaseViewHolder helper, SectionItem item) {
            helper.setText(android.R.id.text1, item.header);
        }

        @Override
        protected void convert(BaseViewHolder helper, SectionItem item) {
            ImageView imageView = helper.getView(R.id.imageView);
            GlideApp.with(helper.itemView).load(item.t.image)
                    .placeholder(R.drawable.bg_placeholder)
                    .into(imageView);
        }
    }

    public static class SectionItem extends SectionEntity<SectionModel> {
        public SectionItem(String header) {
            super(true, header);
        }

        public SectionItem(SectionModel sectionModel) {
            super(sectionModel);
        }
    }

    public static class SectionModel {
        final String name;
        final String image;

        public SectionModel(String name, String image) {
            this.name = name;
            this.image = image;
        }
    }
}
