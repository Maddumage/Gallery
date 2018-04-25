package com.roshan.gallery.helper;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class ItemLayout extends RelativeLayout {

    public ItemLayout(Context context){
        super(context);
    }

    public ItemLayout(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
    }

    public ItemLayout(Context context, AttributeSet attributeSet, int defStyleAttr){
        super(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ItemLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
