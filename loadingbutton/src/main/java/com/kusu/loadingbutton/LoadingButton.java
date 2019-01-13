package com.kusu.loadingbutton;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class LoadingButton extends FrameLayout {
    public LoadingButton(@androidx.annotation.NonNull Context context) {
        super(context);
    }

    public LoadingButton(@androidx.annotation.NonNull Context context, @androidx.annotation.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingButton(@androidx.annotation.NonNull Context context, @androidx.annotation.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LoadingButton(@androidx.annotation.NonNull Context context, @androidx.annotation.Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
