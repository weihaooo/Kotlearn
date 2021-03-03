package com.assignment.kotlearn.kotlearn.ui


import android.content.Context
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.assignment.kotlearn.kotlearn.QuizRecord

import com.assignment.kotlearn.kotlearn.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class FriendListAdapter(context: Context, friendListRecords: List<String>) : ArrayAdapter<String>(context, 0, friendListRecords) {
    private class ViewHolder {
        var tvFriendUsername: TextView? = null
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val viewHolder: ViewHolder
        val friendUsername = getItem(position)
        if (convertView == null) {
            viewHolder = ViewHolder()
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.fragment_friend_list_adapter, parent, false)
            viewHolder.tvFriendUsername = convertView.findViewById(R.id.tvFriendUsername) as TextView
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }

        viewHolder.tvFriendUsername!!.text = friendUsername

        return convertView!!
    }

}
