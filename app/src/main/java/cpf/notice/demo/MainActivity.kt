package cpf.notice.demo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cpf.notice.NoticeView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NoticeView.OnNoticeItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notice.setData(
            mutableListOf(
                "明月几时有？把酒问青天。",
                "不知天上宫阙，今夕是何年。",
                "我欲乘风归去，又恐琼楼玉宇，高处不胜寒。",
                "起舞弄清影，何似在人间。"
            )
        )
        notice.setOnItemClickListener(this)
    }

    override fun onNoticeItemClick(position: Int) {
        Toast.makeText(this, position.toString(), Toast.LENGTH_SHORT).show()
    }
}