package com.example.adslidebar;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.example.entity.News;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ViewPager vp;
	private List<News> list = new ArrayList<News>();
	private TextView tv;
	private LinearLayout ll;
	private View view;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			vp.setCurrentItem(vp.getCurrentItem()+1, true);
		};
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initList();
		inintListener();
		initDot();
	}

	// ��ʼ��������
	private void initDot() {
		for (int i = 0; i < list.size(); i++) {
			view = new View(this);
			LayoutParams params = new LayoutParams(8, 8);// Ϊ�ؼ����ÿ�߲���
			if (i != 0) {
				params.leftMargin = 5;// ���ò��ֲ�������߾�
			}
			view.setLayoutParams(params);// �����õĲ��ֲ���Ӧ�õ�view��
			view.setBackgroundResource(R.drawable.selector_dot);
			view.setEnabled(i == vp.getCurrentItem()%list.size());

			ll.addView(view);
		}
	}

	private void initList() {
		list.add(new News(R.drawable.a, "����������,�ҾͲ��ܵ���"));
		list.add(new News(R.drawable.b, "�����ֻ����ˣ��ڳ������ϸ������˺ϳ�"));
		list.add(new News(R.drawable.c, "�������ʵ�Ӱ���ھ���Ļ"));
		list.add(new News(R.drawable.d, "����TV���ʹ���"));
		list.add(new News(R.drawable.e, "��Ѫ��˿�ķ���"));

		vp.setAdapter(new MyPagerAdapter());
		
		int item = vp.getCurrentItem();// ��ȡ��ǰ������ʾ��Page��ViewPager�е�����(λ��)
		tv.setText(list.get(item%list.size()).getNewsTitle());
		
		//�����󻬶�Ч��
		int offset = Integer.MAX_VALUE/2%list.size();
		vp.setCurrentItem(Integer.MAX_VALUE/2-offset);
		
		//����һ����ʱ����ʵ���Զ�����Ч��
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				handler.sendEmptyMessage(0);
			}
		}, 5000,5000);

	}
	

	/**
	 * ����ViewPager��Adapter����ListView�кܴ�����Ƶ㣩
	 * @author Administrator
	 *
	 */
	class MyPagerAdapter extends PagerAdapter{


		/**
		 * ���ٳ���������Ŀ��viewʱ���õķ���,ViewPager�����ڴ��б���3����ͼ�Ļ���ؼ�(�Ѿ�������,���ڲ��ŵ�,��Ҫ�����),
		 * ��������(����3��)��,��������������������ͼ,������µ����ݵ�������.
		 * 
		 * container:��ǰ��PageView���� position:��ǰ�谡Ӵ���ٵڼ���page
		 * object:��ǰ��Ҫ���ٵ�view����
		 */
		@Override
		public void destroyItem(ViewGroup container, int position,
				Object object) {
			container.removeView((View) object);// һ������д������
		}

		/**
		 * ��Ŀ��Ҫ��ʾ��������ͼ,��Ҫ����������ͼ���ؼ���
		 * 
		 * container:��ǰ��ViewPager���� position:��ǰpager��ViewPager�е�λ��
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = View.inflate(MainActivity.this,
					R.layout.viewpager_item, null);

			ImageView iv = (ImageView) view.findViewById(R.id.iv_item);
			News news = list.get(position%list.size());
			iv.setImageResource(news.getSourceId());
			container.addView(view);
			return view;
		}

		/**
		 * ��ȡҪ��ʾ����Ŀ��(����),�����ڴ�����ౣ��3��(�Ѿ�������,���ڲ��ŵ�,��Ҫ�����),�����Ķ������destroy��������
		 * ViewPager�ڳ�ʼ��ʱ���ȵ������������ȡ��Ŀ��
		 */
		@Override
		public int getCount() {
			// TODO �Զ����ɵķ������
			return Integer.MAX_VALUE;
		}

		/**
		 * view:���ڻ���ȥ��view object����Ҫ�����view
		 * ���أ�true����ʾҪ����ȥ��view�ͽ�Ҫ�����view��ͬһ��view����ôʹ�û��棬���´���
		 * false����ʾҪ������view�ͽ�Ҫ�����view����ͬһ��view����ô�´���һ��view����
		 */
		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO �Զ����ɵķ������
			return view == object;// ֱ������д�Ϳ���(��Ҳ�ǹȸ���Ƽ�д��):����Ҫ�����
									// view�ͽ�Ҫ��������ͼ��ͬһ��(����һ�뷵����),����true,
									// ���򷵻�false(����һ�Ѳ�ֱ��������һ��)
		}
	}
	
	private void initView() {
		vp = (ViewPager) findViewById(R.id.vp);
		tv = (TextView) findViewById(R.id.tv);
		ll = (LinearLayout) findViewById(R.id.ll_dot);
	}

	@SuppressWarnings("deprecation")
	private void inintListener() {
		vp.setOnPageChangeListener(new OnPageChangeListener() {
			/**
			 * Page��ѡ�е�ʱ�����
			 * 
			 * ������position����ѡ�е�Page��ViewPager�е�����
			 */
			@Override
			public void onPageSelected(int position) {
				tv.setText(list.get(position%list
						.size()).getNewsTitle());
				ll.removeAllViews();
				initDot();
			}
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
			}
			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});

	}

}
