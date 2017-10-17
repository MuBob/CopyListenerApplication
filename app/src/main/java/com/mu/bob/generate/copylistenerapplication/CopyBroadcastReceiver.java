package com.mu.bob.generate.copylistenerapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Administrator on 2017/10/16.
 */

public class CopyBroadcastReceiver extends BroadcastReceiver {
    public static final String ACTION="com.mu.bob.generate.copylistenerapplication.copy.broadcast.action";
    private static final String TAG = "CopyReceiverTAG";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "CopyBroadcastReceiver.onReceive: action"+intent.getAction());
        if(ACTION.equals(intent.getAction())){
            CharSequence copyText = intent.getCharSequenceExtra("copyText");
            Log.i(TAG, "CopyBroadcastReceiver.onReceive: copyText="+copyText);
            DBManager.getInstance().insert(copyText);
        }
    }

}
