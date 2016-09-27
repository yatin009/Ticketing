package io.webguru.ticketing.ViewElements;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by yatin on 9/3/2015.
 */
public class AdjustImageView extends ImageView {

    public AdjustImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, heightMeasureSpec);
    }
}
