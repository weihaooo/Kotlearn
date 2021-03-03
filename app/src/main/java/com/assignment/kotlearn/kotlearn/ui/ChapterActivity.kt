package com.assignment.kotlearn.kotlearn.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import com.assignment.kotlearn.kotlearn.R

class ChapterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapter)
        var chapter : String = intent.getStringExtra("Chapter")
        var textView: TextView = findViewById<TextView>(R.id.textView)
        var imgView: ImageView = findViewById<ImageView>(R.id.imageView4)

        when(chapter){
            "Intro" -> {
                textView.setText(R.string.intro_content)
                imgView.setImageDrawable(getDrawable(R.drawable.kotlin_introduction_hello_world_tutorial))
            }
            "Variables" ->{
                textView.setText(R.string.variables_content)
                imgView.setImageDrawable(getDrawable(R.drawable.kotlin_variables_data_types_string_array))
            }
            "Data Type" -> {
                textView.setText(R.string.datatypes_content)
                imgView.setImageDrawable(getDrawable(R.drawable.kotlin_variables_data_types_string_array))
            }
            "Operators" -> {
                textView.setText(R.string.operations_content)
                imgView.setImageDrawable(getDrawable(R.drawable.kotlin_operators))
            }
            "Control Flow" -> {
                textView.setText(R.string.controlflow_content)
                imgView.setImageDrawable(getDrawable(R.drawable.kotlin_control_flow))
            }
            "Null Safety" -> {
                textView.setText(R.string.null_content)
                imgView.setImageDrawable(getDrawable(R.drawable.kotlin_nullable_types_and_null_safety))
            }
        }


    }
}
