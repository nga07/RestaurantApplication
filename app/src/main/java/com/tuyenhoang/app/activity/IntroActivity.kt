package com.tuyenhoang.app.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tuyenhoang.app.R
import android.content.Intent
import android.os.Handler

class IntroActivity : AppCompatActivity() {
    private val SPLASH_TIMER = 2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.intro_layout)
        Handler().postDelayed({
            val intent = Intent(applicationContext, WelcomeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }, SPLASH_TIMER.toLong())
    }
}