package itcurves.ncs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SystemsBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			String action = intent.getAction();

			if (action.equals("android.intent.action.BOOT_COMPLETED")) {
				Log.w(getClass().toString(), "BOOT COMPLETE RECIEVED IN CAB DISPATCH");
				Intent pushIntent = new Intent(context, TaxiPlexer.class);
				pushIntent.addCategory(Intent.CATEGORY_LAUNCHER);
				pushIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(pushIntent);

			} else if (action.equals("android.intent.action.ACTION_POWER_CONNECTED")) {
				Intent pushIntent = new Intent(context, TaxiPlexer.class);
				pushIntent.addCategory(Intent.CATEGORY_LAUNCHER);
				pushIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(pushIntent);
				AVL_Service.pref.edit().putBoolean("CHARGING", true).commit();
				for (IMessageListener list : AVL_Service.msg_listeners.values())
					list.receivedSystemBroadCast(action);
				Log.w(getClass().toString(), "ACTION_POWER_CONNECTED RECIEVED IN CAB DISPATCH");

			} else if (action.equals("android.intent.action.ACTION_POWER_DISCONNECTED")) {
				AVL_Service.pref.edit().putBoolean("CHARGING", false).commit();
				for (IMessageListener list : AVL_Service.msg_listeners.values())
					list.receivedSystemBroadCast(action);
				Log.w(getClass().toString(), "ACTION_POWER_DISCONNECTED RECIEVED IN CAB DISPATCH");

			} else if (action.equals("android.intent.action.ACTION_SHUTDOWN")) {
				for (IMessageListener list : AVL_Service.msg_listeners.values())
					list.receivedSystemBroadCast(action);
				Log.w(getClass().toString(), "ACTION_SHUTDOWN RECIEVED IN CAB DISPATCH");

			} else if (action.equals("android.intent.action.ACTION_REBOOT")) {
				for (IMessageListener list : AVL_Service.msg_listeners.values())
					list.receivedSystemBroadCast(action);
				Log.w(getClass().toString(), "ACTION_REBOOT RECIEVED IN CAB DISPATCH");
			}
		} catch (Exception e) {
			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.exception("[exception in SystemsBroadcastReceiver in systemsbroadcastreciever][SystemsBroadcastReceiver][" + e.getLocalizedMessage() + "]");
		}
	}// onReceive
}