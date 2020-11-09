package com.example.myapplication.notiy.toast;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.IntDef;

import com.example.myapplication.R;


/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2018/6/13
 *     desc   :
 * </pre>
 */

public class InfoNoticeBaseToast extends Toast
{
	/**
	 * Construct an empty Toast object.  You must call {@link #setView} before you
	 * can call {@link #show}.
	 *
	 * @param context The context to use.  Usually your {@link Application}
	 *                or {@link Activity} object.
	 */

	@SuppressWarnings("unused")
	private static final String TAG = InfoNoticeBaseToast.class.getSimpleName();

	protected Context mContext = null;
	private RelativeLayout mRootLayout = null;

	public InfoNoticeBaseToast(Context context)
	{
		super(context);
		mContext = context;
		mRootLayout = new RelativeLayout(mContext);
		setView(mRootLayout);
		setToastDuration(Toast.LENGTH_SHORT);
		setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, mContext.getResources().getDimensionPixelOffset(R.dimen.margin_large));
	}

	public void addView(View childView)
	{
		if (childView != null)
		{
			mRootLayout.addView(childView);
		}
	}

	public InfoNoticeBaseToast setToastDuration(@Duration int duration)
	{
		setDuration(duration);
		return this;
	}

	public void showAtLocation(int gravity, int xOffset, int yOffset)
	{
		setGravity(gravity, xOffset, yOffset);
		show();
	}

	public void dismiss()
	{
		super.cancel();
	}

	@IntDef({ LENGTH_LONG, LENGTH_SHORT })
	public @interface Duration
	{

	}

}
