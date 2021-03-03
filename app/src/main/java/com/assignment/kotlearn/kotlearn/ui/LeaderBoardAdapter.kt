package com.assignment.kotlearn.kotlearn.ui


import android.content.Context
import android.support.annotation.ColorInt
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ArrayAdapter
import android.widget.ImageView
import com.assignment.kotlearn.kotlearn.QuizRecord
import com.assignment.kotlearn.kotlearn.R
import com.assignment.kotlearn.kotlearn.R.id.tvRank


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class LeaderBoardAdapter (context: Context, leaderboardRecords: List<QuizRecord>) : ArrayAdapter<QuizRecord>(context, 0, leaderboardRecords) {


    private class ViewHolder {
        var tvRank: TextView? = null
        var tvUsername: TextView? = null
        var tvScore: TextView? = null
        var tvTiming: TextView?=null
        var cvLeader: CardView?= null
        var ivRank: ImageView?=null
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val viewHolder: ViewHolder
        val leader = getItem(position)
        if (convertView == null) {
            viewHolder = ViewHolder()
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.fragment_leader_board_adapter, parent, false)
            viewHolder.tvRank = convertView.findViewById(R.id.tvRank) as TextView
            viewHolder.tvUsername = convertView.findViewById(R.id.tvUsername) as TextView
            viewHolder.tvScore = convertView.findViewById(R.id.tvScore) as TextView
            viewHolder.tvTiming = convertView.findViewById(R.id.tvTiming) as TextView
            viewHolder.cvLeader = convertView.findViewById(R.id.cvLeader) as CardView
            viewHolder.ivRank = convertView.findViewById(R.id.ivRank) as ImageView
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }
        // Populate the data into the template view using the data object
        val typedValue = TypedValue()
        val theme = context!!.getTheme()
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)
        @ColorInt val primaryColor = typedValue.data
        if(position+1===1) {
            //rank first
            viewHolder.ivRank!!.setImageResource(R.drawable.gold_medal)
//            viewHolder.cvLeader!!.setCardBackgroundColor(primaryColor)
        }
        else if(position+1===2)
        {
            //rank 2
            viewHolder.ivRank!!.setImageResource(R.drawable.silver_medal)
        }
        else if(position+1===3){
            //rank 3
            viewHolder.ivRank!!.setImageResource(R.drawable.bronze_medal)
        }
        else{
            //all other ranks
            viewHolder.ivRank!!.setImageResource(android.R.color.transparent)
        }
        viewHolder.tvRank!!.text = (position+1).toString()
        viewHolder.tvUsername!!.text = leader!!.username
        viewHolder.tvScore!!.text = leader.score
        viewHolder.tvTiming!!.text= "(" + leader.minutes + ":" + String.format("%02d", leader.seconds.toInt()) + ":" +
                String.format("%03d", leader.milliseconds.toInt())+")"
        //Picasso.with(context).load(Uri.parse(book!!.getCoverUrl())).error(R.drawable.ic_nocover).into(viewHolder.ivCover)
        // Return the completed view to render on screen
        return convertView!!
    }


}