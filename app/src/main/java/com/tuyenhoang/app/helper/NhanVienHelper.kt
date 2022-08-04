package com.tuyenhoang.app.helper

import android.util.Log
import com.tuyenhoang.app.connect.ConnectSQl
import com.tuyenhoang.app.const.ColumnName
import com.tuyenhoang.app.model.NhanVien
import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement

class NhanVienHelper {
    private var conn:Connection?=null
    fun addNhanVien(nhanVien: NhanVien): Boolean {
        conn = ConnectSQl.instance.connectToSQlServer()
        return if (conn!=null){
            val query = "insert into ${ColumnName.USER} values (" +
                    "'${nhanVien.fullName}','${nhanVien.username}'," +
                    "'${nhanVien.password}','${nhanVien.email}'," +
                    "'${nhanVien.numberPhone}','${nhanVien.sex}'," +
                    "'${nhanVien.dateOfBirth}','${nhanVien.permission}'," +
                    "'${nhanVien.moneyOrder}');"
            val statement = conn!!.createStatement()
            statement.executeUpdate(query)
            conn!!.close()
            true
        }else{
            Log.d("NhanVienHelper","errr")
            false
        }
    }
    fun updateMoneyOrderNhanVien(moneyOrder:String,idNhanVien: Int){
        conn=ConnectSQl.instance.connectToSQlServer()
        if (conn!=null){
            val query = "update ${ColumnName.USER} set " +
                    "${ColumnName.MONEYORDER}='$moneyOrder' where id='$idNhanVien';"
            val statement = conn!!.createStatement()
            statement.executeUpdate(query)
            conn!!.close()
        }
    }
    fun getAllMoneyStaff():Int{
        conn=ConnectSQl.instance.connectToSQlServer()
        return if (conn!=null){
            val query = "select * from ${ColumnName.USER} where ${ColumnName.IDPERMISSION}='2';"
            var money = 0
            var totalMoney=0
            val statement: Statement = conn!!.createStatement()
            val result = statement.executeQuery(query)
            while (result.next()) {
                money = result.getString(ColumnName.MONEYORDER).toInt()
                totalMoney+=money
            }
            conn!!.close()
            totalMoney
        }else{
            0
        }
    }
    fun updateMoneyManager(moneyOrder:String){
        val totalMoney=getAllMoneyStaff()+moneyOrder.toInt()
        conn=ConnectSQl.instance.connectToSQlServer()
        if (conn!=null){
            val query = "update ${ColumnName.USER} set " +
                    "${ColumnName.MONEYORDER}='$totalMoney' where ${ColumnName.IDPERMISSION}='1';"
            val statement = conn!!.createStatement()
            statement.executeUpdate(query)
            conn!!.close()
        }
    }

    fun getMoneyOrderNhanVien(idNhanVien: Int):Int{
        conn=ConnectSQl.instance.connectToSQlServer()
        return if (conn!=null){
            val query = "select * from ${ColumnName.USER} where id='$idNhanVien';"
            var money = 0
            val statement: Statement = conn!!.createStatement()
            val result = statement.executeQuery(query)
            while (result.next()) {
                money = result.getString(ColumnName.MONEYORDER).toInt()
            }
            conn!!.close()
            money
        }else{
            0
        }
    }
    fun updateNhanVien(nhanVien: NhanVien, idNhanVien: Int): Boolean {
        conn=ConnectSQl.instance.connectToSQlServer()
        if (conn!=null){
            val query = "update ${ColumnName.USER} set " +
                    "${ColumnName.FULLNAME}='${nhanVien.fullName}'," +
                    "${ColumnName.PASSWORD}='${nhanVien.password}'," +
                    "${ColumnName.EMAIL}='${nhanVien.email}'," +
                    "${ColumnName.NUMBERPHONE}='${nhanVien.numberPhone}'," +
                    "${ColumnName.SEX}='${nhanVien.sex}'," +
                    "${ColumnName.DATEOFBIRTH}='${nhanVien.dateOfBirth}'," +
                    "${ColumnName.PERMISSION}='${nhanVien.permission}'," +
                    "${ColumnName.MONEYORDER}='${nhanVien.moneyOrder}' where id='$idNhanVien';"
            val statement = conn!!.createStatement()
            statement.executeUpdate(query)
            conn!!.close()
            return true
        }else{
            return false
        }
    }

    fun checkLogin(username: String, password: String): Int {
        conn=ConnectSQl.instance.connectToSQlServer()
        return if (conn!=null){
            val query =
                "select * from ${ColumnName.USER} where CONVERT(VARCHAR,${ColumnName.USERNAME})='$username'" +
                        " and CONVERT(VARCHAR,${ColumnName.PASSWORD})='$password';"
            var idNhanVien = 0
            val statement: Statement = conn!!.createStatement()
            val result = statement.executeQuery(query)
            while (result.next()) {
                idNhanVien = result.getInt("id")
            }
            idNhanVien
        }else{
            0
        }
    }

    fun checkForgot(email: String, username: String, numberPhone: String): Int {
        conn=ConnectSQl.instance.connectToSQlServer()
        return if (conn!=null){
            val query =
                "select * from ${ColumnName.USER} where CONVERT(VARCHAR,${ColumnName.USERNAME})='$username'" +
                        " and CONVERT(VARCHAR,${ColumnName.EMAIL})='$email' and CONVERT(VARCHAR,${ColumnName.NUMBERPHONE})='$numberPhone';"
            var idNhanVien = 0
            val statement: Statement = conn!!.createStatement()
            val result = statement.executeQuery(query)
            while (result.next()) {
                idNhanVien = result.getInt("id")
            }
            conn!!.close()
            idNhanVien
        }else{
            0
        }
    }

    fun checkUsername(username: String): Int {
        conn=ConnectSQl.instance.connectToSQlServer()
        return if (conn!=null){
            val query =
                "select * from ${ColumnName.USER} where CONVERT(VARCHAR,${ColumnName.USERNAME})='$username';"
            var idNhanVien = 0
            val statement: Statement = conn!!.createStatement()
            val result = statement.executeQuery(query)
            while (result.next()) {
                idNhanVien = result.getInt("id")
            }
            conn!!.close()
            idNhanVien
        }else{
            0
        }
    }

    fun updatePassword(password: String, idNhanVien: Int): Boolean {
        conn=ConnectSQl.instance.connectToSQlServer()
        return if (conn!=null){
            val query = "update ${ColumnName.USER} set " +
                    "${ColumnName.PASSWORD}='$password' where id='$idNhanVien';"
            val statement: Statement = conn!!.createStatement()
            statement.executeUpdate(query)
            conn!!.close()
            true
        }else{
            false
        }
    }

    fun checkTonTaiNhanVien(): Boolean {
        conn=ConnectSQl.instance.connectToSQlServer()
        return if (conn!=null){
            var idNhanVien=0
            val query = "select * from ${ColumnName.USER};"
            val statement: Statement = conn!!.createStatement()
            val result = statement.executeQuery(query)
            while (result.next()){
                idNhanVien = result.getInt("id")
            }
            conn!!.close()
            Log.d("Tuyen",idNhanVien.toString())
            idNhanVien!=0
        }else{
            false
        }
    }

    fun getAllNhanVien(): MutableList<NhanVien> {
        val nhanViens = mutableListOf<NhanVien>()
        conn=ConnectSQl.instance.connectToSQlServer()
        return if (conn!=null){
            val query = "select * from ${ColumnName.USER};"
            val statement: Statement = conn!!.createStatement()
            val result = statement.executeQuery(query)
            while (result.next()) {
                val nhanVien = NhanVien()
                nhanVien.fullName = result.getString(ColumnName.FULLNAME)
                nhanVien.email = result.getString(ColumnName.EMAIL)
                nhanVien.sex = result.getString(ColumnName.SEX)
                nhanVien.dateOfBirth = result.getString(ColumnName.DATEOFBIRTH)
                nhanVien.numberPhone = result.getString(ColumnName.NUMBERPHONE)
                nhanVien.username = result.getString(ColumnName.USERNAME)
                nhanVien.password = result.getString(ColumnName.PASSWORD)
                nhanVien.id = result.getInt("id")
                nhanVien.permission = result.getInt(ColumnName.IDPERMISSION)
                nhanVien.moneyOrder=result.getString(ColumnName.MONEYORDER).toInt()
                nhanViens.add(nhanVien)
            }
            conn!!.close()
            nhanViens
        }else{
            nhanViens
        }


    }

    fun deleteNhanVien(idNhanVien: Int): Boolean {
        conn=ConnectSQl.instance.connectToSQlServer()
        return if (conn!=null){
            val query = "delete from ${ColumnName.USER} where id='$idNhanVien';"
            val statement: Statement = conn!!.createStatement()
            statement.executeUpdate(query)
            conn!!.close()
            true
        }else{
            false
        }
    }

    fun getNhanVienToId(idNhanVien: Int): NhanVien {
        conn=ConnectSQl.instance.connectToSQlServer()
        val nhanVien = NhanVien()
        return if (conn!=null){
            val query =
                "select * from ${ColumnName.USER} where id='$idNhanVien';"
            val statement: Statement = conn!!.createStatement()
            val result = statement.executeQuery(query)
            while (result.next()) {
                nhanVien.fullName = result.getString(ColumnName.FULLNAME)
                nhanVien.email = result.getString(ColumnName.EMAIL)
                nhanVien.sex = result.getString(ColumnName.SEX)
                nhanVien.dateOfBirth = result.getString(ColumnName.DATEOFBIRTH)
                nhanVien.numberPhone = result.getString(ColumnName.NUMBERPHONE)
                nhanVien.username = result.getString(ColumnName.USERNAME)
                nhanVien.password = result.getString(ColumnName.PASSWORD)
                nhanVien.id = result.getInt("id")
                nhanVien.permission = result.getInt(ColumnName.IDPERMISSION)
            }
            conn!!.close()
            nhanVien
        }else{
            return nhanVien
        }
    }

    fun getPermissionNhanVien(idNhanVien: Int): Int {
        conn=ConnectSQl.instance.connectToSQlServer()
        var permission = 0
        return if (conn!=null){
            val query =
                "select * from ${ColumnName.USER} where id='$idNhanVien';"
            val statement: Statement = conn!!.createStatement()
            val result = statement.executeQuery(query)
            while (result.next()) {
                permission = result.getInt(ColumnName.IDPERMISSION)
            }
            conn!!.close()
            permission
        }else{
            permission
        }
    }
}