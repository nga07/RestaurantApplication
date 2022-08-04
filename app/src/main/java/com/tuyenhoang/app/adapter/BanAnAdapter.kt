package com.tuyenhoang.app.adapter

import android.annotation.SuppressLint
import android.content.Context

import com.tuyenhoang.app.model.BanAn
import android.widget.BaseAdapter
import android.view.ViewGroup
import android.view.LayoutInflater
import com.tuyenhoang.app.R
import android.widget.TextView
import android.content.Intent
import com.tuyenhoang.app.activity.TrangChuActivity
import com.tuyenhoang.app.model.DonHang
import android.widget.Toast
import com.tuyenhoang.app.fragments.LoaiMonFragment
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.FragmentManager
import com.tuyenhoang.app.activity.ThanhToanActivity
import com.tuyenhoang.app.helper.BanAnHelper
import com.tuyenhoang.app.helper.DonHangHelper
import java.text.SimpleDateFormat
import java.util.*

class BanAnAdapter(var context: Context, var layout: Int, var banAnList: List<BanAn>) :
    BaseAdapter(), View.OnClickListener {
    var viewHolder: ViewHolder? = null
    private var banAnHelper: BanAnHelper
    private var donHangHelper: DonHangHelper
    var fragmentManager: FragmentManager
    override fun getCount(): Int {
        return banAnList.size
    }

    override fun getItem(position: Int): Any {
        return banAnList[position]
    }

    override fun getItemId(position: Int): Long {
        return banAnList[position].maBan.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            viewHolder = ViewHolder()
            view = inflater.inflate(layout, parent, false)
            viewHolder!!.imgBanAn = view.findViewById(R.id.img_customtable_BanAn)
            viewHolder!!.imgGoiMon = view.findViewById(R.id.img_customtable_GoiMon)
            viewHolder!!.imgThanhToan = view.findViewById(R.id.img_customtable_ThanhToan)
            viewHolder!!.imgAnNut = view.findViewById(R.id.img_customtable_AnNut)
            viewHolder!!.txtTenBanAn = view.findViewById(R.id.txt_customtable_TenBanAn)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }
        if (banAnList[position].isDuocChon) {
            HienThiButton()
        } else {
            AnButton()
        }
        val banAn = banAnList[position]
        val kttinhtrang = banAnHelper.getStatusBanAn(banAn.maBan)
        if (kttinhtrang == "true") {
            viewHolder!!.imgBanAn!!.setImageResource(R.drawable.ic_baseline_airline_seat_legroom_normal_40)
        } else {
            viewHolder!!.imgBanAn!!.setImageResource(R.drawable.ic_baseline_event_seat_40)
        }
        viewHolder!!.txtTenBanAn!!.text = banAn.tenBan
        viewHolder!!.imgBanAn!!.tag = position
        viewHolder!!.imgBanAn!!.setOnClickListener(this)
        viewHolder!!.imgGoiMon!!.setOnClickListener(this)
        viewHolder!!.imgThanhToan!!.setOnClickListener(this)
        viewHolder!!.imgAnNut!!.setOnClickListener(this)
        return view!!
    }

    @SuppressLint("SimpleDateFormat")
    override fun onClick(v: View) {
        val id = v.id
        val getIHome = (context as TrangChuActivity).intent

        viewHolder = (v.parent as View).tag as ViewHolder
        val manv = getIHome.getIntExtra("manv", 0)
        val vitri1 = viewHolder!!.imgBanAn!!.tag as Int
        val maban = banAnList[vitri1].maBan
        val tenban = banAnList[vitri1].tenBan
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        val ngaydat = dateFormat.format(calendar.time)
        when (id) {
            R.id.img_customtable_BanAn -> {
                val vitri = v.tag as Int
                banAnList[vitri].isDuocChon = true
                Log.d("Tuyen123",manv.toString())
                HienThiButton()
            }
            R.id.img_customtable_AnNut -> AnButton()
            R.id.img_customtable_GoiMon -> {
                val tinhtrang = banAnHelper.getStatusBanAn(maban)
                if (tinhtrang == "false") {
                    val donHang = DonHang()
                    donHang.maBan = maban
                    donHang.maNV = manv
                    donHang.ngayDat = ngaydat
                    donHang.tinhTrang = "false"
                    donHang.tongTien = "0"
                    val ktra = donHangHelper.addDonHang(donHang)
                    banAnHelper.updateStatusBanAn(maban, "true")
                    if (ktra) {
                        Toast.makeText(
                            context,
                            context.resources.getString(R.string.add_failed),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                val transaction = fragmentManager.beginTransaction()
                val loaiMonFragment = LoaiMonFragment()
                val bDataCategory = Bundle()
                bDataCategory.putInt("maban", maban)
                loaiMonFragment.arguments = bDataCategory
                transaction.replace(R.id.contentView, loaiMonFragment)
                    .addToBackStack("hienthibanan")
                transaction.commit()
            }
            R.id.img_customtable_ThanhToan -> {
                val iThanhToan = Intent(context, ThanhToanActivity::class.java)
                iThanhToan.putExtra("maban", maban)
                iThanhToan.putExtra("tenban", tenban)
                iThanhToan.putExtra("ngaydat", ngaydat)
                iThanhToan.putExtra("manv",manv)
                context.startActivity(iThanhToan)
            }
        }
    }

    private fun HienThiButton() {
        viewHolder!!.imgGoiMon!!.visibility = View.VISIBLE
        viewHolder!!.imgThanhToan!!.visibility = View.VISIBLE
        viewHolder!!.imgAnNut!!.visibility = View.VISIBLE
    }

    private fun AnButton() {
        viewHolder!!.imgGoiMon!!.visibility = View.INVISIBLE
        viewHolder!!.imgThanhToan!!.visibility = View.INVISIBLE
        viewHolder!!.imgAnNut!!.visibility = View.INVISIBLE
    }

    inner class ViewHolder {
        var imgBanAn: ImageView? = null
        var imgGoiMon: ImageView? = null
        var imgThanhToan: ImageView? = null
        var imgAnNut: ImageView? = null
        var txtTenBanAn: TextView? = null
    }

    init {
        banAnHelper = BanAnHelper()
        donHangHelper = DonHangHelper()
        fragmentManager = (context as TrangChuActivity).supportFragmentManager
    }
}