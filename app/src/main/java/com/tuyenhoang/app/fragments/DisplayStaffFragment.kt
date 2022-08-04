package com.tuyenhoang.app.fragments


import android.R.attr.data
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.ContextMenu.ContextMenuInfo
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.Fragment
import com.tuyenhoang.app.R
import com.tuyenhoang.app.activity.ThemNhanVienActivity
import com.tuyenhoang.app.activity.TrangChuActivity
import com.tuyenhoang.app.adapter.NhanVienAdapter
import com.tuyenhoang.app.helper.NhanVienHelper
import com.tuyenhoang.app.model.NhanVien
import java.util.*


class DisplayStaffFragment : Fragment() {
    var gvStaff: GridView? = null
    private var nhanVienHelper:NhanVienHelper?=null
    var nhanViens: List<NhanVien>? = null
    var nhanVienAdapter: NhanVienAdapter? = null
    var isClick=true
    var resultLauncherAdd = registerForActivityResult(
        StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            val ktra = intent!!.getBooleanExtra("ketquaktra", false)
            val chucnang = intent.getStringExtra("chucnang")
            if (chucnang == "themnv") {
                if (ktra) {
                    HienThiDSNV()
                    Toast.makeText(activity, "Thêm thành công", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, "Thêm thất bại", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (ktra) {
                    HienThiDSNV()
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
        val view = inflater.inflate(R.layout.nhanvien_layout, container, false)
        (activity as TrangChuActivity?)!!.supportActionBar!!.title = "Quản lý nhân viên"
        setHasOptionsMenu(true)
        gvStaff = view.findViewById<View>(R.id.gvStaff) as GridView
        nhanVienHelper = NhanVienHelper()
        HienThiDSNV()
        registerForContextMenu(gvStaff!!)
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
        val manv = nhanViens!![vitri].id
        when (id) {
            R.id.itEdit -> {
                val iEdit = Intent(activity, ThemNhanVienActivity::class.java)
                iEdit.putExtra("manv", manv)
                resultLauncherAdd.launch(iEdit)
            }
            R.id.itDelete -> {
                val ktra = nhanVienHelper!!.deleteNhanVien(manv)
                if (ktra) {
                    HienThiDSNV()
                    Toast.makeText(activity, requireActivity().resources.getString(R.string.delete_sucessful), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, requireActivity().resources.getString(R.string.delete_failed), Toast.LENGTH_SHORT).show()
                }
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        val itAddStaff = menu.add(1, R.id.itAddStaff, 1, "Thêm nhân viên")
        val itSort=menu.add(1,R.id.itSort,1,"Sắp xếp")
        itSort.setIcon(R.drawable.ic_baseline_sort_24)
        itSort.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        itAddStaff.setIcon(R.drawable.ic_baseline_add_24)
        itAddStaff.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.itAddStaff -> {
                val iDangky = Intent(activity, ThemNhanVienActivity::class.java)
                resultLauncherAdd.launch(iDangky)
            }
            R.id.itSort ->{
                sortData()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun sortData() {
        val listNhanVien=nhanVienHelper!!.getAllNhanVien()
        if (isClick){
            Collections.sort(
                listNhanVien,
                Comparator<NhanVien> { lhs, rhs ->
                    if (lhs.moneyOrder > rhs.moneyOrder) -1 else if (lhs.moneyOrder < rhs.moneyOrder) 1 else 0
                })
            isClick=!isClick
        }
        else{
            Collections.sort(
                listNhanVien,
                Comparator<NhanVien> { lhs, rhs ->
                    if (lhs.moneyOrder < rhs.moneyOrder) -1 else if (lhs.moneyOrder > rhs.moneyOrder) 1 else 0
                })
            isClick=!isClick
        }


        val arrayAdapter = activity?.let { NhanVienAdapter(it,R.layout.custom_layout_nhanvien, listNhanVien) }
        gvStaff!!.adapter = arrayAdapter
        arrayAdapter!!.notifyDataSetChanged()
    }

    private fun HienThiDSNV() {
        nhanViens = nhanVienHelper!!.getAllNhanVien()
        nhanVienAdapter = activity?.let { NhanVienAdapter(it,R.layout.custom_layout_nhanvien, nhanViens!!) }
        gvStaff!!.adapter = nhanVienAdapter
        nhanVienAdapter!!.notifyDataSetChanged()
    }
}