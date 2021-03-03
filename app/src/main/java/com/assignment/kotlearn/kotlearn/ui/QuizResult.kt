package com.assignment.kotlearn.kotlearn.ui


import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.assignment.kotlearn.kotlearn.QuizDBHelper
import com.assignment.kotlearn.kotlearn.R
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size
import kotlinx.android.synthetic.main.fragment_quiz_result.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class QuizResult : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view= inflater.inflate(R.layout.fragment_quiz_result, container, false)

        view.viewKonfetti.build()
                .addColors(Color.YELLOW, Color.CYAN, Color.MAGENTA)
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addShapes(Shape.RECT, Shape.CIRCLE)
                .addSizes(Size(12))
                .setPosition(50f, view.viewKonfetti.width + 50f, -50f, -50f)
                .streamFor(200, 3000L)

        val bundle = arguments
        val score = bundle!!.getInt("score")
        val time = bundle!!.getString("time")
        if(score>1) {
            view.tvResult.text = "Great job!!"
            view.ivResultIcon.setImageResource(R.drawable.happy)
        }
        else{
            view.tvResult.text = "Good job!\nBut you can do better!"
            view.ivResultIcon.setImageResource(R.drawable.reading)
        }
        view.tvResult2.text="You scored $score with a timing of $time!"
        view.btnFinish.setOnClickListener {

            activity!!.setResult(10001)
            activity!!.finish()
        }
        return view
    }


}
