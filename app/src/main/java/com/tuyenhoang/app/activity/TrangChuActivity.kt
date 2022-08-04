package com.tuyenhoang.app.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationView
import com.tuyenhoang.app.R
import com.tuyenhoang.app.R.drawable
import com.tuyenhoang.app.fragments.*

class TrangChuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var drawerLayout: DrawerLayout? = null
    var navigationView: NavigationView? = null
    var toolbar: Toolbar? = null
    var fragmentManager: FragmentManager? = null
    var TXT_menu_tennv: TextView? = null
    var maquyen = 0
    var manhanvien=0
    var sharedPreferences: SharedPreferences? = null
    var im_icon: ImageView? = null
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menutrangchu_layout)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view_trangchu)
        toolbar = findViewById(R.id.toolbar)
        val view = navigationView!!.getHeaderView(0)
        TXT_menu_tennv = view.findViewById(R.id.txt_menu_tennv)
        im_icon = view.findViewById(R.id.menu_icon)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.opentoggle, R.string.closetoggle
        ) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
            }
        }
        drawerLayout!!.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        navigationView!!.setNavigationItemSelectedListener(this)
        val intent = intent
        val tendn = intent.getStringExtra("tendn")
        manhanvien=intent.getIntExtra("manv",0)
        sharedPreferences = getSharedPreferences("luuquyen", MODE_PRIVATE)
        maquyen = sharedPreferences!!.getInt("maquyen", 0)
        if (maquyen == 1) {
            TXT_menu_tennv!!.text = "Quản lý $tendn"
            im_icon!!.setBackgroundResource(drawable.iconmanager)
        } else {
            TXT_menu_tennv!!.text = "Nhân viên $tendn"
            im_icon!!.setBackgroundResource(drawable.nhanvienicon)
        }
        fragmentManager = supportFragmentManager
        val tranDisplayHome = fragmentManager!!.beginTransaction()
        val bundle = Bundle()
        bundle.putInt("maquyen", maquyen)
        bundle.putInt("manv",manhanvien)
        val trangChuFragment = TrangChuFragment()
        trangChuFragment.arguments = bundle;
        tranDisplayHome.replace(R.id.contentView, trangChuFragment)
        tranDisplayHome.commit()
        navigationView!!.setCheckedItem(R.id.nav_home)
        val menu = navigationView!!.menu
        val nav_dashboard = menu.findItem(R.id.nav_staff)
        if (maquyen == 2) {
            nav_dashboard.isVisible = false

        }
    }

    @SuppressLint("NonConstantResourceId")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.nav_home -> {
                val tranDisplayHome = fragmentManager!!.beginTransaction()
                val bundle = Bundle()
                bundle.putInt("maquyen", maquyen)
                bundle.putInt("manv",manhanvien)
                val trangChuFragment = TrangChuFragment()
                trangChuFragment.arguments=bundle
                tranDisplayHome.replace(R.id.contentView, trangChuFragment)
                tranDisplayHome.commit()
                navigationView!!.setCheckedItem(item.itemId)
                drawerLayout!!.closeDrawers()
            }
            R.id.nav_statistic -> {
                val bundle = Bundle()
                bundle.putInt("maquyen", maquyen)
                bundle.putInt("manv",manhanvien)
                val tranDisplayStatistic = fragmentManager!!.beginTransaction()
                val donHangFragment = DonHangFragment()
                donHangFragment.arguments=bundle
                tranDisplayStatistic.replace(R.id.contentView, donHangFragment)
                tranDisplayStatistic.commit()
                navigationView!!.setCheckedItem(item.itemId)
                drawerLayout!!.closeDrawers()
            }
            R.id.nav_table -> {
                val tranDisplayTable = fragmentManager!!.beginTransaction()
                val displayTableFragment = DisplayTableFragment()
                tranDisplayTable.replace(R.id.contentView, displayTableFragment)
                tranDisplayTable.commit()
                navigationView!!.setCheckedItem(item.itemId)
                drawerLayout!!.closeDrawers()
            }
            R.id.nav_category -> {
                val tranDisplayMenu = fragmentManager!!.beginTransaction()
                val loaiMonFragment = LoaiMonFragment()
                tranDisplayMenu.replace(R.id.contentView, loaiMonFragment)
                tranDisplayMenu.commit()
                navigationView!!.setCheckedItem(item.itemId)
                drawerLayout!!.closeDrawers()
            }
            R.id.nav_staff -> {
                val tranDisplayStaff = fragmentManager!!.beginTransaction()
                val displayStaffFragment = DisplayStaffFragment()
                tranDisplayStaff.replace(R.id.contentView, displayStaffFragment)
                tranDisplayStaff.commit()
                navigationView!!.setCheckedItem(item.itemId)
                drawerLayout!!.closeDrawers()
            }
            R.id.nav_logout -> {
                val intent = Intent(applicationContext, WelcomeActivity::class.java)
                startActivity(intent)
            }
        }
        return false
    }
}