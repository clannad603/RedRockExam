package com.example.redrockexam.logic.repository

import com.example.redrockexam.logic.dao.ContentDao
import com.example.redrockexam.logic.model.bean.ContentInfo

class ContentRepository(private val dao: ContentDao) {
    suspend fun insert(contentInfo: ContentInfo): Long {
        return dao.insertContent(contentInfo)
    }

    suspend fun updateDetail(owner: String, tag: String, title: String, content: String): Int {
        return dao.updateContentInfo(owner, tag, title, content)
    }

    suspend fun delete(content: String): Int {
        return dao.deleteTheContent(content)
    }

    suspend fun loadTag(owner: String, tag: String): MutableList<ContentInfo> {
        return dao.loadTheTagContent(owner, tag)
    }

    suspend fun find(owner: String, tag: String, title: String): ContentInfo {
        return dao.find(owner, tag, title)
    }

    suspend fun deleteInfo(owner: String, tag: String, title: String): Int {
        return dao.deleteContentInfo(owner, tag, title)
    }

    suspend fun getFromTitle(owner: String, title: String): MutableList<ContentInfo> {
        return dao.getFromTag(owner, title)
    }


    /***
     * 普通调用
     */
    fun getTag(owner: String): MutableList<ContentInfo> {
        return dao.getTag(owner)
    }

     fun deleteTag(owner: String, tag: String): Int {
        return dao.deleteTag(owner, tag)
    }



     fun insert_s(contentInfo: ContentInfo): Long {
        return dao.insertContent_s(contentInfo)
    }

     fun updateDetail_s(owner: String, tag: String, title: String, content: String): Int {
        return dao.updateContentInfo_s(owner, tag, title, content)
    }

     fun delete_s(content: String): Int {
        return dao.deleteTheContent_s(content)
    }

     fun loadTag_s(owner: String, tag: String): MutableList<ContentInfo> {
        return dao.loadTheTagContent_s(owner, tag)
    }

     fun find_s(owner: String, tag: String, title: String): ContentInfo {
        return dao.find_s(owner, tag, title)
    }

     fun deleteInfo_s(owner: String, tag: String, title: String): Int {
        return dao.deleteContentInfo_s(owner, tag, title)
    }

    suspend fun getFromTitle_s(owner: String, title: String): MutableList<ContentInfo> {
        return dao.getFromTag(owner, title)
    }

    suspend fun getTag_s(owner: String): MutableList<ContentInfo> {
        return dao.getTag(owner)
    }

    suspend fun deleteTag_s(owner: String, tag: String): Int {
        return dao.deleteTag(owner, tag)
    }
}