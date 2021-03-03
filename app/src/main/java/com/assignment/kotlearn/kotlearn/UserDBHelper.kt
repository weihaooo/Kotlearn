package com.assignment.kotlearn.kotlearn

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.util.Log.DEBUG
import com.google.firebase.firestore.FirebaseFirestore
import android.support.annotation.NonNull
import com.google.android.gms.tasks.*
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class UserDBHelper(context: Context) : SQLiteOpenHelper(context,
        DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "User.db"

        private val SQL_CREATE_ENTRIES =
                "CREATE TABLE " + UserTableInfo.TABLE_NAME + " (" +
                        UserTableInfo.COLUMN_USERNAME + " TEXT PRIMARY KEY," +
                        UserTableInfo.COLUMN_PASSWORD + " TEXT," +
                        UserTableInfo.COLUMN_EMAIL + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " +
                UserTableInfo.TABLE_NAME
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

    fun insertUser(user: UserRecord): Boolean {
        firestore = FirebaseFirestore.getInstance()
        var success = true
        // Create a new user with a first and last name
        val userMap = HashMap<String, Any>()
        userMap.put("username", user.username)
        userMap.put("password", user.password)
        userMap.put("email", user.email)

        var query: Task<QuerySnapshot> = firestore.collection("users").whereEqualTo("username", user.username)
                .get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                    if (task.isSuccessful) {
                        /*for (document in task.result) {
                            Log.d(TAG, document.id + " => " + document.data)

                        }*/
                    } else {
                        Log.w(TAG, "Error getting documents.", task.exception)
                        success = false
                    }
                })
        var userSnapshot: QuerySnapshot = Tasks.await(query)

        if(userSnapshot.isEmpty){
            Log.d(TAG, "No such user")
            firestore.collection("users")
                    .add(userMap)
                    .addOnSuccessListener(OnSuccessListener<DocumentReference> { documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.id)})
                    .addOnFailureListener(OnFailureListener { e -> Log.w(TAG, "Error adding document", e);success = false })

        } else{
            Log.d(TAG, "User already exist")
            success = false
        }

        return success
        // Add a new document with a generated ID
        val db = writableDatabase
        val values = ContentValues()

        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from "+ UserTableInfo.TABLE_NAME + " WHERE username = '" + user.username + "'", null)

        } catch (e: SQLiteException) {
            //db.execSQL(SQL_CREATE_ENTRIES)
            return false
        }
        if(cursor.count == 0) {
            values.put(UserTableInfo.COLUMN_USERNAME, user.username)
            values.put(UserTableInfo.COLUMN_PASSWORD, user.password)
            values.put(UserTableInfo.COLUMN_EMAIL, user.email)

            val newRowId = db.insert(UserTableInfo.TABLE_NAME,
                    null, values)
            return true
        }
        return false
    }

    fun login(username: String, password: String): Boolean {
        firestore = FirebaseFirestore.getInstance()
        val db = writableDatabase
        var cursor: Cursor? = null

        var success = true
        // Create a new user with a first and last name

        try {
                var query: Task<QuerySnapshot> = firestore.collection("users").whereEqualTo("username", username)
                    .get()
                    .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                        if (task.isSuccessful) {

                        } else {
                            Log.w(TAG, "Error getting documents.", task.exception)
                            success = false
                        }
                    })
            var userSnapshot = Tasks.await(query)

            if(userSnapshot.isEmpty){
                success = false
            } else {
                for (document in userSnapshot) {
                    Log.d(TAG, document.id + " => " + document.data)
                    if(password != document.data.getValue("password").toString()){
                        success = false
                    }
                }
            }

            return success

            cursor = db.rawQuery("select * from "+ UserTableInfo.TABLE_NAME + " WHERE username = '" + username + "'", null)

        } catch (e: SQLiteException) {
            //db.execSQL(SQL_CREATE_ENTRIES)
            Log.d("login", e.toString())
            return false
        }

        /*if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                if( username == cursor.getString(cursor.getColumnIndex(UserTableInfo.COLUMN_USERNAME)) && password == cursor.getString(cursor.getColumnIndex(UserTableInfo.COLUMN_PASSWORD)))
                    return true
            }
        }
        return false*/
    }

    fun addFriend(username: String, friend:String): Boolean {
        firestore = FirebaseFirestore.getInstance()

        var success = true

        val gson = Gson()

        var query: Task<QuerySnapshot> = firestore.collection("users").whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                    if (task.isSuccessful) {
                    } else {
                        Log.w(TAG, "Error getting documents.", task.exception)
                        success = false
                    }
                })

        var userSnapshot: QuerySnapshot = Tasks.await(query)

        var queryFriend: Task<QuerySnapshot> = firestore.collection("users").whereEqualTo("username", friend)
                .get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                    if (task.isSuccessful) {
                    } else {
                        Log.w(TAG, "Error getting documents.", task.exception)
                        success = false
                    }
                })

        var friendSnapshot: QuerySnapshot = Tasks.await(queryFriend)

        if(userSnapshot.isEmpty || friendSnapshot.isEmpty){
            Log.e(TAG, "No such user")
            success = false
        } else{

            val userFriend = userSnapshot.documents[0].get("friends")

            var friendList: ArrayList<String>

            val friendFriend = friendSnapshot.documents[0].get("friends")
            var friendFriendList: ArrayList<String>

            if(userFriend == null || userFriend.toString() == "null" || userFriend.toString() == "") {
                Log.e("test", userFriend.toString())
                friendList = ArrayList()

            }else {
                friendList = gson.fromJson(userFriend.toString(), object : TypeToken<ArrayList<String>>() {}.type)
            }

            if(friendFriend == null || friendFriend.toString() == "null" || friendFriend.toString() == "") {
                Log.e("test", friendFriend.toString())
                friendFriendList = ArrayList()

            }else {
                friendFriendList = gson.fromJson(friendFriend.toString(), object : TypeToken<ArrayList<String>>() {}.type)
            }

            if(friendList.contains(friend)){
                Log.e("test", "Already a friend " + friendList.toString())
                success = false
            } else{
                friendList.add(friend)
                friendFriendList.add(username)
                val userMap = HashMap<String, Any>()
                userMap.put("username", userSnapshot.documents[0].get("username").toString())
                userMap.put("password", userSnapshot.documents[0].get("password").toString())
                userMap.put("email", userSnapshot.documents[0].get("email").toString())
                userMap.put("friends", gson.toJson(friendList))

                val friendMap =HashMap<String, Any>()
                friendMap.put("username", friendSnapshot.documents[0].get("username").toString())
                friendMap.put("password", friendSnapshot.documents[0].get("password").toString())
                friendMap.put("email", friendSnapshot.documents[0].get("email").toString())
                friendMap.put("friends", gson.toJson(friendFriendList))

                firestore.collection("users").document(userSnapshot.documents[0].id)
                            .set(userMap)
                firestore.collection("users").document(friendSnapshot.documents[0].id)
                        .set(friendMap)
                Log.e("test", "Add friend")
                success = true
            }
        }

        return success
    }

    fun getFriends(username: String): String {
        firestore = FirebaseFirestore.getInstance()

        var success = true

        //val gson = Gson()

        var query: Task<QuerySnapshot> = firestore.collection("users").whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                    if (task.isSuccessful) {
                    } else {
                        Log.w(TAG, "Error getting documents.", task.exception)
                        success = false
                    }
                })

        var userSnapshot: QuerySnapshot = Tasks.await(query)

        if(success == false || userSnapshot.isEmpty){
            return ""
        } else{
            if(userSnapshot.documents[0].get("friends").toString()!=null || userSnapshot.documents[0].get("friends").toString()!=""){
                return userSnapshot.documents[0].get("friends").toString()
            } else{
                return ""
            }
        }
    }
}