package com.assignment.kotlearn.kotlearn.ui

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.assignment.kotlearn.kotlearn.R
import net.glxn.qrgen.android.QRCode

class GenQRActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    private var myPreferences = "myPrefs"
    private var USERNAME = "username"
    private var EMPTY = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gen_qr)
        supportActionBar?.hide()

        sharedPreferences = this.getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
        val imageView:ImageView = findViewById(R.id.imageView4)
        val bitmap = QRCode.from(sharedPreferences.getString(USERNAME, EMPTY)).withSize(1000, 1000).bitmap()
        imageView.setImageBitmap(bitmap)

        //val path = FileSystems.getDefault().getPath("JSA-QRCode.png")
        //MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path)
    }
}
