package com.example.paintboad;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ImageView iv;
	private CheckBox ck;
	private Paint paint;
	private Bitmap bitmap;
	private Canvas canvas;
	private Bitmap bm;
	private String fileName=null;
	private EditText text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		bm = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
		bitmap = Bitmap.createBitmap(bm.getWidth(),bm.getHeight(),bm.getConfig());
		canvas = new Canvas(bitmap);
		paint = new Paint();
		//����
		canvas.drawBitmap(bitmap, new Matrix(), paint);
		
		
		
		iv = (ImageView) findViewById(R.id.iv);
		ck = (CheckBox) findViewById(R.id.ck);
		
		iv.setOnTouchListener(new OnTouchListener() {
			private int startX;
			private int startY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX = (int) event.getX();
					startY = (int) event.getY();
					break;
				case MotionEvent.ACTION_MOVE:
					//��ȡ����ʱ������
					int x = (int) event.getX();
					int y = (int) event.getY();
					
					if(ck.isChecked()){
						canvas.drawPoint(x, y, paint);
					}else{
						canvas.drawLine(startX, startY, x, y , paint);
						startX=x;
						startY=y;
					}
					iv.setImageBitmap(bitmap);
					break;
				case MotionEvent.ACTION_UP:
					System.out.println("����뿪");
					break;

				}
				return true;
			}
		});
	}
	
	public void click(View v){
		if(ck.isChecked()){
			ck.setText("����");
		}else{
			ck.setText("ʵ��");
		}
	}
	
	public void save(View v){
		text = new EditText(this);
		new AlertDialog.Builder(this)
		.setTitle("�����ļ�")
		.setMessage("�������ļ���")
		.setView(text)
		.setPositiveButton("ȷ��", new MyListener())
		.show();

	}
	public void red(View v){
		paint.setColor(Color.RED);
	}
	public void brush(View v){
		paint.setStrokeWidth(5);
	}
	
	class MyListener implements OnClickListener{

		@Override
		public void onClick(DialogInterface dialog, int which) {
			fileName=text.getText().toString().trim();

			File file = null;
			if(!TextUtils.isEmpty(fileName))
			{
				file = new File("sdcard/"+fileName+".png");
			}else{
				file = new File("sdcard/default.png");
			}
			FileOutputStream stream;
			try {
				stream = new FileOutputStream(file);
				bitmap.compress(CompressFormat.PNG, 100, stream);
				
				//����SD�������㲥����SD������ý���ļ�������MediaStore���ݿ��н�������������ϵͳ�Ķ�ý��Ӧ�þͿ����ҵ����ļ�
				//��������ص��б���
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_MEDIA_MOUNTED);
				intent.setData(Uri.fromFile(Environment.getExternalStorageDirectory()));
				sendBroadcast(intent);
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			Toast.makeText(MainActivity.this, "����ɹ�", 0).show();
			
		}
		
	}
	
}
