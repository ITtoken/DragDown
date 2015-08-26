package com.example.togglebutton;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ToggleButton extends View {

	private ToggleState state;
	private Bitmap bgBmp;
	private Bitmap switchBmp;
	private int currentX;
	private Canvas canvas;
	private int swichBtnposition;
	private onToggleStateChangeListener listener;
	private boolean isSlide = false;

	/**
	 * ���View���ڲ����ļ���xml����ʹ�ã�ϵͳ������������� ��ΪϵͳҪ����XML�ļ������Ե���AttributeSet attrs�������
	 * 
	 * @param context
	 * @param attrs
	 */
	public ToggleButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO �Զ����ɵĹ��캯�����
	}

	/**
	 * ���view��Ҫ��java�����ж�̬��new�������ߵľ����������
	 * 
	 * @param context
	 */
	public ToggleButton(Context context) {
		super(context);
		// TODO �Զ����ɵĹ��캯�����
	}

	public enum ToggleState {
		OPEN, CLOSE
	}

	/**
	 * ���ÿ��ؿؼ��ı���ͼƬ
	 * 
	 * @param slideBgId
	 *            :����ͼƬ����Դid
	 */
	public void setBacgrounfSource(int slideBgId) {
		bgBmp = BitmapFactory.decodeResource(getResources(), slideBgId);
	}

	/**
	 * ���ÿ��عܿؼ��Ŀ��ذ�ťͼƬ
	 * 
	 * @param switckBtnId
	 *            ��Switch���ذ�ť����Դid
	 */
	public void setSwitckSource(int switckBtnId) {
		switchBmp = BitmapFactory.decodeResource(getResources(), switckBtnId);
	}

	/**
	 * ���ÿ��ؿռ�ĳ�ʼ״̬
	 * 
	 * @param stat
	 *            ��ʼ״ֵ̬
	 * @category ToggleState.OPEN:��; ToggleState.CLOSE:��
	 */
	public void setDefaltStat(ToggleState stat) {
		state = stat;
	}

	/**
	 * ���������ÿؼ���ʾ�Ŀ��
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(bgBmp.getWidth(), bgBmp.getHeight());// ���ÿؼ���ʾ�����ֵ
	}

	/**
	 * ����:���ÿؼ���ʾ�ڽ����ϵ�����
	 * �ڻ��Ƶ�ʱ��,ϵͳ����ȥ�ص�onMeasure����,ȷ��һ��������ߵľ���(setMeasuredDimension�Ĳ���),
	 * Ȼ������������η�Χ�ڽ��л��Ƶ�
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		this.canvas = canvas;
		// ���Ʊ���ͼƬ
		canvas.drawBitmap(bgBmp, 0, 0, null);
		// ���ƻ�����ͼƬ

		if (isSlide) {
			swichBtnposition = currentX - switchBmp.getWidth() / 2;
			if (swichBtnposition > 0
					&& swichBtnposition < bgBmp.getWidth()
							- switchBmp.getWidth()) {
				canvas.drawBitmap(switchBmp, swichBtnposition, 0, null);
			} else if (swichBtnposition <= 0) {
				canvas.drawBitmap(switchBmp, 0, 0, null);
			} else {
				canvas.drawBitmap(switchBmp,
						bgBmp.getWidth() - switchBmp.getWidth(), 0, null);
			}
			

		} else {

			if (state == ToggleState.OPEN) {
				canvas.drawBitmap(switchBmp, bgBmp.getWidth() / 2, 0, null);
			} else {
				canvas.drawBitmap(switchBmp, 0, 0, null);
			}
		}

	}

	/**
	 * ���������¼���View���������������������Viewʱ�ص��������
	 * 
	 * @param event
	 *            �������¼�
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		currentX = (int) event.getX();// ��ȡView�����x����ֵ����View�ؼ������Ͻ�Ϊԭ�㣩��ע�⻹��һ��event.getRawX();��
										// ����ǻ�ȡ����Ļ�����x����ֵ��һ��Ļ���Ͻ�Ϊԭ�㣩
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			isSlide = true;
			break;
		case MotionEvent.ACTION_MOVE:
			isSlide = true;
			break;
		case MotionEvent.ACTION_UP:
			isSlide = false;
			if (currentX < bgBmp.getWidth() / 2) {
				if(state != ToggleState.CLOSE){
					state = ToggleState.CLOSE;
					if(listener != null){
						listener.ToggleState(state);
					}
				}
			} else {
				if(state != ToggleState.OPEN){
					state = ToggleState.OPEN;
					if(listener != null){
						listener.ToggleState(state);
					}
				}
				
			}
			
		
			break;
		}
		invalidate();// ��������������ػ棬������÷��򿴲�������Ч��
		return true;
	}

	public void setToggleStateChangeListener(onToggleStateChangeListener listener) {
		this.listener = listener;
	}

	public interface onToggleStateChangeListener {
		/**
		 * @param toggleState ����״̬��
		 * 
		 * @category ��ȡֵ��ToggleState.OPEN �� ToggleState.CLOSE
		 */
		public void ToggleState(ToggleState toggleState);
	}

}
