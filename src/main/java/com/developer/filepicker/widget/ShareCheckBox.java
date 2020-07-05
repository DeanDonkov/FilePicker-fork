package com.developer.filepicker.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.developer.filepicker.R;
import com.developer.filepicker.widget.listeners.OnCheckedChangeListener;

public class ShareCheckBox extends View {
    private Context context;
    private int minDim;
    private Paint paint;
    private RectF bounds;
    private boolean checked;
    private OnCheckedChangeListener onCheckedChangeListener;
    private Path tick;

    private Drawable customImage;


    public ShareCheckBox(Context context) {
        super(context);
        initView(context);
    }

    public ShareCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        customImage = context.getResources().getDrawable(R.drawable.share);
    }

    public ShareCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void initView(Context context) {
        this.context = context;
        checked = false;
        tick = new Path();
        paint = new Paint();
        bounds = new RectF();
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChecked(!checked);
                onCheckedChangeListener.onCheckedChanged(ShareCheckBox.this, isChecked());
            }
        };

        setOnClickListener(onClickListener);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isChecked()) {
            paint.reset();
            paint.setAntiAlias(true);
            bounds.set(minDim / 10, minDim / 10, minDim - (minDim / 10), minDim - (minDim / 10));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                paint.setColor(getResources().getColor(R.color.colorAccent, context.getTheme()));
            } else {
                paint.setColor(getResources().getColor(R.color.colorAccent));
            }
            canvas.drawRoundRect(bounds, minDim / 8, minDim / 8, paint);

            // THIS IS FOR  CHECKBOX tick painting
            paint.setColor(Color.parseColor("#6666ff"));
            paint.setStrokeWidth(minDim / 10);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.BEVEL);
            canvas.drawPath(tick, paint);
        } else {
            Rect imageBounds = canvas.getClipBounds();
            customImage.setBounds(imageBounds);
            customImage.draw(canvas);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        minDim = Math.min(width, height);
        bounds.set(minDim / 10, minDim / 10, minDim - (minDim / 10), minDim - (minDim / 10));
        tick.moveTo(minDim / 4, minDim / 2);
        tick.lineTo(minDim / 2.5f, minDim - (minDim / 3));

        tick.moveTo(minDim / 2.75f, minDim - (minDim / 3.25f));
        tick.lineTo(minDim - (minDim / 4), minDim / 3);
        setMeasuredDimension(width, height);
    }

    public void grayOut(){
        this.setClickable(false);
        this.setChecked(false);
        this.setAlpha(0.7f);
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        invalidate();
    }

    public void setOnCheckedChangedListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }
}
