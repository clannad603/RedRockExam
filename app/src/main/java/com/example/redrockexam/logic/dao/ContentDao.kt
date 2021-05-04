package com.example.redrockexam.logic.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.redrockexam.logic.model.bean.ContentInfo
import com.example.redrockexam.logic.model.bean.DetailInfo
import com.example.redrockexam.logic.model.bean.LoginInfo

@Dao
interface ContentDao {
    @Insert
    suspend fun insertContent(contentInfo: ContentInfo):Long

    @Query("UPDATE contentinfo SET  detailInfo=:detailInfo WHERE owner = :owner")
    suspend fun updateContentInfo(owner: String,detailInfo: DetailInfo)

    @Query("select * from ContentInfo where tag=:tag and owner=:owner")
    suspend fun loadTheTagContent(owner: String,tag:String):MutableList<ContentInfo>

    @Query("delete from ContentInfo where detailInfo=:detailInfo")
    suspend fun deleteTheContent(detailInfo: DetailInfo):Long
}