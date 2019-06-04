package com.mycroft.awesomelibrary.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout

class SquareLinearLayout : LinearLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(View.getDefaultSize(0, widthMeasureSpec),
                View.getDefaultSize(0, heightMeasureSpec))

        val childWidthSize = measuredWidth
        val newWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY)
        val newHeightMeasureSpec = widthMeasureSpec

        super.onMeasure(newWidthMeasureSpec, newHeightMeasureSpec)
    }

}