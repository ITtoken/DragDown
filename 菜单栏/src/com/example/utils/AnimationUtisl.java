package com.example.utils;

import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.RelativeLayout;

public class AnimationUtisl {

	public static int animationCount = 0;//���ڽ��ж����Ŀؼ�������
	public static int showcount=3;//��ʾ�ڽ����ϵĿؼ�������

	/**
	 * ���ؿؼ���������
	 * 
	 * @param rl
	 *            Ҫִ�����ض�������Բ����ļ�
	 * @param offset
	 */
	public static void unshow(RelativeLayout rl, int offset) {
		for (int i = 0; i < rl.getChildCount(); i++) {
			rl.getChildAt(i).setEnabled(false);
		}
		RotateAnimation ra = new RotateAnimation(0, -180,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1);
		ra.setDuration(500);
		ra.setStartOffset(offset);
		ra.setFillAfter(true);
		ra.setAnimationListener(new MyAnimationListener());
		rl.startAnimation(ra);
		
		showcount--;

	}

	/**
	 * ��ʾ�ؼ���������
	 * 
	 * @param rl
	 *            Ҫִ����ʾ��������Բ����ļ�
	 */
	public static void show(RelativeLayout rl, int offset) {
		for (int i = 0; i < rl.getChildCount(); i++) {
			rl.getChildAt(i).setEnabled(true);
		}
		RotateAnimation ra = new RotateAnimation(-180, 0,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1);
		ra.setDuration(500);
		ra.setStartOffset(offset);
		ra.setFillAfter(true);
		ra.setAnimationListener(new MyAnimationListener());
		rl.startAnimation(ra);
		
		showcount++;
	}

	static class MyAnimationListener implements AnimationListener {
		// ������ʼʱ����
		@Override
		public void onAnimationStart(Animation animation) {
			animationCount++;
		}

		// ��������ʱ����
		@Override
		public void onAnimationEnd(Animation animation) {
			animationCount--;
		}

		// �ظ���ʱ����
		@Override
		public void onAnimationRepeat(Animation animation) {

		}

	}

}
