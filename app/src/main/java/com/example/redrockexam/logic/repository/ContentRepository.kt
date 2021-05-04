package com.example.redrockexam.logic.repository

import com.example.redrockexam.logic.dao.ContentDao
import com.example.redrockexam.logic.model.bean.ContentInfo
import com.example.redrockexam.logic.model.bean.DetailInfo

class ContentRepository(private val dao: ContentDao) {
    suspend fun insert(contentInfo: ContentInfo):Long{
        return dao.insertContent(contentInfo)
    }
    suspend fun updateDetail(owner:String,detailInfo: DetailInfo){
        return dao.updateContentInfo(owner, detailInfo)
    }
    suspend fun delete(detailInfo: DetailInfo): Long {
        return dao.deleteTheContent(detailInfo)
    }
    suspend fun loadTag(owner: String,tag:String): MutableList<ContentInfo> {
        return dao.loadTheTagContent(owner, tag)
    }
}