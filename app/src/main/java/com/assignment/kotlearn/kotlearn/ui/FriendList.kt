package com.assignment.kotlearn.kotlearn.ui

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.assignment.kotlearn.kotlearn.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FriendList : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    private var myPreferences = "myPrefs"
    private var USERNAME = "username"
    private var FRIENDS = "friends"
    private var EMPTY = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_list)
        supportActionBar?.hide()

        sharedPreferences = this.getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
        val gson = Gson()
        var friendRecords: ArrayList<String>
        val ivNoFriends = findViewById<ImageView>(R.id.ivNoFriends)
        val tvNoFriends = findViewById<TextView>(R.id.tvNoFriends)
        val lvFriends = findViewById<ListView>(R.id.lvFriends)
        friendRecords = ArrayList()
        if (sharedPreferences.getString(FRIENDS, EMPTY) != EMPTY) {
            friendRecords = gson.fromJson(sharedPreferences.getString(FRIENDS, EMPTY), object : TypeToken<ArrayList<String>>() {}.type)
            ivNoFriends.visibility = View.GONE
            tvNoFriends.visibility = View.GONE
            val friendListAdapter = FriendListAdapter(this, friendRecords)
            lvFriends.adapter = friendListAdapter
            Log.e("test", "dasdsadsad")
        } else {
            lvFriends.visibility = View.GONE
            Log.e("test", "dasaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + sharedPreferences.getString(FRIENDS, EMPTY) + sharedPreferences.getString(USERNAME, EMPTY))
        }


    }
}
