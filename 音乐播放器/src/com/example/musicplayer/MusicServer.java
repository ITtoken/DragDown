package com.example.musicplayer;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;

public class MusicServer extends Service {

	private MediaPlayer mp;
	private Timer timer;

	@Override
	public IBinder onBind(Intent intent) {
		return new MidPlayer();
	}
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		mp = new MediaPlayer();
		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		mp.stop();
		if(mp!=null){
			mp.release();
			mp=null;
		}
		if(timer!=null){
			timer.cancel();
			timer=null;
		}
	}

	class MidPlayer extends Binder implements Musicinterface {

		@Override
		public void start() {
			MusicServer.this.start();
		}

		@Override
		public void pause() {
			MusicServer.this.pause();
			
		}

		@Override
		public void stop() {
			MusicServer.this.stop();
		}

		@Override
		public void continued() {
			MusicServer.this.continued();
			
		}

		@Override
		public void seekTo(int progress) {
			MusicServer.this.seekTo(progress);
		}


	}

	/**
	 * ��ʼ����
	 * 
	 * @param v
	 */
	public void start() {
		try {
			mp.reset();//MediaPlayer���󴴽���reset�Ǳ����һ����ÿ�ζ�Ҫִ�У�
			mp.setDataSource("sdcard/E-Type - Ding Ding Song.mp3");//�����ļ���·��
//			mp.prepare();//׼��������ִ�У�
//			mp.start();//��ʼ����
			mp.prepareAsync();//�첽׼��
			mp.setOnPreparedListener(new OnPreparedListener() {
				
				@Override
				public void onPrepared(MediaPlayer mp) {
					mp.start();//��ʼ����
					addTimer();
					
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

	/**
	 * ��ͣ����
	 * 
	 * @param v
	 */
	public void pause() {
			mp.pause();
	}

	/**
	 * ֹͣ����
	 * 
	 * @param v
	 */
	public void stop() {
		mp.stop();//ֹͣ����
	}

	public void continued() {
		mp.start();
	}
	
	public void seekTo(int progress){
		mp.seekTo(progress);
	}
	
	public void addTimer(){
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				int duration = mp.getDuration();//��ȡ���ŵ����ֵ���ʱ��
				int currentPosition = mp.getCurrentPosition();//��ȡ��ǰ����λ��
				
				Message msg = MainActivity.handler.obtainMessage();
				Bundle data = new Bundle();
				data.putInt("duration", duration);
				data.putInt("currentPosition", currentPosition);
				msg.setData(data);
				MainActivity.handler.sendMessage(msg);
			}
		}, 5, 500);
	}
}
