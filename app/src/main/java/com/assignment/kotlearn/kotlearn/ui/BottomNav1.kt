package com.assignment.kotlearn.kotlearn.ui


import android.app.FragmentManager
import android.app.FragmentTransaction
import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_bottom_nav1.*
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.assignment.kotlearn.kotlearn.QuizDBHelper
import com.assignment.kotlearn.kotlearn.QuizRecord
import com.assignment.kotlearn.kotlearn.R
import com.assignment.kotlearn.kotlearn.R.id.container
import com.assignment.kotlearn.kotlearn.R.id.username
import com.assignment.kotlearn.kotlearn.UserDBHelper
import com.google.firebase.firestore.QuerySnapshot
import com.google.gson.Gson
import com.google.gson.JsonDeserializer
import com.google.gson.JsonSerializer


class BottomNav1 : AppCompatActivity() {

    lateinit var userDBHelper: UserDBHelper
    lateinit var quizDBHelper: QuizDBHelper
    private var getRecordTask: GetRecordTask? = null
    private var getQuizLeaderTask: GetQuizLeaderTask? = null
    lateinit var leaderBoardRecords: ArrayList<QuizRecord>
    lateinit var sharedPreferences: SharedPreferences
    private var myPreferences = "myPrefs"
    private var USERNAME = "username"
    private var FRIENDS = "friends"
    private var CHAPTER_1_LEADERS = "chapter1leaders"
    private var CHAPTER_2_LEADERS = "chapter2leaders"
    private var CHAPTER_3_LEADERS = "chapter3leaders"
    private var CHAPTER_4_LEADERS = "chapter4leaders"
    private var CHAPTER_5_LEADERS = "chapter5leaders"
    private var CHAPTER_6_LEADERS = "chapter6leaders"
    private var CHAPTER_1_SCORE = "chapter1score"
    private var CHAPTER_2_SCORE = "chapter2score"
    private var CHAPTER_3_SCORE = "chapter3score"
    private var CHAPTER_4_SCORE = "chapter4score"
    private var CHAPTER_5_SCORE = "chapter5score"
    private var CHAPTER_6_SCORE = "chapter6score"
    private var EMPTY = "";
    private var getFriendsTask: GetFriendsTask? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
//            R.id.navigation_home -> {
//                val fragmentManager = supportFragmentManager
//                val transaction = fragmentManager.beginTransaction()
//
//                val fragment1 = TutorialGridFragment()
//                transaction.replace(R.id.frame_container, fragment1)
//
//                transaction.commit()
//                return@OnNavigationItemSelectedListener true
//            }
            R.id.navigation_dashboard -> {
                val fragmentManager = supportFragmentManager
                val transaction = fragmentManager.beginTransaction()

                val dailyFragment = DailyQuiz()

                transaction.replace(R.id.frame_container, dailyFragment)

                transaction.commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                val fragmentManager = supportFragmentManager
                val transaction = fragmentManager.beginTransaction()

                val profileFragment = ProfileFragment()
                transaction.replace(R.id.frame_container, profileFragment)

                transaction.commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_leaderboard -> {
                val fragmentManager = supportFragmentManager
                val transaction = fragmentManager.beginTransaction()

                val leaderBoardsFragment = LeaderBoardsFragment()
                transaction.replace(R.id.frame_container, leaderBoardsFragment)

                transaction.commit()
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_qr -> {
                val fragmentManager = supportFragmentManager
                val transaction = fragmentManager.beginTransaction()

                val qrFragment = QRFragment()
                transaction.replace(R.id.frame_container, qrFragment)

                transaction.commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_bottom_nav1)

        quizDBHelper = QuizDBHelper(this)
        userDBHelper = UserDBHelper(this)

        sharedPreferences = this.getSharedPreferences(myPreferences, Context.MODE_PRIVATE)

        getRecordTask = GetRecordTask(sharedPreferences.getString(USERNAME, EMPTY))
        getRecordTask!!.execute(null as Void?)

        getFriendsTask = GetFriendsTask(sharedPreferences.getString(USERNAME, EMPTY), this)
        getFriendsTask!!.execute()

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
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        //first land display on page
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        val dailyFragment = DailyQuiz()

        transaction.replace(R.id.frame_container, dailyFragment)
        transaction.commit()

    }

    override fun onResume() {
        super.onResume()
        getFriendsTask = GetFriendsTask(sharedPreferences.getString(USERNAME, EMPTY), this)
        getFriendsTask!!.execute()
        getRecordTask = GetRecordTask(sharedPreferences.getString(USERNAME, EMPTY))
        getRecordTask!!.execute(null as Void?)
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
    }


    public override fun onStart() {
        super.onStart()
        //refresh fragment after completing chapter/quiz
        getRecordTask = GetRecordTask(sharedPreferences.getString(USERNAME, EMPTY))
        getRecordTask!!.execute(null as Void?)

        when (navigation.selectedItemId) {
//            R.id.navigation_home -> {
//                val fragmentManager = supportFragmentManager
//                val transaction = fragmentManager.beginTransaction()
//
//                val fragment1 = TutorialGridFragment()
//                transaction.replace(R.id.frame_container, fragment1)
//
//                transaction.commit()
//            }
            R.id.navigation_dashboard -> {
                val fragmentManager = supportFragmentManager
                val transaction = fragmentManager.beginTransaction()

                val dailyFragment = DailyQuiz()

                transaction.replace(R.id.frame_container, dailyFragment)

                transaction.commit()
            }
            R.id.navigation_notifications -> {
                val fragmentManager = supportFragmentManager
                val transaction = fragmentManager.beginTransaction()

                val profileFragment = ProfileFragment()
                transaction.replace(R.id.frame_container, profileFragment)

                transaction.commit()
            }
            R.id.navigation_leaderboard -> {
                val fragmentManager = supportFragmentManager
                val transaction = fragmentManager.beginTransaction()

                val leaderBoardsFragment = LeaderBoardsFragment()
                transaction.replace(R.id.frame_container, leaderBoardsFragment)

                transaction.commit()
            }

            R.id.navigation_qr -> {
                val fragmentManager = supportFragmentManager
                val transaction = fragmentManager.beginTransaction()

                val qrFragment = QRFragment()
                transaction.replace(R.id.frame_container, qrFragment)

                transaction.commit()
            }

        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Toast.makeText(this, "the returned message from ActivityIntent2 is ", Toast.LENGTH_LONG).show()

        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10001 && resultCode == 10001) {

            Toast.makeText(this, "the returned message from ActivityIntent4 is ", Toast.LENGTH_LONG).show()

            val fragmentManager = supportFragmentManager
            val transaction = fragmentManager.beginTransaction()

            val blankFragment = DailyQuiz()
            transaction.replace(R.id.frame_container, blankFragment)
            transaction.commit()
        }
    }

    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }

    inner class GetRecordTask internal constructor(private val mUsername: String) : AsyncTask<Void, Void, Boolean>() {

        lateinit var result: QuerySnapshot

        override fun doInBackground(vararg params: Void): Boolean? {

            try {
                result = quizDBHelper.getQuizRecord(mUsername)
                val editor = sharedPreferences.edit()

                for (document in result) {

                    if (document.get("chapterID").toString() == "1") {
                        editor.putString(CHAPTER_1_SCORE, document.get("score").toString())
                    } else if (document.get("chapterID").toString() == "2") {
                        editor.putString(CHAPTER_2_SCORE, document.get("score").toString())
                    } else if (document.get("chapterID").toString() == "3") {
                        editor.putString(CHAPTER_3_SCORE, document.get("score").toString())
                    } else if (document.get("chapterID").toString() == "4") {
                        editor.putString(CHAPTER_4_SCORE, document.get("score").toString())
                    } else if (document.get("chapterID").toString() == "5") {
                        editor.putString(CHAPTER_5_SCORE, document.get("score").toString())
                    } else if (document.get("chapterID").toString() == "6") {
                        editor.putString(CHAPTER_6_SCORE, document.get("score").toString())
                    }
                }
                editor.apply()

                return true

            } catch (e: InterruptedException) {
                return false
            }
        }

        override fun onPostExecute(success: Boolean?) {

        }
    }

    inner class GetQuizLeaderTask internal constructor(private val mChapterID: String) : AsyncTask<Void, Void, Boolean>() {

        override fun doInBackground(vararg params: Void): Boolean? {

            val editor = sharedPreferences.edit()

            try {
                leaderBoardRecords = ArrayList()
                val gson = Gson()
                var result: QuerySnapshot = quizDBHelper.getQuizLeaders(mChapterID)
                for (documents in result) {
                    val leader = QuizRecord(mChapterID, documents.get("username").toString(),
                            documents.get("score").toString(), documents.get("minutes").toString(), documents.get("seconds").toString(),
                            documents.get("milliseconds").toString())
                    leaderBoardRecords.add(leader)

                }
                if (mChapterID == "1") {
                    editor.putString(CHAPTER_1_LEADERS, gson.toJson(leaderBoardRecords))
                } else if (mChapterID == "2") {
                    editor.putString(CHAPTER_2_LEADERS, gson.toJson(leaderBoardRecords))
                } else if (mChapterID == "3") {
                    editor.putString(CHAPTER_3_LEADERS, gson.toJson(leaderBoardRecords))
                } else if (mChapterID == "4") {
                    editor.putString(CHAPTER_4_LEADERS, gson.toJson(leaderBoardRecords))
                } else if (mChapterID == "5") {
                    editor.putString(CHAPTER_5_LEADERS, gson.toJson(leaderBoardRecords))
                } else if (mChapterID == "6") {
                    editor.putString(CHAPTER_6_LEADERS, gson.toJson(leaderBoardRecords))
                }
                editor.apply()
                return true

            } catch (e: InterruptedException) {
                return false
            }
        }

        override fun onPostExecute(success: Boolean?) {

            if (success!!) {
            } else {
                //Toast.makeText(context, "Don't be discouraged, try again!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    inner class GetFriendsTask internal constructor(private val mUsername: String, private val context: Context) : AsyncTask<Void, Void, Boolean>() {


        override fun doInBackground(vararg params: Void): Boolean? {

            try {
                var result = userDBHelper.getFriends(mUsername)
                val editor = sharedPreferences.edit()

                if (result != "") {
                    editor.putString(FRIENDS, result)
                    editor.apply()
                }

                return true

            } catch (e: InterruptedException) {

                return false
            }
        }

        override fun onPostExecute(success: Boolean?) {
            Log.e("Friends", sharedPreferences.getString(FRIENDS, EMPTY))
        }
    }
}
