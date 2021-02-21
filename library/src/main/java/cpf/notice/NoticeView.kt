package cpf.notice

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.core.animation.doOnEnd
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author : cpf
 * @date : 2/21/21
 * description : 垂直滚动消息
 */
class NoticeView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    interface OnNoticeItemClickListener {
        fun onNoticeItemClick(position: Int)
    }

    private var job: Job? = null
    private var valueAnimator: ValueAnimator? = null
    private var startDis = 0f
    private var nowDis = 0f
    private var data: MutableList<String> = mutableListOf()

    private val paint: Paint by lazy {
        Paint().also {
            it.isAntiAlias = true
            it.color = textColor
            it.textSize = textSize
            it.textAlign = Paint.Align.LEFT
            startDis = height / 2f + textSize / 3f
            nowDis = startDis
        }
    }

    var position = 0

    /**
     * 字体大小，默认为View高度的1/3
     */
    @Px
    var textSize: Float
        get() {
            if (field == 0f) {
                field = height / 3f
            }
            return field
        }

    /**
     * 字体颜色，默认为GRAY
     */
    @ColorInt
    var textColor: Int

    /**
     * 滚动动画时间，单位毫秒
     */
    var durationMs: Long

    /**
     * 滚动延迟时间，单位毫秒
     */
    var delayMs: Long

    /**
     * 动画插值器
     */
    var interpolator: Interpolator = DecelerateInterpolator()

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.NoticeView)
        textColor = ta.getColor(R.styleable.NoticeView_android_textColor, Color.GRAY)
        textSize = ta.getDimension(R.styleable.NoticeView_android_textSize, 0f)
        durationMs = ta.getInt(R.styleable.NoticeView_android_duration, 300).toLong()
        delayMs = ta.getFloat(R.styleable.NoticeView_android_delay, 3000f).toLong()
        ta.recycle()
    }

    /**
     * 设置数据
     */
    fun setData(data: MutableList<String>) {
        stopAnimInternal()
        this.data = data
        invalidate()
        startAnimInternal()
    }

    /**
     * 设置Item点击事件
     */
    fun setOnItemClickListener(itemClickListener: OnNoticeItemClickListener) {
        setOnClickListener { itemClickListener.onNoticeItemClick(position) }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (data.isEmpty()) {
            return
        }

        val size = data.size

        /**
         * now
         */
        if (size > 0) {
            canvas.drawText(data[position], 0f, nowDis, paint)
        }

        /**
         * next
         */
        if (size > 1) {
            canvas.drawText(data[(position + 1) % size], 0f, nowDis + height, paint)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startAnimInternal()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopAnimInternal()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        startDis = height / 2f + textSize / 3f
        nowDis = startDis
    }

    private fun startAnimInternal() {
        stopAnimInternal()
        if (data.size < 2) {
            return
        }
        if (delayMs < durationMs) {
            delayMs = durationMs
        }
        job = MainScope().launch {
            while (true) {
                delay(delayMs)
                valueAnimator = ValueAnimator.ofFloat(nowDis, nowDis - height).also {
                    it.interpolator = interpolator
                    it.duration = durationMs
                    it.doOnEnd {
                        nowDis = startDis
                        if (++position == data.size) {
                            position = 0
                        }
                    }
                    it.addUpdateListener { animator ->
                        nowDis = animator.animatedValue as Float
                        postInvalidate()
                    }
                    it.start()
                }
            }
        }
    }

    private fun stopAnimInternal() {
        valueAnimator?.cancel()
        valueAnimator = null
        job?.cancel()
        job = null
    }
}