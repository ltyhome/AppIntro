package com.github.appintro.indicator

import android.content.Context
import android.view.Gravity
import android.view.Gravity.CENTER
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.github.appintro.R

/**
 * An [IndicatorController] that shows a list of dots and highlight the selected dot.
 * Use this when the number of page you're dealing with is not too high.
 */
class LineIndicatorController(context: Context) : IndicatorController, LinearLayout(context) {

    override var selectedIndicatorColor =
        ContextCompat.getColor(context, R.color.appintro_default_line_selected_color)
        set(value) {
            field = value
            selectPosition(currentPosition)
        }

    override var unselectedIndicatorColor =
        ContextCompat.getColor(context, R.color.appintro_default_line_selected_color)
        set(value) {
            field = value
            selectPosition(currentPosition)
        }
    private val selectedIndicatorDrawable =
        ContextCompat.getDrawable(this.context, R.drawable.ic_appintro_indicator_line_selected)
    private val unselectedIndicatorDrawable =
        ContextCompat.getDrawable(this.context, R.drawable.ic_appintro_indicator_line)
    private var currentPosition = 0
    private var slideCount = 0

    override fun newInstance(context: Context): View {
        val newLayoutParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.MATCH_PARENT
        )
        newLayoutParams.gravity = Gravity.CENTER_VERTICAL
        layoutParams = newLayoutParams
        orientation = HORIZONTAL
        gravity = CENTER
        return this
    }

    override fun initialize(slideCount: Int) {
        this.slideCount = slideCount
        for (i in 0 until slideCount) {
            val dot = ImageView(this.context)
            dot.setImageDrawable(
                ContextCompat.getDrawable(this.context, R.drawable.ic_appintro_indicator_line)
            )
            val params = LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            )
            params.leftMargin = resources.getDimensionPixelSize(R.dimen.appintro_indicator_inset)
            params.rightMargin = resources.getDimensionPixelSize(R.dimen.appintro_indicator_inset)
            params.topMargin = resources.getDimensionPixelSize(R.dimen.appintro_indicator_inset)
            params.bottomMargin = resources.getDimensionPixelSize(R.dimen.appintro_indicator_inset)
            if (slideCount == 1) {
                dot.visibility = View.INVISIBLE
            }
            this.addView(dot, params)
        }
        selectPosition(0)
    }

    override fun selectPosition(index: Int) {
        currentPosition = index
        for (i in 0 until slideCount) {
            val drawable = if (i == index) {
                selectedIndicatorDrawable
            } else {
                unselectedIndicatorDrawable
            }
            val tint = if (i == index) {
                selectedIndicatorColor
            } else {
                unselectedIndicatorColor
            }
            (getChildAt(i) as ImageView).apply {
                setImageDrawable(drawable)
                DrawableCompat.setTint(this.drawable, tint)
            }
        }
    }
}
