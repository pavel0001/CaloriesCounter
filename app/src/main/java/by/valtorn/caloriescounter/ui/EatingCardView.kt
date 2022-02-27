package by.valtorn.caloriescounter.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import by.valtorn.caloriescounter.R
import by.valtorn.caloriescounter.databinding.ViewEatingCardBinding
import by.valtorn.caloriescounter.utils.date.stringForCalories
import by.valtorn.caloriescounter.utils.orZero
import org.joda.time.DateTime

class EatingCardView(
    context: Context,
    attrs: AttributeSet? = null,
) : ConstraintLayout(context, attrs) {

    private val binding = ViewEatingCardBinding.inflate(LayoutInflater.from(context), this, false).also {
        addView(it.root)
    }

    fun setupEatingView(time: DateTime?, calories: Int?, eatingName: String, clickListener: () -> (Unit)) {
        with(binding) {
            vecTime.text = time?.stringForCalories()
            vecCalories.text = calories.orZero().toString()
            vecEatingName.text = eatingName
            vecRoot.setOnClickListener {
                clickListener.invoke()
            }
        }
    }

    fun setSelectedState(isItSelected: Boolean) {
        with(binding) {
            root.background = if (isItSelected) {
                AppCompatResources.getDrawable(context, R.drawable.bg_shadow)
            } else {
                AppCompatResources.getDrawable(context, R.drawable.bg_stroke)
            }
        }
    }
}