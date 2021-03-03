package com.assignment.kotlearn.kotlearn.ui

import android.content.Context
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.assignment.kotlearn.kotlearn.ui.LeaderBoardFragment


class LeaderBoardFragmentPagerAdapter(fm: FragmentManager, private val context: Context) : FragmentPagerAdapter(fm) {
    internal val PAGE_COUNT = 6
    private val tabTitles = arrayOf("Chapt 1", "Chapt 2", "Chapt 3", "Chapt 4", "Chapt 5", "Chapt 6")

    //    override fun getCount(): Int {
//        return PAGE_COUNT
//    }
//
//    override fun getItem(position: Int): Fragment {
//        return LeaderBoardFragment().newInstance(position + 1)
//    }
//
//    override fun getPageTitle(position: Int): CharSequence? {
//        // Generate title based on item position
//        return tabTitles[position]
//    }
    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }
    override fun getCount(): Int {
        return PAGE_COUNT
    }

    override fun getItem(position: Int): Fragment {
        return LeaderBoardFragment().newInstance(position + 1)
    }
}



