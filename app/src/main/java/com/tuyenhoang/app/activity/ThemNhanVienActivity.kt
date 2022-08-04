package com.tuyenhoang.app.activity

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import android.annotation.SuppressLint
import android.os.Bundle
import com.tuyenhoang.app.R
import com.tuyenhoang.app.model.NhanVien
import android.content.Intent
import android.view.View
import android.widget.*
import androidx.databinding.DataBindingUtil
import com.tuyenhoang.app.databinding.ThemnhanvienLayoutBinding
import com.tuyenhoang.app.helper.NhanVienHelper
import java.util.*

class ThemNhanVienActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ThemnhanvienLayoutBinding
    private var nhanVienHelper: NhanVienHelper? = null
    var ngaySinh: String? = null
    var manv = 0
    var ktra: Boolean = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.themnhanvien_layout)
        nhanVienHelper = NhanVienHelper()
        manv = intent.getIntExtra("manv", 0)
        if (manv != 0) {
            binding.txtlAddstaffTenDN.visibility = View.GONE
            binding.txtAddstaffTitle.text = "Sửa nhân viên"
            val nhanVien = nhanVienHelper!!.getNhanVienToId(manv)
            binding.txtlAddstaffHoVaTen.editText!!.setText(nhanVien.fullName)
            binding.txtlAddstaffTenDN.editText!!.setText(nhanVien.username)
            binding.txtlAddstaffEmail.editText!!.setText(nhanVien.email)
            binding.txtlAddstaffSDT.editText!!.setText(nhanVien.numberPhone)
            binding.txtlAddstaffMatKhau.editText!!.setText(nhanVien.password)
            val sex = nhanVien.sex
            when (sex) {
                "Nam" -> {
                    binding.rdAddstaffNam.isChecked = true
                }
                "Nữ" -> {
                    binding.rdAddstaffNu.isChecked = true
                }
                else -> {
                    binding.rdAddstaffKhac.isChecked = true
                }
            }
            if (nhanVien.permission == 1) {
                binding.rdAddstaffQuanLy.isChecked = true
            } else {
                binding.rdAddstaffNhanVien.isChecked = true
            }
            val date = nhanVien.dateOfBirth
            val items = date!!.split("/").toTypedArray()
            val day = items[0].toInt()
            val month = items[1].toInt() - 1
            val year = items[2].toInt()
            binding.dtAddstaffNgaySinh.updateDate(year, month, day)
            binding.btnAddstaffThemNV.text = "Sửa nhân viên"
        }
        binding.btnAddstaffThemNV.setOnClickListener(this)
        binding.imgAddstaffBack.setOnClickListener(this)
    }

    @SuppressLint("NonConstantResourceId")
    override fun onClick(v: View) {
        val id = v.id
        val chucnang: String
        val fullName = binding.txtlAddstaffHoVaTen.editText!!.text.toString()
        val username = binding.txtlAddstaffTenDN.editText!!.text.toString()
        val email = binding.txtlAddstaffEmail.editText!!.text.toString()
        val numberPhone = binding.txtlAddstaffSDT.editText!!.text.toString()
        val password = binding.txtlAddstaffMatKhau.editText!!.text.toString()
        var sex = ""
        var permission = 0
        when (id) {
            R.id.btn_addstaff_ThemNV -> {
                if (!validateGender() or !validateAge() or
                    !validate(fullName, binding.txtlAddstaffHoVaTen) or
                    !validate(username, binding.txtlAddstaffTenDN) or
                    !validate(email, binding.txtlAddstaffEmail) or
                    !validate(numberPhone, binding.txtlAddstaffSDT) or
                    !validate(password, binding.txtlAddstaffMatKhau)
                ) {
                    return
                }

                when (binding.rgAddstaffGioiTinh.checkedRadioButtonId) {
                    R.id.rd_addstaff_Nam -> sex = "Men"
                    R.id.rd_addstaff_Nu -> sex = "Women"
                    R.id.rd_addstaff_Khac -> sex = "Diff"
                }
                when (binding.rgAddstaffQuyen.checkedRadioButtonId) {
                    R.id.rd_addstaff_QuanLy -> permission = 1
                    R.id.rd_addstaff_NhanVien -> permission = 2
                }
                ngaySinh =
                    (binding.dtAddstaffNgaySinh.dayOfMonth.toString() + "/" + (binding.dtAddstaffNgaySinh.month + 1) + "/" + binding.dtAddstaffNgaySinh.year)
                val checkUser = nhanVienHelper!!.checkUsername(username)
                if (checkUser != 0) {
                    binding.txtlAddstaffTenDN.error = "Tên tài khoản đã có người sử dụng!"
                    return
                }
                val moneyOrder=0
                val nhanVien = NhanVien()
                nhanVien.fullName = fullName
                nhanVien.username = username
                nhanVien.email = email
                nhanVien.numberPhone = numberPhone
                nhanVien.password = password
                nhanVien.sex = sex
                nhanVien.dateOfBirth = ngaySinh
                nhanVien.permission = permission
                nhanVien.moneyOrder=moneyOrder
                if (manv != 0) {
                    ktra = nhanVienHelper!!.updateNhanVien(nhanVien, manv)
                    chucnang = "sua"
                } else {
                    ktra = nhanVienHelper!!.addNhanVien(nhanVien)
                    chucnang = "themnv"
                }
                val intent = Intent()
                intent.putExtra("ketquaktra", ktra)
                intent.putExtra("chucnang", chucnang)
                setResult(RESULT_OK, intent)
                finish()
            }
            R.id.img_addstaff_back -> {
                finish()
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            }
        }
    }

    private fun validateGender(): Boolean {
        return if (binding.rgAddstaffGioiTinh.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Hãy chọn giới tính", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

    private fun validateAge(): Boolean {
        val currentYear = Calendar.getInstance()[Calendar.YEAR]
        val userAge = binding.dtAddstaffNgaySinh.year
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