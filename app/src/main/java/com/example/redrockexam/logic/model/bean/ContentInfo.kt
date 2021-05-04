package com.example.redrockexam.logic.model.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ContentInfo(val owner:String, val tag:String, val detailInfo: DetailInfo){
    @PrimaryKey(autoGenerate = true)
    var id:Long =0
}
