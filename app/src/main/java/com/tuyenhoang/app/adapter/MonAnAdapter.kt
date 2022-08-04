package com.tuyenhoang.app.adapter

import com.tuyenhoang.app.model.MonAn
import android.widget.BaseAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import android.view.LayoutInflater
import com.tuyenhoang.app.R
import android.widget.TextView
import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageView
import java.lang.NullPointerException

class MonAnAdapter(var context: Context, var layout: Int, var monAnList: List<MonAn>) :
    BaseAdapter() {
    var viewholder: Viewholder? = null
    override fun getCount(): Int {
        return monAnList.size
    }

    override fun getItem(position: Int): Any {
        return monAnList[position]
    }

    override fun getItemId(position: Int): Long {
        return monAnList[position].maMon.toLong()
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            viewholder = Viewholder()
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(layout, parent, false)
            viewholder!!.img_custommenu_HinhMon = view.findViewById(R.id.img_custommenu_HinhMon)
            viewholder!!.txt_custommenu_TenMon = view.findViewById(R.id.txt_custommenu_TenMon)
            viewholder!!.txt_custommenu_TinhTrang = view.findViewById(R.id.txt_custommenu_TinhTrang)
            viewholder!!.txt_custommenu_GiaTien = view.findViewById(R.id.txt_custommenu_GiaTien)
            viewholder!!.txt_custommenu_Count=view.findViewById(R.id.txt_custommenu_Count)
            view.tag = viewholder
        } else {
            viewholder = view.tag as Viewholder
        }
        val monAn = monAnList[position]
        viewholder!!.txt_custommenu_TenMon!!.text = monAn.tenMon
        viewholder!!.txt_custommenu_GiaTien!!.text = monAn.giaTien + " VNĐ"
        viewholder!!.txt_custommenu_Count!!.text="Đã bán: "+monAn.count
        try {
            if (monAn.tinhTrang == "true") {
                viewholder!!.txt_custommenu_TinhTrang!!.text = "Còn món"
            } else {
                viewholder!!.txt_custommenu_TinhTrang!!.text = "Hết món"
            }
        } catch (e: NullPointerException) {
            viewholder!!.txt_custommenu_TinhTrang!!.text = "Còn món"
        }
        if (monAn.hinhAnh != null) {
            val menuimage = monAn.hinhAnh
            val bitmap = BitmapFactory.decodeByteArray(menuimage, 0, menuimage!!.size)
            viewholder!!.img_custommenu_HinhMon!!.setImageBitmap(bitmap)
        } else {
            viewholder!!.img_custommenu_HinhMon!!.setImageResource(R.drawable.supcahoi)
        }
        return view!!
    }

    class Viewholder {
        var img_custommenu_HinhMon: ImageView? = null
        var txt_custommenu_TenMon: TextView? = null
        var txt_custommenu_GiaTien: TextView? = null
        var txt_custommenu_TinhTrang: TextView? = null
        var txt_custommenu_Count:TextView?=null
    }
}