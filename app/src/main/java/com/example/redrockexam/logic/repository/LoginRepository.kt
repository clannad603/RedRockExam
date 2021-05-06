package com.example.redrockexam.logic.repository

import com.example.redrockexam.logic.dao.LoginDao
import com.example.redrockexam.logic.model.bean.LoginInfo

class LoginRepository(private val dao: LoginDao) {
    val subscribers = dao.loadAllUsers()
    suspend fun insert(loginInfo: LoginInfo): Long {
        return dao.insertUser(loginInfo)
    }

    suspend fun findOwner(owner: String): LoginInfo {
        return dao.findTheOwner(owner)
    }

    suspend fun updateUri(owner: String, uri: String) {
        return dao.updateUserInfo(owner, uri)
    }

     fun insert_s(loginInfo: LoginInfo): Long {
        return dao.insertUser_s(loginInfo)
    }

     fun findOwner_s(owner: String): LoginInfo {
        return dao.findTheOwner_s(owner)
    }

     fun updateUri_s(owner: String, uri: String) {
        return dao.updateUserInfo_s(owner, uri)
    }

}