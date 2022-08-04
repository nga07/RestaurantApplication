package com.tuyenhoang.app.activity

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import android.os.Bundle
import com.tuyenhoang.app.R
import android.view.View
import android.widget.Button
import com.tuyenhoang.app.model.ChiTietDonHang
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.tuyenhoang.app.databinding.ThemsoluongmonLayoutBinding
import com.tuyenhoang.app.helper.ChiTietDonHangHelper
import com.tuyenhoang.app.helper.DonHangHelper
import com.tuyenhoang.app.helper.MonAnHelper

class ThemSoLuongMonActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var binding: ThemsoluongmonLayoutBinding
    private var maban = 0
    private var mamon = 0
    private var donHangHelper: DonHangHelper? = null
    private var chiTietDonHangHelper: ChiTietDonHangHelper? = null
    private var monAnHelper:MonAnHelper?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.themsoluongmon_layout)
        donHangHelper = DonHangHelper()
        chiTietDonHangHelper = ChiTietDonHangHelper()
        monAnHelper= MonAnHelper()
        val intent = intent
        maban = intent.getIntExtra("maban", 0)
        mamon = intent.getIntExtra("mamon", 0)
        binding.btnAmountmenuDongY.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val amount = binding.txtlAmountmenuSoLuong.editText!!.text.toString()
        when(view.id){
            R.id.btn_amountmenu_DongY->{
                if (!validate(amount, binding.txtlAmountmenuSoLuong)) {
                    return
                }
                val madondat = donHangHelper!!.getIdDonHangToMaBan(maban, "false")
                val ktra = chiTietDonHangHelper!!.checkTonTaiMonAn(madondat, mamon)
                if (ktra) {
                    val oldAmount = chiTietDonHangHelper!!.getAmountToId(madondat, mamon)
                    val totalAmount = oldAmount + amount.toInt()
                    updateCountDish(totalAmount,mamon)
                    val chiTietDonHang = ChiTietDonHang()
                    chiTietDonHang.maMon = mamon
                    chiTietDonHang.maDonDat = madondat
                    chiTietDonHang.soLuong = totalAmount
                    val check = chiTietDonHangHelper!!.updateAmount(chiTietDonHang)
                    if (check) {
                        Toast.makeText(
                            applicationContext,
                            resources.getString(R.string.add_sucessful),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            resources.getString(R.string.add_failed),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    updateCountDish(amount.toInt(),mamon)
                    val chiTietDonHang = ChiTietDonHang()
                    chiTietDonHang.maMon = mamon
                    chiTietDonHang.maDonDat = madondat
                    chiTietDonHang.soLuong = amount.toInt()
                    val check = chiTietDonHangHelper!!.addChiTietDonHang(chiTietDonHang)
                    if (check) {
                        Toast.makeText(
                            applicationContext,
                            resources.getString(R.string.add_sucessful),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            resources.getString(R.string.add_failed),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                finish()
            }
        }
    }
    private fun updateCountDish(count:Int,idMon:Int){
        val ollCount=monAnHelper!!.getCountMonToId(idMon)
        val newCount=count+ollCount
        monAnHelper!!.updateCountMon(newCount,idMon)
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