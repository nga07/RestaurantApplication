package com.tuyenhoang.app.helper

import android.util.Log
import com.tuyenhoang.app.connect.ConnectSQl
import com.tuyenhoang.app.const.ColumnName
import com.tuyenhoang.app.model.MonAn
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import java.sql.Statement

class MonAnHelper {
    private var conn :Connection?=null
    fun addMon(monAn:MonAn):Boolean{
        conn= ConnectSQl.instance.connectToSQlServer()
        return if (conn!=null){
            val query =
                "insert into ${ColumnName.DISH} (${ColumnName.NAMEDISH},${ColumnName.MONEY},${ColumnName.STATUS},${ColumnName.IMAGE},${ColumnName.IDTYPEDISH},${ColumnName.COUNTDISH}) values (?,?,?,?,?,?);"
            val preparedStatement: PreparedStatement = conn!!.prepareStatement(query)
            preparedStatement.setString(1, monAn.tenMon)
            preparedStatement.setString(2, monAn.giaTien)
            preparedStatement.setString(3, monAn.tinhTrang)
            preparedStatement.setBytes(4, monAn.hinhAnh)
            preparedStatement.setInt(5, monAn.maLoai)
            preparedStatement.setString(6, monAn.count.toString())
            preparedStatement.execute()
            conn!!.close()
            true
        }else{
            false
        }
    }
    fun deleteMon(idMon:Int):Boolean{
        conn= ConnectSQl.instance.connectToSQlServer()
        return if (conn!=null){
            val query= "delete from ${ColumnName.DISH} where id='$idMon';"
            val statement: Statement = conn!!.createStatement()
            statement.executeUpdate(query)
            conn!!.close()
            true
        }else{
            false
        }
    }
    fun updateMon(monAn:MonAn,idMon:Int):Boolean{
        conn= ConnectSQl.instance.connectToSQlServer()
        return if (conn!=null){
            val query = "update ${ColumnName.DISH} set ${ColumnName.NAMEDISH}= ? , " +
                    "${ColumnName.MONEY}= ? , " +
                    "${ColumnName.STATUS}= ? , " +
                    "${ColumnName.IMAGE}= ? , " +
                    "${ColumnName.IDTYPEDISH}= ? where id='$idMon';"
            val preparedStatement: PreparedStatement = conn!!.prepareStatement(query)
            preparedStatement.setString(1, monAn.tenMon)
            preparedStatement.setString(2, monAn.giaTien)
            preparedStatement.setString(3, monAn.tinhTrang)
            preparedStatement.setBytes(4, monAn.hinhAnh)
            preparedStatement.setInt(5, monAn.maLoai)
            preparedStatement.execute()
            conn!!.close()
            true
        }else{
            false
        }
    }
    fun getAllMonToLoaiMon(idLoaiMon:Int):MutableList<MonAn>{
        val monAns= mutableListOf<MonAn>()
        conn= ConnectSQl.instance.connectToSQlServer()
        return if (conn!=null){
            val query = "select * from ${ColumnName.DISH} where ${ColumnName.IDTYPEDISH}='$idLoaiMon';"
            val statement: Statement = conn!!.createStatement()
            val result = statement.executeQuery(query)
            while (result.next()) {
                val monAn = MonAn()
                monAn.maMon = result.getInt("id")
                monAn.maLoai = result.getInt(ColumnName.IDTYPEDISH)
                monAn.tenMon = result.getString(ColumnName.NAMEDISH)
                monAn.hinhAnh = result.getBytes(ColumnName.IMAGE)
                monAn.tinhTrang = result.getString(ColumnName.STATUS)
                monAn.giaTien = result.getString(ColumnName.MONEY)
                monAn.count=result.getString(ColumnName.COUNTDISH).toInt()
                monAns.add(monAn)
            }
            conn!!.close()
            monAns
        }else{
            monAns
        }


    }
    fun getMonToId(idMonAn:Int):MonAn{
        conn = ConnectSQl.instance.connectToSQlServer()
        val monAn = MonAn()
        return if (conn!=null){
            val query = "select * from ${ColumnName.DISH} where id='$idMonAn';"
            val statement = conn!!.createStatement()
            val result = statement.executeQuery(query)
            while (result.next()) {
                monAn.maMon = result.getInt("id")
                monAn.tenMon = result.getString("namedish")
                monAn.giaTien = result.getString("money")
                monAn.tinhTrang = result.getString("status")
                monAn.hinhAnh = result.getBytes("image")
                monAn.maLoai = result.getInt("idtypedish")
            }
            conn!!.close()
            monAn
        }else{
            monAn
        }
    }
    fun getCountMonToId(idMon: Int):Int{
        conn = ConnectSQl.instance.connectToSQlServer()
        var count=0
        return if (conn!=null){
            val query = "select * from ${ColumnName.DISH} where id='$idMon';"
            val statement = conn!!.createStatement()
            val result = statement.executeQuery(query)
            while (result.next()) {
                count=result.getString(ColumnName.COUNTDISH).toInt()
            }
            conn!!.close()
            count
        }else{
            count
        }
    }
    fun updateCountMon(count:Int,idMon: Int){
        conn = ConnectSQl.instance.connectToSQlServer()
        if (conn!=null){
            val query = "update ${ColumnName.DISH} set " +
                    "${ColumnName.COUNTDISH}='$count' where id='$idMon';"
            val statement = conn!!.createStatement()
            statement.executeUpdate(query)
            conn!!.close()
        }
    }
}