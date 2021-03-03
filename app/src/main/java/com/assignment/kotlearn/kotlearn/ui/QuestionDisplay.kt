package com.assignment.kotlearn.kotlearn.ui

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.assignment.kotlearn.kotlearn.R
import com.assignment.kotlearn.kotlearn.ui.DailyQuizQuestions
import kotlinx.android.synthetic.main.fragment_question_display.view.*
import android.text.method.Touch.onTouchEvent
import android.view.MotionEvent
import android.widget.LinearLayout
import android.view.View.OnTouchListener




// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [QuestionDisplay.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [QuestionDisplay.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class QuestionDisplay : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_question_display, container, false)

        val bundle = arguments
        val question = bundle!!.getString("question")
        val choices = bundle.getStringArray("choices")
        val answer = bundle.getString("answer")
        val progress = bundle.getInt("progress")
        val totalQuestions = bundle.getInt("totalQuestion")
        val chapter = bundle.getString("chapter")

        view.currentChapter.text=chapter
        //set progress bar accordingly
        view.progressBar.max=totalQuestions
        view.progressBar.progress=progress
        view.tvDisplayCurrentQ.text= "Question "+(progress+1)+"/$totalQuestions"
        //get the questions for this quiz
        view.tvQuestion.text=question
        //set choices
        view.btnAnswer1.text=choices[0]
        view.btnAnswer2.text=choices[1]
        view.btnAnswer3.text=choices[2]
        view.btnAnswer4.text=choices[3]


        view.btnAnswer1.setOnClickListener{
            view.btnAnswer1.isClickable=false
            view.btnAnswer2.isClickable=false
            view.btnAnswer3.isClickable=false
            view.btnAnswer4.isClickable=false
            checkAnswer(view.btnAnswer1,answer)
        }
        view.btnAnswer2.setOnClickListener{
            view.btnAnswer1.isClickable=false
            view.btnAnswer2.isClickable=false
            view.btnAnswer3.isClickable=false
            view.btnAnswer4.isClickable=false
            checkAnswer(view.btnAnswer2,answer)}
        view.btnAnswer3.setOnClickListener {
            view.btnAnswer1.isClickable=false
            view.btnAnswer2.isClickable=false
            view.btnAnswer3.isClickable=false
            view.btnAnswer4.isClickable=false
            checkAnswer(view.btnAnswer3, answer)
        }
        view.btnAnswer4.setOnClickListener {
            view.btnAnswer1.isClickable=false
            view.btnAnswer2.isClickable=false
            view.btnAnswer3.isClickable=false
            view.btnAnswer4.isClickable=false
            checkAnswer(view.btnAnswer4, answer)
        }

        return view
    }


    fun checkAnswer(btn: Button, answer: String){
        if(btn.text===answer){
            btn.setBackgroundResource(R.drawable.curved_button_correct)
            (activity as DailyQuizQuestions).passBackResult(true)
        }
        else{
            btn.setBackgroundResource(R.drawable.curved_button_wrong)
            (activity as DailyQuizQuestions).passBackResult(false)
        }

    }



}
