package com.skarim.music_player.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;
import android.preference.PreferenceManager;


public class CommonTasks {
	static Context context;

	public CommonTasks(Context context) {
		// TODO Auto-generated constructor stub
	}
	public static void showToastMessage(Context _context, String message) {
		Toast.makeText(_context, message, Toast.LENGTH_SHORT).show();
	}

	public static void logd (String msg) {
		Log.d(CommonConstraints.SKM, msg);
	}
	
	public static boolean isOnline(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}
	
	public static boolean isOnlineWithMessage(Context context){
		boolean isOnline = CommonTasks.isOnline(context);
		if (!isOnline) {
			CommonTasks.showToastMessage(context, "No internet connection.\nPlease check and try again. ");
			return false;
		} else {
			return true;
		}
	}
	
	public static void savePreferences(Context context, String key, String value) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public static String getPreferences(Context context, String prefKey) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		return sharedPreferences.getString(prefKey, "");
	}
	
	public static String getOnlydate(String date){		
		return date.substring(0, 10);
	}
	
	public static void setVibration(Context context){
		Vibrator vb = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        vb.vibrate(100);
	}

}
