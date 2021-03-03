package com.assignment.kotlearn.kotlearn.ui

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import com.assignment.kotlearn.kotlearn.R
import com.assignment.kotlearn.kotlearn.R.id.signup_button
import com.assignment.kotlearn.kotlearn.UserDBHelper
import com.assignment.kotlearn.kotlearn.UserRecord
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    lateinit var userDBHelper: UserDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        userDBHelper = UserDBHelper(this)

        signup_button.setOnClickListener {
            val usernameEdit = findViewById<AutoCompleteTextView>(R.id.username)
            val emailEdit = findViewById<AutoCompleteTextView>(R.id.email)
            val pwEditText = findViewById<EditText>(R.id.password)
            val cpwEditText = findViewById<EditText>(R.id.confirmPassword)
            var pass = true

            if(pwEditText.text.length <= 4 || pwEditText.text.toString() != cpwEditText.text.toString()) {
                if (pwEditText.text.length <= 4) {

                    pwEditText.error = "Your password should be longer than 4 characters"
                    pass = false

                } else if (pwEditText.text.toString() != cpwEditText.text.toString()) {

                    cpwEditText.error = "Your password does not match"
                    pass = false
                }
            }else {

                pwEditText.error = null
                cpwEditText.error = null
            }

            if(emailEdit.text.contains("@") == false || emailEdit.text.toString() == "") {
                emailEdit.error = "Invalid Email"
                pass = false
            } else {
                emailEdit.error = null
            }

            if(pass){

                var addUserTask = AddUserTask(usernameEdit.text.toString(), pwEditText.text.toString(), emailEdit.text.toString())
                addUserTask.execute()

            }
        }
    }

    /*fun addUser(username:String, email: String, password: String ): Boolean {

        var result: Boolean = userDBHelper.insertUser(UserRecord(username,password,email))

        return result

    }*/

    inner class AddUserTask internal constructor(private val mUsername: String, private val mPassword: String, private val mEmail: String) : AsyncTask<Void, Void, Boolean>() {

        override fun doInBackground(vararg params: Void): Boolean? {

            try {
                var result: Boolean = userDBHelper.insertUser(UserRecord(mUsername,mPassword,mEmail))

                return result

            } catch (e: InterruptedException) {
                return false
            }
        }

        override fun onPostExecute(success: Boolean?) {

            val usernameEdit = findViewById<AutoCompleteTextView>(R.id.username)

            if(success!!) {
                usernameEdit.error = null
                val myIntent = Intent()
                myIntent.putExtra("message",
                        "You have successfully registered. Please proceed to login now.")
                setResult(Activity.RESULT_OK, myIntent)
                finish()
            } else {
                usernameEdit.error = "Username exists. Please change another username to use."
            }
        }

        override fun onCancelled() {
        }
    }
}
