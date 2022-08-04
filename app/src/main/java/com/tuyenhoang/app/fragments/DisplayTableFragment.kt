package com.tuyenhoang.app.fragments

import android.widget.GridView
import com.tuyenhoang.app.model.BanAn
import com.tuyenhoang.app.adapter.BanAnAdapter
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import android.app.Activity
import android.widget.Toast
import com.tuyenhoang.app.R
import android.os.Bundle
import android.view.*
import com.tuyenhoang.app.activity.TrangChuActivity
import android.view.ContextMenu.ContextMenuInfo
import android.widget.AdapterView.AdapterContextMenuInfo
import com.tuyenhoang.app.activity.ChinhSuaBanAnActivity
import androidx.activity.result.ActivityResult
import androidx.fragment.app.Fragment
import com.tuyenhoang.app.activity.ThemBanAnActivity
import com.tuyenhoang.app.helper.BanAnHelper

class DisplayTableFragment : Fragment() {
    var GVDisplayTable: GridView? = null
    var banAnList:List<BanAn>?=null
    private var banAnHelper:BanAnHelper?=null
    var banAnAdapter: BanAnAdapter? = null
    var resultLauncherAdd = registerForActivityResult(
        StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            val ktra = intent!!.getBooleanExtra("ketquathem", false)
            if (ktra) {
                HienThiDSBan()
                Toast.makeText(activity, "Thêm thành công", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "Thêm thất bại", Toast.LENGTH_SHORT).show()
            }
        }
    }
    var resultLauncherEdit = registerForActivityResult(
        StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            val ktra = intent!!.getBooleanExtra("ketquasua", false)
            if (ktra) {
                HienThiDSBan()
                Toast.makeText(
                    activity,
                    resources.getString(R.string.edit_sucessful),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    activity,
                    resources.getString(R.string.edit_failed),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.banan_layout, container, false)
        setHasOptionsMenu(true)
        (activity as TrangChuActivity?)!!.supportActionBar!!.title = "Quản lý bàn ăn"
        GVDisplayTable = view.findViewById<View>(R.id.gvDisplayTable) as GridView
        banAnHelper = BanAnHelper()
        HienThiDSBan()
        registerForContextMenu(GVDisplayTable!!)
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
        val maban = banAnList!![vitri].maBan
        when (id) {
            R.id.itEdit -> {
                val intent = Intent(activity, ChinhSuaBanAnActivity::class.java)
                intent.putExtra("maban", maban)
                resultLauncherEdit.launch(intent)
            }
            R.id.itDelete -> {
                val ktraxoa = banAnHelper!!.deleteBanAn(maban)
                if (ktraxoa) {
                    HienThiDSBan()
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
        return super.onContextItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        val itAddTable = menu.add(1, R.id.itAddTable, 1, R.string.addTable)
        itAddTable.setIcon(R.drawable.ic_baseline_add_24)
        itAddTable.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.itAddTable -> {
                val iAddTable = Intent(activity, ThemBanAnActivity::class.java)
                resultLauncherAdd.launch(iAddTable)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        banAnAdapter!!.notifyDataSetChanged()
    }

    private fun HienThiDSBan() {
        banAnList = banAnHelper!!.getAllBanAn()
        banAnAdapter = activity?.let { BanAnAdapter(it, R.layout.custom_layout_displaytable, banAnList!!) }
        GVDisplayTable!!.adapter = banAnAdapter
        banAnAdapter!!.notifyDataSetChanged()
    }
}