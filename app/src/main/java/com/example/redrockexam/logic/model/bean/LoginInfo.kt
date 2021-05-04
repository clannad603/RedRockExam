package com.example.redrockexam.logic.model.bean

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.net.URI
@Entity
data class LoginInfo(val owner:String , val password: String, val uri:String){
    @PrimaryKey(autoGenerate = true)
    var id:Long =0
}


