package com.tuyenhoang.app.fragments

import android.widget.GridView
import com.tuyenhoang.app.model.LoaiMon
import com.tuyenhoang.app.adapter.LoaiMonAdapter
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import android.app.Activity
import android.widget.Toast
import android.os.Bundle
import android.view.*
import com.tuyenhoang.app.R
import com.tuyenhoang.app.activity.TrangChuActivity
import android.widget.AdapterView.OnItemClickListener
import android.view.ContextMenu.ContextMenuInfo
import android.widget.AdapterView.AdapterContextMenuInfo
import com.tuyenhoang.app.activity.ThemLoaiMonAnActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.tuyenhoang.app.helper.LoaiMonHelper

class LoaiMonFragment : Fragment() {
    var gvCategory: GridView? = null
    var loaiMonList: List<LoaiMon>? = null
    private var loaiMonHelper:LoaiMonHelper?=null
    var adapter: LoaiMonAdapter? = null
    var maban = 0
    var resultLauncherCategory = registerForActivityResult(
        StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            val ktra = intent!!.getBooleanExtra("ktra", false)
            val chucnang = intent.getStringExtra("chucnang")
            if (chucnang == "themloai") {
                if (ktra) {
                    HienThiDSLoai()
                    Toast.makeText(activity, "Thêm thành công", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, "Thêm thất bại", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (ktra) {
                    HienThiDSLoai()
                    Toast.makeText(activity, "Sủa thành công", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, "sửa thất bại", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.loaimonan_layout, container, false)
        setHasOptionsMenu(true)
        (activity as TrangChuActivity?)!!.supportActionBar!!.title = "Quản lý loại món ăn"
        gvCategory = view.findViewById<View>(R.id.gvCategory) as GridView
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        loaiMonHelper = LoaiMonHelper()
        HienThiDSLoai()
        val bDataCategory = arguments
        if (bDataCategory != null) {
            maban = bDataCategory.getInt("maban")
        }
        gvCategory!!.onItemClickListener = OnItemClickListener { _, _, position, _ ->
            val maloai = loaiMonList!![position].maLoai
            val tenloai = loaiMonList!![position].tenLoai
            val monAnFragment = MonAnFragment()
            val bundle = Bundle()
            bundle.putInt("maloai", maloai)
            bundle.putString("tenloai", tenloai)
            bundle.putInt("maban", maban)
            monAnFragment.arguments = bundle
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.contentView, monAnFragment).addToBackStack("hienthiloai")
            transaction.commit()
        }
        registerForContextMenu(gvCategory!!)
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
        val maloai = loaiMonList!![vitri].maLoai
        when (id) {
            R.id.itEdit -> {
                val iEdit = Intent(activity, ThemLoaiMonAnActivity::class.java)
                iEdit.putExtra("maloai", maloai)
                resultLauncherCategory.launch(iEdit)
            }
            R.id.itDelete -> {
                val ktra = loaiMonHelper!!.deleteLoaiMon(maloai)
                if (ktra) {
                    HienThiDSLoai()
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
        val itAddCategory = menu.add(1, R.id.itAddCategory, 1, R.string.addCategory)
        itAddCategory.setIcon(R.drawable.ic_baseline_add_24)
        itAddCategory.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.itAddCategory -> {
                val intent = Intent(activity, ThemLoaiMonAnActivity::class.java)
                resultLauncherCategory.launch(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun HienThiDSLoai() {
        loaiMonList = loaiMonHelper!!.getAllLoaiMon()
        adapter = activity?.let { LoaiMonAdapter(it, R.layout.custom_layout_themloai, loaiMonList!!) }
        gvCategory!!.adapter = adapter
        adapter!!.notifyDataSetChanged()
    }
}