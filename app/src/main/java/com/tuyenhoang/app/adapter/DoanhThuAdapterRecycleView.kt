package com.tuyenhoang.app.adapter

import android.annotation.SuppressLint
import android.content.Context
import com.tuyenhoang.app.model.DonHang
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.tuyenhoang.app.R
import com.tuyenhoang.app.helper.BanAnHelper
import com.tuyenhoang.app.helper.NhanVienHelper

class DoanhThuAdapterRecycleView(
    var context: Context,
    var layout: Int,
    var donHangList: List<DonHang>
) : RecyclerView.Adapter<DoanhThuAdapterRecycleView.ViewHolder>() {
    private var nhanVienHelper: NhanVienHelper
    private var banAnHelper: BanAnHelper
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(layout, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val donHang = donHangList[position]
        holder.txt_customstatistic_MaDon.text = "Mã đơn: " + donHang.maDonDat
        holder.txt_customstatistic_NgayDat.text = donHang.ngayDat
        if (donHang.tongTien == "0") {
            holder.txt_customstatistic_TongTien.visibility = View.INVISIBLE
        } else {
            holder.txt_customstatistic_TongTien.visibility = View.VISIBLE
        }
        if (donHang.tinhTrang == "true") {
            holder.txt_customstatistic_TrangThai.text = "Đã thanh toán"
        } else {
            holder.txt_customstatistic_TrangThai.text = "Chưa thanh toán"
        }
        val nhanVien = nhanVienHelper.getNhanVienToId(donHang.maNV)
        holder.txt_customstatistic_TenNV.text = nhanVien.fullName
        holder.txt_customstatistic_BanDat.text = banAnHelper.getBanAnToId(donHang.maBan)
    }

    override fun getItemCount(): Int {
        return donHangList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txt_customstatistic_MaDon: TextView
        var txt_customstatistic_NgayDat: TextView
        var txt_customstatistic_TenNV: TextView
        var txt_customstatistic_BanDat: TextView
        var txt_customstatistic_TongTien: TextView
        var txt_customstatistic_TrangThai: TextView

        init {
            txt_customstatistic_MaDon = itemView.findViewById(R.id.txt_customstatistic_MaDon)
            txt_customstatistic_NgayDat = itemView.findViewById(R.id.txt_customstatistic_NgayDat)
            txt_customstatistic_TenNV = itemView.findViewById(R.id.txt_customstatistic_TenNV)
            txt_customstatistic_BanDat = itemView.findViewById(R.id.txt_customstatistic_BanDat)
            txt_customstatistic_TongTien = itemView.findViewById(R.id.txt_customstatistic_TongTien)
            txt_customstatistic_TrangThai =
                itemView.findViewById(R.id.txt_customstatistic_TrangThai)
        }
    }

    init {
        nhanVienHelper = NhanVienHelper()
        banAnHelper = BanAnHelper()
    }
}