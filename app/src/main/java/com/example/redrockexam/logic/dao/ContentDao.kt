package com.example.redrockexam.logic.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.redrockexam.logic.model.bean.ContentInfo
import com.example.redrockexam.logic.model.bean.LoginInfo

@Dao
interface ContentDao {
    @Insert
    suspend fun insertContent(contentInfo: ContentInfo): Long

    @Query("UPDATE contentinfo SET  content=:content WHERE owner = :owner and tag=:tag and title=:title")
    suspend fun updateContentInfo(owner: String, tag: String, title: String, content: String): Int

    @Query("delete from ContentInfo WHERE owner = :owner and tag=:tag and title=:title")
    suspend fun deleteContentInfo(owner: String, tag: String, title: String): Int

    @Query("select * from ContentInfo where tag=:tag and owner=:owner ")
    suspend fun loadTheTagContent(owner: String, tag: String): MutableList<ContentInfo>

    @Query("SELECT * from ContentInfo where owner=:owner and tag=:tag and title=:title")
    suspend fun find(owner: String, tag: String, title: String): ContentInfo

    @Query("delete from ContentInfo where content=:content")
    suspend fun deleteTheContent(content: String): Int

    @Query("select * from ContentInfo where owner=:owner and title=:title")
    suspend fun getFromTag(owner: String, title: String): MutableList<ContentInfo>

    @Query("select * from ContentInfo where owner=:owner")
    suspend fun getTag(owner: String): MutableList<ContentInfo>

    @Query("delete from ContentInfo where tag = :tag and owner =:owner")
    suspend fun deleteTag(owner: String, tag: String): Int
}