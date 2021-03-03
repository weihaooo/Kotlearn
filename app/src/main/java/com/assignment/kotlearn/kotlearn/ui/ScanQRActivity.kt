package com.assignment.kotlearn.kotlearn.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Toast
import com.assignment.kotlearn.kotlearn.R
import com.assignment.kotlearn.kotlearn.UserDBHelper
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import java.io.IOException

class ScanQRActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    private var myPreferences = "myPrefs"
    private var USERNAME = "username"
    private var FRIENDS = "friends"
    private var EMPTY = ""
    lateinit var userDBHelper: UserDBHelper
    private var addFriendTask: AddFriendTask? = null
    private var getFriendsTask: GetFriendsTask? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_qr)
        supportActionBar?.hide()
        userDBHelper = UserDBHelper(this)
        sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE)

        val cameraView = findViewById<SurfaceView>(R.id.surfaceView)
        //val tv = findViewById<TextView>(R.id.textView)

        val barcodeDetector = BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build()

        val cameraSource = CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .build()

        cameraView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    cameraSource.start(cameraView.holder)

                } catch (ie: IOException) {
                    Log.e("Camera source", ie.message)
                }
            }

            override fun surfaceChanged(holder: SurfaceHolder,
                                        format: Int, width: Int, height: Int) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.stop()
            }
        })

        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {

            override fun release() {}

            override fun receiveDetections(detections: Detector.Detections<Barcode>) {
                var barcodes = detections.detectedItems

                if (barcodes.size() != 0) {
                    //add friend here
                    addFriendTask = AddFriendTask(sharedPreferences.getString("username",EMPTY), barcodes.valueAt(0).displayValue, this@ScanQRActivity)
                    addFriendTask!!.execute()
                    finish()
                }
                Thread.sleep(500)
            }
        })
    }

    inner class AddFriendTask internal constructor(private val mUsername:String, private val friend:String, private val context:Context) : AsyncTask<Void, Void, Boolean>() {


        override fun doInBackground(vararg params: Void): Boolean? {

            try {
                var result = userDBHelper.addFriend(mUsername, friend)

                return result

            } catch (e: InterruptedException) {

                return false
            }
        }

        override fun onPostExecute(success: Boolean?) {

            if(success!!){
                Toast.makeText(context, "You have successfully added a new friend " + friend + "!", Toast.LENGTH_SHORT).show()
                getFriendsTask = GetFriendsTask(mUsername,context)
                getFriendsTask!!.execute()

            } else{
                Toast.makeText(context, "Failed to add friend", Toast.LENGTH_SHORT).show()
            }
        }
    }

    inner class GetFriendsTask internal constructor(private val mUsername:String, private val context:Context) : AsyncTask<Void, Void, Boolean>() {


        override fun doInBackground(vararg params: Void): Boolean? {

            try {
                var result = userDBHelper.getFriends(mUsername)
                val editor = sharedPreferences.edit()

                if(result != ""){
                    editor.putString(FRIENDS, result)
                    editor.apply()
                }

                return true

            } catch (e: InterruptedException) {

                return false
            }
        }

        override fun onPostExecute(success: Boolean?) {

        }
    }
}
