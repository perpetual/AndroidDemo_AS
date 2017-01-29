package com.example.androiddemo.activity;

import java.io.ByteArrayOutputStream;

import com.example.androiddemo.R;
import com.example.androiddemo.R.id;
import com.example.androiddemo.R.layout;
import com.example.androiddemo.R.string;
import com.example.androiddemo.utils.AndroidDemoUtil;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class SurfaceViewActivity extends Activity {

	private boolean mIsPreview = false;
	private byte[] mPreviewBuffer = new byte[1024 * 1024 * 16];
	private PreviewCallback mPreviewCallback = null;
	private Bitmap mViewBitmap = null;
	private Object mBitmapLock = new Object();
	private boolean mIsBitmapChange = true;
	private int mViewWidth = 0;
	private int mViewHeight = 0;
	private WindowManager mWindowManager = null;
	private Thread mPaintThread = null;
	private Paint mPaint = new Paint();

	private SurfaceView mSurfaceView = null;
	private SurfaceView mBigView = null;
	private Camera mCamera = null;
	private Button mStartBtn = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initData();
		initUI();
		bindUI();
	}

	/**
	 * ˽�к���
	 */
	private void initData() {

		mPreviewCallback = new PreviewCallback() {

			@Override
			public void onPreviewFrame(byte[] data, Camera camera) {
				synchronized (mBitmapLock) {
					decodeDataToBitmap(data);
					if (null == mViewBitmap) {
						return;
					}
					mIsBitmapChange = true;
					Log.d("xxx", "onPreviewFrame:" + data.length + ":" + mViewBitmap);
				}
			}
		};
		
//		mViewBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lines);

		mPaintThread = new Thread() {
			@Override
			public void run() {
				while (true) {
					drawBitmap();
					try {
						sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};

		mPaint.setColor(Color.BLACK);
		mPaint.setFilterBitmap(false);
	}

	private void initUI() {
		Window window = getWindow();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		Point point = new Point();
		// mWindowManager = getWindowManager();
		// mWindowManager.getDefaultDisplay().getSize(point);
		// mViewWidth = point.x;
		// mViewHeight = point.y;
		setContentView(R.layout.surface_layout);
	}

	private void bindUI() {
		mSurfaceView = (SurfaceView) findViewById(R.id.surfaceview);
		// mSurfaceView.getHolder().setFixedSize(480, 800);
		mSurfaceView.getHolder().setType(
				SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				if (null != mCamera) {
					if (mIsPreview) {
						mCamera.stopPreview();
					}
					mCamera.release();
					mCamera = null;
				}
			}
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				try {
					mCamera = Camera.open();
					WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
					Display display = wm.getDefaultDisplay();
					Camera.Parameters params = mCamera.getParameters();
					params.setPreviewSize(display.getWidth(),
							display.getHeight());
					params.setPreviewFrameRate(3);
					params.setPictureFormat(PixelFormat.JPEG);
					params.setJpegQuality(85);
					params.setPictureSize(display.getWidth(),
							display.getHeight());
					mCamera.setParameters(params);
					mCamera.setPreviewDisplay(mSurfaceView.getHolder());
					mCamera.addCallbackBuffer(mPreviewBuffer);
				} catch (Exception e) {
				}
			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
				AndroidDemoUtil.showToast("xxx");
			}
		});

		mBigView = (SurfaceView) findViewById(R.id.bigview);

		mStartBtn = (Button) findViewById(R.id.start);
		mStartBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mIsPreview) {
					mCamera.stopPreview();
					mCamera.setPreviewCallback(null);
					mStartBtn.setText(R.string.start);
				} else {
					mCamera.startPreview();
					mStartBtn.setText(R.string.stop);
					mCamera.setPreviewCallback(mPreviewCallback);
				}
				mIsPreview = !mIsPreview;
			}
		});
		mPaintThread.start();
	}

	private void drawBitmap() {
		synchronized (mBitmapLock) {
			Log.d("xxx", "drawBitmap1:" + mIsBitmapChange + ":" + mViewBitmap);
			Canvas canvas = mBigView.getHolder().lockCanvas(null);
			if (null == canvas) {
				return;
			}
			if (!mIsBitmapChange || null == mViewBitmap) {
				mBigView.getHolder().unlockCanvasAndPost(canvas);
				return;
			}

			Matrix matrix = new Matrix();
			int w = canvas.getWidth();
			int h = canvas.getHeight();
			matrix.postScale((float)canvas.getWidth() / mViewBitmap.getHeight(), (float)canvas.getHeight() / mViewBitmap.getWidth());
			canvas.drawBitmap(mViewBitmap, matrix, mPaint);
			mIsBitmapChange = false;
			mBigView.getHolder().unlockCanvasAndPost(canvas);
			Log.d("xxx", "drawBitmap2:" + w + ":" + h);
		}

	}
	
	private void decodeDataToBitmap(byte[] data) {
		Size size = mCamera.getParameters().getPreviewSize();
		YuvImage image = new YuvImage(data, ImageFormat.NV21, size.width, size.height, null);
		if (null == image) {
			return;
		}
		try {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			Bitmap bitmap = null;
			image.compressToJpeg(new Rect(0, 0, size.width, size.height), 80, stream);
			mViewBitmap = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());
			stream.close();
		} catch (Exception e) {
			
		}
	}
}
