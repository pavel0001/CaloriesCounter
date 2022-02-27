package by.valtorn.caloriescounter.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import by.valtorn.caloriescounter.databinding.ViewPointIconBinding

class PointIcon(
    context: Context,
    attrs: AttributeSet? = null,
) : LinearLayout(context, attrs) {

    private val binding = ViewPointIconBinding.inflate(LayoutInflater.from(context), this, false).also {
        addView(it.root)
    }

    fun setValue(value: Int) {
        with(binding) {
            vpiText.text = value.toString()
        }
    }
}