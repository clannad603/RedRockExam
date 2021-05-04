package com.example.redrockexam.logic.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.redrockexam.logic.model.bean.LoginInfo

@Dao
interface LoginDao {
    @Insert
    suspend fun insertUser(loginInfo: LoginInfo):Long
    @Update
    suspend fun updateUser(newUser: LoginInfo)
    @Query("select * from LoginInfo")
    fun loadAllUsers():MutableList<LoginInfo>
    @Query("select * from LoginInfo where owner = :owner")
    suspend fun findTheOwner(owner:String): LoginInfo
}