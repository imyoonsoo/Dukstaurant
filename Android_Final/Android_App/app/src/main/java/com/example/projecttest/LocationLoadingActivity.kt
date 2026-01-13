package com.example.projecttest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide

class LocationLoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_loading)

        val gifImageView = findViewById<ImageView>(R.id.imageView31)
        Glide.with(this)
            .asGif()
            .load(R.drawable.giphy)
            .into(gifImageView)


        Handler().postDelayed({
            val intent = Intent(this@LocationLoadingActivity, MyLocationActivity::class.java)
            startActivity(intent)
            finish()
        }, 2500)


        //Toast.makeText(this@LocationLoadingActivity, "", Toast.LENGTH_SHORT).show()
    }

}