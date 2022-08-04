package com.tuyenhoang.app.helper

import com.tuyenhoang.app.connect.ConnectSQl
import com.tuyenhoang.app.const.ColumnName
import com.tuyenhoang.app.model.ThanhToan
import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement

class ThanhToanHelper {
    private var conn:Connection?=null
    fun getAllMonToMaDon(idDonHang:Int):MutableList<ThanhToan>{
        val thanhToans= mutableListOf<ThanhToan>()
        conn=ConnectSQl.instance.connectToSQlServer()
        return if (conn!=null){
            val query="select * from ${ColumnName.INFOORDER} ctdd, ${ColumnName.DISH} mon where ctdd.${ColumnName.IDDISH} = mon.id and ctdd.id='$idDonHang';"
            val statement:Statement=conn!!.createStatement()
            val result=statement.executeQuery(query)
            while (result.next()){
                val thanhToan=ThanhToan()
                thanhToan.soLuong=result.getInt(ColumnName.AMOUNT)
                thanhToan.giaTien=result.getString(ColumnName.MONEY)
                thanhToan.tenMon=result.getString(ColumnName.NAMEDISH)
                thanhToan.hinhAnh=result.getBytes(ColumnName.IMAGE)
                thanhToans.add(thanhToan)
            }
            thanhToans
        }else{
            thanhToans
        }
    }
}