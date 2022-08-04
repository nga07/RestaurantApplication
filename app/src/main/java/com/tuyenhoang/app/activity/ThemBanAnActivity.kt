package com.tuyenhoang.app.activity

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import android.os.Bundle
import com.tuyenhoang.app.R
import android.content.Intent
import android.view.View
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.tuyenhoang.app.databinding.ThembananLayoutBinding
import com.tuyenhoang.app.helper.BanAnHelper

class ThemBanAnActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var binding:ThembananLayoutBinding
    private var banAnHelper:BanAnHelper?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.thembanan_layout)
        banAnHelper= BanAnHelper()
        binding.btnAddtableTaoBan.setOnClickListener(this)
    }


    override fun onClick(view: View) {
        val nameTable = binding.txtlAddtableTenban.editText!!.text.toString()
        when(view.id){
            R.id.btn_addtable_TaoBan->{
                if (!validate(nameTable,binding.txtlAddtableTenban)){
                    return
                }
                val check = banAnHelper!!.addBanAn(nameTable)
                val intent = Intent()
                intent.putExtra("ketquathem", check)
                setResult(RESULT_OK, intent)
                finish()
            }
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