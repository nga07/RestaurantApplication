package com.tuyenhoang.app.helper

import com.tuyenhoang.app.connect.ConnectSQl
import com.tuyenhoang.app.const.ColumnName
import com.tuyenhoang.app.model.LoaiMon
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import java.sql.Statement

class LoaiMonHelper {
    private var conn: Connection? = null
    fun addLoaiMon(loaiMon: LoaiMon): Boolean {
        conn = ConnectSQl.instance.connectToSQlServer()
        return if (conn != null) {
            val query =
                "insert into ${ColumnName.TYPEDISH} (${ColumnName.IMAGETYPEDISH},${ColumnName.NAMETYPEDISH}) values (?,?);"
            val preparedStatement: PreparedStatement = conn!!.prepareStatement(query)
            preparedStatement.setBytes(1, loaiMon.hinhAnh)
            preparedStatement.setString(2, loaiMon.tenLoai)
            preparedStatement.execute()
            conn!!.close()
            true
        } else {
            false
        }

    }

    fun deleteLoaiMon(idMaLoai: Int): Boolean {
        conn = ConnectSQl.instance.connectToSQlServer()
        return if (conn != null) {
            val query = "delete from ${ColumnName.TYPEDISH} where id='$idMaLoai';"
            val statement: Statement = conn!!.createStatement()
            statement.executeUpdate(query)
            conn!!.close()
            true
        } else {
            false
        }

    }

    fun updateLoaiMon(loaiMon: LoaiMon, idLoaiMon: Int): Boolean {
        conn = ConnectSQl.instance.connectToSQlServer()
        return if (conn != null) {
            val query = "update ${ColumnName.TYPEDISH} set ${ColumnName.IMAGETYPEDISH}= ? ," +
                    "${ColumnName.NAMETYPEDISH}= ? where id='$idLoaiMon';"
            val preparedStatement: PreparedStatement = conn!!.prepareStatement(query)
            preparedStatement.setBytes(1, loaiMon.hinhAnh)
            preparedStatement.setString(2, loaiMon.tenLoai)
            preparedStatement.execute()
            conn!!.close()
            true
        } else {
            false
        }
    }

    fun getAllLoaiMon(): MutableList<LoaiMon> {
        val loaiMons = mutableListOf<LoaiMon>()
        conn = ConnectSQl.instance.connectToSQlServer()
        return if (conn!=null){
            val query = "select * from ${ColumnName.TYPEDISH};"
            val statement: Statement = conn!!.createStatement()
            val result = statement.executeQuery(query)
            while (result.next()) {
                val loaiMon = LoaiMon()
                loaiMon.maLoai = result.getInt("id")
                loaiMon.tenLoai = result.getString(ColumnName.NAMETYPEDISH)
                loaiMon.hinhAnh = result.getBytes(ColumnName.IMAGETYPEDISH)
                loaiMons.add(loaiMon)
            }
            conn!!.close()
            loaiMons
        }else{
            loaiMons
        }

    }

    fun getLoaiMonToID(idLoaiMon: Int): LoaiMon {
        val loaiMon = LoaiMon()
        conn = ConnectSQl.instance.connectToSQlServer()
        return if (conn!=null){
            val query = "select * from ${ColumnName.TYPEDISH} where id='$idLoaiMon';"
            val statement: Statement = conn!!.createStatement()
            val result = statement.executeQuery(query)
            while (result.next()) {
                loaiMon.maLoai = result.getInt("id")
                loaiMon.tenLoai = result.getString(ColumnName.NAMETYPEDISH)
                loaiMon.hinhAnh = result.getBytes(ColumnName.IMAGETYPEDISH)
            }
            conn!!.close()
            loaiMon
        }else{
            loaiMon
        }
    }
}