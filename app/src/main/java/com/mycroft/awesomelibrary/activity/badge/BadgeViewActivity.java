package com.mycroft.awesomelibrary.activity.badge;

import android.view.Gravity;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.mycroft.awesomelibrary.R;
import com.mycroft.awesomelibrary.activity.common.BaseCommonComponentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * @author mycroft
 */
public class BadgeViewActivity extends BaseCommonComponentActivity {

    @BindView(R.id.cartText)
    TextView cartText;

    @Override
    protected int getResId() {
        return R.layout.activity_badge_view;
    }

    private Badge mBadge;

    @Override
    protected void initViews() {
        super.initViews();
        ButterKnife.bind(this);

        mBadge = new QBadgeView(this)
                .bindTarget(cartText)
                .setBadgeTextColor(ContextCompat.getColor(this, android.R.color.white))
                .setBadgeBackgroundColor(ContextCompat.getColor(this, android.R.color.black))
                .setBadgeGravity(Gravity.TOP | Gravity.START)
                .setOnDragStateChangedListener((dragState, badge, targetView) -> {
                    // nothing
                })
                .setShowShadow(false)
                .setBadgeNumber(5);

        cartText.setOnClickListener(v -> {
            if (mBadge.getBadgeNumber() == 5) {
                mBadge.setBadgeNumber(0);
            } else {
                mBadge.setBadgeNumber(5);
            }
        });
    }

    @Override
    protected void loadData() {

    }

}
