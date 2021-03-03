package com.assignment.kotlearn.kotlearn.ui

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import java.util.*
import android.util.Log
import android.widget.TextView
import com.assignment.kotlearn.kotlearn.*
import com.assignment.kotlearn.kotlearn.R.id.textView
import com.assignment.kotlearn.kotlearn.R.id.tvQuestion
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import kotlin.collections.ArrayList
import kotlinx.android.synthetic.main.fragment_question_display.*


class DailyQuizQuestions : AppCompatActivity() {
    val REQUEST_CODE : Int = 1
    var currentQuestionNumber:Int=0
    lateinit var questionsList:ArrayList<QuestionRecord>
    var score=0
    lateinit var quizDBHelper: QuizDBHelper
    lateinit var sharedPreferences: SharedPreferences
    private var myPreferences = "myPrefs"
    private var ISLOGGEDIN = "isloggedin"
    private var USERNAME = "username"
    private var EMPTY = "";
    private var insertRecordTask: InsertRecordTask? = null
    private var CHAPTER_1_SCORE = "chapter1score"
    private var CHAPTER_2_SCORE = "chapter2score"
    private var CHAPTER_3_SCORE = "chapter3score"
    private var CHAPTER_4_SCORE = "chapter4score"
    private var CHAPTER_5_SCORE = "chapter5score"
    private var CHAPTER_6_SCORE = "chapter6score"
    private var CHAPTER_1_LEADERS = "chapter1leaders"
    private var CHAPTER_2_LEADERS = "chapter2leaders"
    private var CHAPTER_3_LEADERS = "chapter3leaders"
    private var CHAPTER_4_LEADERS = "chapter4leaders"
    private var CHAPTER_5_LEADERS = "chapter5leaders"
    private var CHAPTER_6_LEADERS = "chapter6leaders"
    var Seconds: Int = 0
    var Minutes:Int = 0
    var MilliSeconds:Int = 0
    var MillisecondTime: Long = 0
    var StartTime:Long = 0
    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.daily_quiz_questions_activity)
        supportActionBar?.hide()

        handler = Handler()

        sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE)

        quizDBHelper = QuizDBHelper(this)

        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        val questionDisplay = QuestionDisplay()

        questionsList= intent.getSerializableExtra("Questions") as ArrayList<QuestionRecord>
        var chapterText= intent.getSerializableExtra("ChapterText")
        var chapterID= intent.getSerializableExtra("ChapterID")

        val bundle = Bundle()
        var currentQuestion= questionsList[currentQuestionNumber]
        bundle.putString("question", currentQuestion.question)
        bundle.putString("chapter", "Chapter "+chapterID+" - "+ chapterText)
        bundle.putStringArray("choices", currentQuestion.choices)
        bundle.putString ("answer", currentQuestion.answer)
        bundle.putInt ("progress", currentQuestionNumber)
        bundle.putInt("totalQuestion",questionsList.size)


        //currentQuestion.type --> display correct type of fragment depending on question
        questionDisplay.arguments=bundle
        transaction.replace(R.id.flDisplayQuestion, questionDisplay)

        transaction.commit()
        StartTime = SystemClock.uptimeMillis()
        handler.postDelayed(runnable,0)

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((requestCode == REQUEST_CODE) && (resultCode == Activity.RESULT_OK)) {
            if (data!!.hasExtra("message")) {
                val returnedMessage = data!!.extras.getString("message")
                Toast.makeText(this, "the returned message from ActivityIntent4 is " +
                        returnedMessage, Toast.LENGTH_LONG).show()
            }
        }
    }
    fun passBackResult(result:Boolean){
        currentQuestionNumber++
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        //tabulate score
        if(result){
            score++
        }

        if(currentQuestionNumber<questionsList.size) {

            //display questionDisplay fragment
            val questionDisplay = QuestionDisplay()
            val bundle = Bundle()
            var currentQuestion = questionsList[currentQuestionNumber]
            bundle.putString("question", currentQuestion.question)
            bundle.putStringArray("choices", currentQuestion.choices)
            bundle.putString("answer", currentQuestion.answer)
            bundle.putInt("progress", currentQuestionNumber)
            bundle.putInt("totalQuestion", questionsList.size)

            var chapterText= intent.getSerializableExtra("ChapterText")
            var chapterID= intent.getSerializableExtra("ChapterID")
            bundle.putString("chapter", "Chapter "+chapterID+" - "+ chapterText)

            //currentQuestion.type --> display correct type of fragment depending on question
            questionDisplay.arguments = bundle
            transaction.replace(R.id.flDisplayQuestion, questionDisplay)


        }else{
            /*//display result
            val quizResult= QuizResult()
            val bundle = Bundle()
            //must update the score in database ***
            bundle.putInt("score",score)
            quizResult.arguments=bundle
*/
            handler.removeCallbacks(runnable)
            Log.e("Time", ("" + Minutes + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds)))
            var username = ""
            if(sharedPreferences.getString(USERNAME, EMPTY) != EMPTY){
                username = sharedPreferences.getString(USERNAME, EMPTY)
            }

            insertRecordTask = InsertRecordTask(username,intent.getIntExtra("ChapterID", 0).toString()
                    ,score.toString(),Minutes,Seconds,MilliSeconds,this)
            insertRecordTask!!.execute(null as Void?)

//            quizDBHelper.insertQuizRecord(username,intent.getIntExtra("ChapterID", 0).toString(),score.toString())
        }
        Handler(Looper.getMainLooper()).postDelayed({
            transaction.commit()
        }, 200)

    }

    var runnable: Runnable = object : Runnable {

        override fun run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime

            Seconds = (MillisecondTime / 1000).toInt()

            Minutes = Seconds / 60

            Seconds = Seconds % 60

            MilliSeconds = (MillisecondTime % 1000).toInt()

            handler.postDelayed(this, 0)
        }

    }

    inner class InsertRecordTask internal constructor(private val mUsername: String, private val mChapterID: String,
                                                      private val mScore: String,private val mMinutes:Int,private val mSeconds: Int,
                                                      private val mMilliseconds: Int,
                                                      private val context:Context) : AsyncTask<Void, Void, Boolean>() {

        override fun doInBackground(vararg params: Void): Boolean? {

            try {

                var result: Boolean = quizDBHelper.insertQuizRecord(mUsername,mChapterID,mScore,mMinutes,mSeconds,mMilliseconds)

                return result

            } catch (e: InterruptedException) {
                return false
            }
        }

        override fun onPostExecute(success: Boolean?) {

            val quizResult= QuizResult()
            val bundle = Bundle()
            bundle.putInt("score",score)
            bundle.putString("time",("" + Minutes + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds)))
            quizResult.arguments=bundle
            val fragmentManager = supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.flDisplayQuestion, quizResult)
            transaction.commit()

            if(success!!){
                val editor = sharedPreferences.edit()
                Toast.makeText(context, "Congratulations, you have broken your old record.", Toast.LENGTH_SHORT).show()

                var getQuizLeaderTask:GetQuizLeaderTask

                if(mChapterID == "1"){
                    editor.putString(CHAPTER_1_SCORE, score.toString())
                    getQuizLeaderTask = GetQuizLeaderTask("1")
                    getQuizLeaderTask!!.execute(null as Void?)
                    getQuizLeaderTask = GetQuizLeaderTask("2")
                    getQuizLeaderTask!!.execute(null as Void?)
                    getQuizLeaderTask = GetQuizLeaderTask("3")
                    getQuizLeaderTask!!.execute(null as Void?)
                    getQuizLeaderTask = GetQuizLeaderTask("4")
                    getQuizLeaderTask!!.execute(null as Void?)
                    getQuizLeaderTask = GetQuizLeaderTask("5")
                    getQuizLeaderTask!!.execute(null as Void?)
                    getQuizLeaderTask = GetQuizLeaderTask("6")
                    getQuizLeaderTask!!.execute(null as Void?)
                } else if(mChapterID == "2"){
                    editor.putString(CHAPTER_2_SCORE, score.toString())
                } else if(mChapterID == "3"){
                    editor.putString(CHAPTER_3_SCORE, score.toString())
                } else if(mChapterID == "4"){
                    editor.putString(CHAPTER_4_SCORE, score.toString())
                } else if(mChapterID == "5"){
                    editor.putString(CHAPTER_5_SCORE, score.toString())
                } else if(mChapterID == "6"){
                    editor.putString(CHAPTER_6_SCORE, score.toString())
                }
                editor.apply()

            } else{
                Toast.makeText(context, "Don't be discouraged, try again!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    inner class GetQuizLeaderTask internal constructor(private val mChapterID: String) : AsyncTask<Void, Void, Boolean>() {

        override fun doInBackground(vararg params: Void): Boolean? {

            val editor = sharedPreferences.edit()

            try {
                var leaderBoardRecords:ArrayList<QuizRecord> = ArrayList()
                val gson = Gson()
                var result: QuerySnapshot = quizDBHelper.getQuizLeaders(mChapterID)
                for(documents in result){
                    val leader= QuizRecord(mChapterID, documents.get("username").toString(),
                            documents.get("score").toString(), documents.get("minutes").toString(), documents.get("seconds").toString(),
                            documents.get("milliseconds").toString())
                    leaderBoardRecords.add(leader)

                }
                if(mChapterID == "1") {
                    editor.putString(CHAPTER_1_LEADERS, gson.toJson(leaderBoardRecords))
                } else if(mChapterID == "2") {
                    editor.putString(CHAPTER_2_LEADERS, gson.toJson(leaderBoardRecords))
                } else if(mChapterID == "3") {
                    editor.putString(CHAPTER_3_LEADERS, gson.toJson(leaderBoardRecords))
                } else if(mChapterID == "4") {
                    editor.putString(CHAPTER_4_LEADERS, gson.toJson(leaderBoardRecords))
                } else if(mChapterID == "5") {
                    editor.putString(CHAPTER_5_LEADERS, gson.toJson(leaderBoardRecords))
                } else if(mChapterID == "6") {
                    editor.putString(CHAPTER_6_LEADERS, gson.toJson(leaderBoardRecords))
                }
                editor.apply()
                return true

            } catch (e: InterruptedException) {
                return false
            }
        }

        override fun onPostExecute(success: Boolean?) {

            if(success!!){
            } else{
                //Toast.makeText(context, "Don't be discouraged, try again!", Toast.LENGTH_SHORT).show()
            }

        }
    }

}
