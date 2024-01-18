package com.example.experiments2.component.dialog.profile;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;
import androidx.core.view.WindowInsetsCompat;


public class MyScrollView extends ScrollView {

    public MyScrollView(Context context) {
        super(context);
    }
    public MyScrollView( Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
    public MyScrollView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        boolean isShown = WindowInsetsCompat
                .toWindowInsetsCompat(getRootWindowInsets())
                .isVisible(WindowInsetsCompat.Type.ime());
        if (isShown) {
            scrollTo(0, 0);
        }

        super.onScrollChanged(l, t, oldl, oldt);
    }
}