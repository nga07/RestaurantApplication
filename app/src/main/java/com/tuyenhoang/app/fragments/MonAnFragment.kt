package com.tuyenhoang.app.fragments

import android.widget.GridView
import com.tuyenhoang.app.model.MonAn
import com.tuyenhoang.app.adapter.MonAnAdapter
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import android.app.Activity
import android.widget.Toast
import android.os.Bundle
import android.util.Log
import android.view.*
import com.tuyenhoang.app.R
import com.tuyenhoang.app.activity.TrangChuActivity
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView
import com.tuyenhoang.app.activity.ThemSoLuongMonActivity
import android.view.ContextMenu.ContextMenuInfo
import android.widget.AdapterView.AdapterContextMenuInfo
import com.tuyenhoang.app.activity.ThemMonAnActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.tuyenhoang.app.adapter.NhanVienAdapter
import com.tuyenhoang.app.helper.MonAnHelper
import com.tuyenhoang.app.model.NhanVien
import java.util.*
import kotlin.Comparator

class MonAnFragment : Fragment() {
    var maloai = 0
    var maban = 0
    var tenloai: String? = null
    var tinhtrang: String? = null
    var gvDisplayMenu: GridView? = null
    private var monAnHelper:MonAnHelper?=null
    var monAnList= mutableListOf<MonAn>()
    var monAnAdapter: MonAnAdapter? = null
    private var countIsClick=0
    var resultLauncherMenu = registerForActivityResult(
        StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            val ktra = intent!!.getBooleanExtra("ktra", false)
            val chucnang = intent.getStringExtra("chucnang")
            if (chucnang == "themmon") {
                if (ktra) {
                    HienThiDSMon()
                    Toast.makeText(activity, "Thêm thành công", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, "Thêm thất bại", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (ktra) {
                    HienThiDSMon()
                    Toast.makeText(activity, "Sửa thành công", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, "Sửa thất bại", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.monan_layout, container, false)
        (activity as TrangChuActivity?)!!.supportActionBar!!.title = "Quản lý món ăn"
        monAnHelper = MonAnHelper()
        gvDisplayMenu = view.findViewById<View>(R.id.gvDisplayMenu) as GridView
        val bundle = arguments
        if (bundle != null) {
            maloai = bundle.getInt("maloai")
            tenloai = bundle.getString("tenloai")
            maban = bundle.getInt("maban")
            HienThiDSMon()
            gvDisplayMenu!!.onItemClickListener =
                OnItemClickListener { _: AdapterView<*>?, _: View?, position: Int, id: Long ->
                    tinhtrang = monAnList[position].tinhTrang
                    if (maban != 0) {
                        if (tinhtrang == "true") {
                            val iAmount = Intent(activity, ThemSoLuongMonActivity::class.java)
                            iAmount.putExtra("maban", maban)
                            iAmount.putExtra("mamon", monAnList[position].maMon)
                            startActivity(iAmount)
                        } else {
                            Toast.makeText(
                                activity,
                                "Món đã hết, không thể thêm",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
        }
        setHasOptionsMenu(true)
        registerForContextMenu(gvDisplayMenu!!)
        view.setOnKeyListener { v: View?, keyCode: Int, event: KeyEvent ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                parentFragmentManager.popBackStack(
                    "hienthiloai",
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
            }
            false
        }
        return view
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        requireActivity().menuInflater.inflate(R.menu.edit_context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        val menuInfo = item.menuInfo as AdapterContextMenuInfo
        val vitri = menuInfo.position
        val mamon = monAnList[vitri].maMon
        when (id) {
            R.id.itEdit -> {
                val iEdit = Intent(activity, ThemMonAnActivity::class.java)
                iEdit.putExtra("mamon", mamon)
                iEdit.putExtra("maLoai", maloai)
                iEdit.putExtra("tenLoai", tenloai)
                resultLauncherMenu.launch(iEdit)
            }
            R.id.itDelete -> {
                val ktra = monAnHelper!!.deleteMon(mamon)
                if (ktra) {
                    HienThiDSMon()
                    Toast.makeText(
                        activity,
                        requireActivity().resources.getString(R.string.delete_sucessful),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        activity,
                        requireActivity().resources.getString(R.string.delete_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        val itAddMenu = menu.add(1, R.id.itAddMenu, 1, R.string.addMenu)
        itAddMenu.setIcon(R.drawable.ic_baseline_add_24)
        itAddMenu.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        val itSort=menu.add(1,R.id.itSort,1,"Sắp xếp")
        itSort.setIcon(R.drawable.ic_baseline_sort_24)
        itSort.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.itAddMenu -> {
                val intent = Intent(activity, ThemMonAnActivity::class.java)
                intent.putExtra("maLoai", maloai)
                intent.putExtra("tenLoai", tenloai)
                resultLauncherMenu.launch(intent)
            }
            R.id.itSort->{
                sortData()
                countIsClick++
                Log.d("Click",countIsClick.toString())
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun sortData() {
        val listMon=monAnHelper!!.getAllMonToLoaiMon(maloai)

        if (countIsClick==0){
            listMon.sortWith { lhs, rhs ->
                if (lhs.count > rhs.count) {
                    -1
                } else if (lhs.count < rhs.count) {
                    1
                } else {
                    0
                }
            }

        }
        if (countIsClick==1){
            listMon.sortWith { lhs, rhs ->
                if (lhs.giaTien!! > rhs.giaTien!!) {
                    -1
                } else if (lhs.giaTien!! < rhs.giaTien!!) {
                    1
                } else {
                    0
                }
            }

        }
        if (countIsClick==2){
            listMon.sortWith { lhs, rhs ->
                if (lhs.tenMon!! > rhs.tenMon!!) {
                    -1
                } else if (lhs.tenMon!! < rhs.tenMon!!) {
                    1
                } else {
                    0
                }
            }
            countIsClick=-1
        }

        val arrayAdapter = activity?.let { MonAnAdapter(it,R.layout.custom_layout_themmonan, listMon) }
        gvDisplayMenu!!.adapter = arrayAdapter
        arrayAdapter!!.notifyDataSetChanged()
    }

    private fun HienThiDSMon() {
        monAnList = monAnHelper!!.getAllMonToLoaiMon(maloai)
        monAnAdapter = activity?.let { MonAnAdapter(it, R.layout.custom_layout_themmonan, monAnList!!) }
        gvDisplayMenu!!.adapter = monAnAdapter
        monAnAdapter!!.notifyDataSetChanged()
    }
}