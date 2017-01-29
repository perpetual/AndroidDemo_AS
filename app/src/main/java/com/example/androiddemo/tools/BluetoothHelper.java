package com.example.androiddemo.tools;

import java.util.Iterator;
import java.util.Set;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.text.TextUtils;

import com.example.androiddemo.BluetoothReceiver;
import com.example.androiddemo.model.OperationCode;
import com.example.androiddemo.receiver.BaseBroadcastReceiver;
import com.example.androiddemo.utils.AndroidDemoUtil;
import com.example.androiddemo.utils.LogUtil;
import com.example.androiddemo.utils.SystemServiceUtil;

/**
 * <pre>
 * Copyright (C) 1998-2015 TENCENT Inc.All Rights Reserved.
 * 
 * Description：外部使用蓝牙sco状态还是优先使用{@link AudioManager#ACTION_SCO_AUDIO_STATE_UPDATED}中相关的状态
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * garyzhao		2015-3-26		Create
 * </pre>
 */
public class BluetoothHelper extends CommonCallbacks implements
		BaseBroadcastReceiver.IBaseBroadcastReceiver {

	private static final String TAG = BluetoothHelper.class.getSimpleName();
	
	
	private static final ComponentName sComponentName = new ComponentName(BluetoothReceiver.class.getPackage().getName(), BluetoothReceiver.class.getName());
	
	private BluetoothReceiver mSCOAudioReceiver = null;
	private Context mContext = null;
	BluetoothProfile mBluetoothProxy = null; 
	private BluetoothProfile.ServiceListener mServiceListener = null;
	private boolean mSCOAudioStarted = false;
	
	public static boolean isConnectHeadset() {
		try {
			if (!isBluetoothCanUse()) {
				return false;
			}
			if (android.os.Build.VERSION.SDK_INT >= AndroidDemoUtil.API_LEVEL_14) {
				int connectionState = BluetoothAdapter.getDefaultAdapter()
						.getProfileConnectionState(BluetoothProfile.HEADSET);
				return BluetoothProfile.STATE_CONNECTED == connectionState;
			} else {
				return isBluetoothCanUse(getBluetoothAudioDeviceClass());
			}
		} catch (Exception e) {
		}
		return false;
	}

	public BluetoothHelper(Context context) {
		mContext = context;
		mSCOAudioReceiver = new BluetoothReceiver();

		/**
		 * 注册蓝牙耳机连接状态广播
		 */
		mSCOAudioReceiver.register(mContext, AndroidDemoUtil
				.createIntentFilter(BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED), this);
		/**
		 * 注册蓝牙A2DP协议连接状态广播
		 */
		mSCOAudioReceiver.register(mContext,
				AndroidDemoUtil.createIntentFilter(BluetoothHeadset.ACTION_AUDIO_STATE_CHANGED),
				this);
		/**
		 * 注册低级ACL连接状态广播
		 */
		mSCOAudioReceiver.register(mContext, AndroidDemoUtil.createIntentFilter(
				BluetoothDevice.ACTION_ACL_CONNECTED,
				BluetoothDevice.ACTION_ACL_DISCONNECTED), this);
		
		/**
		 * 在相对高版本的SDK中可以监听蓝牙耳机服务是否开启
		 * 注册上就会回调一次
		 */
		if (AndroidDemoUtil.isSDKVersionAtLeast(AndroidDemoUtil.API_LEVEL_11)) {
			mServiceListener = new BluetoothProfile.ServiceListener() {
				
				@Override
				public void onServiceDisconnected(int profile) {
					doCallbacks(OperationCode.OP_CODE_BLUETOOTH_SERVICE_CONNECTION_UPDATE, profile,
							0, AndroidDemoUtil.argumentsToString(profile, "onServiceConnected"), null);
				}
				
				@Override
				public void onServiceConnected(int profile, BluetoothProfile proxy) {
					mBluetoothProxy = proxy;
					doCallbacks(OperationCode.OP_CODE_BLUETOOTH_SERVICE_CONNECTION_UPDATE, profile, 0, "onServiceDisconnected", null);
				}
			};
			BluetoothAdapter.getDefaultAdapter().getProfileProxy(mContext, mServiceListener,
					BluetoothHeadset.HEADSET);
		}
	}

	public static String getHeadsetConnectState(int state) {
		String stateString = "";
		final int stateCode = state < 0 ? BluetoothAdapter.getDefaultAdapter().getProfileConnectionState(BluetoothProfile.HEADSET) : state;
		switch (BluetoothAdapter.getDefaultAdapter().getProfileConnectionState(BluetoothProfile.HEADSET)) {
		case BluetoothHeadset.STATE_CONNECTED:
			stateString = "STATE_CONNECTED";
			break;
		case BluetoothHeadset.STATE_CONNECTING:
			stateString = "STATE_CONNECTING";
			break;
		case BluetoothHeadset.STATE_DISCONNECTED:
			stateString = "STATE_DISCONNECTED";
			break;
		case BluetoothHeadset.STATE_DISCONNECTING:
			stateString = "STATE_DISCONNECTING";
			break;
		default:
			stateString = String.valueOf(stateCode);
			break;
		}
		return stateString;
	}
	

	public void release() {
		mSCOAudioReceiver.unregister(mContext);
		BluetoothAdapter.getDefaultAdapter().closeProfileProxy(BluetoothHeadset.HEADSET, mBluetoothProxy);
		removeAll();
	}
	
	public static String getA2DPConnectState(int audioState) {
		String state = "";
		switch (audioState) {
		case BluetoothHeadset.STATE_AUDIO_CONNECTED:
			state = "STATE_AUDIO_CONNECTED";
			break;
		case BluetoothHeadset.STATE_AUDIO_CONNECTING:
			state = "STATE_AUDIO_CONNECTING";
			break;
		case BluetoothHeadset.STATE_AUDIO_DISCONNECTED:
			state = "STATE_AUDIO_DISCONNECTED";
			break;
		default:
			state = String.valueOf(audioState);
			break;
		}
		return state;
	}
	
	public static String getScoAudioConnectionState(int scoAudioState) {
		String state = "";
		switch (scoAudioState) {
		case AudioManager.SCO_AUDIO_STATE_DISCONNECTED:
			state = "SCO_AUDIO_STATE_DISCONNECTED";
			break;
		case AudioManager.SCO_AUDIO_STATE_CONNECTING:
			state = "SCO_AUDIO_STATE_CONNECTING";
			break;
		case AudioManager.SCO_AUDIO_STATE_CONNECTED:
			state = AndroidDemoUtil.argumentsToString("SCO_AUDIO_STATE_CONNECTED", SystemServiceUtil.getAudioManager().isBluetoothA2dpOn());
			break;
		case AudioManager.SCO_AUDIO_STATE_ERROR:
			state = "SCO_AUDIO_STATE_ERROR";
			break;
		default:
			state = String.valueOf(scoAudioState);
			break;
		}
		return state;
	}
	
	public static String getBluetoothDeviceInfo(BluetoothDevice bd) {
		if (null == bd) {
			return "";
		}
		return AndroidDemoUtil.argumentsToString(bd.getName(), bd.getAddress(),
				"BondState", bd.getBondState(), "DeviceClass【", Integer.toHexString(bd.getBluetoothClass().getDeviceClass()),
				"】MajorDeviceClass【", Integer.toHexString(bd.getBluetoothClass().getMajorDeviceClass()),
				"】isBluetoothHeadset", isBluetoothHeadset(bd));
	}
	
	public static boolean isBluetoothHeadset(BluetoothDevice bluetoothDevice) {
		if (null == bluetoothDevice) {
			return false;
		}
		return bluetoothDevice.getBluetoothClass().getDeviceClass() == getBluetoothAudioDeviceClass();
	}

	public static int getBluetoothAudioDeviceClass() {
		return BluetoothClass.Device.Major.AUDIO_VIDEO;
	}
	
	private static boolean isBluetoothCanUse() {
		return isBluetoothCanUse(-1);
	}
		
	private static boolean isBluetoothCanUse(int bluetoothMajorDeviceClass) {
		BluetoothAdapter adp = BluetoothAdapter.getDefaultAdapter();
		if (adp == null) {
			LogUtil.d(TAG, "dkbt BluetoothAdapter.getDefaultAdapter() == null");
			return false;
		}
		if (!adp.isEnabled()) {
			LogUtil.d(TAG, "dkbt !adp.isEnabled()");
			return false;
		}
		Set<BluetoothDevice> setDev = adp.getBondedDevices();
		if (setDev == null || setDev.size() == 0) {
			LogUtil.d(TAG, "dkbt setDev == null || setDev.size() == 0");
			return false;
		}
		boolean hasBond = false;
		Iterator<BluetoothDevice> devs = setDev.iterator();
		for (Iterator<BluetoothDevice> it = devs; it.hasNext();) {
			BluetoothDevice dev = it.next();
			if (dev.getBondState() == BluetoothDevice.BOND_BONDED) {
				if (bluetoothMajorDeviceClass < 0) {
					hasBond = true;
					break;
				} else if (dev.getBluetoothClass().getMajorDeviceClass() == bluetoothMajorDeviceClass) {
					hasBond = true;
					break;
				}
			}
		}
		if (hasBond == false) {
			LogUtil.d(TAG, "dkbt hasBond == false");
			return false;
		}
		return true;
	}

	/**
	 * 蓝牙的打开底层有计数器
	 * @return
	 *
	 * @author garyzhao in 2015-3-30
	 */
	@TargetApi(8)
	public static boolean startBluetoothSCO() {

		AudioManager am = SystemServiceUtil.getAudioManager();

		if (Integer.valueOf(android.os.Build.VERSION.SDK) >= AndroidDemoUtil.API_LEVEL_8) {
			boolean isConnectHeadset = isConnectHeadset();
			boolean isBluetoothScoAvailableOffCall = am.isBluetoothScoAvailableOffCall();			
			boolean isCalling = PhoneStatusWatcher.isCalling();
			if (!isConnectHeadset || !isBluetoothScoAvailableOffCall || isCalling) {
				return false;
			}
			am.startBluetoothSco();
			am.setBluetoothScoOn(true);
			return true;
		}
		return false;
	}

	/**
	 * 蓝牙的关闭底层有计数器
	 * 
	 *
	 * @author garyzhao in 2015-3-30
	 */
	@TargetApi(8)
	public static void stopBluetoothSCO() {
		if (Integer.valueOf(android.os.Build.VERSION.SDK) >= AndroidDemoUtil.API_LEVEL_8) {

			AudioManager am = SystemServiceUtil.getAudioManager();
			if (!PhoneStatusWatcher.isCalling()) {
				am.unregisterMediaButtonEventReceiver(sComponentName);
				am.stopBluetoothSco();
			}
		}
	}
	
	@Override
	public void onReciveBroadcast(Context context, Intent intent) {
		if (TextUtils.equals(BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED, intent.getAction())) {
			doCallbacks(OperationCode.OP_CODE_ACTION_HEADSET_CONNECTION_STATE_UPDATE,
					intent.getIntExtra(BluetoothHeadset.EXTRA_STATE, -1), 0, intent.getAction(),
					null);
		} else if (TextUtils
				.equals(BluetoothHeadset.ACTION_AUDIO_STATE_CHANGED, intent.getAction())) {
			doCallbacks(OperationCode.OP_CODE_ACTION_A2DP_CONNECTION_STATE_UPDATE,
					intent.getIntExtra(BluetoothHeadset.EXTRA_STATE, -1), 0, intent.getAction(),
					intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE));
		} else if (TextUtils.equals(BluetoothDevice.ACTION_ACL_CONNECTED, intent.getAction())
				|| TextUtils.equals(BluetoothDevice.ACTION_ACL_DISCONNECTED, intent.getAction())) {
			doCallbacks(OperationCode.OP_CODE_ACL_CONNECTION_STATE_UPDATE, 0, 0,
					intent.getAction(), intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE));
		}
	}
}
