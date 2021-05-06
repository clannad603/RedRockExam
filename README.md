# RedRockExam
# 内容

## 一、结构

### 由于本次考核要求没有给以网络接口，所以对数据的存储，我使用了ROOM来完成，数据库中有两张表，分别为

```kotlin
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

```

### 和

```kotlin

@Entity
data class LoginInfo(val owner: String, val password: String, val uri: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

```

