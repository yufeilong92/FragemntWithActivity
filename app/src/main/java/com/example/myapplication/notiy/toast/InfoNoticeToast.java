package com.example.myapplication.notiy.toast;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;

import com.example.myapplication.R;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2016/12/2
 *     desc   : 始终只显示一个toast
 * </pre>
 */

public final class InfoNoticeToast extends InfoNoticeBaseToast
{
	/**
	 * Construct an empty Toast object.  You must call {@link #setView} before you
	 * can call {@link #show}.
	 *
	 * @param context The context to use.  Usually your {@link Application}
	 * or {@link Activity} object.
	 */

	@SuppressWarnings("unused")
	private static final String TAG = InfoNoticeToast.class.getSimpleName();

	private static InfoNoticeToast mInstance = null;

	private TextView mContentTextView = null;

	public static InfoNoticeToast getInstance(Context context)
	{
		if (mInstance == null)
		{
			mInstance = new InfoNoticeToast(context.getApplicationContext());
		}
		return mInstance;
	}

	private InfoNoticeToast(Context context)
	{
		super(context);
		addView(initChildView());
	}

	private View initChildView()
	{
		mContentTextView = new TextView(mContext);
		int textSize = mContext.getResources().getDimensionPixelOffset(R.dimen.text_size_super_little);
		int textColor = mContext.getResources().getColor(android.R.color.white);
		int horizontalPadding = mContext.getResources().getDimensionPixelOffset(R.dimen.padding_small);
		int verticalPadding = mContext.getResources().getDimensionPixelOffset(R.dimen.padding_micro);
		setTextSize(10);
		setTextColor(textColor);
		setTextBackgroundResource(R.drawable.toast_bg);
		mContentTextView.setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding);
		mContentTextView.setGravity(Gravity.CENTER);
		return mContentTextView;
	}

	public InfoNoticeToast setText(String text)
	{
		mContentTextView.setText(text);
		return this;
	}

	public InfoNoticeToast setTextResId(int resId)
	{
		mContentTextView.setText(resId);
		return this;
	}

	public InfoNoticeToast setTextSize(int size)
	{
		mContentTextView.setTextSize(size);
		return this;
	}

	public InfoNoticeToast setTextColor(@ColorInt int color)
	{
		mContentTextView.setTextColor(color);
		return this;
	}

	public InfoNoticeToast setTextBackgroundResource(@DrawableRes int resId)
	{
		mContentTextView.setBackgroundResource(resId);
		return this;
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

}
