package com.assignment.kotlearn.kotlearn.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_leader_board.view.*
import android.widget.ListView
import com.assignment.kotlearn.kotlearn.QuizDBHelper
import com.assignment.kotlearn.kotlearn.QuizRecord
import com.assignment.kotlearn.kotlearn.R
import com.google.firebase.firestore.QuerySnapshot
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_bottom_nav1.*
import java.lang.reflect.Type


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [LeaderBoardFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 *
 */
class LeaderBoardFragment : Fragment() {

    val ARG_PAGE = "ARG_PAGE"
    lateinit var sharedPreferences: SharedPreferences
    private var myPreferences = "myPrefs"
    private var CHAPTER_1_LEADERS = "chapter1leaders"
    private var CHAPTER_2_LEADERS = "chapter2leaders"
    private var CHAPTER_3_LEADERS = "chapter3leaders"
    private var CHAPTER_4_LEADERS = "chapter4leaders"
    private var CHAPTER_5_LEADERS = "chapter5leaders"
    private var CHAPTER_6_LEADERS = "chapter6leaders"
    private var EMPTY = "";
    lateinit var leaderBoardRecords:ArrayList<QuizRecord>

    lateinit var quizDBHelper: QuizDBHelper


    private var mPage: Int = 0

    fun newInstance(page: Int): LeaderBoardFragment {
        mPage = page
        val args = Bundle()
        args.putInt(ARG_PAGE, page)
        val fragment = LeaderBoardFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPage = arguments!!.getInt(ARG_PAGE)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_leader_board, container, false)
        //val textView = view.tv1
        //textView.text = "Fragment #$mPage"
        sharedPreferences = this.activity!!.getSharedPreferences(myPreferences, Context.MODE_PRIVATE)

        val lvLeaders = view.lvLeaders as ListView
        val gson = Gson()
        leaderBoardRecords = ArrayList()
        if(mPage == 1){
            if (sharedPreferences.getString(CHAPTER_1_LEADERS, EMPTY) != EMPTY) {
                leaderBoardRecords = gson.fromJson(sharedPreferences.getString(CHAPTER_1_LEADERS, EMPTY), object : TypeToken<ArrayList<QuizRecord>>() {}.type)
            }
        }else if(mPage == 2){
            if (sharedPreferences.getString(CHAPTER_2_LEADERS, EMPTY) != EMPTY) {
            leaderBoardRecords = gson.fromJson(sharedPreferences.getString(CHAPTER_2_LEADERS, EMPTY), object : TypeToken<ArrayList<QuizRecord>>() {}.type)
        }
        }else if(mPage == 3){
            if (sharedPreferences.getString(CHAPTER_3_LEADERS, EMPTY) != EMPTY) {
                leaderBoardRecords = gson.fromJson(sharedPreferences.getString(CHAPTER_3_LEADERS, EMPTY), object : TypeToken<ArrayList<QuizRecord>>() {}.type)
            }
        }else if(mPage == 4){
            if (sharedPreferences.getString(CHAPTER_4_LEADERS, EMPTY) != EMPTY) {
                leaderBoardRecords = gson.fromJson(sharedPreferences.getString(CHAPTER_4_LEADERS, EMPTY), object : TypeToken<ArrayList<QuizRecord>>() {}.type)
            }
        }else if(mPage == 5){
            if (sharedPreferences.getString(CHAPTER_5_LEADERS, EMPTY) != EMPTY) {
                leaderBoardRecords = gson.fromJson(sharedPreferences.getString(CHAPTER_5_LEADERS, EMPTY), object : TypeToken<ArrayList<QuizRecord>>() {}.type)
            }
        }else if(mPage == 6){
            if (sharedPreferences.getString(CHAPTER_6_LEADERS, EMPTY) != EMPTY) {
                leaderBoardRecords = gson.fromJson(sharedPreferences.getString(CHAPTER_6_LEADERS, EMPTY), object : TypeToken<ArrayList<QuizRecord>>() {}.type)
            }


        }
        val leaderBoardAdapter = LeaderBoardAdapter(context!!, leaderBoardRecords)
        lvLeaders.adapter = leaderBoardAdapter

        return view
    }


}
