package com.tuyenhoang.app.helper

import com.tuyenhoang.app.connect.ConnectSQl
import com.tuyenhoang.app.const.ColumnName
import com.tuyenhoang.app.model.ChiTietDonHang
import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement

class ChiTietDonHangHelper {
    private var conn:Connection?=null
    fun checkTonTaiMonAn(idChiTietDonHang:Int,idMon:Int):Boolean{
        conn=ConnectSQl.instance.connectToSQlServer()
        return if (conn!=null){
            var id=0
            val query="select * from ${ColumnName.INFOORDER} where ${ColumnName.IDDISH}='$idMon' and " +
                    "id='$idChiTietDonHang';"
            val statement: Statement =conn!!.createStatement()
            val result=statement.executeQuery(query)
            while (result.next()){
                id=result.getInt("id")
            }
            conn!!.close()
            id!=0
        }else{
            false
        }
    }
    fun getAmountToId(idChiTietDonHang:Int,idMon:Int):Int{
        conn=ConnectSQl.instance.connectToSQlServer()
        var amount=0
        return if (conn!=null){
            val query="select * from ${ColumnName.INFOORDER} where ${ColumnName.IDDISH}='$idMon' and " +
                    "id='$idChiTietDonHang';"
            val statement: Statement =conn!!.createStatement()
            val result=statement.executeQuery(query)
            while (result.next()){
                amount=result.getInt(ColumnName.AMOUNT)
            }
            conn!!.close()
            amount
        }else{
            amount
        }
    }
    fun updateAmount(chiTietDonHang: ChiTietDonHang):Boolean{
        conn=ConnectSQl.instance.connectToSQlServer()
        return if (conn!=null){
            val query="update ${ColumnName.INFOORDER} set ${ColumnName.IDDISH}='${chiTietDonHang.maMon}' where id='${chiTietDonHang.maDonDat}';"
            val statement:Statement=conn!!.createStatement()
            statement.executeUpdate(query)
            conn!!.close()
            true
        }else{
            false
        }
    }
    fun addChiTietDonHang(chiTietDonHang: ChiTietDonHang):Boolean{
        conn=ConnectSQl.instance.connectToSQlServer()
        return if (conn!=null){
            val query="insert into ${ColumnName.INFOORDER} values ('${chiTietDonHang.maDonDat}','${chiTietDonHang.maMon}','${chiTietDonHang.soLuong}');"
            val statement:Statement=conn!!.createStatement()
            statement.executeUpdate(query)
            conn!!.close()
            true
        }else{
            false
        }
    }
}