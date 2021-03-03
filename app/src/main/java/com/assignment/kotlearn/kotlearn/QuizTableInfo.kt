package com.assignment.kotlearn.kotlearn

import android.provider.BaseColumns

class QuizTableInfo : BaseColumns {

    companion object {
        val TABLE_NAME = "quiz"
        val COLUMN_QUIZRECORDID = "quizrecordID"
        val COLUMN_CHAPTERID = "chapterID"
        val COLUMN_USERNAME = "username"
        val COLUMN_SCORE = "score"
    }


}