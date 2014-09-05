package tr.com.aliok.meetingroomkiosk.android.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.cengalabs.flatui.views.FlatTextView;

public class BlinkingTextView extends FlatTextView {


    public BlinkingTextView(Context context) {
        super(context);
    }

    public BlinkingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BlinkingTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1000); //You can manage the time of the blink with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        this.startAnimation(anim);
    }
}
