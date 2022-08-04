package com.tuyenhoang.app.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.textfield.TextInputLayout
import com.tuyenhoang.app.R
import com.tuyenhoang.app.databinding.ActivityXacNhanMatKhauBinding
import com.tuyenhoang.app.helper.NhanVienHelper

class XacNhanMatKhauActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var binding:ActivityXacNhanMatKhauBinding
    private var nhanVienHelper:NhanVienHelper?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_xac_nhan_mat_khau)
        nhanVienHelper= NhanVienHelper()
        binding.imgXacnhanBack.setOnClickListener(this)
        binding.btnXacnhanConfirm.setOnClickListener(this)
    }
    override fun onClick(view: View) {

         when(view.id){
             R.id.img_xacnhan_back->{
                 val intent =Intent(applicationContext,DangNhapActivity::class.java)
                 startActivity(intent)
             }
             R.id.btn_xacnhan_confirm->{
                 val password1=binding.txtXacnhanMatKhau.editText!!.text.toString()
                 val password2=binding.txtlXacnhanXacNhanMatKhau.editText!!.text.toString()
                 if (!validate(password1,binding.txtXacnhanMatKhau) or
                     !validate(password2,binding.txtlXacnhanXacNhanMatKhau)){
                     return
                 }

                 if (password1 == password2){
                     val intent = intent
                     val manv = intent.getIntExtra("manv",0)

                     val checkUser= nhanVienHelper!!.updatePassword(password1,manv)
                     if (checkUser){
                         Toast.makeText(applicationContext,"Cập nhật mật khẩu thành công!",Toast.LENGTH_SHORT).show()
                         val intent = Intent(applicationContext,DangNhapActivity::class.java)
                         startActivity(intent)
                     }
                     else{
                         Toast.makeText(applicationContext,"Vui lòng thử lại",Toast.LENGTH_SHORT).show()
                     }
                 }
                 if (password1 != password2){
                     Toast.makeText(applicationContext,"Bạn nhập lại mật khẩu!",Toast.LENGTH_LONG).show()
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