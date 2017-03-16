

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by chenpengfei on 2017/3/6.
 * mail:1026264263@qq.com
 * 安卓上下滚动通知
 */

public class NoticeView extends View {

    private int size;

    private int position;

    private Paint paint;

    private Timer timer;

    private float startDis, nowDis, dis;

    private ShowText showText;


    public void setData(ShowText showText) {
        size = showText.getSize();
        this.showText = showText;
        invalidate();
    }

    public interface ShowText {
        String getText(int position);

        int getSize();
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public NoticeView(Context context) {
        super(context);
    }

    public NoticeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (paint == null) {
            paint = new Paint();
            paint.setColor(Color.GRAY);
            paint.setAntiAlias(true);
            paint.setTextSize(getHeight() / 3);
            paint.setTextAlign(Paint.Align.LEFT);
            startDis = (int) (getHeight() / 2 + paint.getTextSize() / 3);
            nowDis = startDis;
            dis = getHeight();
        }
        if (size >= 2) {
            /**
             * now
             */
            canvas.drawText(showText.getText(position), 0, nowDis, paint);
            /**
             * next
             */
            canvas.drawText(showText.getText((position + 1) % size), 0, nowDis + dis, paint);
        }

    }

    public void setOnItemClickListener(final OnItemClickListener itemClickListener) {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.click(position);
            }
        });
    }

    /**
     * 时间间隔
     */
    public void start(int delay, int period) {
        if (size < 2) {
            return;
        }
        if (timer == null) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    anim();
                }
            }, delay, period);
        }
    }

    public interface OnItemClickListener {
        void click(int position);
    }

    private void anim() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (nowDis + dis != startDis) {
                    try {
                        Thread.sleep(5);
                        nowDis--;
                        postInvalidate();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                nowDis = startDis;
                if (++position == size)
                    position = 0;
                postInvalidate();
            }
        }).start();
    }

    public void stop() {
        if (timer != null)
            timer.cancel();
        timer = null;

    }
}
