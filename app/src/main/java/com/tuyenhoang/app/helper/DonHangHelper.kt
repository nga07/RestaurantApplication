package com.tuyenhoang.app.helper

import android.util.Log
import com.tuyenhoang.app.connect.ConnectSQl
import com.tuyenhoang.app.const.ColumnName
import com.tuyenhoang.app.model.DonHang
import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement

class DonHangHelper {
    private var conn: Connection? = null
    fun addDonHang(donHang: DonHang): Boolean {
        conn = ConnectSQl.instance.connectToSQlServer()
        return if (conn != null) {
            val query = "insert into ${ColumnName.ORDER} values (" +
                    "'${donHang.maBan}','${donHang.maNV}','${donHang.ngayDat}','${donHang.tongTien}','${donHang.tinhTrang}');"
            val statement: Statement = conn!!.createStatement()
            statement.executeUpdate(query)
            conn!!.close()
            true
        } else {
            false
        }

    }

    fun getAllDonHang(): MutableList<DonHang> {
        val donHangs = mutableListOf<DonHang>()
        conn = ConnectSQl.instance.connectToSQlServer()
        return if (conn != null) {
            val query = "select * from ${ColumnName.ORDER};"
            val statement: Statement = conn!!.createStatement()
            val result = statement.executeQuery(query)
            while (result.next()) {
                val donHang = DonHang()
                donHang.maDonDat = result.getInt("id")
                donHang.maBan = result.getInt(ColumnName.IDTABLE)
                donHang.tongTien = result.getString(ColumnName.TOTALMONEY)
                donHang.tinhTrang = result.getString(ColumnName.STATUSORDER)
                donHang.ngayDat = result.getString(ColumnName.DATEORDER)
                donHang.maNV = result.getInt(ColumnName.IDUSER)
                donHangs.add(donHang)
            }
            conn!!.close()

            donHangs
        } else {
            donHangs
        }

    }
    fun getDonHangToId(idNhanVien:Int): MutableList<DonHang> {
        val donHangs = mutableListOf<DonHang>()
        conn = ConnectSQl.instance.connectToSQlServer()
        return if (conn != null) {
            val query = "select * from ${ColumnName.ORDER} where ${ColumnName.IDUSER}='$idNhanVien';"
            val statement: Statement = conn!!.createStatement()
            val result = statement.executeQuery(query)
            while (result.next()) {
                val donHang = DonHang()
                donHang.maDonDat = result.getInt("id")
                donHang.maBan = result.getInt(ColumnName.IDTABLE)
                donHang.tongTien = result.getString(ColumnName.TOTALMONEY)
                donHang.tinhTrang = result.getString(ColumnName.STATUSORDER)
                donHang.ngayDat = result.getString(ColumnName.DATEORDER)
                donHang.maNV = result.getInt(ColumnName.IDUSER)
                donHangs.add(donHang)
            }
            conn!!.close()
            donHangs
        } else {
            donHangs
        }

    }
    fun getDonHangToDate(date: String): MutableList<DonHang> {
        val donHangs = mutableListOf<DonHang>()
        conn = ConnectSQl.instance.connectToSQlServer()
        return if (conn != null) {
            val query =
                "select * from ${ColumnName.ORDER} where ${ColumnName.DATEORDER} like '$date';"
            val statement: Statement = conn!!.createStatement()
            val result = statement.executeQuery(query)
            while (result.next()) {
                val donHang = DonHang()
                donHang.maDonDat = result.getInt("id")
                donHang.maBan = result.getInt(ColumnName.IDTABLE)
                donHang.tongTien = result.getString(ColumnName.TOTALMONEY)
                donHang.tinhTrang = result.getString(ColumnName.STATUSORDER)
                donHang.ngayDat = result.getString(ColumnName.DATEORDER)
                donHang.maNV = result.getInt(ColumnName.IDUSER)
                donHangs.add(donHang)
            }
            conn!!.close()
            donHangs
        } else {
            donHangs
        }

    }

    fun getIdDonHangToMaBan(id: Int, status: String): Int {
        conn = ConnectSQl.instance.connectToSQlServer()
        var idDonHang = 0
        return if (conn != null) {
            val query =
                "select * from ${ColumnName.ORDER} where ${ColumnName.IDTABLE}='$id' and CONVERT(VARCHAR,${ColumnName.STATUSORDER})='$status';"
            val statement: Statement = conn!!.createStatement()
            val result = statement.executeQuery(query)
            while (result.next()) {
                idDonHang = result.getInt("id")
            }
            conn!!.close()
            idDonHang
        } else {
            idDonHang
        }

    }

    fun updateTotalMoney(id: Int, totalMoney: String): Boolean {
        conn = ConnectSQl.instance.connectToSQlServer()
        return if (conn != null) {
            val query =
                "update ${ColumnName.ORDER} set ${ColumnName.TOTALMONEY}='$totalMoney' where id='$id';"
            val statement: Statement = conn!!.createStatement()
            statement.executeUpdate(query)
            conn!!.close()
            true
        } else {
            false
        }
    }

    fun updateStatusDonHang(idBan: Int, status: String): Boolean {
        conn = ConnectSQl.instance.connectToSQlServer()
        return if (conn != null) {
            val query =
                "update ${ColumnName.ORDER} set ${ColumnName.STATUSORDER}='$status' where ${ColumnName.IDTABLE}='$idBan';"
            val statement: Statement = conn!!.createStatement()
            statement.executeUpdate(query)
            conn!!.close()
            true
        } else {
            false
        }
    }
}