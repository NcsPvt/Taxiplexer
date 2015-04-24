package itcurves.ncs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class Dialie_PIM {
	// private final String INTENT_MDT_SENDER = "mdt_sender";
	private final String INTENT_PIM_RECEIVER = "pim_receiver";
	// "INTENT_PIM_RECEIVER = "pim_receiver";"

	private final BroadcastReceiver mReceiverFromPIM = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equalsIgnoreCase(INTENT_PIM_RECEIVER)) {
				Bundle extra = intent.getExtras();
				String data = extra.getString("message");
				// list_data.add(data);
				// h.obtainMessage().sendToTarget();
			}
		}
	};
}
