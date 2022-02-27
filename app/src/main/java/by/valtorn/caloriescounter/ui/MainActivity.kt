package by.valtorn.caloriescounter.ui

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import by.valtorn.caloriescounter.R
import by.valtorn.caloriescounter.database.calories.CaloriesDay
import by.valtorn.caloriescounter.databinding.ActivityMainBinding
import by.valtorn.caloriescounter.utils.positiveOrNul
import by.valtorn.caloriescounter.utils.viewBinding
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet


class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel by lazy { ViewModelProvider(this)[MainVM::class.java] }

    private val caloriesGoal = 1450
    private val burn = 300

    private val dataSetLabelBackground = "backgroundSet"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initUI()
        initVM()
    }

    private fun loadBitmapFromView(v: View): Bitmap? {
        v.measure(v.layoutParams.width, v.layoutParams.height)
        val b = Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)
        val c = Canvas(b)
        c.drawColor(Color.TRANSPARENT)
        v.layout(v.left, v.top, v.right, v.bottom)
        v.draw(c)
        return b
    }

    private fun initUI() {
        with(binding) {
            amChart.description.isEnabled = false
            amChart.xAxis.apply {
                isEnabled = false
                setDrawGridLines(false)
                axisMinimum = 0F
            }
            amChart.axisLeft.apply {
                isEnabled = false
                setDrawGridLines(false)
            }
            amChart.axisRight.apply {
                isEnabled = false
                setDrawGridLines(false)
            }
            amChart.legend.isEnabled = false
            amChart.setPinchZoom(false)
        }
    }

    private fun initVM() {
        with(binding) {
            viewModel.currentCaloriesDay.observe(this@MainActivity) {
                reDrawChart(it)
                amGoalValue.text = caloriesGoal.toString()
                amBurnValue.text = burn.toString()
                amEatingValue.text = it?.getEatingCalories().toString()
                amTotal.text = it?.getEatingCalories()?.minus(burn)?.positiveOrNul().toString()
                amEatingCardBreakfast.setupEatingView(time = it?.breakfastTime, calories = it?.breakfastCalories, eatingName = getString(R.string.main_breakfast)) {
                    viewModel.selectEating(MainVM.EatingType.BREAKFAST)
                    MaterialDialog(this@MainActivity).input(inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL, maxLength = 4) { _, charSequence ->
                        viewModel.setupBreakfast(charSequence.toString().toInt())
                    }.show {
                        message(text = getString(R.string.main_add))
                        positiveButton(R.string.common_ok)
                        negativeButton(R.string.common_cancel)
                    }
                }
                amEatingCardLunch.setupEatingView(time = it?.lunchTime, calories = it?.lunchCalories, eatingName = getString(R.string.main_lunch)) {
                    viewModel.selectEating(MainVM.EatingType.LUNCH)
                    MaterialDialog(this@MainActivity).input(inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL, maxLength = 4) { _, charSequence ->
                        viewModel.setupLunch(charSequence.toString().toInt())
                    }.show {
                        message(text = getString(R.string.main_add))
                        positiveButton(R.string.common_ok)
                        negativeButton(R.string.common_cancel)
                    }
                }
                amEatingCardDinner.setupEatingView(time = it?.dinnerTime, calories = it?.dinnerCalories, eatingName = getString(R.string.main_dinner)) {
                    viewModel.selectEating(MainVM.EatingType.DINNER)
                    MaterialDialog(this@MainActivity).input(inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL, maxLength = 4) { _, charSequence ->
                        viewModel.setupDinner(charSequence.toString().toInt())
                    }.show {
                        message(text = getString(R.string.main_add))
                        positiveButton(R.string.common_ok)
                        negativeButton(R.string.common_cancel)
                    }
                }
            }
            viewModel.selectedEating.observe(this@MainActivity) {
                amEatingCardBreakfast.setSelectedState(it == MainVM.EatingType.BREAKFAST)
                amEatingCardLunch.setSelectedState(it == MainVM.EatingType.LUNCH)
                amEatingCardDinner.setSelectedState(it == MainVM.EatingType.DINNER)
            }
        }
    }

    private fun reDrawChart(caloriesDay: CaloriesDay?) {
        with(binding) {
            val backgroundSetData = arrayListOf(
                Entry(0f, 0f),
                Entry(2f, 0f),
                Entry(4f, 0f),
                Entry(6f, 0f)
            )
            caloriesDay?.breakfastCalories?.let {
                if (it > 0) {
                    val sourceView = PointIcon(this@MainActivity).apply {
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT
                        )
                        setValue(it)
                    }
                    val bitmapFromView = loadBitmapFromView(sourceView)
                    backgroundSetData.add(Entry(1F, it.toFloat(), BitmapDrawable(resources, bitmapFromView)))
                }
            }
            caloriesDay?.lunchCalories?.let {
                if (it > 0) {
                    val sourceView = PointIcon(this@MainActivity).apply {
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT
                        )
                        setValue(it)
                    }
                    val bitmapFromView = loadBitmapFromView(sourceView)
                    backgroundSetData.add(Entry(3F, it.toFloat(), BitmapDrawable(resources, bitmapFromView)))
                }
            }
            caloriesDay?.dinnerCalories?.let {
                if (it > 0) {
                    val sourceView = PointIcon(this@MainActivity).apply {
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT
                        )
                        setValue(it)
                    }
                    val bitmapFromView = loadBitmapFromView(sourceView)
                    backgroundSetData.add(Entry(5F, it.toFloat(), BitmapDrawable(resources, bitmapFromView)))
                }
            }
            if (amChart.data != null &&
                amChart.data.dataSetCount > 0
            ) {
                val backgroundSet = amChart.data.getDataSetByLabel(dataSetLabelBackground, true) as LineDataSet
                backgroundSet.values = backgroundSetData.sortedBy { it.x }
                amChart.data.notifyDataChanged()
                amChart.notifyDataSetChanged()
                amChart.invalidate()
            } else {
                val backgroundSet = LineDataSet(backgroundSetData.toList().sortedBy { it.x }, dataSetLabelBackground)
                backgroundSet.mode = LineDataSet.Mode.CUBIC_BEZIER
                backgroundSet.cubicIntensity = 0.2f
                backgroundSet.lineWidth = 2f
                backgroundSet.circleRadius = 2f
                backgroundSet.setCircleColor(getColor(R.color.black))
                backgroundSet.highLightColor = Color.GREEN
                backgroundSet.color = getColor(R.color.waterspout)
                backgroundSet.fillColor = Color.WHITE
                backgroundSet.setDrawHorizontalHighlightIndicator(false)
                backgroundSet.setDrawValues(false)
                backgroundSet.setDrawCircles(false)

                val data = LineData()
                data.addDataSet(backgroundSet)
                data.setValueTextSize(12f)
                data.setValueTextColor(getColor(R.color.gray))
                amChart.data = data
                amChart.invalidate()
            }
        }
    }
}