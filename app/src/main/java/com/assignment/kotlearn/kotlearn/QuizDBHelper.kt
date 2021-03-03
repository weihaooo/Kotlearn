package com.assignment.kotlearn.kotlearn

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.support.v4.view.accessibility.AccessibilityEventCompat.getRecordCount
import android.util.Log
import android.util.Log.DEBUG
import com.assignment.kotlearn.kotlearn.R.id.username
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

class QuizDBHelper(context: Context) : SQLiteOpenHelper(context,
        DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "Quiz.db"

        private val SQL_CREATE_ENTRIES =
                "CREATE TABLE " + QuizTableInfo.TABLE_NAME + " (" +
                        QuizTableInfo.COLUMN_QUIZRECORDID + " TEXT PRIMARY KEY," +
                        QuizTableInfo.COLUMN_CHAPTERID + " TEXT," +
                        QuizTableInfo.COLUMN_USERNAME + " TEXT," +
                        QuizTableInfo.COLUMN_SCORE+ " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " +
                QuizTableInfo.TABLE_NAME
    }

    lateinit var firestore: FirebaseFirestore

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?,
                           oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?,
                             oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun insertQuizRecord(username: String, chapterID: String, score: String,minutes: Int,seconds: Int, milliseconds:Int): Boolean {

        firestore = FirebaseFirestore.getInstance()

        var success = true
        // Create a new user with a first and last name
        val quizMap = HashMap<String, Any>()
        quizMap.put("username", username)
        quizMap.put("chapterID", chapterID)
        quizMap.put("score", score)
        quizMap.put("minutes",minutes)
        quizMap.put("seconds",seconds)
        quizMap.put("milliseconds",milliseconds)

        var query: Task<QuerySnapshot> = firestore.collection("quiz").whereEqualTo("username", username).whereEqualTo("chapterID", chapterID)
                .get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                    if (task.isSuccessful) {
                    } else {
                        Log.w(ContentValues.TAG, "Error getting documents.", task.exception)
                        success = false
                    }
                })

        var quizSnapshot: QuerySnapshot = Tasks.await(query)

        if(!quizSnapshot.isEmpty){
            success = false
            if(quizSnapshot.documents[0].get("score").toString().toInt() < score.toInt() ||
                    (quizSnapshot.documents[0].get("score").toString().toInt() == score.toInt() &&
                            quizSnapshot.documents[0].get("minutes").toString().toInt() > minutes) ||
                    (quizSnapshot.documents[0].get("score").toString().toInt() == score.toInt() &&
                            quizSnapshot.documents[0].get("minutes").toString().toInt() == minutes &&
                            quizSnapshot.documents[0].get("seconds").toString().toInt() > seconds) ||
                    (quizSnapshot.documents[0].get("score").toString().toInt() == score.toInt() &&
                            quizSnapshot.documents[0].get("minutes").toString().toInt() == minutes &&
                            quizSnapshot.documents[0].get("seconds").toString().toInt() == seconds &&
                            quizSnapshot.documents[0].get("milliseconds").toString().toInt() > milliseconds)){

                firestore.collection("quiz").document(quizSnapshot.documents[0].id)
                        .set(quizMap)
                success = true
            }
        } else{
            firestore.collection("quiz")
                    .add(quizMap)
                    .addOnSuccessListener(OnSuccessListener<DocumentReference> { documentReference -> Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: " + documentReference.id)})
                    .addOnFailureListener(OnFailureListener { e -> Log.w(ContentValues.TAG, "Error adding document", e);success = false })

            success = true
        }

        return success

        val db = writableDatabase
        val values = ContentValues()

        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from "+ QuizTableInfo.TABLE_NAME + " WHERE username = '" + username + "' AND chapterID = '" + chapterID + "'", null)

        } catch (e: SQLiteException) {
            //db.execSQL(SQL_CREATE_ENTRIES)
            return false
        }
        if(cursor.count == 0) {

            values.put(QuizTableInfo.COLUMN_USERNAME, username)
            values.put(QuizTableInfo.COLUMN_QUIZRECORDID, getRecordCount()+1)
            values.put(QuizTableInfo.COLUMN_CHAPTERID, chapterID)
            values.put(QuizTableInfo.COLUMN_SCORE, score)

            val newRowId = db.insert(UserTableInfo.TABLE_NAME,
                    null, values)
            return true
        } else {
            if (cursor!!.moveToFirst()) {
                if(cursor.getString(cursor.getColumnIndex(QuizTableInfo.COLUMN_SCORE)).toInt() >= score.toInt()){
                    return false
                } else{
                    val quizRecordID = cursor.getString(cursor.getColumnIndex(QuizTableInfo.COLUMN_QUIZRECORDID))
                    deleteRecord(quizRecordID)
                    values.put(QuizTableInfo.COLUMN_USERNAME, username)
                    values.put(QuizTableInfo.COLUMN_QUIZRECORDID, quizRecordID)
                    values.put(QuizTableInfo.COLUMN_CHAPTERID, chapterID)
                    values.put(QuizTableInfo.COLUMN_SCORE, score)

                    val newRowId = db.insert(UserTableInfo.TABLE_NAME,
                            null, values)
                    return true
                }
            }
        }
        return false
    }

    fun getQuizRecord(username: String): QuerySnapshot {

        firestore = FirebaseFirestore.getInstance()

        var query: Task<QuerySnapshot> = firestore.collection("quiz").whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                    if (task.isSuccessful) {
                    } else {
                        Log.w(ContentValues.TAG, "Error getting documents.", task.exception)
                    }
                })

        var quizSnapshot: QuerySnapshot = Tasks.await(query)

        return quizSnapshot
    }

    fun getQuizLeaders(chapterID:String): QuerySnapshot {

        firestore = FirebaseFirestore.getInstance()

        var query: Task<QuerySnapshot> = firestore.collection("quiz").whereEqualTo("chapterID", chapterID)
                                        .orderBy("score",Query.Direction.DESCENDING).orderBy("minutes",Query.Direction.ASCENDING)
                                        .orderBy("seconds",Query.Direction.ASCENDING).orderBy("milliseconds",Query.Direction.ASCENDING).limit(10)
                                        .get()
                                        .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                                            if (task.isSuccessful) {
                                            } else {
                                                Log.w(ContentValues.TAG, "Error getting documents.", task.exception)
                                            }
                                        })

        var quizSnapshot: QuerySnapshot = Tasks.await(query)

        return quizSnapshot
    }

    fun deleteRecord(quizRecordID: String):Boolean {
        val db = writableDatabase
        val selection = QuizTableInfo.COLUMN_QUIZRECORDID + " = ?"
        val selectionArgs = arrayOf(quizRecordID)

        db.delete(QuizTableInfo.TABLE_NAME, selection, selectionArgs)

        return true
    }

    fun getRecordCount(): Int {
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from "+ QuizTableInfo.TABLE_NAME, null)

        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return 0
        }
        return cursor.count
    }

}