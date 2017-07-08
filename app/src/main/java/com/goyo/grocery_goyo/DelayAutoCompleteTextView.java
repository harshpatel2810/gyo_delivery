package com.goyo.grocery_goyo;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;

/**
 * Created by Admin on 5/23/2017.
 */

public class DelayAutoCompleteTextView extends android.support.v7.widget.AppCompatAutoCompleteTextView {

    //This is the class which is extended by AutoCompleteTextView widget
    //Variable to check the time for the Message Text Changed
    private static final int MESSAGE_TEXT_CHANGED = 50;
    //Default time for the Auto Complete Delay
    private static final int DEFAULT_AUTOCOMPLETE_DELAY = 150;
    private int mAutoCompleteDelay = DEFAULT_AUTOCOMPLETE_DELAY;
    //ProgressBar is also Taken which will indicate as progress indicator
    private ProgressBar mLoadingIndicator;
    //Handler message which allows to filter the query of the user
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            DelayAutoCompleteTextView.super.performFiltering((CharSequence) msg.obj, msg.arg1);
        }
    };
    public DelayAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void setLoadingIndicator(ProgressBar progressBar) {
        mLoadingIndicator = progressBar;
    }

    public void setAutoCompleteDelay(int autoCompleteDelay) {
        mAutoCompleteDelay = autoCompleteDelay;
    }

    @Override
    //It is the method which will handle the filtering of the text
    protected void performFiltering(CharSequence text, int keyCode) {
        //if indicator is not null than the progress bar will be visible
        if (mLoadingIndicator != null) {
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }
        //It is called when the message from the AutoComplete TextView is removed
        mHandler.removeMessages(MESSAGE_TEXT_CHANGED);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MESSAGE_TEXT_CHANGED, text), mAutoCompleteDelay);
    }

    @Override
    //It is the method
    public void onFilterComplete(int count) {
        if (mLoadingIndicator != null) {
            mLoadingIndicator.setVisibility(View.GONE);
        }
        super.onFilterComplete(count);
    }
}