package com.tuyenhoang.app.fragments

import com.tuyenhoang.app.model.DonHang
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import com.tuyenhoang.app.R
import com.tuyenhoang.app.activity.TrangChuActivity
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView
import android.content.Intent
import android.view.View
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.tuyenhoang.app.activity.ChiTietDonHangActivity
import com.tuyenhoang.app.adapter.DoanhThuAdapter
import com.tuyenhoang.app.helper.DonHangHelper

class DonHangFragment : Fragment() {
    var lvStatistic: ListView? = null
    var donHangs: List<DonHang>? = null
    private var donHangHelper:DonHangHelper?=null
    var doanhThuAdapter: DoanhThuAdapter? = null
    var madon = 0
    var manv = 0
    var maban = 0
    var ngaydat: String? = null
    var tongtien: String? = null
    var maquyen=0
    var manhanvien=0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        maquyen = requireArguments().getInt("maquyen")
        manhanvien=requireArguments().getInt("manv")
        val view = inflater.inflate(R.layout.thongke_layout, container, false)
        (activity as TrangChuActivity?)!!.supportActionBar!!.title = "Quản lý doanh thu"
        setHasOptionsMenu(true)
        lvStatistic = view.findViewById<View>(R.id.lvStatistic) as ListView
        donHangHelper = DonHangHelper()
        if (maquyen==1){
            donHangs = donHangHelper!!.getAllDonHang()
        }
        if (maquyen==2){
            donHangs = donHangHelper!!.getDonHangToId(manhanvien)
        }

        doanhThuAdapter =
            activity?.let { DoanhThuAdapter(it, R.layout.custom_layout_donhang, donHangs!!) }
        lvStatistic!!.adapter = doanhThuAdapter
        doanhThuAdapter!!.notifyDataSetChanged()
        lvStatistic!!.onItemClickListener =
            OnItemClickListener { _: AdapterView<*>?, _: View?, position: Int, _: Long ->
                madon = donHangs!![position].maDonDat
                manv = donHangs!![position].maNV
                maban = donHangs!![position].maBan
                ngaydat = donHangs!![position].ngayDat
                tongtien = donHangs!![position].tongTien
                val intent = Intent(activity, ChiTietDonHangActivity::class.java)
                intent.putExtra("madon", madon)
                intent.putExtra("manv", manv)
                intent.putExtra("maban", maban)
                intent.putExtra("ngaydat", ngaydat)
                intent.putExtra("tongtien", tongtien)
                startActivity(intent)
            }
        return view
    }
}