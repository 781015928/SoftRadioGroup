package com.czg.soft;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by czg on 2017/7/13.
 */

@SuppressLint("AppCompatCustomView")
public class SoftRadioButton extends FrameLayout implements SoftCheckable {


    /**
     * down  false  up true
     */

    private boolean mBroadcasting;
    private boolean orientation;
    private TextView mTv_checkable_text;
    private ImageView mIv_checkable_up;
    private ImageView mIv_checkable_down;
    private OnCheckedChangeListener mOnCheckedChangeWidgetListener;
    private int textColor = Color.BLACK;
    private int textColorChecked = Color.RED;
    private int upImageRes, upImageResChecked, downImageRes, downImageResChecked;


    private SoftRadioButton(Context context) {
        super(context);
    }

    public SoftRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public SoftRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }


    private void initView(AttributeSet attrs) {

        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.SoftRadioButton);

        String text = ta.getString(R.styleable.SoftRadioButton_text);
        textColor = ta.getColor(R.styleable.SoftRadioButton_textColor, textColor);
        textColorChecked = ta.getColor(R.styleable.SoftRadioButton_textColorChecked, textColorChecked);

        upImageRes = ta.getResourceId(R.styleable.SoftRadioButton_upImage, 0);
        downImageRes = ta.getResourceId(R.styleable.SoftRadioButton_downImage, 0);
        upImageResChecked = ta.getResourceId(R.styleable.SoftRadioButton_upImageChecked, 0);
        downImageResChecked = ta.getResourceId(R.styleable.SoftRadioButton_downImageChecked, 0);

        ta.recycle();


        LayoutInflater inflate = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflate.inflate(R.layout.soft_item_view, this, true);
        mTv_checkable_text = (TextView) findViewById(R.id.tv_checkable_text);
        mIv_checkable_up = (ImageView) findViewById(R.id.iv_checkable_up);
        mIv_checkable_down = (ImageView) findViewById(R.id.iv_checkable_down);
        mTv_checkable_text.setText(text);

        refreshView();
        setOnClickListener(null);
    }

    private boolean ischecked;

    public void setText(CharSequence text) {
        mTv_checkable_text.setText(text);
    }

    public CharSequence getText() {
       return mTv_checkable_text.getText();
    }

    private OnClickListener ml;

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        ml = l;
        super.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ml != null) {
                    ml.onClick(v);
                }


                if (isChecked()) {
                    orientation = !orientation;
                    setChecked(true, orientation);
                } else {
                    setChecked(true, orientation);
                }

                refreshView();
            }
        });
    }

    private SoftRadioGroup softGroup;

    public SoftRadioGroup getGroup() {
        if (softGroup == null) {
            if (getParent() != null && getParent() instanceof SoftRadioGroup) {
                softGroup = (SoftRadioGroup) getParent();
            }
        }
        return softGroup;
    }

    @Override
    public void setChecked(boolean checked, boolean isOrientation) {
        if (checked) {
            makeCallBack(isOrientation);
        }


        if (ischecked != checked) {//刷新其他的
            ischecked = checked;

            refreshView();
            if (mBroadcasting) {
                return;
            }

            mBroadcasting = true;

            if (mOnCheckedChangeWidgetListener != null) {
                mOnCheckedChangeWidgetListener.onCheckedChanged(this, ischecked);
            }


            mBroadcasting = false;
        } else {
            if (isOrientation != orientation) {
                refreshView();
                if (mBroadcasting) {
                    return;
                }

                mBroadcasting = true;
                if (mOnCheckedChangeWidgetListener != null) {
                    mOnCheckedChangeWidgetListener.onCheckedChanged(this, ischecked);
                }


                mBroadcasting = false;
            }
        }
    }

    private void makeCallBack(boolean isOrientation) {
        SoftRadioGroup group = getGroup();
        if (group != null && group.getOnCheckedChangeListener() != null) {
            group.getOnCheckedChangeListener().onCheckedChanged(group, getId(), isOrientation);
        }
    }

    @Override
    public boolean isChecked() {
        return ischecked;
    }

    public boolean isOrientation() {
        return orientation;
    }

    @Override
    public void toggle() {
        ischecked = !ischecked;
        refreshView();
    }


    private void refreshView() {
        if (isChecked()) {
            if (orientation) {

                mIv_checkable_up.setImageResource(upImageResChecked);
                mIv_checkable_down.setImageResource(downImageRes);
            } else {
                mIv_checkable_up.setImageResource(upImageRes);
                mIv_checkable_down.setImageResource(downImageResChecked);
            }

            mTv_checkable_text.setTextColor(textColorChecked);

        } else {
            mIv_checkable_up.setImageResource(upImageRes);
            mIv_checkable_down.setImageResource(downImageRes);
            mTv_checkable_text.setTextColor(textColor);
        }

    }

    public void setOnCheckedChangeWidgetListener(OnCheckedChangeListener onCheckedChangeWidgetListener) {
        mOnCheckedChangeWidgetListener = onCheckedChangeWidgetListener;
    }


    public interface OnCheckedChangeListener {
        /**
         * Called when the checked state of a compound button has changed.
         *
         * @param buttonView The compound button view whose state has changed.
         * @param isChecked  The new checked state of buttonView.
         */
        void onCheckedChanged(SoftRadioButton buttonView, boolean isChecked);
    }


}
