package com.tuyenhoang.app.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.GridView
import com.tuyenhoang.app.model.ThanhToan
import com.tuyenhoang.app.adapter.ThanhToanAdapter
import android.os.Bundle
import com.tuyenhoang.app.R
import android.view.View
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.tuyenhoang.app.databinding.ChitietdonhangLayoutBinding
import com.tuyenhoang.app.helper.BanAnHelper
import com.tuyenhoang.app.helper.NhanVienHelper
import com.tuyenhoang.app.helper.ThanhToanHelper

class ChiTietDonHangActivity : AppCompatActivity() {
    private lateinit var binding:ChitietdonhangLayoutBinding
    private var madon = 0
    private var manv = 0
    private var maban = 0
    private var ngaydat: String? = null
    private var tongtien: String? = null
    private var thanhToanList= mutableListOf<ThanhToan>()
    private var nhanVienHelper:NhanVienHelper?=null
    private var banAnHelper:BanAnHelper?=null
    private var thanhToanHelper:ThanhToanHelper?=null
    private var thanhToanAdapter: ThanhToanAdapter? = null
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.chitietdonhang_layout)
        val intent = intent
        madon = intent.getIntExtra("madon", 0)
        manv = intent.getIntExtra("manv", 0)
        maban = intent.getIntExtra("maban", 0)
        ngaydat = intent.getStringExtra("ngaydat")
        tongtien = intent.getStringExtra("tongtien")
       nhanVienHelper= NhanVienHelper()
        banAnHelper= BanAnHelper()
        thanhToanHelper= ThanhToanHelper()
        if (madon != 0) {
            binding.txtDetailstatisticMaDon.text = "Mã đơn: $madon"
            binding.txtDetailstatisticNgayDat.text = ngaydat
            binding.txtDetailstatisticTongTien.text = "$tongtien VNĐ"
            val nhanVien = nhanVienHelper!!.getNhanVienToId(manv)
            binding.txtDetailstatisticTenNV.text = nhanVien.fullName
            binding.txtDetailstatisticTenBan.text = banAnHelper!!.getBanAnToId(maban)
            HienThiDSCTDD()
        }
        binding.imgDetailstatisticBackbtn.setOnClickListener(View.OnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        })
    }

    private fun HienThiDSCTDD() {
        thanhToanList = thanhToanHelper!!.getAllMonToMaDon(madon)
        thanhToanAdapter = ThanhToanAdapter(this, R.layout.custom_layout_thanhtoan, thanhToanList)
        binding.gvDetailStatistic.adapter = thanhToanAdapter
        thanhToanAdapter!!.notifyDataSetChanged()
    }
}