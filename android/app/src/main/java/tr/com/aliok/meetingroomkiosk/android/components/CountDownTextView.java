package tr.com.aliok.meetingroomkiosk.android.components;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;

import com.cengalabs.flatui.views.FlatTextView;

import org.apache.commons.lang3.time.DurationFormatUtils;

import java.util.Date;

import tr.com.aliok.meetingroomkiosk.android.util.Clock;

/**
 * Displays a countdown timer which updates remaining time every second.
 */
public class CountDownTextView extends FlatTextView {
    private static final long DEFAULT_COUNT_DOWN_INTERVAL = 1000L;

    private CountDownTimer mCountDownTimer;

    private CountDownListener countDownListener;

    public CountDownTextView(Context context) {
        super(context);
    }

    public CountDownTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CountDownTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void startCountDown(Date untilTime) {
        if (mCountDownTimer != null)        // cancel the old one if it exists
            mCountDownTimer.cancel();

        // start new countdown
        mCountDownTimer = new CountDownTimer(untilTime.getTime() - Clock.nowMs(), DEFAULT_COUNT_DOWN_INTERVAL) {

            public void onTick(long millisUntilFinished) {
                setText(DurationFormatUtils.formatDuration(millisUntilFinished, "H:mm:ss"));
            }

            public void onFinish() {
                if (countDownListener != null)
                    countDownListener.onCountDownFinished(CountDownTextView.this);
            }
        };

        mCountDownTimer.start();
    }

    public void stopCountDown() {
        if (mCountDownTimer != null)
            mCountDownTimer.cancel();
    }

    public void setCountDownListener(CountDownListener countDownListener) {
        this.countDownListener = countDownListener;
    }

    public interface CountDownListener {
        public void onCountDownFinished(CountDownTextView countDownTextView);
    }

}
