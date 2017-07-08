package com.goyo.grocery_goyo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
/**
 * Created by Admin on 7/8/2017.
 */
@SuppressLint("AppCompatCustomView")
public class SwipeButton extends Button
{

    public SwipeButton(Context context) {
        super(context);
    }

    public SwipeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SwipeButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}