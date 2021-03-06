package com.example.redrockexam.logic.model.bean

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class ContentInfo(
    val owner: String,
    val tag: String,
    val startTime: Date?,
    val endTime: Date?,
    val title: String,
    val content: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
