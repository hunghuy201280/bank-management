package com.example.bankmanagement.view.full_screen_image

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.example.bankmanagement.R
import com.example.bankmanagement.utils.Utils.Companion.getBitmapFromURL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FullScreenImage : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_full)

        val extras = intent.extras
        val link = extras!!.getString("imageLink")


        val imgDisplay: ImageView = findViewById<View>(R.id.imgDisplay) as ImageView
        val btnClose: Button = findViewById<View>(R.id.btnClose) as Button


        btnClose.setOnClickListener { finish() }
        GlobalScope.launch(Dispatchers.IO) {

            val bmp = getBitmapFromURL(link)
            withContext(Dispatchers.Main) {
                imgDisplay.setImageBitmap(bmp)

            }
        }
    }
}