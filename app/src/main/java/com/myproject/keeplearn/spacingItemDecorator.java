package com.myproject.keeplearn;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class spacingItemDecorator extends RecyclerView.ItemDecoration {
    private final int verticalSpaceHeight;
    public spacingItemDecorator(int verticalSpaceHeight){
        this.verticalSpaceHeight=verticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.bottom=verticalSpaceHeight;
    }
}
