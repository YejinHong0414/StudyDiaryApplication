package com.guru2_6.studydiaryapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import java.lang.Exception

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // hiding title bar of this activity
        window.requestFeature(Window.FEATURE_NO_TITLE)

        // making this  activity full screen
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)

//        try {
//            Thread.sleep(3000);
//            val intent = Intent(this, TabpagerActivity::class.java)
//            startActivity(intent)
//            finish()
//        } catch (e:Exception){
//            return;
//        }

        val intent = Intent(this, TabpagerActivity::class.java)
        try {
            Thread.sleep(3000);
            startActivity(intent)
            finish()
        } finally {
            return;
        }






//        Handler().postDelayed({
//            val intent = Intent (this@SplashActivity, TabpagerActivity::class.java)
//            startActivity(intent)
//            finish()
//        },3000)
    }
}