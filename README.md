# Notice
简洁的垂直滚动消息，跑马灯
使用自定义View开发，拥有更高的性能
## Usage
添加 jitPack.io 仓库
``` groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
添加依赖
``` groovy
	implementation 'com.github.cyynf:Notice:2.0.0'
```
使用
``` xml
    <cpf.notice.NoticeView
        android:id="@+id/notice"
        android:layout_width="match_parent"
        android:layout_height="30dp"
        android:delay="2000"
        android:duration="300"
        android:textColor="#2E58E7"
        android:textSize="14sp" />
```
``` kotlin
        notice.setData(
            mutableListOf(
                "明月几时有？把酒问青天。",
                "不知天上宫阙，今夕是何年。",
                "我欲乘风归去，又恐琼楼玉宇，高处不胜寒。",
                "起舞弄清影，何似在人间。"
            )
        )
        notice.setOnItemClickListener(this)
```