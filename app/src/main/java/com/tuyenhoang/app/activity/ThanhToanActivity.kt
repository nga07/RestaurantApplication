package com.tuyenhoang.app.activity

import androidx.appcompat.app.AppCompatActivity
import com.tuyenhoang.app.model.ThanhToan
import com.tuyenhoang.app.adapter.ThanhToanAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.tuyenhoang.app.R
import android.view.View
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.tuyenhoang.app.databinding.ThanhtoanLayoutBinding
import com.tuyenhoang.app.helper.BanAnHelper
import com.tuyenhoang.app.helper.DonHangHelper
import com.tuyenhoang.app.helper.NhanVienHelper
import com.tuyenhoang.app.helper.ThanhToanHelper
import java.lang.Exception

class ThanhToanActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ThanhtoanLayoutBinding
    private var donHangHelper: DonHangHelper? = null
    private var banAnHelper: BanAnHelper? = null
    private var thanhToanHelper: ThanhToanHelper? = null
    private var thanhToans: List<ThanhToan>? = null
    private var thanhToanAdapter: ThanhToanAdapter? = null
    private var tongtien: Long = 0
    private var maban = 0
    private var madondat = 0
    private var fragmentManager: FragmentManager? = null
    private var manv = 0
    private var nhanVienHelper: NhanVienHelper? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.thanhtoan_layout)
        donHangHelper = DonHangHelper()
        thanhToanHelper = ThanhToanHelper()
        banAnHelper = BanAnHelper()
        nhanVienHelper=NhanVienHelper()
        fragmentManager = supportFragmentManager
        val intent = intent
        maban = intent.getIntExtra("maban", 0)
        val tenban = intent.getStringExtra("tenban")
        val ngaydat = intent.getStringExtra("ngaydat")
        manv = intent.getIntExtra("manv", 0)
        Log.d("Tuyen1234",manv.toString())
        binding.txtPaymentTenBan.text = tenban
        binding.txtPaymentNgayDat.text = ngaydat
        if (maban != 0) {
            HienThiThanhToan()
            for (i in thanhToans!!.indices) {
                val soluong = thanhToans!![i].soLuong
                val giatien = thanhToans!![i].giaTien
                tongtien += soluong.toLong() * giatien!!.toInt()
            }
            binding.txtPaymentTongTien.text = "$tongtien VNĐ"
        }
        binding.btnPaymentThanhToan.setOnClickListener(this)
        binding.imgPaymentBackbtn.setOnClickListener(this)
    }

    @SuppressLint("NonConstantResourceId", "SetTextI18n")
    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_payment_ThanhToan -> {
                val ktraban = banAnHelper!!.updateStatusBanAn(maban, "false")
                val ktradondat = donHangHelper!!.updateStatusDonHang(maban, "true")
                val ktratongtien =
                    donHangHelper!!.updateTotalMoney(madondat, tongtien.toString())
                val ollMoney = nhanVienHelper!!.getMoneyOrderNhanVien(manv)
                Log.d("Tuyen12",ollMoney.toString())
                val money = (tongtien+ollMoney).toInt()
                nhanVienHelper!!.updateMoneyOrderNhanVien(money.toString(), manv)
                nhanVienHelper!!.updateMoneyManager(tongtien.toString())
                if (ktraban && ktradondat && ktratongtien) {
                    HienThiThanhToan()
                    binding.txtPaymentTongTien.text = "0 VNĐ"
                    Toast.makeText(applicationContext, "Thanh toán thành công!", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(applicationContext, "Lỗi thanh toán!", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.img_payment_backbtn -> finish()
        }
    }

    private fun HienThiThanhToan() {
        madondat = donHangHelper!!.getIdDonHangToMaBan(maban, "false")
        thanhToans = thanhToanHelper!!.getAllMonToMaDon(madondat)
        thanhToanAdapter = ThanhToanAdapter(this, R.layout.custom_layout_thanhtoan, thanhToans!!)
        binding.gvDisplayPayment.adapter = thanhToanAdapter
        thanhToanAdapter!!.notifyDataSetChanged()
    }
}