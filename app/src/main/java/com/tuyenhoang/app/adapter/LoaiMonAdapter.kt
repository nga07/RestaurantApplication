package com.tuyenhoang.app.adapter

import android.content.Context
import com.tuyenhoang.app.model.LoaiMon
import android.widget.BaseAdapter
import android.view.ViewGroup
import android.view.LayoutInflater
import com.tuyenhoang.app.R
import android.widget.TextView
import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageView

class LoaiMonAdapter(var context: Context, var layout: Int, var loaiMonList: List<LoaiMon>) :
    BaseAdapter() {
    var viewHolder: ViewHolder? = null
    override fun getCount(): Int {
        return loaiMonList.size
    }

    override fun getItem(position: Int): Any {
        return loaiMonList[position]
    }

    override fun getItemId(position: Int): Long {
        return loaiMonList[position].maLoai.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            viewHolder = ViewHolder()
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(layout, parent, false)
            viewHolder!!.img_customcategory_HinhLoai =
                view.findViewById(R.id.img_customcategory_HinhLoai)
            viewHolder!!.txt_customcategory_TenLoai =
                view.findViewById(R.id.txt_customcategory_TenLoai)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }
        val loaiMon = loaiMonList[position]
        viewHolder!!.txt_customcategory_TenLoai!!.text = loaiMon.tenLoai
        val categoryimage = loaiMon.hinhAnh
        val bitmap = BitmapFactory.decodeByteArray(categoryimage, 0, categoryimage!!.size)
        viewHolder!!.img_customcategory_HinhLoai!!.setImageBitmap(bitmap)
        return view!!
    }

    inner class ViewHolder {
        var txt_customcategory_TenLoai: TextView? = null
        var img_customcategory_HinhLoai: ImageView? = null
    }
}