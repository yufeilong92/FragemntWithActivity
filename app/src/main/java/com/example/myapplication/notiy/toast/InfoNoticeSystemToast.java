package com.example.myapplication.notiy.toast;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;

import com.example.myapplication.R;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     date   : 2017/10/23
 *     desc   : customer toast
 * </pre>
 */

public final class InfoNoticeSystemToast extends SystemToast
{
	/**
	 * Construct an empty Toast object. You must call {@link #setView} before
	 * you can call {@link #showMsg}.
	 *
	 * @param context
	 *            The context to use. Usually your {@link Application} or
	 *            {@link Activity} object.
	 */

	@SuppressWarnings("unused")
	private static final String TAG = InfoNoticeSystemToast.class.getSimpleName();

	private static InfoNoticeSystemToast mInstance = null;

	private Context mContext = null;
	private TextView mContentTextView = null;

	private InfoNoticeSystemToast(Context context)
	{
		super(context);
		mContext = context;
		RelativeLayout rootLayout = new RelativeLayout(mContext);
		mContentTextView = new TextView(mContext);
		int textSize = mContext.getResources().getDimensionPixelOffset(R.dimen.text_size_super_little);
		int textColor = mContext.getResources().getColor(android.R.color.white);
		int horizontalPadding = mContext.getResources().getDimensionPixelOffset(R.dimen.padding_small);
		int verticalPadding = mContext.getResources().getDimensionPixelOffset(R.dimen.padding_micro);
		setTextSize(textSize);
		setTextColor(textColor);
		setTextBackgroundResource(R.drawable.toast_bg);
		mContentTextView.setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding);
		mContentTextView.setGravity(Gravity.CENTER);
		rootLayout.addView(mContentTextView);
		setView(rootLayout);
		setDuration(Toast.LENGTH_SHORT);
		setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, mContext.getResources().getDimensionPixelOffset(R.dimen.margin_large));
	}

	public static InfoNoticeSystemToast getInstance(Context context)
	{
		if (mInstance == null)
		{
			mInstance = new InfoNoticeSystemToast(context.getApplicationContext());
		}
		return mInstance;
	}

	public InfoNoticeSystemToast setText(String text)
	{
		mContentTextView.setText(text);
		return this;
	}

	public InfoNoticeSystemToast setTextResId(int resId)
	{
		mContentTextView.setText(resId);
		return this;
	}

	public InfoNoticeSystemToast setTextSize(int size)
	{
		mContentTextView.setTextSize(size);
		return this;
	}

	public InfoNoticeSystemToast setTextColor(@ColorInt int color)
	{
		mContentTextView.setTextColor(color);
		return this;
	}

	public InfoNoticeSystemToast setTextBackgroundResource(@DrawableRes int resId)
	{
		mContentTextView.setBackgroundResource(resId);
		return this;
	}

	public void showAtLocation(int gravity, int xOffset, int yOffset)
	{
		setGravity(gravity, xOffset, yOffset);
		show();
	}

	public void showMsg(int resId)
	{
		setTextResId(resId);
		show();
	}

	public void showMsg(String str)
	{
		setText(str);
		show();
	}

	public void dismiss()
	{
		super.cancel();
	}

}
