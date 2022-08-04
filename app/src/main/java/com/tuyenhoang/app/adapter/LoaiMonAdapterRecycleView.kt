package com.tuyenhoang.app.adapter

import android.content.Context
import com.tuyenhoang.app.model.LoaiMon
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.tuyenhoang.app.R

class LoaiMonAdapterRecycleView(
    var context: Context,
    var layout: Int,
    var loaiMonList: List<LoaiMon>
) : RecyclerView.Adapter<LoaiMonAdapterRecycleView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val loaiMon = loaiMonList[position]
        holder.txt_customcategory_TenLoai.text = loaiMon.tenLoai
        val categoryimage = loaiMon.hinhAnh
        val bitmap = BitmapFactory.decodeByteArray(categoryimage, 0, categoryimage!!.size)
        holder.img_customcategory_HinhLoai.setImageBitmap(bitmap)
    }

    override fun getItemCount(): Int {
        return loaiMonList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txt_customcategory_TenLoai: TextView
        var img_customcategory_HinhLoai: ImageView

        init {
            txt_customcategory_TenLoai = itemView.findViewById(R.id.txt_customcategory_TenLoai)
            img_customcategory_HinhLoai = itemView.findViewById(R.id.img_customcategory_HinhLoai)
        }
    }
}