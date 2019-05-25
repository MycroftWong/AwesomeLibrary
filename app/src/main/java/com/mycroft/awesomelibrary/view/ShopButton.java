package com.mycroft.awesomelibrary.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SizeUtils;
import com.mycroft.awesomelibrary.R;

/**
 * @author mycroft
 */
public class ShopButton extends LinearLayout {
    public ShopButton(Context context) {
        this(context, null);
    }

    public ShopButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShopButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ShopButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private Button mSubButton;
    private TextView mCountText;
    private Button mAddButton;

    private int mMin;
    private int mMax;

    private int mCurrentCount;

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        setBackgroundResource(R.drawable.shop_button);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ShopButton, defStyleAttr, defStyleRes);

        initViews(context, attrs, defStyleAttr, defStyleRes, a);
        initViewsStyle(a);

        mCurrentCount = 1;

        mMin = a.getInt(R.styleable.ShopButton_min, 1);
        mMax = a.getInt(R.styleable.ShopButton_max, 1);

        a.recycle();

        setMax(mMax);
        setMin(mMin);
    }

    private void initViews(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, TypedArray a) {

        mSubButton = new Button(context, attrs, defStyleAttr, defStyleRes);
        mCountText = new TextView(context, attrs, defStyleAttr, defStyleRes);
        mAddButton = new Button(context, attrs, defStyleAttr, defStyleRes);

        int defaultButtonWidth = SizeUtils.dp2px(24);

        defaultButtonWidth = a.getDimensionPixelSize(R.styleable.ShopButton_buttonWidth, defaultButtonWidth);
        LayoutParams buttonLp = new LayoutParams(defaultButtonWidth, LayoutParams.MATCH_PARENT);

        LayoutParams countLp = new LayoutParams(0, LayoutParams.MATCH_PARENT);
        countLp.weight = 1;

        mSubButton.setLayoutParams(buttonLp);
        mCountText.setLayoutParams(countLp);
        mSubButton.setLayoutParams(buttonLp);

        addView(mSubButton, buttonLp);
        addView(mCountText, countLp);
        addView(mAddButton, buttonLp);

        mSubButton.setOnClickListener(mClickListener);
        mAddButton.setOnClickListener(mClickListener);
    }

    private void initViewsStyle(TypedArray a) {

        int[] colors = new int[]{Color.rgb(0x33, 0x33, 0x33), Color.rgb(0XED, 0XED, 0XED)};
        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_enabled};
        states[1] = new int[]{};
        ColorStateList defaultTextColor = new ColorStateList(states, colors);

        ColorStateList defaultButtonTextColor = a.getColorStateList(R.styleable.ShopButton_buttonTextColor);
        if (defaultButtonTextColor == null) {
            defaultButtonTextColor = defaultTextColor;
        }

        ColorStateList defaultCountTextColor = a.getColorStateList(R.styleable.ShopButton_countTextColor);
        if (defaultCountTextColor == null) {
            defaultCountTextColor = defaultTextColor;
        }

        int defaultButtonTextSize = SizeUtils.dp2px(12);
        int defaultCountTextSize = defaultButtonTextSize;

        defaultButtonTextSize = a.getDimensionPixelSize(R.styleable.ShopButton_buttonTextSize, defaultButtonTextSize);
        defaultCountTextSize = a.getDimensionPixelSize(R.styleable.ShopButton_countTextSize, defaultCountTextSize);

        Drawable buttonBackground = a.getDrawable(R.styleable.ShopButton_buttonBackground);
        Drawable countBackground = a.getDrawable(R.styleable.ShopButton_countBackground);

        mSubButton.setTypeface(Typeface.DEFAULT_BOLD);
        mSubButton.setGravity(Gravity.CENTER);
        mSubButton.setTextColor(defaultButtonTextColor);
        mSubButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, defaultButtonTextSize);
        mSubButton.setText("-");
        if (buttonBackground != null) {
            mSubButton.setBackground(buttonBackground);
        }

        mCountText.setGravity(Gravity.CENTER);
        mCountText.setTextColor(defaultCountTextColor);
        mCountText.setTextSize(TypedValue.COMPLEX_UNIT_PX, defaultCountTextSize);
        mCountText.setText(String.valueOf(mCurrentCount));
        if (countBackground != null) {
            mCountText.setBackground(countBackground);
        }

        mAddButton.setTypeface(Typeface.DEFAULT_BOLD);
        mAddButton.setGravity(Gravity.CENTER);
        mAddButton.setTextColor(defaultButtonTextColor);
        mAddButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, defaultButtonTextSize);
        mAddButton.setText("+");
        if (buttonBackground != null) {
            mAddButton.setBackground(buttonBackground);
        }
    }

    private final OnClickListener mClickListener = v -> {
        if (mSubButton == v) {
            if (mCurrentCount > mMin) {
                mCurrentCount--;
            }
        } else if (mAddButton == v) {
            if (mCurrentCount < mMax) {
                mCurrentCount++;
            }
        }
        setCount(mCurrentCount);
    };

    /**
     * 获取数量
     *
     * @return current count
     */
    public int getCount() {
        return mCurrentCount;
    }

    public void setCount(int count) {
        count = Math.max(mMin, Math.min(mMax, count));

        mCurrentCount = count;
        mCountText.setText(String.valueOf(mCurrentCount));

        mSubButton.setEnabled(mMin < mCurrentCount);
        mAddButton.setEnabled(mMax > mCurrentCount);
    }

    public void setMin(int min) {
        if (min > mMax) {
            throw new IllegalArgumentException("min > max");
        }
        mMin = min;
        setCount(mCurrentCount);
    }

    public void setMax(int max) {
        if (max < mMin) {
            throw new IllegalArgumentException("max < min");
        }
        mMax = max;
        setCount(mCurrentCount);
    }
}
