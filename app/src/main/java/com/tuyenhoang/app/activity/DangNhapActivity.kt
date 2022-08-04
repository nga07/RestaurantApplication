package com.tuyenhoang.app.activity

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import android.os.Bundle
import com.tuyenhoang.app.R
import android.content.Intent
import android.widget.Toast
import android.view.View
import androidx.databinding.DataBindingUtil
import com.tuyenhoang.app.databinding.DangnhapLayoutBinding
import com.tuyenhoang.app.helper.NhanVienHelper

class DangNhapActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var binding:DangnhapLayoutBinding
    private var nhanVienHelper:NhanVienHelper?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.dangnhap_layout)
        nhanVienHelper= NhanVienHelper()
        binding.btnLoginDangNhap.setOnClickListener(this)
        binding.btnLoginDangKy.setOnClickListener(this)
        binding.btnLoginQuenMatKhau.setOnClickListener(this)
        binding.imgLoginBack.setOnClickListener(this)
    }
    override fun onClick(view: View) {
       when(view.id){
           R.id.btn_login_DangNhap->{
               val username = binding.txtLoginUsername.editText!!.text.toString()
               val password = binding.txtLoginPassword.editText!!.text.toString()
               if (!validate(username,binding.txtLoginUsername) or
                   !validate(password,binding.txtLoginPassword)) {
                   return
               }

               val checkUser=nhanVienHelper!!.checkLogin(username,password)
               val permission=nhanVienHelper!!.getPermissionNhanVien(checkUser)
               if (!nhanVienHelper!!.checkTonTaiNhanVien()) {
                   if (username == "admin" && password == "admin") {
                       val intent = Intent(applicationContext, TrangChuActivity::class.java)
                       startActivity(intent)
                   }
                   else{
                       Toast.makeText(this,"Sai tên tài khoản hoặc mật khẩu",Toast.LENGTH_SHORT).show()
                   }
               }
               if (checkUser != 0) {
                   val sharedPreferences = getSharedPreferences("luuquyen", MODE_PRIVATE)
                   val editor = sharedPreferences.edit()
                   editor.putInt("maquyen", permission)
                   editor.apply()
                   val intent = Intent(applicationContext, TrangChuActivity::class.java)
                   intent.putExtra("tendn", username)
                   intent.putExtra("manv", checkUser)
                   startActivity(intent)
               }
               else {
                   if (username != "admin" && password != "admin"){
                       Toast.makeText(
                           applicationContext,
                           "Sai tên tài khoản hoặc mật khẩu",
                           Toast.LENGTH_SHORT
                       ).show()
                   }
               }
           }
           R.id.btn_login_DangKy->{
               val intent = Intent(applicationContext, DangKyActivity::class.java)
               startActivity(intent)
           }
           R.id.btn_login_QuenMatKhau->{
               val intent = Intent(applicationContext, CapNhatMatKhauActivity::class.java)
               startActivity(intent)
           }
           R.id.img_login_back->{
               val intent = Intent(applicationContext, WelcomeActivity::class.java)
               startActivity(intent)
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