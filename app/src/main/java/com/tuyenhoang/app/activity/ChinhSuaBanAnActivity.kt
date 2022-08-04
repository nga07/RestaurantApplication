package com.tuyenhoang.app.activity

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import android.os.Bundle
import com.tuyenhoang.app.R
import android.content.Intent
import android.view.View
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.tuyenhoang.app.databinding.SuabananLayoutBinding
import com.tuyenhoang.app.helper.BanAnHelper

class ChinhSuaBanAnActivity : AppCompatActivity() {
    private lateinit var binding:SuabananLayoutBinding
    private var banAnHelper:BanAnHelper?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.suabanan_layout)
        banAnHelper= BanAnHelper()
        val idTable = intent.getIntExtra("maban", 0)
        binding.btnEdittableSuaBan.setOnClickListener(View.OnClickListener {
            val nameTable = binding.txtlEdittableTenban.editText!!.text.toString()
            val check = banAnHelper!!.updateBanAn(idTable, nameTable)
            val intent = Intent()
            intent.putExtra("ketquasua", check)
            setResult(RESULT_OK, intent)
            finish()
        })
    }
}