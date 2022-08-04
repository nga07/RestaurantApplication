package com.tuyenhoang.app.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tuyenhoang.app.R
import android.content.Intent
import android.view.View
import androidx.databinding.DataBindingUtil
import com.tuyenhoang.app.databinding.WelcomeLayoutBinding

class WelcomeActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var binding:WelcomeLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.welcome_layout)
        binding.btnLogin.setOnClickListener(this)
        binding.btnSignup.setOnClickListener(this)
    }
    override fun onClick(view: View) {
        when(view.id){
            R.id.btn_login->{
                val intent = Intent(applicationContext, DangNhapActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_signup->{
                val intent = Intent(applicationContext, DangKyActivity::class.java)
                startActivity(intent)
            }
        }

    }
}