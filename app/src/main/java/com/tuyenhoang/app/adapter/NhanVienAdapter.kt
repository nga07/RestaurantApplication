package com.tuyenhoang.app.adapter

import android.annotation.SuppressLint
import android.content.Context
import com.tuyenhoang.app.model.NhanVien
import android.widget.BaseAdapter
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.tuyenhoang.app.R
import android.widget.TextView

class NhanVienAdapter(var context: Context, var layout: Int, var nhanViens: List<NhanVien>) :
    BaseAdapter() {
    private var viewHolder: ViewHolder? = null
    override fun getCount(): Int {
        return nhanViens.size
    }

    override fun getItem(position: Int): Any {
        return nhanViens[position]
    }

    override fun getItemId(position: Int): Long {
        return nhanViens[position].id.toLong()
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            viewHolder = ViewHolder()
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(layout, parent, false)
            viewHolder!!.img_customstaff_HinhNV = view.findViewById(R.id.img_customstaff_HinhNV)
            viewHolder!!.txt_customstaff_TenNV = view.findViewById(R.id.txt_customstaff_TenNV)
            viewHolder!!.txt_customstaff_TenQuyen = view.findViewById(R.id.txt_customstaff_TenQuyen)
            viewHolder!!.txt_customstaff_SDT = view.findViewById(R.id.txt_customstaff_SDT)
            viewHolder!!.txt_customstaff_Email = view.findViewById(R.id.txt_customstaff_Email)
            viewHolder!!.txt_customstaff_DoanhThu=view.findViewById(R.id.txt_customstaff_Doanhthu)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }
        val nhanVien = nhanViens[position]
        viewHolder!!.txt_customstaff_TenNV!!.text = "Tên: "+nhanVien.fullName
        var quyen = ""
        if (nhanVien.permission ==1) {
            quyen = "Quản lý"
            viewHolder!!.img_customstaff_HinhNV!!.setBackgroundResource(R.drawable.iconmanager)
        }
        if (nhanVien.permission == 2) {
            quyen = "Nhân viên"
            viewHolder!!.img_customstaff_HinhNV!!.setBackgroundResource(R.drawable.nhanvienicon)
        }
        viewHolder!!.txt_customstaff_TenQuyen!!.text = "Chức vụ: $quyen"
        viewHolder!!.txt_customstaff_SDT!!.text = "Số điện thoại: "+nhanVien.numberPhone
        viewHolder!!.txt_customstaff_Email!!.text = "Email: "+nhanVien.email
        viewHolder!!.txt_customstaff_DoanhThu!!.text= "Doanh thu: "+nhanVien.moneyOrder.toString()+" VND"
        return view!!
    }

    class ViewHolder {
        var img_customstaff_HinhNV: ImageView? = null
        var txt_customstaff_TenNV: TextView? = null
        var txt_customstaff_TenQuyen: TextView? = null
        var txt_customstaff_SDT: TextView? = null
        var txt_customstaff_Email: TextView? = null
        var txt_customstaff_DoanhThu:TextView?=null
    }

}