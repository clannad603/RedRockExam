# RedRockExam
# 内容
![image](https://github.com/clannad603/RedRockExam/blob/master/app/src/main/res/GIF-210506_195844.gif)
![image](https://github.com/clannad603/RedRockExam/blob/master/app/src/main/res/GIF-210506_200027.gif)
![image](https://github.com/clannad603/RedRockExam/blob/master/app/src/main/res/GIF-210506_200332.gif)
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

### 一张表为事件任务表，另一张表为个人信息表，我通过在DAO里面对不同属性来做选择条件，来完成了这次的主要内容。另外我通过对查找条件绑定了Owner来确保每个用户的能拿到的属性分离

### APP的代码采用的MVVM架构，其中设计思路来源与上次寒假考核搭建的结构，这次的内容配合上次能大致的走一遍MVVM，线程方面采用了协程来完成（配合ViewModel的viewmodelscope将从ROOM获取数据的方法封装在了ViewModel层）（我在厂库层也写了没有suspend标注的方法，可以通过同样的调用方式来进行数据库操作），利用了广播和服务，使得在进入APP后将启动一个前台服务，并且退出或退出软件时关闭）使用了MateriaDesign的一些控件，利用了百度获取的沉浸式工具类和按钮动画和安卓第一行代码中封装的工具类和之前寒假考核封装的工具类，使用了glide和pickerview来进行本地图片加载和选择时间，由于开机广播无法接收和之前出了一个大Bug导致闹铃功能没有实现，具有搜索标签的功能，和前台通知标签为重要的任务，实现了清单分类，删除，增加功能，和增加任务功能，以及开机动画，但是，具体页面大多重复，（时间不足）

## 二、具体功能

### （1.）登录，注册，退出

#### 1.在进行登录操作时，首先会检查数据库中是否存在该用户，如果不存在，将使用弹窗来进行通知，如果在数据库中查询到了结果，将对返回结果进行判断，如果密码符合，将进入主页面

#### 2.在进行注册活动时，将对每一个输入进行判断，在全不为空时进行密码比对，然后先向数据库中查找是否存在账户，若存在将弹窗提示已注册，若不存在，向数据库中插入数据

#### 3.在主页面点击退出时，将弹出对话框来询问用户是否选择退出，若选择退出，将关闭所有活动，返回登录活动，同时关闭前台服务

### （2.）搜索功能

#### 在主页面点击右上角的搜索按钮，将进入搜索页面，使用 searchView来进行搜索，将输入框中的结果进行相应的数据处理，即搜索表中的title 部分，然后在下方的空白处显示出清单，点击清单中的ITEM，进入相应的详情

### （3.）保存图片

#### 在主页面右上角点击图片位置将弹窗提示禁止点击，长按将进入系统相册，用户可选择图片，然后图片将在相应位置显示，并将图片URI保存至数据库中，在第二次启动时，将在数据库中进行查找。然后加载图片，此功能使用了Glide的相关函数来进行图片裁剪

### （4.）添加任务

#### 在主页面左下角点击按钮添加，将进入添加任务活动，对数据进行判空，然后首先向数据库中查找是否存在相同owner,tag,title的数据，若存在，将调用update方法来对数据库中的数据进行更新，并弹窗提醒此数据在数据库中的位置，若tag不存在，将弹窗提醒，清单需要在主页面进行添加后可操作，若tag存在，且具体数据不存在，将向数据库中插入一个事件

### (5.清单管理)

#### 在按下主页面的悬浮按钮后，将弹出对话框来提醒是否要添加清单，在用户输入要添加的清单后，将会立刻刷新主页面中的清单，在点击清单后，将进入一个只属于该tag的清单页面，与搜索活动类型，在长按后进行删除，在短按后进入详情，此外，对于内置的清单为了保证稳定性，进行了长按事件的选择，如果为内置清单，将无法删除

### （6.前台通知）

#### 在进入主页面时，将向服务发送广播，服务在接收到广播后，会进行数据库数据请求，将title为重要的数据进行展示，在应用关闭后服务终结

## 三、使用的一些技巧

### （1.）room的相关操作，

#### 因为room中是不能直接存对象的，但与此同时，官方给与了转换器来进行相关操作,比如，我这次使用的@TypeConverter注释来使用的存储DATE类

```kotlin
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}
```

#### room使用的Sql语句来进行查找,我这次使用了其中的select update delete等操作，并且where后可以指定多个参数来进行操作，这个小技巧可以很方便的确定想要拿到的值，并且room的各种注释中@Query感觉最好用

### （2.）使用委托的shareprefence操作

#### 我将一些属性使用委托的方式来进行保存在全局中，我在这里保存了owner属性，因为我所有数据操作均和这个键对应，每一个活动都需要获取这个属性，于是我把他封装至了基类中，这样方便了数据的请求

### （3.）glide的一些操作

#### glide 有着强大的图片处理功能，能通过uri直接载入图片，并且能对图片进行裁剪，若使用自定义的高斯模糊，还可以通过glide进行载入，并且还具有图片裁剪功能

### (4.)pickerview的一些操作

#### pickerview可以通过他的一些方法来定义选择的东西。

### (5.)livedata的一些属性

#### livedata可以在协程中直接使用.value，在线程中需要使用postvalue

### (6.)协程与ViewModel

#### viewModel具有一个viewmodelscope，可以在viewmodel中使用viewmodelscope.launch来调用suspend方法

## 四、心得体会

### 开机广播有很大的概率无法启动，在真机上曾经启动过，但是后来又启动不了了，空指针异常即使是kotlin这么针对空也无法阻止，并且这个错误十分难找出来，room的一些bug，升级数据库要么在appdatabase哪里写入新表，要么只能删除本地数据，不然会报错，并且他的官方引入方式会报错？？？但是熟悉了room的操作后，的确比较方便，kotlin的扩展函数，十分方便，本来我是使用改装的toast后来发现还是扩展函数香，并且可以向view 类添加带有缩放动画的点击方法，可以减少很多代码量，但由于时间原因，还没有将其全部使用，kotlin还有不少坑需要去挖

#### 
