package com.assignment.kotlearn.kotlearn.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.assignment.kotlearn.kotlearn.R
import kotlinx.android.synthetic.main.fragment_leader_boards.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [LeaderBoardsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [LeaderBoardsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class LeaderBoardsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        val view = inflater.inflate(R.layout.fragment_leader_boards, container, false)

        val fragmentManager1 = getChildFragmentManager()
        val viewPager = view.viewpager1 as ViewPager
        viewPager.adapter = LeaderBoardFragmentPagerAdapter(fragmentManager1, activity!!)

        //Give the TabLayout the ViewPager
        val tabLayout = view.sliding_tabs as TabLayout
        tabLayout.setupWithViewPager(viewPager)


        return view
    }

}
