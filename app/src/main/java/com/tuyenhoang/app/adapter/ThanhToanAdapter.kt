package com.tuyenhoang.app.adapter


import com.tuyenhoang.app.model.ThanhToan
import android.widget.BaseAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import android.view.LayoutInflater
import com.tuyenhoang.app.R
import android.widget.TextView
import android.graphics.BitmapFactory
import android.view.View
import de.hdodenhof.circleimageview.CircleImageView

class ThanhToanAdapter(var context: Context, var layout: Int, var thanhToanList: List<ThanhToan>) :
    BaseAdapter() {
    var viewHolder: ViewHolder? = null
    override fun getCount(): Int {
        return thanhToanList.size
    }

    override fun getItem(position: Int): Any {
        return thanhToanList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            viewHolder = ViewHolder()
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(layout, parent, false)
            viewHolder!!.img_custompayment_HinhMon =
                view.findViewById(R.id.img_custompayment_HinhMon)
            viewHolder!!.txt_custompayment_TenMon = view.findViewById(R.id.txt_custompayment_TenMon)
            viewHolder!!.txt_custompayment_SoLuong =
                view.findViewById(R.id.txt_custompayment_SoLuong)
            viewHolder!!.txt_custompayment_GiaTien =
                view.findViewById(R.id.txt_custompayment_GiaTien)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }
        val thanhToan = thanhToanList[position]
        viewHolder!!.txt_custompayment_TenMon!!.text = thanhToan.tenMon
        viewHolder!!.txt_custompayment_SoLuong!!.text = thanhToan.soLuong.toString()
        viewHolder!!.txt_custompayment_GiaTien!!.text= thanhToan.giaTien.toString()+ "VNĐ"
        val paymentimg = thanhToan.hinhAnh
        val bitmap = BitmapFactory.decodeByteArray(paymentimg, 0, paymentimg!!.size)
        viewHolder!!.img_custompayment_HinhMon!!.setImageBitmap(bitmap)
        return view!!
    }

    class ViewHolder {
        var img_custompayment_HinhMon: CircleImageView? = null
        var txt_custompayment_TenMon: TextView? = null
        var txt_custompayment_SoLuong: TextView? = null
        var txt_custompayment_GiaTien: TextView? = null
    }
}