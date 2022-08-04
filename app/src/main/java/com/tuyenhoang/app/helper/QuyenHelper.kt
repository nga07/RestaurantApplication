package com.tuyenhoang.app.helper

import com.tuyenhoang.app.connect.ConnectSQl
import com.tuyenhoang.app.const.ColumnName
import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement

class QuyenHelper {
    private var conn:Connection?=null
    fun addQuyen(namePermission:String){
        conn=ConnectSQl.instance.connectToSQlServer()
        if (conn!=null){
            val query="insert into ${ColumnName.PERMISSION} values('$namePermission');"
            val statement:Statement=conn!!.createStatement()
            statement.executeUpdate(query)
        }
    }
}