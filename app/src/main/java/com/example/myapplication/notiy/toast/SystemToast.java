package com.example.myapplication.notiy.toast;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.StringRes;

import com.example.myapplication.R;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2017/12/21
 *     desc   : 系统级的Toast弹框，替代原生Toast，解决部分手机不显示原生Toast
 *     			https://github.com/zhitaocai/ToastCompat_Deprecated
 * </pre>
 */

public class SystemToast
{
	public static final String TAG = SystemToast.class.getSimpleName();

	public static final int TOAST_LONG = 3500;
	public static final int TOAST_SHORT = 2000;

	/**
	 * 维护toast队列
	 */
	private static BlockingQueue<SystemToast> mQueue = new LinkedBlockingDeque<>();

	/**
	 * 原子操作：判断当前是否在读取{@linkplain #mQueue 队列}来显示toast
	 */
	private static AtomicInteger mAtomicInteger = new AtomicInteger(0);
	private static Handler mHandler = new Handler();

	private WindowManager mWM = null;
	private View mContentView = null;
	private WindowManager.LayoutParams mParams = null;
	private int mDuration = 0;

	public SystemToast(Context context)
	{
		mWM = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

		mParams = new WindowManager.LayoutParams();
		mParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
		mParams.width = WRAP_CONTENT;
		mParams.height = WRAP_CONTENT;
		/**
		 * no focus, no touch
		 */
		mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
		/**
		 * no black background
		 */
		mParams.format = PixelFormat.TRANSLUCENT;
		mParams.windowAnimations = android.R.style.Animation_Toast;
		mParams.type = WindowManager.LayoutParams.TYPE_TOAST;
		mParams.x = 0;
		mParams.y = context.getResources().getDimensionPixelOffset(R.dimen.toast_offset_y);
	}

	public static SystemToast makeText(Context context, CharSequence text, int duration)
	{
		SystemToast toast = new SystemToast(context);
		LinearLayout layout = new LinearLayout(context);
		layout.setLayoutParams(new LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
		layout.setBackgroundResource(R.drawable.toast_bg);
		TextView textView = new TextView(context);
		LayoutParams params = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
		int horizontalMargin = context.getResources().getDimensionPixelOffset(R.dimen.margin_micro);
		int verticalMargin = context.getResources().getDimensionPixelOffset(R.dimen.margin_micro);
		params.setMargins(horizontalMargin, verticalMargin, horizontalMargin, verticalMargin);
		textView.setId(R.id.toast_text);
		textView.setLayoutParams(params);
		textView.setText(text);
		textView.setTextColor(context.getResources().getColor(android.R.color.white));
		layout.addView(textView);

		toast.mContentView = layout;
		toast.setDuration(duration);
		return toast;
	}

	public static SystemToast makeText(Context context, @StringRes int resId, int duration)
	{
		return makeText(context, context.getResources().getString(resId), duration);
	}

	public void setView(View view)
	{
		mContentView = view;
	}

	/**
	 * 可以设置除开{@link Toast#LENGTH_LONG}、{@link Toast#LENGTH_SHORT}的时长
	 *
	 * @param duration : ms
	 */
	public void setDuration(int duration)
	{
		if (duration < 0)
		{
			duration = 0;
		}

		if (duration == Toast.LENGTH_SHORT)
		{
			duration = TOAST_SHORT;
		}
		else if (duration == Toast.LENGTH_LONG)
		{
			duration = TOAST_LONG;
		}
		mDuration = duration;
	}

	public void setGravity(int gravity, int xOffset, int yOffset)
	{
		mParams.gravity = gravity;
		mParams.x = xOffset;
		mParams.y = yOffset;
	}

	public void show()
	{
		/**
		 * 1. 将本次需要显示的toast加入到队列中
		 */
		mQueue.offer(this);

		/**
		 * 2. 如果队列还没有激活，就激活队列，依次展示队列中的toast
		 */
		if (0 == mAtomicInteger.get())
		{
			mAtomicInteger.incrementAndGet();
			mHandler.post(mActive);
		}
	}

	public void cancel()
	{
		/**
		 * 1. 如果队列已经处于非激活状态或者队列没有toast了，就表示队列没有toast正在展示了，直接return
		 */
		if (0 == mAtomicInteger.get() && mQueue.isEmpty())
		{
			return;
		}

		/**
		 * 2. 当前显示的toast是否为本次要取消的toast，如果是的话
		 * 2.1 先移除之前的队列逻辑
		 * 2.2 立即暂停当前显示的toast
		 * 2.3 重新激活队列
		 */
		if (this.equals(mQueue.peek()))
		{
			mHandler.removeCallbacks(mActive);
			mHandler.post(mHide);
			mHandler.post(mActive);
		}
		// TODO 如果一个Toast在队列中的等候展示，当调用了这个toast的取消时，考虑是否应该从对队列中移除，看产品需求吧
	}

	private void handleShow()
	{
		if (mContentView != null)
		{
			if (mContentView.getParent() != null)
			{
				mWM.removeView(mContentView);
			}
			mWM.addView(mContentView, mParams);
		}
	}

	private void handleHide()
	{
		if (mContentView != null)
		{
			/**
			 * note: checking parent() just to make sure the view has
			 * been added...  i have seen cases where we get here when
			 * the view isn't yet added, so let's try not to crash.
			 */
			if (mContentView.getParent() != null)
			{
				mWM.removeView(mContentView);
				/**
				 * 同时从队列中移除这个toast
				 */
				mQueue.poll();
			}
		}
	}

	private static void activeQueue()
	{
		SystemToast systemToast = mQueue.peek();
		if (systemToast == null)
		{
			/**
			 * 如果不能从队列中获取到toast的话，那么就表示已经暂时完所有的toast了
			 * 这个时候需要标记队列状态为：非激活读取中
			 */
			mAtomicInteger.decrementAndGet();
		}
		else
		{
			/**
			 * 如果还能从队列中获取到toast的话，那么就表示还有toast没有展示
			 * 1. 展示队首的toast
			 * 2. 设置一定时间后主动采取toast消失措施
			 * 3. 设置展示完毕之后再次执行本逻辑，以展示下一个toast
			 */
			mHandler.post(systemToast.mShow);
			mHandler.postDelayed(systemToast.mHide, systemToast.mDuration);
			mHandler.postDelayed(mActive, systemToast.mDuration);
		}
	}

	private final Runnable mShow = new Runnable()
	{
		@Override
		public void run()
		{
			handleShow();
		}
	};

	private final Runnable mHide = new Runnable()
	{
		@Override
		public void run()
		{
			handleHide();
		}
	};

	private static final Runnable mActive = new Runnable()
	{
		@Override
		public void run()
		{
			activeQueue();
		}
	};

}
