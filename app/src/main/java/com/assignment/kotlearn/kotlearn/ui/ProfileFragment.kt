package com.assignment.kotlearn.kotlearn.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.assignment.kotlearn.kotlearn.R
import com.assignment.kotlearn.kotlearn.ui.LeaderBoardFragmentPagerAdapter
import kotlinx.android.synthetic.main.fragment_leader_boards.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import android.graphics.Color.parseColor
import org.eazegraph.lib.models.PieModel
import org.eazegraph.lib.charts.PieChart


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ProfileFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var sharedPreferences: SharedPreferences
    private var myPreferences = "myPrefs"
    private var ISLOGGEDIN = "isloggedin"
    private var USERNAME = "username"
    private var EMPTY = "";
    private var CHAPTER_1_SCORE = "chapter1score"
    private var CHAPTER_2_SCORE = "chapter2score"
    private var CHAPTER_3_SCORE = "chapter3score"
    private var CHAPTER_4_SCORE = "chapter4score"
    private var CHAPTER_5_SCORE = "chapter5score"
    private var CHAPTER_6_SCORE = "chapter6score"
    private var FRIENDS = "friends"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        sharedPreferences = this.activity!!.getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
        val usernameView = view.findViewById<TextView>(R.id.textView_username)
        usernameView.setText(sharedPreferences.getString(USERNAME, EMPTY))

        var attempted = 0f
        var not_attempted = 0f
        var perfect = 0f
        if (sharedPreferences.getString(CHAPTER_1_SCORE, EMPTY) != EMPTY)
        {
            if (sharedPreferences.getString(CHAPTER_1_SCORE, EMPTY).toInt()===5){
                        perfect+=1f
                    }
            else attempted+=1f
        }
        else not_attempted+=1f
        if (sharedPreferences.getString(CHAPTER_2_SCORE, EMPTY) != EMPTY)
        {
            if (sharedPreferences.getString(CHAPTER_2_SCORE, EMPTY).toInt()===5){
                perfect+=1f
            }
            else attempted+=1f
        }
        else not_attempted+=1f
        if (sharedPreferences.getString(CHAPTER_3_SCORE, EMPTY) != EMPTY)
        {
            if (sharedPreferences.getString(CHAPTER_3_SCORE, EMPTY).toInt()===5){
                perfect+=1f
            }
            else attempted+=1f
        }
        else not_attempted+=1f
        if (sharedPreferences.getString(CHAPTER_4_SCORE, EMPTY) != EMPTY)
        {
            if (sharedPreferences.getString(CHAPTER_4_SCORE, EMPTY).toInt()===5){
                perfect+=1f
            }
            else attempted+=1f
        }
        else not_attempted+=1f
        if (sharedPreferences.getString(CHAPTER_5_SCORE, EMPTY) != EMPTY)
        {
            if (sharedPreferences.getString(CHAPTER_5_SCORE, EMPTY).toInt()===5){
                perfect+=1f
            }
            else attempted+=1f
        }
        else not_attempted+=1f
        if (sharedPreferences.getString(CHAPTER_6_SCORE, EMPTY) != EMPTY)
        {
            if (sharedPreferences.getString(CHAPTER_6_SCORE, EMPTY).toInt()===5){
                perfect+=1f
            }
            else attempted+=1f
        }
        else not_attempted+=1f
        val mPieChart = view.findViewById(R.id.piechart) as PieChart

        mPieChart.addPieSlice(PieModel("Number Quizzes Attempted", attempted, Color.parseColor("#FE6DA8")))
        mPieChart.addPieSlice(PieModel("Number Quizzes Not Attempted", not_attempted, Color.parseColor("#56B7F1")))
        mPieChart.addPieSlice(PieModel("Number Quizzes With Perfect Score", perfect, Color.parseColor("#FED70E")))

        mPieChart.startAnimation()
        val editor = sharedPreferences.edit()
        val logoutBtn = view.findViewById<Button>(R.id.button_logout)
        logoutBtn.setOnClickListener {
            editor.putString(ISLOGGEDIN, "false")
            editor.putString(CHAPTER_1_SCORE, "")
            editor.putString(CHAPTER_2_SCORE, "")
            editor.putString(CHAPTER_3_SCORE, "")
            editor.putString(CHAPTER_4_SCORE, "")
            editor.putString(CHAPTER_5_SCORE, "")
            editor.putString(CHAPTER_6_SCORE, "")
            editor.putString(FRIENDS, "")
            editor.apply()
            activity!!.finish()
        }

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        //val mViewPager = view.viewpager as MaterialViewPager

//        val fragmentManager1 = getChildFragmentManager()
//        val viewPager = view.viewpager2 as ViewPager
//        viewPager.adapter = LeaderBoardFragmentPagerAdapter(fragmentManager1, activity!!)
//
//         //Give the TabLayout the ViewPager
//        val tabLayout = view.sliding_tabs2 as TabLayout
//        tabLayout.setupWithViewPager(viewPager)

        // mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter()!!.getCount())
        // mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager())

//        val logo = findViewById(R.id.logo_white)
//        if (logo != null) {
//            logo!!.setOnClickListener(View.OnClickListener {
//                mViewPager.notifyHeaderChanged()
//                Toast.makeText(getApplicationContext(), "Yes, the title is clickable", Toast.LENGTH_SHORT).show()
//            })
//        }

        return view
    }


}
