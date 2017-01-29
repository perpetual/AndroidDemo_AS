package com.example.androiddemo.tools;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class PhoneStatusWatcher {

	private static boolean calling;
	private TelephonyManager tm;
	private PhoneStateListener phoneStateListener;

	private List<PhoneCallListener> phoneCallListeners = new LinkedList<PhoneCallListener>();

	public interface PhoneCallListener {
		public void onPhoneCall(int state);
	}

	public void addPhoneCallListener(PhoneCallListener listener) {
		phoneCallListeners.add(listener);
	}

	public void removePhoneCallListener(PhoneCallListener listener) {
		phoneCallListeners.remove(listener);
	}

	public void clearPhoneCallListener() {
		phoneCallListeners.clear();
	}

	public static boolean isCalling() {
		return calling;
	}

	public void begin(Context context) {
		if (tm == null) {
			tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		}

		if (phoneStateListener == null) {
			phoneStateListener = new PhoneStateListener() {

				@Override
				public void onCallStateChanged(int state, String incomingNumber) {

					for (PhoneCallListener listener : phoneCallListeners) {
						listener.onPhoneCall(state);
					}

					super.onCallStateChanged(state, incomingNumber);
					switch (state) {
					case TelephonyManager.CALL_STATE_IDLE:
						calling = false;
						break;
					case TelephonyManager.CALL_STATE_RINGING:
					case 3:
						// ����coolpad7230����֤��ͨʱ����Ӧ
					case TelephonyManager.CALL_STATE_OFFHOOK:
						calling = true;
						break;

					default:
						break;
					}
				}

			};
		}
		if(tm != null){
			tm.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
		}
	}

	public void end() {
		if (tm != null) {
			tm.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
			phoneStateListener = null;
		}
	}
}
