package com.guru2_6.studydiaryapplication

import android.content.Context
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.graphics.Color
import android.widget.TextView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.guru2_6.studydiaryapplication.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class GraphFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("life_cycle", "F onCreateView")
        // fragment가 인터페이스를 처음 그릴때 호출된다
        // inflater -> 뷰를 그려주는 역할
        // container -> 부모 뷰
        super.onCreate(savedInstanceState)

        return inflater.inflate(R.layout.activity_graph, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("life_cycle", "F onViewCreated")
        super.onViewCreated(view, savedInstanceState)

        val linechart: LineChart = requireView()!!.findViewById(R.id.Linechart) //getView
        val entries: ArrayList<Entry> = ArrayList()
        val stickylabel: TextView = requireView()!!.findViewById(R.id.stickyLabel)

        entries.add(Entry(1f, 0F))
        entries.add(Entry(2f, 2f))
        entries.add(Entry(3f, 4f))
        entries.add(Entry(4f, 6f))
        entries.add(Entry(5f, 3f))

        val dataSet: LineDataSet = LineDataSet(entries, "날짜")
        dataSet.lineWidth = 2f
        dataSet.circleRadius = 4f
        dataSet.setCircleColor(Color.parseColor("#ffffff"))
        dataSet.circleHoleColor = Color.BLUE
        dataSet.setDrawCircleHole(true)
        dataSet.setDrawCircles(true)
        dataSet.setDrawFilled(true)
        dataSet.fillColor = Color.WHITE

        /*
        if (Utils.getSDKInt() >= 18) {
            val drawable = ContextCompat.getDrawable(this, R.drawable.fade_red)
            dataSet.fillDrawable = drawable
        } else {
            dataSet.fillColor = Color.BLACK
        }*/
        //dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        dataSet.setDrawHorizontalHighlightIndicator(false)
        dataSet.setDrawHighlightIndicators(false)
        dataSet.setDrawValues(false)

        val lineData: LineData = LineData(dataSet)
        linechart.data = lineData

        val xAxis: XAxis = linechart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textColor = Color.BLACK
        xAxis.textSize = 11f
        xAxis.granularity = 1f
        xAxis.valueFormatter = StickyDateAxisValueFormatter(chart = linechart, sticky = stickylabel)

        xAxis.enableGridDashedLine(8f, 24f, 0f)

        val yLAxis: YAxis = linechart.axisLeft
        yLAxis.textColor = Color.BLACK
        // yLAxis.setAxisMinValue(0.3f)
        yLAxis.valueFormatter = TimeAxisValueFormatter()

        val yRAxis: YAxis = linechart.axisRight
        yRAxis.setDrawLabels(false)
        yRAxis.setDrawAxisLine(false)
        yRAxis.setDrawGridLines(false)

        val description: Description = Description()
        description.text = ""

        linechart.isDoubleTapToZoomEnabled = false
        // 뒷배경에 선 그리기
        linechart.setDrawGridBackground(false)
        linechart.description = description
        linechart.animateY(2000, Easing.EaseInCubic)
        linechart.invalidate()
        linechart.xAxis.yOffset = 15f
        linechart.axisLeft.xOffset = 15f

        val marker: ExampleMarker =
            ExampleMarker(requireActivity().applicationContext, R.layout.activity_markerview) // activity!!
        marker.chartView = linechart
        linechart.marker = marker

    }

    class ExampleMarker : MarkerView {
        lateinit var tvContent: TextView

        constructor (context: Context?, layoutResource: Int) : super(context, layoutResource) {
            tvContent = findViewById(R.id.tvContent)
        }

        override fun draw(canvas: Canvas?) {
            canvas!!.translate(-(width / 2).toFloat(), -height.toFloat())
            super.draw(canvas)
        }

        override fun refreshContent(e: Entry?, highlight: Highlight?) {
            tvContent.text = e?.y.toString()
            super.refreshContent(e, highlight)
        }

    }

    class StickyDateAxisValueFormatter : ValueFormatter {
        private var c: GregorianCalendar
        private var chart: LineChart
        private var sticky: TextView
        private var lastFormattedValue = 1e9f
        private var lastMonth = 0
        private var lastYear = 0
        private var stickyMonth = -1
        private var stickyYear = -1
        private val mFormat =
            SimpleDateFormat("dd MMM", Locale.ENGLISH)

        constructor(chart: LineChart, sticky: TextView) {
            c = GregorianCalendar()
            this.chart = chart
            this.sticky = sticky
        }

        override fun getFormattedValue(value: Float): String? {
            if (value < chart.lowestVisibleX) {
                return ""
            }

            val days: Float = value
            val isFirstValue: Boolean = value < lastFormattedValue

            if (isFirstValue) {
                lastMonth = 50
                lastYear = 5000

                c.set(2020, 8, 10)
                c.add(GregorianCalendar.DATE, chart.lowestVisibleX.toInt())

                stickyMonth = c.get(GregorianCalendar.MONTH)
                stickyYear = c.get(GregorianCalendar.YEAR)

                val stickyText: String = mFormat.format(c.time)
                sticky.setText(stickyText)
            }

            c.set(2020, 8, 10)
            c.add(GregorianCalendar.DATE, days.toInt())
            val d: Date = c.time

            val dayOfMonth: Int = c.get(GregorianCalendar.DAY_OF_MONTH)
            val month: Int = c.get(GregorianCalendar.MONTH)
            val year: Int = c.get(GregorianCalendar.YEAR)

            val monthString: String = mFormat.format(d)

            if ((month > stickyMonth || year > stickyYear) && !isFirstValue) {
                stickyMonth = month
                stickyYear = year
                val stickyText: String = monthString + "\n" + year
                sticky.setText(stickyText)
            }

            val ret: String

            if ((month > lastMonth || year > lastYear) && isFirstValue) {
                ret = monthString
            } else {
                ret = dayOfMonth.toString();
            }

            lastMonth = month
            lastYear = year
            lastFormattedValue = value

            return month.toString() + "월" + ret + "일"
        }
    }

    class TimeAxisValueFormatter : ValueFormatter() {
        var currentDateTimeString: String = DateFormat.getDateTimeInstance().format(Date())
        //val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        //var Strnow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        // var datenow = LocalDate.parse(Strnow, DateTimeFormatter.ISO_DATE)
        private val tFormat = SimpleDateFormat("H시간", Locale.ENGLISH)


        override fun getFormattedValue(value: Float): String? {
            // val currentDate = sdf.format(Date())
            // return currentDate
            val millis = TimeUnit.HOURS.toMillis(value.toLong())
            val rTime = millis + 1
            return tFormat.format(Date(rTime))
        }
    }


}