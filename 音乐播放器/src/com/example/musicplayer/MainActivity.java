package com.example.musicplayer;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity {

	static Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Bundle data = msg.getData();
			int duration = data.getInt("duration");
			int currentPosition = data.getInt("currentPosition");

			sb.setProgress(currentPosition);
			sb.setMax(duration);
		};
	};
	Musicinterface mi;
	private Intent intent;
	private static SeekBar sb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sb = (SeekBar) findViewById(R.id.sb);
		sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				int progress = sb.getProgress();
				mi.seekTo(progress);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO �Զ����ɵķ������

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO �Զ����ɵķ������

			}
		});

		intent = new Intent(this, MusicServer.class);
		startService(intent);
		bindService(intent, new MyServConn(), BIND_AUTO_CREATE);

	}

	class MyServConn implements ServiceConnection {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mi = (Musicinterface) service;
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO �Զ����ɵķ������

		}

	}

	/**
	 * ��ʼ����
	 * 
	 * @param v
	 */
	public void start(View v) {
		mi.start();
	}

	/**
	 * ��ͣ����
	 * 
	 * @param v
	 */
	public void pause(View v) {
		mi.pause();
	}

	/**
	 * ֹͣ����
	 * 
	 * @param v
	 */
	public void stop(View v) {
		mi.stop();
	}

	public void continued(View v) {
		mi.continued();
	}

}
