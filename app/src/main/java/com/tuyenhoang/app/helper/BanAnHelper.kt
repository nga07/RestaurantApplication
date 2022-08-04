package com.tuyenhoang.app.helper

import com.tuyenhoang.app.connect.ConnectSQl
import com.tuyenhoang.app.const.ColumnName
import com.tuyenhoang.app.model.BanAn
import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement

class BanAnHelper {
    private var conn:Connection?=null
    fun addBanAn(nameTable:String):Boolean{
        conn=ConnectSQl.instance.connectToSQlServer()
        return if (conn!=null){
            val query="insert into ${ColumnName.TABLE} values ('$nameTable','false');"
            val statement:Statement=conn!!.createStatement()
            statement.executeUpdate(query)
            conn!!.close()
            true
        }else{
            false
        }


    }
    fun deleteBanAn(id:Int):Boolean{
        conn=ConnectSQl.instance.connectToSQlServer()
        return if (conn!=null){
            val query="delete from "+ColumnName.TABLE+" where id='$id';"
            val statement:Statement=conn!!.createStatement()
            statement.executeUpdate(query)
            conn!!.close()
            true
        }else{
            false
        }
    }
    fun updateBanAn(id:Int,nameTable: String):Boolean{
        conn=ConnectSQl.instance.connectToSQlServer()
        return if (conn!=null){
            val query="update ${ColumnName.TABLE} set ${ColumnName.NAMETABLE}='$nameTable' where id='$id';"
            val statement:Statement=conn!!.createStatement()
            statement.executeUpdate(query)
            conn!!.close()
            true
        }else{
            false
        }
    }
    fun getAllBanAn():MutableList<BanAn>{

        conn=ConnectSQl.instance.connectToSQlServer()
        val banAns= mutableListOf<BanAn>()
        return if (conn!=null){
            val query="select * from ${ColumnName.TABLE};"
            val statement=conn!!.createStatement()
            val result=statement.executeQuery(query)
            while (result.next()){
                val banAn=BanAn()
                banAn.maBan=result.getInt("id")
                banAn.tenBan=result.getString(ColumnName.NAMETABLE)
                banAns.add(banAn)

            }
            conn!!.close()
            banAns
        }else{
            banAns
        }
    }
    fun getStatusBanAn(id:Int):String{
        conn=ConnectSQl.instance.connectToSQlServer()
        var status=""
        return if (conn!=null){
            val query="select * from ${ColumnName.TABLE} where id='$id';"
            val statement:Statement=conn!!.createStatement()
            val result=statement.executeQuery(query)
            while (result.next()){
                status=result.getString(ColumnName.STATUSTABLE)
            }
            conn!!.close()
            status
        }else{
            status
        }
    }
    fun updateStatusBanAn(id:Int,status:String):Boolean{
        conn=ConnectSQl.instance.connectToSQlServer()
        return if (conn!=null){
            val query="update ${ColumnName.TABLE} set ${ColumnName.STATUSTABLE}='$status' where id='$id';"
            val statement:Statement=conn!!.createStatement()
            statement.executeUpdate(query)
            conn!!.close()
            true
        }else{
            false
        }
    }
    fun getBanAnToId(id:Int):String {
        var nameTable = ""
        conn = ConnectSQl.instance.connectToSQlServer()
        return if (conn != null) {
            val query = "select * from ${ColumnName.TABLE} where id='$id';"
            val statement: Statement = conn!!.createStatement()
            val result = statement.executeQuery(query)
            while (result.next()) {
                nameTable = result.getString(ColumnName.NAMETABLE)
            }
            conn!!.close()
            nameTable
        } else {
            nameTable
        }
    }
}