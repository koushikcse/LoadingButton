package com.kusu.loadingbutton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import androidx.appcompat.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class LoadingButton extends AppCompatButton implements View.OnTouchListener {

    private boolean isShadowColorDefined = false;
    //Custom values
    private boolean isShadowEnabled = true;
    private boolean isCircularButton = false;
    private int mButtonColor;
    private int mloaderColor;
    private int mShadowColor;
    private int mShadowHeight;
    private int mCornerRadius;
    private boolean isLoading = false;
    //Native values
    private int mPaddingLeft;
    private int mPaddingRight;
    private int mPaddingTop;
    private int mPaddingBottom;
    //Background drawable
    private Drawable pressedDrawable;
    private Drawable unpressedDrawable;

    private CircularAnimatedDrawable mAnimatedDrawable;
    private int mPaddingProgress = 15;
    private int mStrokeWidth = 10;
    private int mMaxProgress = 100;
    private int mColorIndicator;
    private int mProgress = 100;
    private String text = "";
    private Canvas mcanvas;

    @SuppressLint("ClickableViewAccessibility")
    public LoadingButton(Context context) {
        super(context);
        init();
        this.setOnTouchListener(this);
    }

    @SuppressLint("ClickableViewAccessibility")
    public LoadingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        parseAttrs(context, attrs);
        this.setOnTouchListener(this);
    }

    @SuppressLint("ClickableViewAccessibility")
    public LoadingButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        parseAttrs(context, attrs);
        this.setOnTouchListener(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        refresh();
    }

    private void init() {
        //Init default values
        isShadowEnabled = true;
        Resources resources = getResources();
        if (resources == null) return;
        mButtonColor = resources.getColor(R.color.fbutton_default_color);
        mShadowColor = resources.getColor(R.color.fbutton_default_shadow_color);
        mShadowHeight = resources.getDimensionPixelSize(R.dimen.fbutton_default_shadow_height);
        mCornerRadius = resources.getDimensionPixelSize(R.dimen.fbutton_default_conner_radius);
        text = getText().toString();
    }

    private void parseAttrs(Context context, AttributeSet attrs) {
        //Load from custom attributes
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingButton);
        if (typedArray == null) return;
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int attr = typedArray.getIndex(i);
            if (attr == R.styleable.LoadingButton_lb_isShadowEnable) {
                isShadowEnabled = typedArray.getBoolean(attr, true); //Default is true
            } else if (attr == R.styleable.LoadingButton_lb_buttonColor) {
                mButtonColor = typedArray.getColor(attr, getResources().getColor(R.color.unpressed_color));
            } else if (attr == R.styleable.LoadingButton_lb_loaderColor) {
                mloaderColor = typedArray.getColor(attr, getResources().getColor(R.color.white));
            } else if (attr == R.styleable.LoadingButton_lb_shadowColor) {
                mShadowColor = typedArray.getColor(attr, getResources().getColor(R.color.pressed_color));
                isShadowColorDefined = true;
            } else if (attr == R.styleable.LoadingButton_lb_shadowHeight) {
                mShadowHeight = typedArray.getDimensionPixelSize(attr, R.dimen.fbutton_default_shadow_height);
            } else if (attr == R.styleable.LoadingButton_lb_cornerRadius) {
                mCornerRadius = typedArray.getDimensionPixelSize(attr, R.dimen.fbutton_default_conner_radius);
            } else if (attr == R.styleable.LoadingButton_lb_isCircular) {
                isCircularButton = typedArray.getBoolean(attr, false);
            } else if (attr == R.styleable.LoadingButton_lb_isLoading) {
                isLoading = typedArray.getBoolean(attr, false);
            } else if (attr == R.styleable.LoadingButton_lb_loaderMargin) {
                mPaddingProgress = mCornerRadius = typedArray.getDimensionPixelSize(attr, R.dimen.fbutton_default_progress_margin);
            } else if (attr == R.styleable.LoadingButton_lb_loaderWidth) {
                mStrokeWidth = mCornerRadius = typedArray.getDimensionPixelSize(attr, R.dimen.fbutton_default_progress_width);

            }
        }
        typedArray.recycle();

        //Get paddingLeft, paddingRight
        int[] attrsArray = new int[]{
                android.R.attr.paddingLeft,  // 0
                android.R.attr.paddingRight, // 1
        };
        TypedArray ta = context.obtainStyledAttributes(attrs, attrsArray);
        if (ta == null) return;
        mPaddingLeft = ta.getDimensionPixelSize(0, 0);
        mPaddingRight = ta.getDimensionPixelSize(1, 0);
        ta.recycle();

        //Get paddingTop, paddingBottom
        int[] attrsArray2 = new int[]{
                android.R.attr.paddingTop,   // 0
                android.R.attr.paddingBottom,// 1
        };
        TypedArray ta1 = context.obtainStyledAttributes(attrs, attrsArray2);
        if (ta1 == null) return;
        mPaddingTop = ta1.getDimensionPixelSize(0, 0);
        mPaddingBottom = ta1.getDimensionPixelSize(1, 0);
        ta1.recycle();
    }


    public void refresh() {
        int alpha = Color.alpha(mButtonColor);
        float[] hsv = new float[3];
        Color.colorToHSV(mButtonColor, hsv);
        hsv[2] *= 0.8f; // value component

        //if shadow color was not defined, generate shadow color = 80% brightness
        if (!isShadowColorDefined) {
            mShadowColor = Color.HSVToColor(alpha, hsv);
        }
        //Create pressed background and unpressed background drawables

        if (this.isEnabled()) {
            if (isShadowEnabled) {
                pressedDrawable = createDrawable(mCornerRadius, Color.TRANSPARENT, mButtonColor);
                unpressedDrawable = createDrawable(mCornerRadius, mButtonColor, mShadowColor);
            } else {
                mShadowHeight = 0;
                pressedDrawable = createDrawable(mCornerRadius, mShadowColor, Color.TRANSPARENT);
                unpressedDrawable = createDrawable(mCornerRadius, mButtonColor, Color.TRANSPARENT);
            }
        } else {
            Color.colorToHSV(mButtonColor, hsv);
            hsv[1] *= 0.60f; // saturation component
            int disabledColor = mShadowColor = Color.HSVToColor(alpha, hsv);
            // Disabled button does not have shadow
            pressedDrawable = createDrawable(mCornerRadius, disabledColor, Color.TRANSPARENT);
            unpressedDrawable = createDrawable(mCornerRadius, disabledColor, Color.TRANSPARENT);
        }
        updateBackground(unpressedDrawable);
        //Set padding
        this.setPadding(mPaddingLeft, mPaddingTop + mShadowHeight, mPaddingRight, mPaddingBottom + mShadowHeight);
    }

    private void updateBackground(Drawable background) {
        if (background == null) return;
        //Set button background
        this.setBackground(background);
    }

    private LayerDrawable createDrawable(int radius, int topColor, int bottomColor) {

        float[] outerRadius = new float[]{radius, radius, radius, radius, radius, radius, radius, radius};

        //Top
        RoundRectShape topRoundRect = new RoundRectShape(outerRadius, null, null);
        ShapeDrawable topShapeDrawable = new ShapeDrawable(topRoundRect);
        topShapeDrawable.getPaint().setColor(topColor);
        //Bottom
        RoundRectShape roundRectShape = new RoundRectShape(outerRadius, null, null);
        ShapeDrawable bottomShapeDrawable = new ShapeDrawable(roundRectShape);
        bottomShapeDrawable.getPaint().setColor(bottomColor);
        //Create array
        Drawable[] drawArray = {bottomShapeDrawable, topShapeDrawable};
        LayerDrawable layerDrawable = new LayerDrawable(drawArray);

        //Set shadow height
        if (isShadowEnabled && topColor != Color.TRANSPARENT) {
            //unpressed drawable
            layerDrawable.setLayerInset(0, 0, 0, 0, 0);  /*index, left, top, right, bottom*/
        } else {
            //pressed drawable
            layerDrawable.setLayerInset(0, 0, mShadowHeight, 0, 0);  /*index, left, top, right, bottom*/
        }
        layerDrawable.setLayerInset(1, 0, 0, 0, mShadowHeight);  /*index, left, top, right, bottom*/

        return layerDrawable;

    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        refresh();
    }

    //Getter
    public boolean isShadowEnabled() {
        return isShadowEnabled;
    }

    //Setter
    public void setShadowEnabled(boolean isShadowEnabled) {
        this.isShadowEnabled = isShadowEnabled;
        refresh();
    }

    public int getButtonColor() {
        return mButtonColor;
    }

    public void setButtonColor(int buttonColor) {
        this.mButtonColor = buttonColor;
        refresh();
    }

    public int getShadowColor() {
        return mShadowColor;
    }

    public void setShadowColor(int shadowColor) {
        this.mShadowColor = shadowColor;
        isShadowColorDefined = true;
        refresh();
    }

    public int getShadowHeight() {
        return mShadowHeight;
    }

    public void setShadowHeight(int shadowHeight) {
        this.mShadowHeight = shadowHeight;
        refresh();
    }

    public void setCornerRadius(int cornerRadius) {
        this.mCornerRadius = cornerRadius;
        refresh();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                updateBackground(pressedDrawable);
                this.setPadding(mPaddingLeft, mPaddingTop + mShadowHeight, mPaddingRight, mPaddingBottom);
                break;
            case MotionEvent.ACTION_MOVE:
                Rect r = new Rect();
                view.getLocalVisibleRect(r);
                if (!r.contains((int) motionEvent.getX(), (int) motionEvent.getY() + 3 * mShadowHeight) &&
                        !r.contains((int) motionEvent.getX(), (int) motionEvent.getY() - 3 * mShadowHeight)) {
                    updateBackground(unpressedDrawable);
                    this.setPadding(mPaddingLeft, mPaddingTop + mShadowHeight, mPaddingRight, mPaddingBottom + mShadowHeight);
                }
                break;
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                updateBackground(unpressedDrawable);
                this.setPadding(mPaddingLeft, mPaddingTop + mShadowHeight, mPaddingRight, mPaddingBottom + mShadowHeight);
                break;
        }
        return false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if (isCircularButton) {
            mCornerRadius = widthSize / 2;
            refresh();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mcanvas = canvas;
        if (isLoading) {
            drawIndeterminateProgress(canvas);
            setText("");
        } else {
            if (text.length() != 0)
                setText(text);
        }
    }

    private void drawIndeterminateProgress(Canvas canvas) {
        if (mAnimatedDrawable == null) {
            int offset = (getWidth() - getHeight()) / 2;
            //mColorIndicator = getResources().getColor(R.color.white);
            mColorIndicator = mloaderColor;
            mAnimatedDrawable = new CircularAnimatedDrawable(mColorIndicator, mStrokeWidth);
            int left = offset + mPaddingProgress;
            int right = getWidth() - offset - mPaddingProgress;
            int bottom = getHeight() - mPaddingProgress;
            int top = mPaddingProgress;
            mAnimatedDrawable.setBounds(left, top, right, bottom);
            mAnimatedDrawable.setCallback(this);
            mAnimatedDrawable.start();
        } else {
            mAnimatedDrawable.draw(canvas);
        }
    }

    private void setLoading(boolean loading) {
        isLoading = loading;
        if (isLoading) {
            drawIndeterminateProgress(mcanvas);
            setText("");
        } else {
            if (text.length() != 0)
                setText(text);
        }
    }

    public String getButtonText() {
        return text;
    }

    public void setButtonText(String text) {
        this.text = text;
    }

    public void showLoading() {
        setLoading(true);
    }

    public void hideLoading() {
        setLoading(false);
    }
}
