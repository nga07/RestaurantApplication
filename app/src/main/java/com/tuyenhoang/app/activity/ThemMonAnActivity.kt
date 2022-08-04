package com.tuyenhoang.app.activity

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import android.graphics.Bitmap
import android.content.SharedPreferences
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import android.graphics.BitmapFactory
import android.os.Bundle
import com.tuyenhoang.app.R
import android.graphics.drawable.BitmapDrawable
import com.tuyenhoang.app.model.MonAn
import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.*
import androidx.databinding.DataBindingUtil
import com.tuyenhoang.app.databinding.ThemmonanLayoutBinding
import com.tuyenhoang.app.helper.MonAnHelper
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.lang.Exception
import java.lang.NullPointerException

class ThemMonAnActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding:ThemmonanLayoutBinding
    private var monHelper: MonAnHelper? = null
    var tenloai: String? = null
    var sTenMon: String? = null
    var sGiaTien: String? = null
    var sTinhTrang: String? = null
    var bitmapold: Bitmap? = null
    var maloai = 0
    var maquyen = 0
    var mamon = 0
    var sharedPreferences: SharedPreferences? = null
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
                binding.imgAddmenuThemHinh.setImageBitmap(bitmap)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.themmonan_layout)
        val intent = intent
        maloai = intent.getIntExtra("maLoai", -1)
        tenloai = intent.getStringExtra("tenLoai")
        monHelper = MonAnHelper()
        binding.txtlAddmenuLoaiMon.editText!!.setText(tenloai)
        val drawable = binding.imgAddmenuThemHinh.drawable as BitmapDrawable
        bitmapold = drawable.bitmap
        mamon = getIntent().getIntExtra("mamon", 0)
        if (mamon != 0) {
            binding.txtAddmenuTitle.text = "Sửa món ăn"
            val monAn = monHelper!!.getMonToId(mamon)
            binding.txtlAddmenuTenMon.editText!!.setText(monAn.tenMon)
            binding.txtlAddmenuGiaTien.editText!!.setText(monAn.giaTien)
            val image = monAn.hinhAnh
            val bitmap = BitmapFactory.decodeByteArray(image, 0, image!!.size)
            binding.imgAddmenuThemHinh.setImageBitmap(bitmap)
            sharedPreferences = getSharedPreferences("luuquyen", MODE_PRIVATE)
            maquyen = sharedPreferences!!.getInt("maquyen", 0)
            if (maquyen == 1) {
                binding.layoutTrangthaimon.visibility = View.VISIBLE
                val tinhtrang = monAn.tinhTrang
                try {
                    if (tinhtrang == "true") {
                        binding.rdAddmenuConMon.isChecked = true
                    }
                } catch (e: NullPointerException) {
                    binding.rdAddmenuHetMon.isChecked = true
                }
            }
            if (maquyen==2){
                binding.layoutTrangthaimon.visibility = View.GONE
            }
            binding.btnAddmenuThemMon.text = "Sửa món"
        }
        binding.imgAddmenuThemHinh.setOnClickListener(this)
        binding.btnAddmenuThemMon.setOnClickListener(this)
        binding.imgAddmenuBack.setOnClickListener(this)
    }

    @SuppressLint("NonConstantResourceId")
    override fun onClick(v: View) {
        val id = v.id
        val ktra: Boolean
        val chucnang: String
        val nameDish = binding.txtlAddmenuTenMon.editText!!.text.toString()
        val money = binding.txtlAddmenuGiaTien.editText!!.text.toString()
        var status=""
        when (id) {
            R.id.img_addmenu_ThemHinh -> {
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
            R.id.img_addmenu_back -> {
                finish()
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            }
            R.id.btn_addmenu_ThemMon -> {
                if (!validateImage() or !validate(nameDish,binding.txtlAddmenuTenMon) or !validate(money,binding.txtlAddmenuGiaTien)) {
                    return
                }

                when (binding.rgAddmenuTinhTrang.checkedRadioButtonId) {
                    R.id.rd_addmenu_ConMon -> status = "true"
                    R.id.rd_addmenu_HetMon -> status = "false"
                }
                val monAn = MonAn()
                monAn.maLoai = maloai
                monAn.tenMon = nameDish
                monAn.giaTien = money
                monAn.tinhTrang = status
                monAn.hinhAnh = imageViewToByte(binding.imgAddmenuThemHinh)
                monAn.count=0
                if (mamon != 0) {
                    ktra = monHelper!!.updateMon(monAn, mamon)
                    chucnang = "suamon"
                } else {
                    ktra = monHelper!!.addMon(monAn)
                    chucnang = "themmon"
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
        val drawable = binding.imgAddmenuThemHinh.drawable as BitmapDrawable
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

