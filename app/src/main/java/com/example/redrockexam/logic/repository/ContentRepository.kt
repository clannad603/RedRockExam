package com.example.redrockexam.logic.repository

import com.example.redrockexam.logic.dao.ContentDao
import com.example.redrockexam.logic.model.bean.ContentInfo

class ContentRepository(private val dao: ContentDao) {
    suspend fun insert(contentInfo: ContentInfo):Long{
        return dao.insertContent(contentInfo)
    }
    suspend fun updateDetail(owner: String,tag:String,title:String,content:String):Int{
        return dao.updateContentInfo(owner, tag, title, content)
    }
    suspend fun delete(content: String): Int {
        return dao.deleteTheContent(content)
    }
    suspend fun loadTag(owner: String,tag:String): MutableList<ContentInfo> {
        return dao.loadTheTagContent(owner, tag)
    }
    suspend fun find(owner: String,tag: String,title:String):ContentInfo{
        return dao.find(owner, tag, title)
    }
    suspend fun deleteInfo(owner: String,tag: String,title:String):Int{
        return dao.deleteContentInfo(owner,tag,title)
    }
    suspend fun getFromTitle(owner: String,title: String):MutableList<ContentInfo>{
        return  dao.getFromTag(owner, title)
    }
}