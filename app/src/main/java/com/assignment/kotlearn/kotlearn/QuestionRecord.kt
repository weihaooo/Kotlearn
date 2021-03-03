package com.assignment.kotlearn.kotlearn

import java.io.Serializable

class QuestionRecord(val questionID: String, val chapterID: String, val type: String, val question: String,val answer: String, val choices: Array<String>):Serializable