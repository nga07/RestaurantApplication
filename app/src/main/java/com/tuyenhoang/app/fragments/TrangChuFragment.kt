package com.tuyenhoang.app.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.tuyenhoang.app.R
import com.tuyenhoang.app.activity.TrangChuActivity
import com.tuyenhoang.app.adapter.DoanhThuAdapterRecycleView
import com.tuyenhoang.app.adapter.LoaiMonAdapterRecycleView
import com.tuyenhoang.app.helper.DonHangHelper
import com.tuyenhoang.app.helper.LoaiMonHelper
import com.tuyenhoang.app.model.DonHang
import com.tuyenhoang.app.model.LoaiMon
import java.text.SimpleDateFormat
import java.util.*

class TrangChuFragment : Fragment(), View.OnClickListener {

    var rcv_displayhome_LoaiMon: RecyclerView? = null
    var rcv_displayhome_DonTrongNgay: RecyclerView? = null
    var layout_displayhome_ThongKe: RelativeLayout? = null
    var layout_displayhome_XemBan: RelativeLayout? = null
    var layout_displayhome_XemMenu: RelativeLayout? = null
    var layout_displayhome_XemNV: RelativeLayout? = null
    var txt_displayhome_ViewAllCategory: TextView? = null
    var txt_displayhome_ViewAllStatistic: TextView? = null
    private var loaiMonHelper: LoaiMonHelper? = null
    private var donHangHelper: DonHangHelper? = null
    var loaiMonList: List<LoaiMon>? = null
    var donHangs: List<DonHang>? = null
    var loaiMonAdapterRecycleView: LoaiMonAdapterRecycleView? = null
    var doanhThuAdapterRecycleView: DoanhThuAdapterRecycleView? = null
    var maquyen = 0
    var manhanvien = 0
    var textviewNhanVien: TextView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.trangchu_layout, container, false)
        (activity as TrangChuActivity?)!!.supportActionBar!!.title = "Trang chủ"
        setHasOptionsMenu(true)
        rcv_displayhome_LoaiMon = view.findViewById(R.id.rcv_displayhome_LoaiMon)
        rcv_displayhome_DonTrongNgay = view.findViewById(R.id.rcv_displayhome_DonTrongNgay)
        layout_displayhome_ThongKe = view.findViewById(R.id.layout_displayhome_ThongKe)
        layout_displayhome_XemBan = view.findViewById(R.id.layout_displayhome_XemBan)
        layout_displayhome_XemMenu = view.findViewById(R.id.layout_displayhome_XemMenu)
        layout_displayhome_XemNV = view.findViewById(R.id.layout_displayhome_XemNV)
        txt_displayhome_ViewAllCategory = view.findViewById(R.id.txt_displayhome_ViewAllCategory)
        txt_displayhome_ViewAllStatistic = view.findViewById(R.id.txt_displayhome_ViewAllStatistic)
        textviewNhanVien = view.findViewById(R.id.tv_nhanvien)
        loaiMonHelper = LoaiMonHelper()
        donHangHelper = DonHangHelper()
        maquyen = requireArguments().getInt("maquyen")
        manhanvien = requireArguments().getInt("manv")
        HienThiDSLoai()
        HienThiDonTrongNgay()
        if (maquyen == 2) {
            layout_displayhome_XemNV!!.visibility = View.GONE
            textviewNhanVien!!.visibility = View.GONE
        }
        layout_displayhome_ThongKe!!.setOnClickListener(this)
        layout_displayhome_XemBan!!.setOnClickListener(this)
        layout_displayhome_XemMenu!!.setOnClickListener(this)
        layout_displayhome_XemNV!!.setOnClickListener(this)
        txt_displayhome_ViewAllCategory!!.setOnClickListener(this)
        txt_displayhome_ViewAllStatistic!!.setOnClickListener(this)
        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun HienThiDSLoai() {
        rcv_displayhome_LoaiMon!!.setHasFixedSize(true)
        rcv_displayhome_LoaiMon!!.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        loaiMonList = loaiMonHelper!!.getAllLoaiMon()
        loaiMonAdapterRecycleView =
            activity?.let {
                LoaiMonAdapterRecycleView(
                    it,
                    R.layout.custom_layout_themloai,
                    loaiMonList!!
                )
            }
        rcv_displayhome_LoaiMon!!.adapter = loaiMonAdapterRecycleView
        loaiMonAdapterRecycleView!!.notifyDataSetChanged()
    }

    @SuppressLint("SimpleDateFormat", "NotifyDataSetChanged")
    private fun HienThiDonTrongNgay() {
        rcv_displayhome_DonTrongNgay!!.setHasFixedSize(true)
        rcv_displayhome_DonTrongNgay!!.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        val ngaydat = dateFormat.format(calendar.time)
        donHangs = donHangHelper!!.getDonHangToId(manhanvien)
        doanhThuAdapterRecycleView =
            activity?.let {
                DoanhThuAdapterRecycleView(
                    it,
                    R.layout.custom_layout_donhang,
                    donHangs!!
                )
            }
        rcv_displayhome_DonTrongNgay!!.adapter = doanhThuAdapterRecycleView
        loaiMonAdapterRecycleView!!.notifyDataSetChanged()
    }

    override fun onClick(v: View) {

        val id = v.id
        val navigationView =
            requireActivity().findViewById<View>(R.id.navigation_view_trangchu) as NavigationView
        when (id) {
            R.id.layout_displayhome_ThongKe, R.id.txt_displayhome_ViewAllStatistic -> {
                val bundle = Bundle()
                bundle.putInt("maquyen", maquyen)
                bundle.putInt("manv", manhanvien)
                val tranDisplayStatistic =
                    requireActivity().supportFragmentManager.beginTransaction()
                val donHangFragment = DonHangFragment()
                donHangFragment.arguments = bundle
                tranDisplayStatistic.replace(R.id.contentView, donHangFragment)
                tranDisplayStatistic.addToBackStack(null)
                tranDisplayStatistic.commit()
                navigationView.setCheckedItem(R.id.nav_statistic)
            }
            R.id.txt_displayhome_ViewAllCategory, R.id.layout_displayhome_XemMenu -> {
                val tranDisplayCategory =
                    requireActivity().supportFragmentManager.beginTransaction()
                tranDisplayCategory.replace(R.id.contentView, LoaiMonFragment())
                tranDisplayCategory.addToBackStack(null)
                tranDisplayCategory.commit()
                navigationView.setCheckedItem(R.id.nav_category)
            }
            R.id.layout_displayhome_XemBan -> {
                val tranDisplayStatistic =
                    requireActivity().supportFragmentManager.beginTransaction()
                tranDisplayStatistic.replace(R.id.contentView, DisplayTableFragment())
                tranDisplayStatistic.addToBackStack(null)
                tranDisplayStatistic.commit()
                navigationView.setCheckedItem(R.id.nav_table)
            }
            R.id.layout_displayhome_XemNV -> {
                val tranDisplayStatistic =
                    requireActivity().supportFragmentManager.beginTransaction()
                tranDisplayStatistic.replace(R.id.contentView, DisplayStaffFragment())
                tranDisplayStatistic.addToBackStack(null)
                tranDisplayStatistic.commit()
                navigationView.setCheckedItem(R.id.nav_staff)
            }
        }
    }
}