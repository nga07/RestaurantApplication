package com.tuyenhoang.app.activity

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import android.annotation.SuppressLint
import android.os.Bundle
import com.tuyenhoang.app.R
import com.tuyenhoang.app.model.NhanVien
import android.widget.Toast
import android.content.Intent
import android.view.View
import androidx.databinding.DataBindingUtil
import com.tuyenhoang.app.databinding.DangkyLayoutBinding
import com.tuyenhoang.app.helper.NhanVienHelper
import com.tuyenhoang.app.helper.QuyenHelper
import java.util.*

class DangKyActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: DangkyLayoutBinding
    private var sex: String? = null
    private var nhanVienHelper:NhanVienHelper?=null
    private var quyenHelper:QuyenHelper?=null
    @SuppressLint("NonConstantResourceId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.dangky_layout)
        nhanVienHelper= NhanVienHelper()
        quyenHelper= QuyenHelper()
        binding.btnSignupDangKy.setOnClickListener(this)
        binding.imgSignupBack.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_signup_dangKy -> {
                val fullName = binding.txtSignupName.editText!!.text.toString()
                val username = binding.txtSignupUsername.editText!!.text.toString()
                val email = binding.txtSignupEmail.editText!!.text.toString()
                val numberPhone = binding.txtSignupPhone.editText!!.text.toString()
                val password = binding.txtSignupPassword.editText!!.text.toString()
                if (!validate(fullName, binding.txtSignupName) or
                    !validate(username, binding.txtSignupUsername) or
                    !validate(email, binding.txtSignupEmail) or
                    !validate(numberPhone, binding.txtSignupPhone) or
                    !validate(password, binding.txtSignupPassword)) {
                    return
                }
                if (!validateAge() or !validateGender()) {
                    return
                }
                when (binding.rgSignupSex.checkedRadioButtonId) {
                    R.id.rd_signup_men -> sex = "Men"
                    R.id.rd_signup_women -> sex = "Women"
                    R.id.rd_signup_diff -> sex = "Diff"
                }
                val ngaySinh =
                    binding.dtSignupDate.dayOfMonth.toString() + "/" + (binding.dtSignupDate.month + 1) + "/" + binding.dtSignupDate.year
                val nhanVien = NhanVien()
                nhanVien.fullName = fullName
                nhanVien.username = username
                nhanVien.email = email
                nhanVien.numberPhone = numberPhone
                nhanVien.password = password
                nhanVien.sex = sex
                nhanVien.dateOfBirth = ngaySinh
                if (nhanVienHelper!!.checkTonTaiNhanVien()) {
                    quyenHelper!!.addQuyen("Staff")
                    nhanVien.permission = 2
                } else {
                    quyenHelper!!.addQuyen("Manager")
                    nhanVien.permission = 1
                }
                val check = nhanVienHelper!!.addNhanVien(nhanVien)
                if (check) {
                    Toast.makeText(
                        this@DangKyActivity,
                        resources.getString(R.string.add_sucessful),
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(applicationContext, WelcomeActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this@DangKyActivity,
                        resources.getString(R.string.add_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
            R.id.img_signup_back -> {
                val intent = Intent(applicationContext, WelcomeActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun validateGender(): Boolean {
        return if (binding.rgSignupSex.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Hãy chọn giới tính", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

    private fun validateAge(): Boolean {
        val currentYear = Calendar.getInstance()[Calendar.YEAR]
        val userAge = binding.dtSignupDate.year
        val isAgeValid = currentYear - userAge
        return if (isAgeValid < 16) {
            Toast.makeText(this, "Bạn không đủ tuổi đăng ký!", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

    private fun validate(text: String, textInput: TextInputLayout): Boolean {
        return if (text.isEmpty()) {
            textInput.error = resources.getString(R.string.not_empty)
            false
        } else {
            textInput.error = null
            textInput.isErrorEnabled = false
            true
        }
    }

}