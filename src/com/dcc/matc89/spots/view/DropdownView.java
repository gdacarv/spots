package com.dcc.matc89.spots.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;

public class DropdownView extends ImageView {

	private View mView;
	private OnClickListener mOnClickListener;
	private PopupWindow mPopupWindow;

	public DropdownView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public DropdownView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public DropdownView(Context context) {
		super(context);
		init();
	}

	private void init() {
		super.setOnClickListener(onClickListener);
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			toggleDropDown();
			if(mOnClickListener != null)
				mOnClickListener.onClick(v);
		}
	};

	public void setContentView(View view) {
		mView = view;
		if(mPopupWindow != null){
			if(mPopupWindow.isShowing())
				mPopupWindow.dismiss();
			mPopupWindow.setContentView(view);
		}
	}

	@Override
	public void setOnClickListener(OnClickListener l) {
		mOnClickListener = l;
	}

	public void toggleDropDown() {
		PopupWindow popupWindow = getPopupWindow();
		if(!popupWindow.isShowing())
			popupWindow.showAsDropDown(this);
		else
			popupWindow.dismiss();
	}

	private PopupWindow getPopupWindow() {
		if(mPopupWindow == null) {
			mPopupWindow = new PopupWindow(mView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
			mPopupWindow.setOutsideTouchable(true);
			mPopupWindow.setFocusable(true);
			mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
		}
		return mPopupWindow;
	}
	
	@Override
	protected void onDetachedFromWindow() {
		if(mPopupWindow != null && mPopupWindow.isShowing())
			mPopupWindow.dismiss();
		super.onDetachedFromWindow();
	}
}
