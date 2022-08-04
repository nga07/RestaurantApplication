package com.tuyenhoang.app.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import androidx.databinding.DataBindingUtil
import com.google.android.material.textfield.TextInputLayout
import com.tuyenhoang.app.R
import com.tuyenhoang.app.databinding.ActivityCapNhatMatKhauBinding
import com.tuyenhoang.app.helper.NhanVienHelper

class CapNhatMatKhauActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var binding:ActivityCapNhatMatKhauBinding
    private var nhanVienHelper:NhanVienHelper?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_cap_nhat_mat_khau)
        nhanVienHelper= NhanVienHelper()
        binding.imgForgetBack.setOnClickListener(this)
        binding.btnForgetNext.setOnClickListener(this)
    }
    override fun onClick(view: View) {
      when(view.id){
          R.id.img_forget_back->{
              val intent=Intent(applicationContext,DangNhapActivity::class.java)
              startActivity(intent)
          }
          R.id.btn_forget_next->{
              val email=binding.txtForgetEmail.editText!!.text.toString()
              val username=binding.txtForgetUsername.editText!!.text.toString()
              val numberPhone=binding.txtForgetPhone.editText!!.text.toString()
              if (!validate(username,binding.txtForgetUsername) or
                  !validate(email,binding.txtForgetEmail) or
                  !validate(numberPhone,binding.txtForgetPhone)){
                  return
              }

              val checkUser =nhanVienHelper!!.checkForgot(email,username,numberPhone)
              if (checkUser!=0){
                  val intent=Intent(applicationContext,XacNhanMatKhauActivity::class.java)
                  intent.putExtra("manv", checkUser)
                  startActivity(intent)
              }
              else{
                  Toast.makeText(applicationContext,"Bạn nhập sai thông tin!",Toast.LENGTH_LONG).show()
              }
          }
      }

    }
    private fun validate(text:String,textInput:TextInputLayout): Boolean {
        return if (text.isEmpty()){
            textInput.error=resources.getString(R.string.not_empty)
            false
        }else{
            textInput.error = null
            textInput.isErrorEnabled = false
            true
        }
    }




}