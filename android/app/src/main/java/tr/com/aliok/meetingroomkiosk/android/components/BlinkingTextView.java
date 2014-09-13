package tr.com.aliok.meetingroomkiosk.android.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.cengalabs.flatui.views.FlatTextView;

import tr.com.aliok.meetingroomkiosk.android.R;

/**
 * A flat blinking text view for catching attraction.
 */
public class BlinkingTextView extends FlatTextView {

    // TODO: does this one keep working when activity is in the background?
    // we don't want it to blink if display is switched off
    // same goes with countdown component

    public BlinkingTextView(Context context) {
        super(context);
    }

    public BlinkingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BlinkingTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void startBlinking() {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(getResources().getInteger(R.integer.blinking_text_animation_duration)); //You can manage the time of the blink with this parameter
        anim.setStartOffset(getResources().getInteger(R.integer.blinking_text_animation_offset));
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        this.startAnimation(anim);
    }

    public void stopBlinking() {
        this.clearAnimation();
    }

}
