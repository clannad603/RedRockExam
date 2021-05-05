package com.example.redrockexam.logic.repository

import com.example.redrockexam.logic.dao.ContentDao
import com.example.redrockexam.logic.model.bean.ContentInfo

class ContentRepository(private val dao: ContentDao) {
    suspend fun insert(contentInfo: ContentInfo):Long{
        return dao.insertContent(contentInfo)
    }
    suspend fun updateDetail(owner:String,content:String){
        return dao.updateContentInfo(owner, content)
    }
    suspend fun delete(content: String): Int {
        return dao.deleteTheContent(content)
    }
    suspend fun loadTag(owner: String,tag:String): MutableList<ContentInfo> {
        return dao.loadTheTagContent(owner, tag)
    }
}