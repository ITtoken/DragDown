package com.example.slidemenu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class SlideMenu extends ViewGroup {

	private View menuView, mainView;
	private  int menuHiewWidth;
	private int downX;


	/**
	 * ��Ҫ��̬New��ʱ�򣬵���������캯��
	 * 
	 * @param context
	 */
	public SlideMenu(Context context) {
		super(context);
	}

	/**
	 * Ҫ����xml�ļ���ʹ�ã��ͱ���Ҫʵ��������캯��
	 * 
	 * @param context
	 * @param attrs
	 */
	public SlideMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * ��ViewGroup�����е���View��һ����View����������ϵ�ʱ����ã���ʼ����view������
	 * 
	 * ע�⣺�����޷���ȡ��view�Ŀ��
	 */
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		menuView = getChildAt(0);
		mainView = getChildAt(1);
	}

	/**
	 * �����ӿؼ���Slidemenu�еĻ��ƴ�С
	 * 
	 * widthMeasureSpec���Զ���ؼ���SlideMenu���Ŀ� heightMeasureSpec������ؼ���SlideMenu���ĸ�
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		menuHiewWidth = menuView.getLayoutParams().width;
		menuView.measure(menuHiewWidth, heightMeasureSpec);
		mainView.measure(widthMeasureSpec, heightMeasureSpec);

	}

	/**
	 * �����ӿؼ���SlideMenu����ʾλ��
	 * 
	 * �� ��GroupView��������SlideView�������Ͻ�Ϊԭ�㣬Y������Ϊ�������������Ϊ�����׼
	 * 
	 * l��groupView����ߵ����꣨X���꣩��0 t��GroupView�ϱߵ���ߣ�Y���꣩��0
	 * r��groupView�ұߵ����꣨X��ߣ���Group�Ŀ�� b��GroupView�±ߵ����꣨Y���꣩��GroupView�ĸ�
	 * 
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		menuView.layout(-menuHiewWidth, 0, 0, b);
		mainView.layout(l, t, r, b);
	}

	int slideX = 0;
	private  ScrollAnimation sa;

	/**
	 * ��Ӵ����¼�����
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downX = (int) event.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			int mx = (int) event.getX();
			int moveX = mx - downX;// �ƶ��ľ���

			slideX = getScrollX() - moveX;

			if (slideX < -menuHiewWidth) {
				slideX = -menuHiewWidth;
			} else if (slideX > 0) {
				slideX = 0;
			}
			scrollTo(slideX, 0);
			break;
		case MotionEvent.ACTION_UP:
			if (slideX <= -menuHiewWidth / 2) {
				sa = new ScrollAnimation(this, -menuHiewWidth);
				invalidate();
			} else if (slideX > -menuHiewWidth / 2) {
				sa = new ScrollAnimation(this, 0);
				invalidate();
			}
			startAnimation(sa);
			break;
		}
		return true;
	}

	 class ScrollAnimation extends Animation {

		private View view;
		private int targetX;
		private int startX;
		private float totalScroll;

		public ScrollAnimation(View view, int targetX) {
			super();
			this.view = view;
			this.targetX = targetX;

			startX = view.getScrollX();
			totalScroll = this.targetX - startX;

			setDuration(Math.abs(startX));
		}

		@Override
		protected void applyTransformation(float interpolatedTime,
				Transformation t) {
			super.applyTransformation(interpolatedTime, t);
			int currentX = (int) (startX + totalScroll * interpolatedTime);
			view.scrollTo(currentX, 0);
		}
	}
}
