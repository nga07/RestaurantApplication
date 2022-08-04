package com.tuyenhoang.app.activity

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout
import android.graphics.Bitmap
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import android.graphics.BitmapFactory
import android.annotation.SuppressLint
import android.os.Bundle
import com.tuyenhoang.app.R
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.tuyenhoang.app.model.LoaiMon
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.tuyenhoang.app.databinding.ThemloaimonanLayoutBinding
import com.tuyenhoang.app.helper.LoaiMonHelper
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException

class ThemLoaiMonAnActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding:ThemloaimonanLayoutBinding
    private var loaiMonHelper:LoaiMonHelper?=null
    private var maloai = 0
    private var bitmapold: Bitmap? = null
    private var resultLauncherOpenIMG = registerForActivityResult(
        StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val uri = result.data!!.data
            try {
                val inputStream = contentResolver.openInputStream(
                    uri!!
                )
                val bitmap = BitmapFactory.decodeStream(inputStream)
                binding.imgAddcategoryThemHinh.setImageBitmap(bitmap)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.themloaimonan_layout)
        loaiMonHelper= LoaiMonHelper()
        val drawable = binding.imgAddcategoryThemHinh.drawable as BitmapDrawable
        bitmapold = drawable.bitmap
        maloai = intent.getIntExtra("maloai", 0)
        if (maloai != 0) {
            binding.txtAddcategoryTitle.text = resources.getString(R.string.editcategory)
            val loaiMon = loaiMonHelper!!.getLoaiMonToID(maloai)
            binding.txtlAddcategoryTenLoai.editText!!.setText(loaiMon.tenLoai)
            val categoryimage = loaiMon.hinhAnh
            val bitmap = BitmapFactory.decodeByteArray(categoryimage, 0, categoryimage!!.size)
            binding.imgAddcategoryThemHinh.setImageBitmap(bitmap)
            binding.btnAddcategoryTaoLoai.text = "Sửa loại"
        }
        binding.imgAddcategoryBack.setOnClickListener(this)
        binding.imgAddcategoryThemHinh.setOnClickListener(this)
        binding.btnAddcategoryTaoLoai.setOnClickListener(this)
    }

    @SuppressLint("NonConstantResourceId")
    override fun onClick(v: View) {
        val id = v.id
        val ktra: Boolean
        val chucnang: String
        val name = binding.txtlAddcategoryTenLoai.editText!!.text.toString()
        when (id) {
            R.id.img_addcategory_back -> {
                finish()
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            }
            R.id.img_addcategory_ThemHinh -> {
                val iGetIMG = Intent()
                iGetIMG.type = "image/*"
                iGetIMG.action = Intent.ACTION_GET_CONTENT
                resultLauncherOpenIMG.launch(
                    Intent.createChooser(
                        iGetIMG,
                        resources.getString(R.string.choseimg)
                    )
                )
            }
            R.id.btn_addcategory_TaoLoai -> {
                if (!validateImage() or !validate(name,binding.txtlAddcategoryTenLoai)) {
                    return
                }

                val loaiMon = LoaiMon()
                loaiMon.tenLoai = name
                loaiMon.hinhAnh = imageViewToByte(binding.imgAddcategoryThemHinh)
                if (maloai != 0) {
                    ktra = loaiMonHelper!!.updateLoaiMon(loaiMon, maloai)
                    chucnang = "sualoai"
                } else {
                    ktra = loaiMonHelper!!.addLoaiMon(loaiMon)
                    chucnang = "themloai"
                }
                val intent = Intent()
                intent.putExtra("ktra", ktra)
                intent.putExtra("chucnang", chucnang)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    private fun imageViewToByte(imageView: ImageView?): ByteArray {
        val bitmap = (imageView!!.drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    private fun validateImage(): Boolean {
        val drawable = binding.imgAddcategoryThemHinh.drawable as BitmapDrawable
        val bitmap = drawable.bitmap
        return if (bitmap == bitmapold) {
            Toast.makeText(applicationContext, "Xin chọn hình ảnh", Toast.LENGTH_SHORT).show()
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