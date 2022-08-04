package com.tuyenhoang.app.adapter

import android.annotation.SuppressLint
import android.content.Context
import com.tuyenhoang.app.model.DonHang
import android.widget.BaseAdapter
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.tuyenhoang.app.R
import com.tuyenhoang.app.helper.BanAnHelper
import com.tuyenhoang.app.helper.NhanVienHelper

class DoanhThuAdapter(var context: Context, var layout: Int, var donHangs: List<DonHang>) :
    BaseAdapter() {
    var viewHolder: ViewHolder? = null
    private var nhanVienHelper:NhanVienHelper
    private var banAnHelper:BanAnHelper
    override fun getCount(): Int {
        return donHangs.size
    }

    override fun getItem(position: Int): Any {
        return donHangs[position]
    }

    override fun getItemId(position: Int): Long {
        return donHangs[position].maDonDat.toLong()
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            viewHolder = ViewHolder()
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(layout, parent, false)
            viewHolder!!.txt_customstatistic_MaDon =
                view.findViewById(R.id.txt_customstatistic_MaDon)
            viewHolder!!.txt_customstatistic_NgayDat =
                view.findViewById(R.id.txt_customstatistic_NgayDat)
            viewHolder!!.txt_customstatistic_TenNV =
                view.findViewById(R.id.txt_customstatistic_TenNV)
            viewHolder!!.txt_customstatistic_TongTien =
                view.findViewById(R.id.txt_customstatistic_TongTien)
            viewHolder!!.txt_customstatistic_TrangThai =
                view.findViewById(R.id.txt_customstatistic_TrangThai)
            viewHolder!!.txt_customstatistic_BanDat =
                view.findViewById(R.id.txt_customstatistic_BanDat)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }
        val donHang = donHangs[position]
        viewHolder!!.txt_customstatistic_MaDon!!.text = "Mã đơn: " + donHang.maDonDat
        viewHolder!!.txt_customstatistic_NgayDat!!.text = donHang.ngayDat
        viewHolder!!.txt_customstatistic_TongTien!!.text = donHang.tongTien + " VNĐ"
        if (donHang.tinhTrang == "true") {
            viewHolder!!.txt_customstatistic_TrangThai!!.text = "Đã thanh toán"
        } else {
            viewHolder!!.txt_customstatistic_TrangThai!!.text = "Chưa thanh toán"
        }
        val nhanVien = nhanVienHelper.getNhanVienToId(donHang.maNV)
        viewHolder!!.txt_customstatistic_TenNV!!.text = nhanVien.fullName
        viewHolder!!.txt_customstatistic_BanDat!!.text =
            banAnHelper.getBanAnToId(donHang.maBan)
        return view!!
    }

    inner class ViewHolder {
        var txt_customstatistic_MaDon: TextView? = null
        var txt_customstatistic_NgayDat: TextView? = null
        var txt_customstatistic_TenNV: TextView? = null
        var txt_customstatistic_TongTien: TextView? = null
        var txt_customstatistic_TrangThai: TextView? = null
        var txt_customstatistic_BanDat: TextView? = null
    }

    init {
        nhanVienHelper = NhanVienHelper()
        banAnHelper = BanAnHelper()
    }
}