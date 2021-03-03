package com.assignment.kotlearn.kotlearn.ui

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_daily_quiz.view.*
import android.view.Gravity
import android.util.TypedValue
import com.assignment.kotlearn.kotlearn.R
import com.tmall.ultraviewpager.UltraViewPager
import com.tmall.ultraviewpager.transformer.UltraDepthScaleTransformer
import android.content.Intent.getIntent
import android.support.v4.app.NotificationCompat.getExtras
import android.support.annotation.ColorInt
import android.content.res.Resources.Theme






// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class DailyQuiz() : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_daily_quiz, container, false)

        val fragmentManager1 = childFragmentManager

        val ultraViewPager = view.ultra_viewpager as UltraViewPager
        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL)

        ultraViewPager.adapter = DailyQuizFragmentPagerAdapter(true)

        ultraViewPager.setMultiScreen(0.73f)
        ultraViewPager.setItemRatio(1.0)
        ultraViewPager.setPageTransformer(false, UltraDepthScaleTransformer())

        ultraViewPager.initIndicator()
        val typedValue = TypedValue()
        val theme = context!!.getTheme()
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)
        @ColorInt val primaryColor = typedValue.data
        //set style of indicators
        ultraViewPager.indicator
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(primaryColor)
                .setNormalColor(Color.LTGRAY)
                .setRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, resources.displayMetrics).toInt())
        //set the alignment
        ultraViewPager.indicator.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM)
        //construct built-in indicator, and add it to  UltraViewPager
        ultraViewPager.indicator.build()
        //set an infinite loop
        //ultraViewPager.setInfiniteLoop(true)
        //enable auto-scroll mode
        // ultraViewPager.setAutoScroll(2000)
        ultraViewPager.refresh()

        return view
    }


}
