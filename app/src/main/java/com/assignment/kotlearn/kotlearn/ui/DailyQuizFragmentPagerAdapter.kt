package com.assignment.kotlearn.kotlearn.ui
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity

import android.widget.LinearLayout
import android.view.ViewGroup
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.support.v4.view.PagerAdapter
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.assignment.kotlearn.kotlearn.QuestionRecord
import com.assignment.kotlearn.kotlearn.QuizDBHelper
import com.assignment.kotlearn.kotlearn.R
import com.assignment.kotlearn.kotlearn.R.id.textViewDailyQuizTitle
import com.assignment.kotlearn.kotlearn.R.id.textViewScore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.fragment_daily_quiz_page.view.*


class DailyQuizFragmentPagerAdapter(private val isMultiScr: Boolean) : PagerAdapter() {
    internal val PAGE_COUNT = 6
    lateinit var sharedPreferences: SharedPreferences
    private var myPreferences = "myPrefs"
    private var USERNAME = "username"
    private var CHAPTER_1_SCORE = "chapter1score"
    private var CHAPTER_2_SCORE = "chapter2score"
    private var CHAPTER_3_SCORE = "chapter3score"
    private var CHAPTER_4_SCORE = "chapter4score"
    private var CHAPTER_5_SCORE = "chapter5score"
    private var CHAPTER_6_SCORE = "chapter6score"
    private var EMPTY = "";
    override fun getCount(): Int {
        return PAGE_COUNT
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val linearLayout = LayoutInflater.from(container.context).inflate(R.layout.fragment_daily_quiz_page, null) as LinearLayout
        //new LinearLayout(container.getContext());
        val textViewDailyQuizTitle = linearLayout.findViewById(R.id.textViewDailyQuizTitle) as TextView
        val textViewScore = linearLayout.findViewById(R.id.textViewScore) as TextView
        val imageViewDailyQuiz= linearLayout.findViewById<ImageView>(R.id.imageViewDailyQuiz)
        val questionList = ArrayList<QuestionRecord>()

        sharedPreferences = container.context.getSharedPreferences(myPreferences, Context.MODE_PRIVATE)


        var scoreText = ""
        var chapterText=""
        if(position==0){
            if (sharedPreferences.getString(CHAPTER_1_SCORE, EMPTY) != EMPTY) {

                scoreText= "(Best Score: " + sharedPreferences.getString(CHAPTER_1_SCORE, EMPTY) + ")"
            }
            chapterText="Intro"
            textViewDailyQuizTitle.text = chapterText
            textViewScore.text = "Chapter 1\n" + scoreText
            imageViewDailyQuiz.setImageResource(R.drawable.intro)

            questionList.add(QuestionRecord("1","2","multiple","Which of these IDEs, which is covered lecture, supports Kotlin?", "Intellij", arrayOf("Netbeans", "Intellij", "Visual Studio", "DrJava")))
            questionList.add(QuestionRecord("2","2","multiple","Which is the correct Kotlin print syntax?", "print()", arrayOf("print()", "printf()", "out.println()", "System.out.println()")))
            questionList.add(QuestionRecord("3","2","multiple","Which is the correct Kotlin Single Line Comment syntax?", "//", arrayOf("//", "/**/", "<!-- -->", "#")))
            questionList.add(QuestionRecord("4","2","multiple","What is the default return data type for any function?", "Void", arrayOf("String", "Int", "Null", "Void")))
            questionList.add(QuestionRecord("4","2","multiple","Which is the correct Kotlin syntax to declare a function?", "fun main()", arrayOf("fun main()", "func main()", "function main()", "main() : fun")))

        } else if(position==1){
            if (sharedPreferences.getString(CHAPTER_2_SCORE, EMPTY) != EMPTY) {

            scoreText= "(Best Score: " + sharedPreferences.getString(CHAPTER_2_SCORE, EMPTY) + ")"
            }
            chapterText="Variables"
            textViewDailyQuizTitle.text = chapterText
            textViewScore.text = "Chapter 2\n" + scoreText
            imageViewDailyQuiz.setImageResource(R.drawable.variables)

            questionList.add(QuestionRecord("1","2","multiple","Which of these is an incorrect Kotlin statement?", "String greeting = \"Hello World\"", arrayOf("String greeting = \"Hello World\"", "var greeting: String", "var greeting = \"Hello World\"", "val greeting: String = \"Hello World\"")))
            questionList.add(QuestionRecord("2","2","multiple","Which of is a correct variable keyword?", "val", arrayOf("v", "vat", "val", "varr")))
            questionList.add(QuestionRecord("3","2","multiple","val question3 = \"2018\" \nWhat is the type reference of the statement above?", "String", arrayOf("Int", "Boolean", "String", "Char")))
            questionList.add(QuestionRecord("4","2","multiple","val year = 2018\nyear += 2017\nWhat is the value of 'year'?", "Error", arrayOf("2018", "20172018", "4035", "Error")))
            questionList.add(QuestionRecord("4","2","multiple","var year1 = \"2018\"\nvar year2 = \"2017\"\nvar year3 = \"\$year2 year1\"\nWhat is the value of year3?", "2017 2018", arrayOf("20172018", "2017 2018", "4035", "Error")))
        } else if(position==2){
            if (sharedPreferences.getString(CHAPTER_3_SCORE, EMPTY) != EMPTY) {

                scoreText= "(Best Score: " + sharedPreferences.getString(CHAPTER_3_SCORE, EMPTY) + ")"
            }
            chapterText="Data Type"
            textViewDailyQuizTitle.text = chapterText
            textViewScore.text = "Chapter 3\n" + scoreText

            imageViewDailyQuiz.setImageResource(R.drawable.data_types)

            questionList.add(QuestionRecord("1","2","multiple","val num1 = 126.78f\nval num2 = 325L\nWhat are their respective data types?", "Float, Long", arrayOf("Int, Double", "String, Float", "Float, Long", "Float, Double")))
            questionList.add(QuestionRecord("2","2","multiple","val digitChar = '9'\nWhat is the data type of this variable?", "Char", arrayOf("String", "Int", "Double", "Char")))
            questionList.add(QuestionRecord("3","2","multiple","var name = \"John\"\nvar unknown = name[1]\nWhat is the value in variable unknown?", "'o'", arrayOf("J", "'J'", "o", "'o'")))
            questionList.add(QuestionRecord("4","2","multiple","val array = arrayOf(1,2,3)\nWhat is the data type of the elements?", "Integer", arrayOf("Char", "Integer", "Int", "String")))
            questionList.add(QuestionRecord("4","2","multiple","var myInt = 100\nvar myLong: Long = myInt\nWhat is the value of myLong?", "Error", arrayOf("100", "100.0", "100.00", "Error")))
        } else if(position==3){
            if (sharedPreferences.getString(CHAPTER_4_SCORE, EMPTY) != EMPTY) {

                scoreText= "(Best Score: " + sharedPreferences.getString(CHAPTER_4_SCORE, EMPTY) + ")"
            }
            chapterText="Operators"
            textViewDailyQuizTitle.text = chapterText
            textViewScore.text = "Chapter 4\n" + scoreText

            imageViewDailyQuiz.setImageResource(R.drawable.operators)

            questionList.add(QuestionRecord("1","2","multiple","Which is not a basic data type?", "Triple", arrayOf("Int", "Char", "Double", "Triple")))
            questionList.add(QuestionRecord("2","2","multiple","Which function is correct?", "a.times(b)", arrayOf("a.add(b)", "a.sub(b)", "a.times(b)", "a.mod(b)")))
            questionList.add(QuestionRecord("3","2","multiple","Which statement will produce a True output?", "2 == 2 && 4 != 5", arrayOf("2 == 2 && 4 != 5", "2 != 2 && 4 == 5", "4 > 5 && 2 < 7", "!(7 > 12 || 14 < 18)")))
            questionList.add(QuestionRecord("4","2","multiple","16 shr 2\nWhat is the result of the statement above", "4", arrayOf("162", "8", "4", "2")))
            questionList.add(QuestionRecord("4","2","multiple","var a = 12\nvar b = 18\nprintln(\"Avg of \$a and \$b is equal to \${(a + b) / 2}\")\n\nWhat is the output of the statement above?", "Avg of 12 and 18 is equal to 15", arrayOf("Avg of \$a and \$b is equal to \${ (a + b)/2 }", "Avg of 12 and 18 is equal to 15", "Avg of \"12\" and \"18\" is equal to 15", "Avg of \$a and \$b is equal to 15")))
        } else if(position==4){
            if (sharedPreferences.getString(CHAPTER_5_SCORE, EMPTY) != EMPTY) {

                scoreText= "(Best Score: " + sharedPreferences.getString(CHAPTER_5_SCORE, EMPTY) + ")"
            }
            chapterText="Control Flow"
            textViewDailyQuizTitle.text = chapterText
            textViewScore.text = "Chapter 5\n" + scoreText
            imageViewDailyQuiz.setImageResource(R.drawable.control_flow)

            questionList.add(QuestionRecord("1","2","multiple","var primeNumbers = intArrayOf(2, 3, 5, 7, 11)\n\nfor(index in primeNumbers.______) {\n  println(\"\${primeNumbers[index]}\")\n}\n\nWhat is the name of the property to print all elements?", "index", arrayOf("indices", "index", "count", "length")))
            questionList.add(QuestionRecord("2","2","multiple","var dayOfWeek = 4\n" +
                    "when(dayOfWeek) {\n" +
                    "    1 -> {\n" +
                    "        print(\"Monday \")\n" +
                    "        print(\"First day of the week\")\n" +
                    "    }\n" +
                    "    2 -> println(\"Other days of the week\")\n" +
                    "    else -> println(\"Invalid Day\")\n" +
                    "}\n" +
                    "What is the output of the codes above?", "\"Invalid day\"", arrayOf("\"Monday First day of the week\"", "\"Monday, First day of the week\"", "\"Other days of the week\"", "\"Invalid day\"")))
            questionList.add(QuestionRecord("3","2","multiple","var x = 1\n" +
                    "while(x <= 5) {\n" +
                    "    print(\"\$x \")\n" +
                    "    x++\n" +
                    "}\n" +
                    "What is the output of this program?", "12345", arrayOf("135", "531", "12345", "54321")))
            questionList.add(QuestionRecord("4","2","multiple","var x = 6\n" +
                    "do {\n" +
                    "    print(\"\$x \")\n" +
                    "    x++\n" +
                    "} while(x <= 5)\n" +
                    "What is the output of the program?", "6", arrayOf("654321", "54321", "12345", "6")))
            questionList.add(QuestionRecord("4","2","multiple","var a = 32\n" +
                    "var b = 55\n" +
                    "var max = if(a > b) a else b\n" +
                    "What is the value of max?", "55", arrayOf("32", "55", "0", "1")))
        } else if(position==5){
            if (sharedPreferences.getString(CHAPTER_6_SCORE, EMPTY) != EMPTY) {

                scoreText= "(Best Score: " + sharedPreferences.getString(CHAPTER_6_SCORE, EMPTY) + ")"
            }
            chapterText="Null Safety"
            textViewDailyQuizTitle.text = chapterText
            textViewScore.text = "Chapter 6\n" + scoreText

            imageViewDailyQuiz.setImageResource(R.drawable.null_safety)

            questionList.add(QuestionRecord("1","2","multiple","var greeting: String = \"Hello, World\"\n" +
                    "greeting = null \n" +
                    "What is the value of the variable?", "Error", arrayOf("\"Hello, World\"", "null", "\"\"", "Error")))
            questionList.add(QuestionRecord("2","2","multiple","val name = nullableName ?: \"Guest\"\n" +
                    "What does this syntax represent?", "Value of 'name' is \"Guest\" when 'nullableName' is null", arrayOf("Declaring a nullable variable", "Value of 'name' is \"Guest\" when 'nullableName' is null", "Value of 'name' is always the value of nullableName", "Value of 'name' is \"Guest\" by default")))
            questionList.add(QuestionRecord("3","2","multiple","val nullableName: String? = null\n" +
                    "nullableName!!.toUpperCase()\n" +
                    "What is the output?", "Error", arrayOf("null", "NULL", "\"\"", "Error")))
            questionList.add(QuestionRecord("4","2","multiple","var listOfNullableTypes: List<Int> = listOf(1, 2, null, 3) \n" +
                    "listOfNullableTypes = null\n" +
                    "What are the potential errors?", "Both (1) and (2)", arrayOf("List is not declared to contain nullable types", "List is not declared to be a nullable collection", "Both (1) and (2)", "None of the above")))
            questionList.add(QuestionRecord("4","2","multiple","val nullableName: String? = null\n" +
                    "nullableName?.let { println(it) }\n" +
                    "What is the output?", "Prints nothing", arrayOf("\"null\"", "null", "\"\"", "Prints nothing")))
        }

        linearLayout.btnStartQuiz.setOnClickListener{
            val myIntent = Intent(container.context, DailyQuizQuestions::class.java)


            //questionID: String, val chapterID: String, val type: String, val question: String,val answer: String, val choices: ArrayList<String>


            myIntent.putExtra("Questions", questionList)
            myIntent.putExtra("ChapterID", position+1)
            myIntent.putExtra("ChapterText", chapterText)

            container.context.startActivity(myIntent)
        }

        linearLayout.btnStartChapter.setOnClickListener{
            val intent = Intent(container.context, ChapterActivity::class.java)
            intent.putExtra("Chapter", chapterText)
            container.context!!.startActivity(intent)
        }
        container.addView(linearLayout)
        return linearLayout
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view = `object` as LinearLayout
        container.removeView(view)
    }


}